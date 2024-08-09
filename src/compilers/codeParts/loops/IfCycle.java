package compilers.codeParts.loops;

import compilers.UncompiledCode;
import compilers.codeParts.CodePart;
import compilers.codeParts.operations.ComplexOperation;
import compilers.codeParts.otherLogics.Jump;
import compilers.mathEngine.MathData;

import java.util.List;
import java.util.Map;

import static compilers.mathEngine.MathematicalExpressionReader.readExpression;

public class IfCycle extends CycleCodePart {
    ComplexOperation condition;
    public IfCycle(String conditionExpression, List<CodePart> insideCode, MathData mathData) {
        super(insideCode, mathData);
        condition = readExpression(conditionExpression, mathData);
        //linesCount
        linesCount = 1;//1 т. к. далее еще есть строчка Jump
        for (CodePart cycleCodePart : insideCode) {
            linesCount += cycleCodePart.linesCount;
        }
        linesCount += condition.linesCount;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode) {
        allCycleCodeParts.addAll(condition.insideOperations);
        int jumpLine = previousCPLastLineIndex + linesCount + 1;
        allCycleCodeParts.add(new Jump(Jump.BoolOperationType.equal, condition.finalVarName, "false", jumpLine, mathData));
        allCycleCodeParts.addAll(insideCode);
        return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode);
    }
}
