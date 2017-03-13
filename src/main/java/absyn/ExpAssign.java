package absyn;

import env.Env;
import javaslang.collection.Tree;
import parse.Loc;
import types.BOOL;
import types.Type;
import static semantic.SemanticHelper.typeMismatch;

public abstract class ExpAssign extends AST {

   public ExpAssign(Loc loc, Var var, Exp exp) {
      super(loc);
      this.var = var;
      this.exp = exp;
   }

   // Type of the expression, calculated by the semantic analyser
   public final Var var;
   public final Exp exp;

   @Override
   public Tree.Node<String> toTree() {
      return Tree.of(annotateType("ExpAssign"),
              var.toTree(),
              exp.toTree()
              );
   }

   // Do semantic analysis of the expression
   public Type semantic(Env env) {
      if (var.semantic(env).is(exp.semantic(env))) {
         return var.semantic(env);
      }else{
         throw typeMismatch(var.loc,var.type,exp.type,var.type);
      }
   }

}
