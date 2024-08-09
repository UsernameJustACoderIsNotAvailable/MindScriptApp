package compilers.codeParts.inputOutput;

import compilers.UncompiledCode;
import compilers.codeParts.ComplexCodePart;
import compilers.codeParts.SingleLineCodePart;
import compilers.codeParts.operations.ComplexOperation;
import compilers.mathEngine.MathData;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;
import static compilers.mathEngine.MathematicalExpressionReader.readExpression;

public class Read  extends ComplexCodePart {
    ComplexOperation index;
    String blockVarName;
    String var;
    Read(String blockVarName, String indexExpression, String var, MathData mathData)
    {
        index = readExpression(indexExpression, mathData);
        this.var = var;
        this.blockVarName = blockVarName;
        linesCount = 1 + index.linesCount;
    }
    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode){
        allCycleCodeParts.add(index);
        return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                String.format("read %s %s %s",
                        getVarNameWithPrefix(var, nameSpaceIndex, uncompiledCode),
                        getVarNameWithPrefix(blockVarName, nameSpaceIndex, uncompiledCode),
                        getVarNameWithPrefix(index.finalVarName, nameSpaceIndex, uncompiledCode)
                ) + "\n";
    }
}
