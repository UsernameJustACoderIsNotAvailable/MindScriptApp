package compilers.codeParts.blockControls;

import compilers.UncompiledCode;
import compilers.codeParts.SingleLineCodePart;

public class DrawFlush extends SingleLineCodePart {
    String blockVarName;
    DrawFlush(String blockVarName){
        this.blockVarName = blockVarName;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode){
        return String.format("drawflush %s", blockVarName) + "\n";
    }
}
