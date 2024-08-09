package compilers.codeParts.blockControls;
import compilers.UncompiledCode;
import compilers.codeParts.ComplexCodePart;
import compilers.codeParts.SingleLineCodePart;
import compilers.codeParts.operations.ComplexOperation;
import compilers.mathEngine.MathData;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;
import static compilers.mathEngine.MathematicalExpressionReader.readExpression;

public class Control extends ComplexCodePart {
    enum controlType{
        enable,
        shoot,
        shootp,
        config,
        color
    }
    controlType type;
    String blockVarName;
    String color;
    String config;
    ComplexOperation enabled;
    ComplexOperation x;
    ComplexOperation y;
    String unitVarName;
    ComplexOperation shoot;
    Control(controlType type, String blockVarName, String argument){
        this.type = type;
        switch (type){
            case color -> {
                this.blockVarName = blockVarName;
                this.color = argument;
            }
            case config -> {
                this.blockVarName = blockVarName;
                this.config = argument;
            }
        }
        linesCount = 1;
    }//config, color
    Control(controlType type, String blockVarName, String enabledExpression, MathData mathData){
        this.type = type;
        this.blockVarName = blockVarName;
        this.enabled = readExpression(enabledExpression, mathData);
        linesCount = 1 + enabled.linesCount;
    }//enabled
    Control(controlType type, String blockVarName, String xExpression, String yExpression, String shootExpression, MathData mathData){
        this.type = type;
        this.blockVarName = blockVarName;
        this.x = readExpression(xExpression, mathData);
        this.y = readExpression(yExpression, mathData);
        this.shoot = readExpression(shootExpression, mathData);
        linesCount = 1 + x.linesCount + y.linesCount + shoot.linesCount;
    }//shoot
    Control(controlType type, String blockVarName, String unitVarName, String shootExpression, MathData mathData){
        this.type = type;
        this.blockVarName = blockVarName;
        this.unitVarName = unitVarName;
        this.shoot = readExpression(shootExpression, mathData);
        linesCount = 1 + shoot.linesCount;
    }//shootp

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode){
        switch (type){
            case enable -> {
                allCycleCodeParts.add(enabled);
                return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                        String.format("control enabled %s %s", blockVarName, getVarNameWithPrefix(enabled.finalVarName, nameSpaceIndex, uncompiledCode)) + "\n";
            }
            case config -> {
                return String.format("control config %s %s", blockVarName, getVarNameWithPrefix(config, nameSpaceIndex, uncompiledCode)) + "\n";
            }
            case color -> {
                return String.format("control color %s %s", blockVarName, getVarNameWithPrefix(color, nameSpaceIndex, uncompiledCode)) + "\n";
            }
            case shoot -> {
                allCycleCodeParts.add(x);
                allCycleCodeParts.add(y);
                allCycleCodeParts.add(shoot);
                return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                        String.format("control shoot %s %s %s",
                                getVarNameWithPrefix(x.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(y.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(shoot.finalVarName, nameSpaceIndex, uncompiledCode)
                        ) + "\n";
            }
            case shootp -> {
                allCycleCodeParts.add(shoot);
                return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                        String.format("control shoot %s %s", unitVarName, getVarNameWithPrefix(shoot.finalVarName, nameSpaceIndex, uncompiledCode)) + "\n";
            }
        }
        return null;
    }
}
