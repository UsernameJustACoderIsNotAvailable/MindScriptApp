package compilers.codeParts.blockControls;

import compilers.CompilerData;
import compilers.codeParts.CodePart;
import compilers.codeParts.SingleLineCodePart;
import usefulMethods.PairIntString;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;

public class Sensor extends SingleLineCodePart {
    String returnVarName;
    String blockVarName;
    String sensorType;

    Sensor(String returnVarName, String blockVarName, String sensorType)
    {
        this.returnVarName = returnVarName;
        this.blockVarName = blockVarName;
        this.sensorType = sensorType;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, CompilerData compilerData){
        return String.format("sensor %s %s %s",
                getVarNameWithPrefix(returnVarName, nameSpaceIndex, compilerData.globalVars),
                getVarNameWithPrefix(blockVarName, nameSpaceIndex, compilerData.globalVars),
                sensorType
        ) + "\n";
    }
}
