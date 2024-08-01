package compilers.codeParts.methods;

import compilers.CompilerData;
import compilers.codeParts.CodePart;
import compilers.codeParts.ComplexCodePart;
import compilers.codeParts.math.Set;
import compilers.codeParts.otherLogics.Jump;
import usefulMethods.PairIntString;

import java.util.List;

import static compilers.Settings.methodReturnLineString;

public class SetMethod  extends ComplexCodePart {
    String methodName, returnVar;
    List<String> args;
    List<CodePart> methodCodeCode;
    public int firstLine, methodIndex, methodNameSpace;
    protected SetMethod(String methodName, List<CodePart> methodCodeCode, String returnVar, List<String> args, int methodIndex) {
        this.methodCodeCode = methodCodeCode;
        this.methodName = methodName;
        this.args = args;
        this.returnVar = returnVar;
        this.methodIndex = methodIndex;
        linesCount = 1;//1 т. к. далее еще есть строчка set @counter
        for (CodePart cycleCodePart : methodCodeCode) {
            linesCount += cycleCodePart.linesCount;
        }
    }
    public void setMethodIndexAndNameSpace(int index){
        methodIndex = index;
        methodNameSpace = index + 1;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, CompilerData compilerData) {
        firstLine = previousCPLastLineIndex + 1;
        allCycleCodeParts.addAll(methodCodeCode);
        allCycleCodeParts.add(new Set("@counter", methodReturnLineString + methodIndex));
        return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, methodNameSpace, compilerData);
    }
}
