package compilers.codeParts.loops;

import java.util.List;

import compilers.UncompiledCode;
import compilers.codeParts.CodePart;
import compilers.codeParts.operations.ComplexOperation;
import compilers.codeParts.otherLogics.Jump;
import compilers.mathEngine.MathData;

import static compilers.mathEngine.MathematicalExpressionReader.readExpression;

public class ComplexForCycle extends CycleCodePart {
    ComplexOperation startValue, operation, condition;
    public ComplexForCycle(
            String startValueExpression,
            String operationExpression,
            List<CodePart> insideCode,
            String conditionExpression,
            MathData mathData) {
        super(insideCode, mathData);
        condition = readExpression(conditionExpression, mathData);
        operation = readExpression(operationExpression, mathData);
        startValue = readExpression(startValueExpression, mathData);
        //linesCount
        linesCount = 1;//1 т. к. далее еще есть строчка Jump
        for (CodePart cycleCodePart : insideCode) {
            linesCount += cycleCodePart.linesCount;
        }
        linesCount += startValue.linesCount;
        linesCount += operation.linesCount;
        linesCount += condition.linesCount;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode) {
        allCycleCodeParts.addAll(startValue.insideOperations);
        allCycleCodeParts.addAll(insideCode);
        allCycleCodeParts.addAll(operation.insideOperations);
        allCycleCodeParts.addAll(condition.insideOperations);
        int jumpLine = previousCPLastLineIndex+startValue.linesCount+1;
        allCycleCodeParts.add(new Jump(Jump.BoolOperationType.equal, condition.finalVarName, "true", jumpLine, mathData));
        return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode);
    }
}
