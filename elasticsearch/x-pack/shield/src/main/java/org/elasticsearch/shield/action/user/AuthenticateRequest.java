/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */
package org.elasticsearch.shield.action.user;

import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.ActionRequestValidationException;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.shield.support.Validation;

import java.io.IOException;

import static org.elasticsearch.action.ValidateActions.addValidationError;

/**
 *
 */
public class AuthenticateRequest extends ActionRequest<AuthenticateRequest> implements UserRequest {

    private String username;

    public AuthenticateRequest() {}

    public AuthenticateRequest(String username) {
        this.username = username;
    }

    @Override
    public ActionRequestValidationException validate() {
        Validation.Error error = Validation.Users.validateUsername(username);
        if (error != null) {
            return addValidationError(error.toString(), null);
        }
        return null;
    }

    public String username() {
        return username;
    }

    public void username(String username) {
        this.username = username;
    }

    @Override
    public String[] usernames() {
        return new String[] { username };
    }

    @Override
    public void readFrom(StreamInput in) throws IOException {
        super.readFrom(in);
        username = in.readString();
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        out.writeString(username);
    }
}
