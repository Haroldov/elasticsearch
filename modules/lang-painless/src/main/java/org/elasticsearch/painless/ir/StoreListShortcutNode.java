/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.painless.ir;

import org.elasticsearch.painless.ClassWriter;
import org.elasticsearch.painless.MethodWriter;
import org.elasticsearch.painless.lookup.PainlessMethod;
import org.elasticsearch.painless.phase.IRTreeVisitor;
import org.elasticsearch.painless.symbol.WriteScope;

public class StoreListShortcutNode extends StoreAccessNode {

    /* ---- begin node data ---- */

    private PainlessMethod setter;

    public void setSetter(PainlessMethod setter) {
        this.setter = setter;
    }

    public PainlessMethod getSetter() {
        return setter;
    }

    /* ---- end node data, begin visitor ---- */

    @Override
    public <Input, Output> Output visit(IRTreeVisitor<Input, Output> irTreeVisitor, Input input) {
        return irTreeVisitor.visitStoreListShortcut(this, input);
    }

    /* ---- end visitor ---- */

    @Override
    protected void write(ClassWriter classWriter, MethodWriter methodWriter, WriteScope writeScope) {
        getAccessNode().write(classWriter, methodWriter, writeScope);
        getChildNode().write(classWriter, methodWriter, writeScope);

        methodWriter.writeDebugInfo(location);
        methodWriter.invokeMethodCall(setter);
        methodWriter.writePop(MethodWriter.getType(setter.returnType).getSize());
    }
}
