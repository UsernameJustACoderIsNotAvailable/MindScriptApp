package compilers.codeParts.methods;

import compilers.UncompiledCode;
import compilers.codeParts.ComplexCodePart;
import compilers.codeParts.math.ComplexOperation;
import compilers.codeParts.math.Set;

import static compilers.Settings.*;
import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;

public class ReturnValueFromMethod extends ComplexCodePart {
    ComplexOperation valueToReturn;
    String methodName;

    public ReturnValueFromMethod(ComplexOperation valueToReturn, String methodName){
        this.valueToReturn = valueToReturn;
        this.methodName = methodName;
        linesCount = 2;//т. к. в конце кода этого CodePart будет строчка Set @counter и Set methodReturnVarNameString + methodIndex
        linesCount += valueToReturn.linesCount;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode) {
        int methodIndex = uncompiledCode.getMethodByName(methodName).methodIndex;
        allCycleCodeParts.add(valueToReturn);
        allCycleCodeParts.add(new Set(methodReturnVarNameString + methodIndex, getVarNameWithPrefix(valueToReturn.finalVarName, nameSpaceIndex, uncompiledCode)));
        allCycleCodeParts.add(new Set("@counter", methodReturnLineString + methodIndex));
        return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode);
    }
}
