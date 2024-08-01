package compilers.codeParts.methods;

import compilers.CompilerData;
import compilers.codeParts.ComplexCodePart;
import compilers.codeParts.math.ComplexOperation;
import compilers.codeParts.math.Set;

import static compilers.Settings.*;
import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;

public class ReturnValueFromMethod extends ComplexCodePart {
    ComplexOperation valueToReturn;

    public ReturnValueFromMethod(ComplexOperation valueToReturn){
        this.valueToReturn = valueToReturn;
        linesCount = 1;//т. к. в конце кода этого CodePart будет строчка Set @counter
        linesCount += valueToReturn.linesCount;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, CompilerData compilerData) {
        int methodIndex = compilerData.lastMethodIndex + 1;/* этот ReturnValueFromMethod находится внутри метода,
        который еще не собран полностью, значит индекс этого метода - это иднекс последнего собранного метода + 1 */
        allCycleCodeParts.add(valueToReturn);
        allCycleCodeParts.add(new Set(methodReturnVarNameString, getVarNameWithPrefix(valueToReturn.finalVarName, nameSpaceIndex, compilerData.globalVars)));
        allCycleCodeParts.add(new Set("@counter", methodReturnLineString + methodIndex));
        return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, compilerData);
    }
}
