package compilers.codeParts;

import compilers.UncompiledCode;

import java.util.ArrayList;
import java.util.List;
import static compilers.mathEngine.MathematicalExpressionReader.readExpression;

public abstract class ComplexCodePart extends CodePart{
    protected List<CodePart> allCycleCodeParts = new ArrayList<CodePart>();
    public String getAllCycleCodePartsAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode compilerData){
        StringBuilder resultCode = new StringBuilder();
        for(int i = 0; i < allCycleCodeParts.size(); i++){
            CodePart codePart = allCycleCodeParts.get(i);
            resultCode.append(codePart.getAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, compilerData));
            previousCPLastLineIndex += codePart.linesCount;
        }
        return resultCode.toString();
    }
}
