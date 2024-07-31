package compilers.codeParts.unitControls;

import compilers.CompilerData;
import compilers.codeParts.CodePart;
import compilers.codeParts.SingleLineCodePart;
import usefulMethods.PairIntString;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;

public class Control extends SingleLineCodePart {

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
    String singleVar;
    String x;
    String y;
    String blockVarName;
    String amount;
    String otherVar;
    String returnVarName;
    String returnVarName2;
    String returnVarName3;

    Control(ControlType controlType) {
        this.controlType = controlType;
    }

    Control(ControlType controlType, String singleVar) {
        this.controlType = controlType;
        this.singleVar = singleVar;
    }

    Control(ControlType controlType, String arg1, String arg2) {
        this.controlType = controlType;
        switch (controlType) {
            case move, pathfind, mine -> {
                this.x = arg1;
                this.y = arg2;
                break;
            }
            case itemDrop -> {
                this.blockVarName = arg1;
                this.amount = arg2;
                break;
            }
            case targetp -> {
                this.singleVar = arg1;
                this.otherVar = arg2; // бля, так лень названия придумывать (C)DVD, 2023
                break;
            }
        }
    }

    Control(ControlType controlType, String arg1, String arg2, String arg3) {
        this.controlType = controlType;
        switch (controlType) {
            case approach, target -> {
                this.x = arg1;
                this.y = arg2;
                this.otherVar = arg3;
                break;
            }
            case itemTake -> {
                this.blockVarName = arg1;
                this.amount = arg2;
                this.otherVar = arg3;
                break;
            }
        }
    }

    Control(ControlType controlType, String arg1, String arg2, String arg3, String arg4) {
        this.controlType = controlType;
        switch (controlType) {
            case within -> {
                this.x = arg1;
                this.y = arg2;
                this.otherVar = arg3;
                this.returnVarName = arg4;
                break;
            }
        }
    }

    Control(ControlType controlType, String arg1, String arg2, String arg3, String arg4, String arg5) {
        this.controlType = controlType;
        switch (controlType) {
            case build -> {
                this.x = arg1;
                this.y = arg2;
                this.blockVarName = arg3;
                this.singleVar = arg4;
                this.otherVar = arg5;
                break;
            }
            case getBlock -> {
                this.x = arg1;
                this.y = arg2;
                this.returnVarName = arg3;
                this.returnVarName2 = arg4;
                this.returnVarName3 = arg5;
                break;
            }
        }
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, CompilerData compilerData) {
        switch (controlType){
            case move, pathfind, mine -> {return String.format("ucontrol %s %s %s",
                    controlType.name(),
                    getVarNameWithPrefix(x, nameSpaceIndex, compilerData.globalVars),
                    getVarNameWithPrefix(y, nameSpaceIndex, compilerData.globalVars)
            ) + "\n";}
            case idle, stop, payDrop, payEnter, unbind, autoPathfind -> {return String.format("ucontrol %s", controlType.name()) + "\n";}
            case itemDrop -> {return String.format("ucontrol %s %s %s",
                    controlType.name(),
                    getVarNameWithPrefix(blockVarName, nameSpaceIndex, compilerData.globalVars),
                    getVarNameWithPrefix(amount, nameSpaceIndex, compilerData.globalVars)
            );}
            case itemTake -> {return String.format("ucontrol %s %s %s %s", controlType.name(),
                    blockVarName,
                    getVarNameWithPrefix(amount, nameSpaceIndex, compilerData.globalVars),
                    getVarNameWithPrefix(otherVar, nameSpaceIndex, compilerData.globalVars)
            ) + "\n";}
            case targetp -> {return String.format("ucontrol %s %s %s",
                    controlType.name(),
                    getVarNameWithPrefix(singleVar, nameSpaceIndex, compilerData.globalVars),
                    getVarNameWithPrefix(otherVar, nameSpaceIndex, compilerData.globalVars)
            ) + "\n";}
            case target, approach -> {return String.format("ucontrol %s %s %s %s",
                    controlType.name(),
                    getVarNameWithPrefix(x, nameSpaceIndex, compilerData.globalVars),
                    getVarNameWithPrefix(y, nameSpaceIndex, compilerData.globalVars),
                    getVarNameWithPrefix(otherVar, nameSpaceIndex, compilerData.globalVars)
            ) + "\n";}
            case within -> {return String.format("ucontrol %s %s %s %s %s",
                    controlType.name(),
                    getVarNameWithPrefix(x, nameSpaceIndex, compilerData.globalVars),
                    getVarNameWithPrefix(y, nameSpaceIndex, compilerData.globalVars),
                    getVarNameWithPrefix(otherVar, nameSpaceIndex, compilerData.globalVars),
                    getVarNameWithPrefix(returnVarName, nameSpaceIndex, compilerData.globalVars)
            ) + "\n";}
            case build -> {return String.format("ucontrol %s %s %s %s %s %s",
                    controlType.name(),
                    getVarNameWithPrefix(x, nameSpaceIndex, compilerData.globalVars),
                    getVarNameWithPrefix(y, nameSpaceIndex, compilerData.globalVars),
                    getVarNameWithPrefix(blockVarName, nameSpaceIndex, compilerData.globalVars),
                    getVarNameWithPrefix(singleVar, nameSpaceIndex, compilerData.globalVars),
                    getVarNameWithPrefix(otherVar, nameSpaceIndex, compilerData.globalVars)
            ) + "\n";}
            case getBlock -> {return String.format("ucontrol %s %s %s %s %s %s",
                    controlType.name(),
                    getVarNameWithPrefix(x, nameSpaceIndex, compilerData.globalVars),
                    getVarNameWithPrefix(y, nameSpaceIndex, compilerData.globalVars),
                    getVarNameWithPrefix(returnVarName, nameSpaceIndex, compilerData.globalVars),
                    getVarNameWithPrefix(returnVarName2, nameSpaceIndex, compilerData.globalVars),
                    getVarNameWithPrefix(returnVarName3, nameSpaceIndex, compilerData.globalVars)
            ) + "\n";}
            case flag, payTake, boost -> {return String.format("ucontrol %s %s",
                    controlType.name(),
                    getVarNameWithPrefix(singleVar, nameSpaceIndex, compilerData.globalVars)
            ) + "\n";}
        }
        return null;
    }
}
