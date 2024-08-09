package compilers.codeParts.blockControls;

import compilers.UncompiledCode;
import compilers.codeParts.ComplexCodePart;
import compilers.codeParts.SingleLineCodePart;
import compilers.codeParts.operations.ComplexOperation;
import compilers.mathEngine.MathData;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;
import static compilers.mathEngine.MathematicalExpressionReader.readExpression;

public class GetLink extends ComplexCodePart {
    String returnVarName;
    ComplexOperation blockIndex;
    public GetLink(String blockIndexExpression, String returnVarName, MathData mathData)
    {
        blockIndex = readExpression(blockIndexExpression, mathData);
        this.returnVarName = returnVarName;
        linesCount = 1 + blockIndex.linesCount;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode){
        allCycleCodeParts.add(blockIndex);
        return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                String.format("getlink %s %s",
                        getVarNameWithPrefix(returnVarName, nameSpaceIndex, uncompiledCode),
                        getVarNameWithPrefix(blockIndex.finalVarName, nameSpaceIndex, uncompiledCode)
                ) + "\n";
    }
}
