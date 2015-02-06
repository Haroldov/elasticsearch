/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */
package org.elasticsearch.alerts.actions.email;

import org.elasticsearch.alerts.Alert;
import org.elasticsearch.alerts.actions.Action;
import org.elasticsearch.alerts.actions.ActionException;
import org.elasticsearch.alerts.support.StringTemplateUtils;
import org.elasticsearch.common.ParseField;
import org.elasticsearch.common.component.AbstractComponent;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.internal.Nullable;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.script.ScriptService;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.*;

/**
 */
public class EmailAction extends Action<EmailAction.Result> {

    public static final String TYPE = "email";

    private final List<Address> emailAddresses;

    //Optional, can be null, will use defaults from emailSettings (EmailServiceConfig)
    private final String fromAddress;
    private final StringTemplateUtils.Template subjectTemplate;
    private final StringTemplateUtils.Template messageTemplate;

    private static final StringTemplateUtils.Template DEFAULT_SUBJECT_TEMPLATE = new StringTemplateUtils.Template(
            "Elasticsearch Alert {{alert_name}} triggered", null, "mustache", ScriptService.ScriptType.INLINE);

    private static final StringTemplateUtils.Template DEFAULT_MESSAGE_TEMPLATE = new StringTemplateUtils.Template(
            "{{alert_name}} triggered with {{response.hits.total}} results", null, "mustache", ScriptService.ScriptType.INLINE);


    private final StringTemplateUtils templateUtils;
    private final EmailSettingsService emailSettingsService;

    protected EmailAction(ESLogger logger, EmailSettingsService emailSettingsService,
                          StringTemplateUtils templateUtils, @Nullable StringTemplateUtils.Template subjectTemplate,
                          @Nullable StringTemplateUtils.Template messageTemplate, @Nullable String fromAddress,
                          List<Address> emailAddresses) {
        super(logger);

        this.templateUtils = templateUtils;
        this.emailSettingsService = emailSettingsService;

        this.emailAddresses = new ArrayList<>();
        this.emailAddresses.addAll(emailAddresses);
        this.subjectTemplate = subjectTemplate;
        this.messageTemplate = messageTemplate;
        this.fromAddress = fromAddress;
    }

    @Override
    public String type() {
        return TYPE;
    }

    @Override
    public Result execute(Alert alert, Map<String, Object> data) throws IOException {

        final EmailSettingsService.EmailServiceConfig emailSettings = emailSettingsService.emailServiceConfig();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", emailSettings.host());
        props.put("mail.smtp.port", emailSettings.port());
        final Session session;

        if (emailSettings.password() != null) {
            final String username;
            if (emailSettings.username() != null) {
                username = emailSettings.username();
            } else {
                username = emailSettings.defaultFromAddress();
            }

            if (username == null) {
                throw new ActionException("unable to send email for alert [" + alert.name() + "]. username or the default from address is not set");
            }

            session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, emailSettings.password());
                        }
                    });
        } else {
            session = Session.getDefaultInstance(props);
        }

        try {
            Message email = new MimeMessage(session);

            String fromAddressToUse = emailSettings.defaultFromAddress();
            if (fromAddress != null) {
                fromAddressToUse = fromAddress;
            }

            email.setFrom(new InternetAddress(fromAddressToUse));

            email.setRecipients(Message.RecipientType.TO, emailAddresses.toArray(new Address[1]));

            Map<String, Object> alertParams = new HashMap<>();
            alertParams.put(Action.ALERT_NAME_VARIABLE_NAME, alert.name());
            alertParams.put(RESPONSE_VARIABLE_NAME, data);


            String subject = templateUtils.executeTemplate(
                    subjectTemplate != null ? subjectTemplate : DEFAULT_SUBJECT_TEMPLATE,
                    alertParams);
            email.setSubject(subject);

            String message = templateUtils.executeTemplate(
                    messageTemplate != null ? messageTemplate : DEFAULT_MESSAGE_TEMPLATE,
                    alertParams);
            email.setText(message);

            Transport.send(email);

            return new Result(true, fromAddressToUse, emailAddresses, subject, message  );
        } catch (Throwable e) {
            throw new ActionException("failed to send mail for alert [" + alert.name() + "]", e);
        }

    }

    @Override
    public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
        builder.startObject();
        builder.field(Parser.ADDRESSES_FIELD.getPreferredName());
        builder.startArray();
        for (Address emailAddress : emailAddresses){
            builder.value(emailAddress.toString());
        }
        builder.endArray();

        if (subjectTemplate != null) {
            StringTemplateUtils.writeTemplate(Parser.SUBJECT_TEMPLATE_FIELD.getPreferredName(), subjectTemplate, builder, params);
        }

        if (messageTemplate != null) {
            StringTemplateUtils.writeTemplate(Parser.MESSAGE_TEMPLATE_FIELD.getPreferredName(), messageTemplate, builder, params);
        }

        if (fromAddress != null) {
            builder.field(Parser.FROM_FIELD.getPreferredName(), fromAddress);
        }

        builder.endObject();
        return builder;

    }

    public static class Parser extends AbstractComponent implements Action.Parser<EmailAction> {

        public static final ParseField FROM_FIELD = new ParseField("from");
        public static final ParseField ADDRESSES_FIELD = new ParseField("addresses");
        public static final ParseField MESSAGE_TEMPLATE_FIELD = new ParseField("message_template");
        public static final ParseField SUBJECT_TEMPLATE_FIELD = new ParseField("subject_template");

        private final StringTemplateUtils templateUtils;
        private final EmailSettingsService emailSettingsService;

        @Inject
        public Parser(Settings settings, EmailSettingsService emailSettingsService, StringTemplateUtils templateUtils) {
            super(settings);
            this.templateUtils = templateUtils;
            this.emailSettingsService = emailSettingsService;
        }

        @Override
        public String type() {
            return TYPE;
        }

        @Override
        public EmailAction parse(XContentParser parser) throws IOException {
            StringTemplateUtils.Template subjectTemplate = null;
            StringTemplateUtils.Template messageTemplate = null;
            String fromAddress = null;

            List<Address> addresses = new ArrayList<>();

            String currentFieldName = null;
            XContentParser.Token token;
            while ((token = parser.nextToken()) != XContentParser.Token.END_OBJECT) {
                if (token == XContentParser.Token.FIELD_NAME) {
                    currentFieldName = parser.currentName();
                } else if (token.isValue()) {
                    if (SUBJECT_TEMPLATE_FIELD.match(currentFieldName)) {
                        subjectTemplate = StringTemplateUtils.readTemplate(parser);
                    } else if (MESSAGE_TEMPLATE_FIELD.match(currentFieldName)) {
                        messageTemplate = StringTemplateUtils.readTemplate(parser);
                    } else if (FROM_FIELD.match(currentFieldName)) {
                        fromAddress = parser.text();
                    } else {
                        throw new ActionException("could not parse email action. unexpected field [" + currentFieldName + "]");
                    }
                } else if (token == XContentParser.Token.START_ARRAY) {
                    if (ADDRESSES_FIELD.match(currentFieldName)) {
                        while (parser.nextToken() != XContentParser.Token.END_ARRAY) {
                            try {
                                addresses.add(InternetAddress.parse(parser.text())[0]);
                            } catch (AddressException ae) {
                                throw new ActionException("could not parse email action. unable to parse [" + parser.text() + "] as an email address", ae);
                            }
                        }
                    } else {
                        throw new ActionException("could not parse email action. unexpected field [" + currentFieldName + "]");
                    }
                } else {
                    throw new ActionException("could not parse email action. unexpected token [" + token + "]");
                }
            }

            if (addresses.isEmpty()) {
                throw new ActionException("could not parse email action. [addresses] was not found or was empty");
            }

            return new EmailAction(logger, emailSettingsService, templateUtils, subjectTemplate,
                    messageTemplate, fromAddress, addresses);
        }
    }


    public static class Result extends Action.Result {

        private final String from;
        private final List<Address> recipients;
        private final String subject;
        private final String message;

        public Result(boolean success, String from, List<Address> recipients, String subject, String message) {
            super(TYPE, success);
            this.from = from;
            this.recipients = recipients;
            this.subject = subject;
            this.message = message;
        }

        @Override
        public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
            builder.startObject();
            builder.field("fromAddress", from());
            builder.field("subject", subject());
            builder.array("to", recipients());
            builder.field("message", message());
            builder.field("success", success());
            builder.endObject();
            return builder;
        }

        public String from() {
            return from;
        }

        public String subject() {
            return subject;
        }

        public String message() {
            return message;
        }

        public List<Address> recipients() {
            return recipients;
        }

    }
}
