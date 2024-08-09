package compilers.codeParts.inputOutput;

import compilers.UncompiledCode;
import compilers.codeParts.ComplexCodePart;
import compilers.codeParts.SingleLineCodePart;
import compilers.codeParts.operations.ComplexOperation;
import compilers.mathEngine.MathData;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;
import static compilers.mathEngine.MathematicalExpressionReader.readExpression;

public class Print extends ComplexCodePart {
    ComplexOperation text;
    Print(String textExpression, MathData mathData)
    {
        text = readExpression(textExpression, mathData);
        linesCount = 1 + text.linesCount;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode){
        allCycleCodeParts.add(text);
        return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                String.format("print %s", getVarNameWithPrefix(text.finalVarName, nameSpaceIndex, uncompiledCode)) + "\n";
    }
}
