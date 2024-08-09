package compilers.codeParts.otherLogics;

import compilers.UncompiledCode;
import compilers.codeParts.ComplexCodePart;
import compilers.codeParts.SingleLineCodePart;
import compilers.codeParts.operations.ComplexOperation;
import compilers.mathEngine.MathData;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;
import static compilers.mathEngine.MathematicalExpressionReader.readExpression;

public class Jump extends ComplexCodePart {
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
    ComplexOperation firstArg, secondArg;
    int jumpToIndex;
    public Jump(BoolOperationType boolOperation, String firstArgExpression, String secondArgExpression, int jumpToIndex, MathData mathData){
        this.boolOperation = boolOperation;
        firstArg = readExpression(firstArgExpression, mathData);
        secondArg = readExpression(secondArgExpression, mathData);;
        this.jumpToIndex = jumpToIndex;
        linesCount = 1 + firstArg.linesCount + secondArg.linesCount;
    }
    public Jump(int jumpToIndex){
        this.boolOperation = BoolOperationType.always;
        this.jumpToIndex = jumpToIndex;
        linesCount = 1;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode){
        switch (boolOperation){
            case always -> {
                return String.format("jump %s",
                        boolOperation
                ) + "\n";}
            case equal, notEqual, lessThan, lessThanEq, greaterThan, greaterThanEq, strictEqual -> {
                allCycleCodeParts.add(firstArg);
                allCycleCodeParts.add(secondArg);
                return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                        String.format("jump %s %s %s %s",
                                jumpToIndex,
                                boolOperation,
                                getVarNameWithPrefix(firstArg.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(secondArg.finalVarName, nameSpaceIndex, uncompiledCode)
                        ) + "\n";
            }
        }
        return null;
    }
}
