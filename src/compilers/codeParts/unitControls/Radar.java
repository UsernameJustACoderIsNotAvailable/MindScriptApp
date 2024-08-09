package compilers.codeParts.unitControls;

import compilers.UncompiledCode;
import compilers.codeParts.ComplexCodePart;
import compilers.codeParts.SingleLineCodePart;
import compilers.codeParts.operations.ComplexOperation;
import compilers.mathEngine.MathData;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;
import static compilers.mathEngine.MathematicalExpressionReader.readExpression;

public class Radar extends ComplexCodePart {
    enum FilterType {
        any,
        ally,
        player,
        attacker,
        flying,
        boss,
        ground
    }
    FilterType filter1 = FilterType.any;
    FilterType filter2 = FilterType.any;
    FilterType filter3 = FilterType.any;
    enum SortType
    {
        distance,
        health,
        shield,
        armor,
        maxHealth
    }

    SortType sortType;
    String blockVarName;
    ComplexOperation sortMode; // 1 - normal / 0 - reverse
    String returnVarName;
    Radar(FilterType filter1, SortType sortType, String blockVarName, String sortModeExpression, String returnVarName, MathData mathData)
    {
        this.filter1 = filter1;
        this.sortType = sortType;
        this.blockVarName = blockVarName;
        sortMode = readExpression(sortModeExpression, mathData);
        this.returnVarName = returnVarName;
        linesCount = 1 + sortMode.linesCount;
    }
    Radar(FilterType filter1, FilterType filter2, SortType sortType, String blockVarName, String sortModeExpression, String returnVarName, MathData mathData)
    {
        this.filter1 = filter1;
        this.filter1 = filter2;
        this.sortType = sortType;
        this.blockVarName = blockVarName;
        sortMode = readExpression(sortModeExpression, mathData);
        this.returnVarName = returnVarName;
        linesCount = 1 + sortMode.linesCount;
    }
    Radar(FilterType filter1, FilterType filter2, FilterType filter3, SortType sortType, String blockVarName, String sortModeExpression, String returnVarName, MathData mathData)
    {
        this.filter1 = filter1;
        this.filter1 = filter2;
        this.filter1 = filter3;
        this.sortType = sortType;
        this.blockVarName = blockVarName;
        sortMode = readExpression(sortModeExpression, mathData);
        this.returnVarName = returnVarName;
        linesCount = 1 + sortMode.linesCount;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode){
        allCycleCodeParts.add(sortMode);
        return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                String.format("uradar %s %s %s %s 0 %s %s",
                        filter1,
                        filter2,
                        filter3,
                        sortType,
                        getVarNameWithPrefix(sortMode.finalVarName, nameSpaceIndex, uncompiledCode),
                        getVarNameWithPrefix(returnVarName, nameSpaceIndex, uncompiledCode)
                ) + "\n";
    }
}
