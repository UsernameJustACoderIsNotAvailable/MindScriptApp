package compilers.codeParts.blockControls;

import compilers.UncompiledCode;
import compilers.codeParts.SingleLineCodePart;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;

public class PrintFlush  extends SingleLineCodePart {
    String blockVarName;
    PrintFlush(String blockVarName)
    {
        this.blockVarName = blockVarName;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode){
        return String.format("printflush %s", getVarNameWithPrefix(blockVarName, nameSpaceIndex, uncompiledCode)) + "\n";
    }
}
