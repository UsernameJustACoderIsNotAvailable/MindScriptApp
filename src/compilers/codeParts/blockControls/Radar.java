package compilers.codeParts.blockControls;

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
    ComplexOperation order; // 1 - normal / 0 - reverse
    String returnVarName;

    Radar(FilterType filter1, SortType sortType, String blockVarName, String orderExpression, String returnVarName, MathData mathData)
    {
        this.filter1 = filter1;
        this.sortType = sortType;
        this.blockVarName = blockVarName;
        order = readExpression(orderExpression, mathData);
        this.returnVarName = returnVarName;
        linesCount = 1 + order.linesCount;
    } //one filter
    Radar(FilterType filter1, FilterType filter2, SortType sortType, String blockVarName, String orderExpression, String returnVarName, MathData mathData)
    {
        this.filter1 = filter1;
        this.filter2 = filter2;
        this.sortType = sortType;
        this.blockVarName = blockVarName;
        order = readExpression(orderExpression, mathData);
        this.returnVarName = returnVarName;
        linesCount = 1 + order.linesCount;
    } //two filters
    Radar(FilterType filter1, FilterType filter2, FilterType filter3, SortType sortType, String blockVarName, String orderExpression, String returnVarName, MathData mathData)
    {
        this.filter1 = filter1;
        this.filter2 = filter2;
        this.filter3 = filter3;
        this.sortType = sortType;
        this.blockVarName = blockVarName;
        order = readExpression(orderExpression, mathData);
        this.returnVarName = returnVarName;
        linesCount = 1 + order.linesCount;
    } //three filters

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode){
        allCycleCodeParts.add(order);
        return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                String.format("radar %s %s %s %s %s %s %s",
                        filter1.name(),
                        filter2.name(),
                        filter3.name(),
                        sortType.name(),
                        getVarNameWithPrefix(blockVarName, nameSpaceIndex, uncompiledCode),
                        getVarNameWithPrefix(order.finalVarName, nameSpaceIndex, uncompiledCode),
                        getVarNameWithPrefix(returnVarName, nameSpaceIndex, uncompiledCode)
                ) + "\n";
    }
}
