package absyn;

import env.Env;
import javaslang.collection.List;
import javaslang.collection.Tree;
import org.apache.commons.lang3.ObjectUtils;
import parse.Loc;
import types.BOOL;
import types.Type;
import types.UNIT;

import static semantic.SemanticHelper.typeMismatch;

public class ExpIf extends Exp {

   public final Exp expA;
   public final Exp expB;
   public final Exp expOpt;

   public ExpIf(Loc loc, Exp expA, Exp expB, Exp expOpt) {
      super(loc);
      this.expA = expA;
      this.expB = expB;
      this.expOpt = expOpt;
   }

   public ExpIf(Loc loc, Exp expA, Exp expB) {
      super(loc);
      this.expA = expA;
      this.expB = expB;
      this.expOpt = null;
   }

   @Override
   public Tree.Node<String> toTree() {
      if (expOp == null ){
         return Tree.of(annotateType("ExpIf"),
                 expA.toTree(),
                 expB.toTree());
      }else{
         return Tree.of(annotateType("ExpIf"),
                 expA.toTree(),
                 expB.toTree(),
                 expOpt.toTree());
      }
   }

   @Override
   protected Type semantic_(Env env) {

      Type test = expA.semantic(env);

      if (!test.is(BOOL.T) ){
         throw typeMismatch(expA.loc,expA.type,BOOL.T);
      }

      Type testB = expB.semantic(env);

      if (expOpt == null ){
         if (!testB.is(UNIT.T) ){
            throw typeMismatch(expB.loc,expB.type,UNIT.T);
         }
         return  UNIT.T;
      }else{
         Type testC = expOpt.semantic(env);
         if (!testC.is(UNIT.T) ) {
            throw typeMismatch(expOpt.loc,expOpt.type,UNIT.T);
         }
         return  UNIT.T;

      }


   }
}
