package compilers.codeParts.blockControls;

import compilers.UncompiledCode;
import compilers.codeParts.SingleLineCodePart;
import compilers.mathEngine.MathData;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;

public class DrawFlush extends SingleLineCodePart {
    String blockVarName;
    DrawFlush(String blockVarName){
        this.blockVarName = blockVarName;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode){
        return String.format("drawflush %s", getVarNameWithPrefix(blockVarName, nameSpaceIndex, uncompiledCode)) + "\n";
    }
}
