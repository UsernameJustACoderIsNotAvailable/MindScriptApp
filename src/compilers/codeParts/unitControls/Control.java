package compilers.codeParts.unitControls;

import compilers.UncompiledCode;
import compilers.codeParts.ComplexCodePart;
import compilers.codeParts.operations.ComplexOperation;
import compilers.mathEngine.MathData;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;
import static compilers.mathEngine.MathematicalExpressionReader.readExpression;

public class Control extends ComplexCodePart {

    enum ControlType {
        idle,
        stop,
        move,
        approach,
        pathfind,
        autoPathfind,
        boost,
        target,
        targetp,
        itemDrop,
        itemTake,
        payDrop,
        payTake,
        payEnter,
        mine,
        flag,
        build,
        getBlock,
        within,
        unbind
    }

    ControlType controlType;
    ComplexOperation x, y;
    ComplexOperation amount, someValue, boolVar;
    String unitVar, blockVar, itemVar, config;
    String returnVarName, returnVarName2, returnVarName3;

    Control(ControlType controlType) {
        this.controlType = controlType;
        linesCount = 1;
    }//unbind, idle, stop, autoPathfind, payDrop, payEnter,

    Control(ControlType controlType, String arg1, MathData mathData) {
        this.controlType = controlType;
        switch (controlType) {
            case boost, payTake -> {
                boolVar = readExpression(arg1, mathData);
                linesCount = 1 + boolVar.linesCount;
            }
            case flag -> {
                someValue = readExpression(arg1, mathData);
                linesCount = 1 + someValue.linesCount;
            }
        }
    }//boost, payTake, flag

    Control(ControlType controlType, String arg1, String arg2, MathData mathData) {
        this.controlType = controlType;
        switch (controlType) {
            case move, pathfind, mine -> {
                x = readExpression(arg1, mathData);
                y = readExpression(arg2, mathData);
                linesCount = 1 + x.linesCount + y.linesCount;
            }
            case itemDrop -> {
                blockVar = arg1;
                amount = readExpression(arg2, mathData);
                linesCount = 1 + amount.linesCount;
            }
            case targetp -> {
                unitVar = arg1;
                boolVar = readExpression(arg2, mathData);
                linesCount = 1 + boolVar.linesCount;
            }
        }
    }//move, pathfind, mine, itemDrop, targetp

    Control(ControlType controlType, String arg1, String arg2, String arg3, MathData mathData) {
        this.controlType = controlType;
        switch (controlType) {
            case approach, target -> {
                x = readExpression(arg1, mathData);
                y = readExpression(arg2, mathData);
                someValue = readExpression(arg3, mathData);
                linesCount = 1 + x.linesCount + y.linesCount + someValue.linesCount;
            }
            case itemTake -> {
                this.blockVar = arg1;
                this.itemVar = arg2;
                amount = readExpression(arg3, mathData);
                linesCount = 1 + amount.linesCount;
            }
        }
    }//approach, target, itemTake

    Control(ControlType controlType, String arg1, String arg2, String arg3, String arg4, MathData mathData) {
        this.controlType = controlType;
        x = readExpression(arg1, mathData);
        y = readExpression(arg2, mathData);
        someValue = readExpression(arg3, mathData);
        this.returnVarName = arg4;
        linesCount = 1 + x.linesCount + y.linesCount + someValue.linesCount;
    }//within

    Control(ControlType controlType, String arg1, String arg2, String arg3, String arg4, String arg5, MathData mathData) {
        this.controlType = controlType;
        switch (controlType) {
            case build -> {
                x = readExpression(arg1, mathData);
                y = readExpression(arg2, mathData);
                this.blockVar = arg3;
                someValue = readExpression(arg4, mathData);
                this.config = arg5;
                linesCount = 1 + x.linesCount + y.linesCount + someValue.linesCount;
            }
            case getBlock -> {
                x = readExpression(arg1, mathData);
                y = readExpression(arg2, mathData);
                this.returnVarName = arg3;
                this.returnVarName2 = arg4;
                this.returnVarName3 = arg5;
                linesCount = 1 + x.linesCount + y.linesCount;
            }
        }
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode) {
        switch (controlType){
            case idle, stop, payDrop, payEnter, unbind, autoPathfind -> {return String.format("ucontrol %s", controlType.name()) + "\n";}
            case payTake, boost -> {
                allCycleCodeParts.add(boolVar);
                return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                        String.format("ucontrol %s %s",
                                controlType.name(),
                                getVarNameWithPrefix(boolVar.finalVarName, nameSpaceIndex, uncompiledCode)
                        ) + "\n";}
            case flag -> {
                allCycleCodeParts.add(someValue);
                return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                        String.format("ucontrol %s %s",
                                controlType.name(),
                                getVarNameWithPrefix(someValue.finalVarName, nameSpaceIndex, uncompiledCode)
                        ) + "\n";}
            case move, pathfind, mine -> {
                allCycleCodeParts.add(x);
                allCycleCodeParts.add(y);
                return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                        String.format("ucontrol %s %s %s",
                                controlType.name(),
                                getVarNameWithPrefix(x.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(y.finalVarName, nameSpaceIndex, uncompiledCode)
                        ) + "\n";}
            case itemDrop -> {
                allCycleCodeParts.add(amount);
                return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                        String.format("ucontrol %s %s %s",
                                controlType.name(),
                                getVarNameWithPrefix(blockVar, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(amount.finalVarName, nameSpaceIndex, uncompiledCode)
                        ) + "\n";}
            case itemTake -> {
                allCycleCodeParts.add(amount);
                allCycleCodeParts.add(someValue);
                return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                        String.format("ucontrol itemTake %s %s %s",
                                getVarNameWithPrefix(blockVar, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(amount.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(someValue.finalVarName, nameSpaceIndex, uncompiledCode)
                        ) + "\n";}
            case targetp -> {
                allCycleCodeParts.add(someValue);
                return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                        String.format("ucontrol targetp %s %s",
                                getVarNameWithPrefix(unitVar, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(someValue.finalVarName, nameSpaceIndex, uncompiledCode)
                        ) + "\n";}
            case target, approach -> {
                allCycleCodeParts.add(x);
                allCycleCodeParts.add(y);
                allCycleCodeParts.add(someValue);
                return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                        String.format("ucontrol %s %s %s %s",
                                controlType.name(),
                                getVarNameWithPrefix(x.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(y.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(someValue.finalVarName, nameSpaceIndex, uncompiledCode)
                        ) + "\n";}
            case within -> {
                allCycleCodeParts.add(x);
                allCycleCodeParts.add(y);
                allCycleCodeParts.add(someValue);
                return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode)+
                        String.format("ucontrol within %s %s %s %s",
                                getVarNameWithPrefix(x.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(y.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(someValue.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(returnVarName, nameSpaceIndex, uncompiledCode)
                        ) + "\n";}
            case build -> {
                allCycleCodeParts.add(x);
                allCycleCodeParts.add(y);
                allCycleCodeParts.add(someValue);
                return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                        String.format("ucontrol build %s %s %s %s %s",
                                getVarNameWithPrefix(x.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(y.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(blockVar, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(someValue.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(config, nameSpaceIndex, uncompiledCode)
                        ) + "\n";}
            case getBlock -> {
                allCycleCodeParts.add(x);
                allCycleCodeParts.add(y);
                return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                        String.format("ucontrol getBlock %s %s %s %s %s",
                                getVarNameWithPrefix(x.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(y.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(returnVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(returnVarName2, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(returnVarName3, nameSpaceIndex, uncompiledCode)
                        ) + "\n";}
        }
        return null;
    }
}
