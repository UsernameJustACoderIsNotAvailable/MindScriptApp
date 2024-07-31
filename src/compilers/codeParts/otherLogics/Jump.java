package compilers.codeParts.otherLogics;

import compilers.CompilerData;
import compilers.codeParts.CodePart;
import compilers.codeParts.SingleLineCodePart;
import usefulMethods.PairIntString;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;

public class Jump extends SingleLineCodePart {
    public enum BoolOperationType{
        equal,
        notEqual,
        lessThan,
        lessThanEq,
        greaterThan,
        greaterThanEq,
        strictEqual,
        always
    }

    BoolOperationType boolOperation;
    String firstArg;
    String secondArg;
    int jumpToIndex;
    public Jump(BoolOperationType boolOperation, String firstArg, String secondArg, int jumpToIndex){
        this.boolOperation = boolOperation;
        this.firstArg = firstArg;
        this.secondArg = secondArg;
        this.jumpToIndex = jumpToIndex;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, CompilerData compilerData){
        return String.format("jump %s %s %s %s",
                jumpToIndex,
                boolOperation,
                getVarNameWithPrefix(firstArg, nameSpaceIndex, compilerData.globalVars),
                getVarNameWithPrefix(secondArg, nameSpaceIndex, compilerData.globalVars)
        ) + "\n";
    }
}
