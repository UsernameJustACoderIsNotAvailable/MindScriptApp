package compilers.codeParts.blockControls;

import compilers.UncompiledCode;
import compilers.codeParts.SingleLineCodePart;
import compilers.mathEngine.MathData;

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
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode){
        return String.format("sensor %s %s %s",
                getVarNameWithPrefix(returnVarName, nameSpaceIndex, uncompiledCode),
                getVarNameWithPrefix(blockVarName, nameSpaceIndex, uncompiledCode),
                sensorType
        ) + "\n";
    }
}
