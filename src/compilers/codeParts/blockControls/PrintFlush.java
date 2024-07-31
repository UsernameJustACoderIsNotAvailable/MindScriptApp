package compilers.codeParts.blockControls;

import compilers.CompilerData;
import compilers.codeParts.CodePart;
import compilers.codeParts.SingleLineCodePart;
import usefulMethods.PairIntString;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;

public class PrintFlush  extends SingleLineCodePart {
    String blockVarName;
    PrintFlush(String blockVarName)
    {
        this.blockVarName = blockVarName;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, CompilerData compilerData){
        return String.format("printflush %s", getVarNameWithPrefix(blockVarName, nameSpaceIndex, compilerData.globalVars)) + "\n";
    }
}
