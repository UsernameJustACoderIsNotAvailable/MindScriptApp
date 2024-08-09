package compilers.codeParts.methods;

import compilers.UncompiledCode;
import compilers.codeParts.ComplexCodePart;
import compilers.codeParts.operations.ComplexOperation;
import compilers.codeParts.operations.Set;
import compilers.mathEngine.MathData;

import static compilers.Settings.*;
import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;
import static compilers.mathEngine.MathematicalExpressionReader.readExpression;

public class ReturnValueFromMethod extends ComplexCodePart {
    ComplexOperation valueToReturn;
    String methodName;

    public ReturnValueFromMethod(String valueToReturnExpression, String methodName, MathData mathData){
        valueToReturn = readExpression(valueToReturnExpression, mathData);
        this.methodName = methodName;
        linesCount = 2;//т. к. в конце кода этого CodePart будет строчка Set @counter и Set methodReturnVarNameString + methodIndex
        linesCount += valueToReturn.linesCount;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode) {
        int methodIndex = uncompiledCode.getMethodByName(methodName).methodIndex;
        allCycleCodeParts.add(valueToReturn);
        allCycleCodeParts.add(new Set(methodReturnVarNameString + methodName, getVarNameWithPrefix(valueToReturn.finalVarName, nameSpaceIndex, uncompiledCode)));
        allCycleCodeParts.add(new Set("@counter", methodReturnLineString + methodName));
        return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode);
    }
}
