package compilers.codeParts.operations;

import compilers.UncompiledCode;
import compilers.codeParts.ComplexCodePart;
import compilers.codeParts.SingleLineCodePart;
import compilers.mathEngine.MathData;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;
import static compilers.mathEngine.MathematicalExpressionReader.readExpression;

public class PackColor extends ComplexCodePart {
    String resultVarName;
    ComplexOperation r, g, b, a;// 0 - 1 float

    public PackColor(String resultVarName, String rExpression, String gExpression, String bExpression, String aExpression, MathData mathData){
        this.resultVarName = resultVarName;
        r = readExpression(rExpression, mathData);
        g = readExpression(gExpression, mathData);
        b = readExpression(bExpression, mathData);
        a = readExpression(aExpression, mathData);
        linesCount = 1 + r.linesCount + g.linesCount + b.linesCount + a.linesCount;
    }

    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode){
        allCycleCodeParts.add(r);
        allCycleCodeParts.add(g);
        allCycleCodeParts.add(b);
        allCycleCodeParts.add(a);
        return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                String.format("packcolor %s %s %s %s",
                        getVarNameWithPrefix(r.finalVarName, nameSpaceIndex, uncompiledCode),
                        getVarNameWithPrefix(g.finalVarName, nameSpaceIndex, uncompiledCode),
                        getVarNameWithPrefix(b.finalVarName, nameSpaceIndex, uncompiledCode),
                        getVarNameWithPrefix(a.finalVarName, nameSpaceIndex, uncompiledCode)
                ) + "\n";
    }
}

