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

public interface IRTreeVisitor<Input, Output> {

    Output visitClass(ClassNode irClassNode, Input input);
    Output visitFunction(FunctionNode irFunctionNode, Input input);
    Output visitField(FieldNode irFieldNode, Input input);

    Output visitBlock(BlockNode irBlockNode, Input input);
    Output visitIf(IfNode irIfNode, Input input);
    Output visitIfElse(IfElseNode irIfElseNode, Input input);
    Output visitWhileLoop(WhileLoopNode irWhileLoopNode, Input input);
    Output visitDoWhileLoop(DoWhileLoopNode irDoWhileLoopNode, Input input);
    Output visitForLoop(ForLoopNode irForLoopNode, Input input);
    Output visitForEachLoop(ForEachLoopNode irForEachLoopNode, Input input);
    Output visitForEachSubArrayLoop(ForEachSubArrayNode irForEachSubArrayNode, Input input);
    Output visitForEachSubIterableLoop(ForEachSubIterableNode irForEachSubIterableNode, Input input);
    Output visitDeclarationBlock(DeclarationBlockNode irDeclarationBlockNode, Input input);
    Output visitDeclaration(DeclarationNode irDeclarationNode, Input input);
    Output visitReturn(ReturnNode irReturnNode, Input input);
    Output visitStatementExpression(StatementExpressionNode irStatementExpressionNode, Input input);
    Output visitTry(TryNode irTryNode, Input input);
    Output visitCatch(CatchNode irCatchNode, Input input);
    Output visitThrow(ThrowNode irThrowNode, Input input);
    Output visitContinue(ContinueNode irContinueNode, Input input);
    Output visitBreak(BreakNode irBreakNode, Input input);

    Output visitUnaryMath(UnaryMathNode irUnaryMathNode, Input input);
    Output visitBinaryMath(BinaryMathNode irBinaryMathNode, Input input);
    Output visitBoolean(BooleanNode irBoolNode, Input input);
    Output visitComparison(ComparisonNode irComparisonNode, Input input);
    Output visitCast(CastNode irCastNode, Input input);
    Output visitInstanceof(InstanceofNode irInstanceofNode, Input input);
    Output visitConditional(ConditionalNode irConditionalNode, Input input);
    Output visitElvis(ElvisNode irElvisNode, Input input);
    Output visitListInitialization(ListInitializationNode irListInitializationNode, Input input);
    Output visitMapInitialization(MapInitializationNode irMapInitializationNode, Input input);
    Output visitNewArray(NewArrayNode irNewArrayNode, Input input);
    Output visitNewObject(NewObjectNode irNewObjectNode, Input input);
    Output visitConstant(ConstantNode irConstantNode, Input input);
    Output visitNull(NullNode irNullNode, Input input);
    Output visitDefInterfaceReference(DefInterfaceReferenceNode irDefInterfaceReferenceNode, Input input);
    Output visitTypedInterfaceReference(TypedInterfaceReferenceNode irTypedInterfaceReferenceNode, Input input);
    Output visitTypeCaptureReference(TypedCaptureReferenceNode irTypedCaptureReferenceNode, Input input);
    Output visitStatic(StaticNode irStaticNode, Input input);
    Output visitLoadVariable(LoadVariableNode irLoadVariableNode, Input input);
    Output visitNullSafeSub(NullSafeSubNode irNullSafeSubNode, Input input);
    Output visitLoadDotArrayLengthNode(LoadDotArrayLengthNode irLoadDotArrayLengthNode, Input input);
    Output visitLoadDotDef(LoadDotDefNode irLoadDotDefNode, Input input);
    Output visitLoadDot(LoadDotNode irLoadDotNode, Input input);
    Output visitLoadDotShortcut(LoadDotShortcutNode irDotSubShortcutNode, Input input);
    Output visitLoadListShortcut(LoadListShortcutNode irLoadListShortcutNode, Input input);
    Output visitLoadMapShortcut(LoadMapShortcutNode irLoadMapShortcutNode, Input input);
    Output visitLoadFieldMember(LoadFieldMemberNode irLoadFieldMemberNode, Input input);
    Output visitLoadBraceDef(LoadBraceDefNode irLoadBraceDefNode, Input input);
    Output visitLoadBrace(LoadBraceNode irLoadBraceNode, Input input);
    Output visitStoreDotDef(StoreDotDefNode irStoreDotDefNode, Input input);
    Output visitStoreDot(StoreDotNode irStoreDotNode, Input input);
    Output visitStoreDotShortcut(StoreDotShortcutNode irDotSubShortcutNode, Input input);
    Output visitStoreListShortcut(StoreListShortcutNode irStoreListShortcutNode, Input input);
    Output visitStoreMapShortcut(StoreMapShortcutNode irStoreMapShortcutNode, Input input);
    Output visitStoreFieldMember(StoreFieldMemberNode irStoreFieldMemberNode, Input input);
    Output visitStoreBraceDef(StoreBraceDefNode irStoreBraceDefNode, Input input);
    Output visitStoreBrace(StoreBraceNode irStoreBraceNode, Input input);
    Output visitInvokeCallDef(InvokeCallDefNode irInvokeCallDefNode, Input input);
    Output visitInvokeCall(InvokeCallNode irInvokeCallNode, Input input);
    Output visitInvokeCallMember(InvokeCallMemberNode irInvokeCallMemberNode, Input input);
    Output visitFlipArrayIndex(FlipArrayIndexNode irFlipArrayIndexNode, Input input);
    Output visitFlipCollectionIndex(FlipCollectionIndexNode irFlipCollectionIndexNode, Input input);
    Output visitFlipDefIndex(FlipDefIndexNode irFlipDefIndexNode, Input input);
    Output visitDup(DupNode dupNode, Input input);
}
