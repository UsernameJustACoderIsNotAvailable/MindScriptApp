package compilers.codeParts.blockControls;
import compilers.CompilerData;
import compilers.codeParts.CodePart;
import compilers.codeParts.SingleLineCodePart;
import usefulMethods.PairIntString;

public class Control extends SingleLineCodePart {
    enum controlType{
        enable,
        shoot,
        shootp,
        config,
        color
    }
    controlType type;
    String blockVarName;
    String argument;
    String x;
    String y;
    String unitVarName;
    String shoot;
    Control(controlType type, String blockVarName, String argument){
        this.type = type;
        this.blockVarName = blockVarName;
        this.argument = argument;
    }//enabled, config, color
    Control(controlType type, String blockVarName, String x, String y, String shoot){
        this.type = type;
        this.blockVarName = blockVarName;
        this.x = x;
        this.y = y;
        this.shoot = shoot;
    }//shoot
    Control(controlType type, String blockVarName, String unitVarName, String shoot){
        this.type = type;
        this.blockVarName = blockVarName;
        this.unitVarName = unitVarName;
        this.shoot = shoot;
    }//shootp

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, CompilerData compilerData){
        switch (type){
            case enable -> {return String.format("control enabled %s %s", blockVarName, argument) + "\n";}
            case config -> {return String.format("control config %s %s", blockVarName, argument) + "\n";}
            case color -> {return String.format("control color %s %s", blockVarName, argument) + "\n";}
            case shoot -> {return String.format("control shoot %s %s %s", x, y, shoot) + "\n";}
            case shootp -> {return String.format("control shoot %s %s", unitVarName, shoot) + "\n";}
        }
        return null;
    }
}
