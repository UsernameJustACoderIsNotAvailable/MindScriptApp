package compilers.codeParts.otherLogics;

import compilers.UncompiledCode;
import compilers.codeParts.ComplexCodePart;
import compilers.codeParts.SingleLineCodePart;
import compilers.codeParts.operations.ComplexOperation;
import compilers.mathEngine.MathData;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;
import static compilers.mathEngine.MathematicalExpressionReader.readExpression;

public class Wait  extends ComplexCodePart {
    ComplexOperation waitTime;
    Wait(String waitTimeExpression, MathData mathData){
        waitTime = readExpression(waitTimeExpression, mathData);
        linesCount = 1 + waitTime.linesCount;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode){
        allCycleCodeParts.add(waitTime);
        return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                String.format("wait %s", getVarNameWithPrefix(waitTime.finalVarName, nameSpaceIndex, uncompiledCode)) + "\n";
    }
}
