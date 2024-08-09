package compilers.codeParts.loops;

import compilers.UncompiledCode;
import compilers.codeParts.CodePart;
import compilers.codeParts.operations.ComplexOperation;
import compilers.codeParts.otherLogics.Jump;

import java.util.List;

public class WhileCycle extends CycleCodePart {
    ComplexOperation condition;
    protected WhileCycle(List<CodePart> insideCode, ComplexOperation condition) {
        super(insideCode);
        this.condition = condition;
        linesCount = 1;//1 т. к. далее еще есть строчка Jump
        for (CodePart cycleCodePart : insideCode) {
            linesCount += cycleCodePart.linesCount;
        }
        linesCount += condition.linesCount;
    }
    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode) {
        allCycleCodeParts.addAll(insideCode);
        allCycleCodeParts.addAll(condition.insideOperations);
        int jumpLine = previousCPLastLineIndex+1;
        allCycleCodeParts.add(new Jump(Jump.BoolOperationType.equal, condition.finalVarName, "true", jumpLine));
        return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode);
    }
}
