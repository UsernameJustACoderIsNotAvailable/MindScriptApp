package compilers.codeParts.loops;

import compilers.CompilerData;
import compilers.codeParts.CodePart;
import compilers.codeParts.ComplexCodePart;
import compilers.codeParts.math.ComplexOperation;
import compilers.codeParts.otherLogics.Jump;

import java.util.List;

public class IfCycle extends ComplexCodePart {
    ComplexOperation condition;
    public IfCycle(ComplexOperation condition, List<CodePart> insideCode) {
        super(insideCode);
        this.condition = condition;
        //linesCount
        linesCount = 1;//1 т. к. далее еще есть строчка Jump
        for (CodePart cycleCodePart : insideCode) {
            linesCount += cycleCodePart.linesCount;
        }
        linesCount += condition.linesCount;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, CompilerData compilerData) {
        allCycleCodeParts.addAll(condition.insideOperations);
        int jumpLine = previousCPLastLineIndex + linesCount + 1;
        allCycleCodeParts.add(new Jump(Jump.BoolOperationType.equal, condition.finalVarName, "false", jumpLine));
        allCycleCodeParts.addAll(insideCode);
        StringBuilder resultCode = new StringBuilder();
        for(int i = 0; i < allCycleCodeParts.size(); i++){
            CodePart codePart = allCycleCodeParts.get(i);
            resultCode.append(codePart.getAsCompiledCode(previousCPLastLineIndex + i - 1, nameSpaceIndex, compilerData));
        }
        return resultCode.toString();
    }
}
