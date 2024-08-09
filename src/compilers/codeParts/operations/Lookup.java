package compilers.codeParts.operations;

import compilers.UncompiledCode;
import compilers.codeParts.CodePart;
import compilers.codeParts.ComplexCodePart;
import compilers.codeParts.SingleLineCodePart;
import compilers.mathEngine.MathData;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;
import static compilers.mathEngine.MathematicalExpressionReader.readExpression;

public class Lookup extends ComplexCodePart {
    public enum lookupType{
        block, unit, item, liquid
    }
    lookupType type;
    ComplexOperation index;
    String resultVarName;

    public Lookup(String resultVarName, lookupType type, String indexExpression, MathData mathData){
        index = readExpression(indexExpression, mathData);
        this.type = type;
        this.resultVarName = resultVarName;
        linesCount = 1 + index.linesCount;
    }

    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode){
        allCycleCodeParts.add(index);
        return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                String.format("lookup %s %s %s",
                        type,
                        getVarNameWithPrefix(resultVarName, nameSpaceIndex, uncompiledCode),
                        getVarNameWithPrefix(index.finalVarName, nameSpaceIndex, uncompiledCode)
                ) + "\n";
    }
}
