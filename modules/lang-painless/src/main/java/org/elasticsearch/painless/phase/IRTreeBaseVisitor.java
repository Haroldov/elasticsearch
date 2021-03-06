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

package org.elasticsearch.painless.phase;

import org.elasticsearch.painless.ir.BinaryMathNode;
import org.elasticsearch.painless.ir.BlockNode;
import org.elasticsearch.painless.ir.BooleanNode;
import org.elasticsearch.painless.ir.BreakNode;
import org.elasticsearch.painless.ir.CastNode;
import org.elasticsearch.painless.ir.CatchNode;
import org.elasticsearch.painless.ir.ClassNode;
import org.elasticsearch.painless.ir.ComparisonNode;
import org.elasticsearch.painless.ir.ConditionalNode;
import org.elasticsearch.painless.ir.ConstantNode;
import org.elasticsearch.painless.ir.ContinueNode;
import org.elasticsearch.painless.ir.DeclarationBlockNode;
import org.elasticsearch.painless.ir.DeclarationNode;
import org.elasticsearch.painless.ir.DefInterfaceReferenceNode;
import org.elasticsearch.painless.ir.DoWhileLoopNode;
import org.elasticsearch.painless.ir.DupNode;
import org.elasticsearch.painless.ir.ElvisNode;
import org.elasticsearch.painless.ir.FieldNode;
import org.elasticsearch.painless.ir.FlipArrayIndexNode;
import org.elasticsearch.painless.ir.FlipCollectionIndexNode;
import org.elasticsearch.painless.ir.FlipDefIndexNode;
import org.elasticsearch.painless.ir.ForEachLoopNode;
import org.elasticsearch.painless.ir.ForEachSubArrayNode;
import org.elasticsearch.painless.ir.ForEachSubIterableNode;
import org.elasticsearch.painless.ir.ForLoopNode;
import org.elasticsearch.painless.ir.FunctionNode;
import org.elasticsearch.painless.ir.IfElseNode;
import org.elasticsearch.painless.ir.IfNode;
import org.elasticsearch.painless.ir.InstanceofNode;
import org.elasticsearch.painless.ir.InvokeCallDefNode;
import org.elasticsearch.painless.ir.InvokeCallMemberNode;
import org.elasticsearch.painless.ir.InvokeCallNode;
import org.elasticsearch.painless.ir.ListInitializationNode;
import org.elasticsearch.painless.ir.LoadBraceDefNode;
import org.elasticsearch.painless.ir.LoadBraceNode;
import org.elasticsearch.painless.ir.LoadDotArrayLengthNode;
import org.elasticsearch.painless.ir.LoadDotDefNode;
import org.elasticsearch.painless.ir.LoadDotNode;
import org.elasticsearch.painless.ir.LoadDotShortcutNode;
import org.elasticsearch.painless.ir.LoadFieldMemberNode;
import org.elasticsearch.painless.ir.LoadListShortcutNode;
import org.elasticsearch.painless.ir.LoadMapShortcutNode;
import org.elasticsearch.painless.ir.LoadVariableNode;
import org.elasticsearch.painless.ir.MapInitializationNode;
import org.elasticsearch.painless.ir.NewArrayNode;
import org.elasticsearch.painless.ir.NewObjectNode;
import org.elasticsearch.painless.ir.NullNode;
import org.elasticsearch.painless.ir.NullSafeSubNode;
import org.elasticsearch.painless.ir.ReturnNode;
import org.elasticsearch.painless.ir.StatementExpressionNode;
import org.elasticsearch.painless.ir.StaticNode;
import org.elasticsearch.painless.ir.StoreBraceDefNode;
import org.elasticsearch.painless.ir.StoreBraceNode;
import org.elasticsearch.painless.ir.StoreDotDefNode;
import org.elasticsearch.painless.ir.StoreDotNode;
import org.elasticsearch.painless.ir.StoreDotShortcutNode;
import org.elasticsearch.painless.ir.StoreFieldMemberNode;
import org.elasticsearch.painless.ir.StoreListShortcutNode;
import org.elasticsearch.painless.ir.StoreMapShortcutNode;
import org.elasticsearch.painless.ir.ThrowNode;
import org.elasticsearch.painless.ir.TryNode;
import org.elasticsearch.painless.ir.TypedCaptureReferenceNode;
import org.elasticsearch.painless.ir.TypedInterfaceReferenceNode;
import org.elasticsearch.painless.ir.UnaryMathNode;
import org.elasticsearch.painless.ir.WhileLoopNode;

public class IRTreeBaseVisitor<Input, Output> implements IRTreeVisitor<Input, Output> {

    @Override
    public Output visitClass(ClassNode irClassNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitFunction(FunctionNode irFunctionNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitField(FieldNode irFieldNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitBlock(BlockNode irBlockNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitIf(IfNode irIfNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitIfElse(IfElseNode irIfElseNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitWhileLoop(WhileLoopNode irWhileLoopNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitDoWhileLoop(DoWhileLoopNode irDoWhileLoopNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitForLoop(ForLoopNode irForLoopNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitForEachLoop(ForEachLoopNode irForEachLoopNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitForEachSubArrayLoop(ForEachSubArrayNode irForEachSubArrayNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitForEachSubIterableLoop(ForEachSubIterableNode irForEachSubIterableNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitDeclarationBlock(DeclarationBlockNode irDeclarationBlockNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitDeclaration(DeclarationNode irDeclarationNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitReturn(ReturnNode irReturnNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitStatementExpression(StatementExpressionNode irStatementExpressionNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitTry(TryNode irTryNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitCatch(CatchNode irCatchNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitThrow(ThrowNode irThrowNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitContinue(ContinueNode irContinueNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitBreak(BreakNode irBreakNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitUnaryMath(UnaryMathNode irUnaryMathNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitBinaryMath(BinaryMathNode irBinaryMathNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitBoolean(BooleanNode irBoolNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitComparison(ComparisonNode irComparisonNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitCast(CastNode irCastNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitInstanceof(InstanceofNode irInstanceofNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitConditional(ConditionalNode irConditionalNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitElvis(ElvisNode irElvisNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitListInitialization(ListInitializationNode irListInitializationNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitMapInitialization(MapInitializationNode irMapInitializationNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitNewArray(NewArrayNode irNewArrayNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitNewObject(NewObjectNode irNewObjectNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitConstant(ConstantNode irConstantNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitNull(NullNode irNullNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitDefInterfaceReference(DefInterfaceReferenceNode irDefInterfaceReferenceNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitTypedInterfaceReference(TypedInterfaceReferenceNode irTypedInterfaceReferenceNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitTypeCaptureReference(TypedCaptureReferenceNode irTypedCaptureReferenceNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitStatic(StaticNode irStaticNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitLoadVariable(LoadVariableNode irLoadVariableNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitNullSafeSub(NullSafeSubNode irNullSafeSubNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitLoadDotArrayLengthNode(LoadDotArrayLengthNode irLoadDotArrayLengthNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitLoadDotDef(LoadDotDefNode irLoadDotDefNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitLoadDot(LoadDotNode irLoadDotNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitLoadDotShortcut(LoadDotShortcutNode irDotSubShortcutNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitLoadListShortcut(LoadListShortcutNode irLoadListShortcutNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitLoadMapShortcut(LoadMapShortcutNode irLoadMapShortcutNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitLoadFieldMember(LoadFieldMemberNode irLoadFieldMemberNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitLoadBraceDef(LoadBraceDefNode irLoadBraceDefNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitLoadBrace(LoadBraceNode irLoadBraceNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitStoreDotDef(StoreDotDefNode irStoreDotDefNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitStoreDot(StoreDotNode irStoreDotNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitStoreDotShortcut(StoreDotShortcutNode irDotSubShortcutNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitStoreListShortcut(StoreListShortcutNode irStoreListShortcutNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitStoreMapShortcut(StoreMapShortcutNode irStoreMapShortcutNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitStoreFieldMember(StoreFieldMemberNode irStoreFieldMemberNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitStoreBraceDef(StoreBraceDefNode irStoreBraceDefNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitStoreBrace(StoreBraceNode irStoreBraceNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitInvokeCallDef(InvokeCallDefNode irInvokeCallDefNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitInvokeCall(InvokeCallNode irInvokeCallNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitInvokeCallMember(InvokeCallMemberNode irInvokeCallMemberNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitFlipArrayIndex(FlipArrayIndexNode irFlipArrayIndexNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitFlipCollectionIndex(FlipCollectionIndexNode irFlipCollectionIndexNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitFlipDefIndex(FlipDefIndexNode irFlipDefIndexNode, Input input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output visitDup(DupNode dupNode, Input input) {
        throw new UnsupportedOperationException();
    }
}
