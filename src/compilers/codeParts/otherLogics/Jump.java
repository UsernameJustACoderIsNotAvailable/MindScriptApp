package compilers.codeParts.otherLogics;

import compilers.UncompiledCode;
import compilers.codeParts.SingleLineCodePart;

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
    public Jump(int jumpToIndex){
        this.boolOperation = BoolOperationType.always;
        this.jumpToIndex = jumpToIndex;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode){
        switch (boolOperation){
            case always -> {
                return String.format("jump %s",
                        boolOperation
                ) + "\n";}
            case equal, notEqual, lessThan, lessThanEq, greaterThan, greaterThanEq, strictEqual -> {
                return String.format("jump %s %s %s %s",
                        jumpToIndex,
                        boolOperation,
                        getVarNameWithPrefix(firstArg, nameSpaceIndex, uncompiledCode),
                        getVarNameWithPrefix(secondArg, nameSpaceIndex, uncompiledCode)
                ) + "\n";
            }
        }
        return null;
    }
}
