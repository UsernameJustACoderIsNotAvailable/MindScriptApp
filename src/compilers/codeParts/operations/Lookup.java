package compilers.codeParts.operations;

import compilers.UncompiledCode;
import compilers.codeParts.CodePart;
import compilers.codeParts.ComplexCodePart;
import compilers.codeParts.SingleLineCodePart;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;
import static compilers.mathEngine.MathematicalExpressionReader.readExpression;

public class Lookup extends SingleLineCodePart {
    enum lookupType{
        block, unit, item, liquid
    }
    lookupType type;
    String index;
    String resultVarName;

    public Lookup(String resultVarName, lookupType type, String index){
        this.index = index;
        this.type = type;
        this.resultVarName = resultVarName;
    }

    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode){
        return String.format("lookup %s %s %s",
                type,
                getVarNameWithPrefix(resultVarName, nameSpaceIndex, uncompiledCode),
                getVarNameWithPrefix(index, nameSpaceIndex, uncompiledCode)
        );
    }
}
