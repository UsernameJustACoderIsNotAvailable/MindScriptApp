package compilers.codeParts.methods;

import compilers.UncompiledCode;
import compilers.codeParts.CodePart;
import compilers.codeParts.ComplexCodePart;
import compilers.codeParts.operations.Set;

import java.util.List;

import static compilers.Settings.methodReturnLineString;

public class Method extends ComplexCodePart {
    public String methodName;
    public List<String> args;
    List<CodePart> methodCode;
    public int firstLine, methodIndex, methodNameSpace;
    public Method(String methodName, List<String> args, List<CodePart> methodCode) {
        this.methodCode = methodCode;
        this.methodName = methodName;
        this.args = args;
        linesCount = 1;//1 т. к. далее еще есть строчка set @counter
        for (CodePart cycleCodePart : methodCode) {
            linesCount += cycleCodePart.linesCount;
        }
    }
    public void setMethodData(int index, int previousCPLastLineIndex){
        methodIndex = index;
        methodNameSpace = index + 1;
        firstLine = previousCPLastLineIndex + 1;
    }//должно быть вызвано перед сборкой всего кода

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode) {
        allCycleCodeParts.addAll(methodCode);
        allCycleCodeParts.add(new Set("@counter", methodReturnLineString + methodName));
        return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, methodNameSpace, uncompiledCode);
    }
}
