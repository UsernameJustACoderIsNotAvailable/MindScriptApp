package compilers.codeParts.blockControls;

import compilers.CompilerData;
import compilers.codeParts.CodePart;
import compilers.codeParts.SingleLineCodePart;
import usefulMethods.PairIntString;

public class DrawFlush extends SingleLineCodePart {
    String blockVarName;
    DrawFlush(String blockVarName){
        this.blockVarName = blockVarName;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, CompilerData compilerData){
        return String.format("drawflush %s", blockVarName) + "\n";
    }
}
