package compilers.codeParts.inputOutput;

import compilers.UncompiledCode;
import compilers.codeParts.SingleLineCodePart;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;

public class Read  extends SingleLineCodePart {
    String index;
    String blockVarName;
    String var;
    Read(String blockVarName, String index, String var)
    {
        this.index = index;
        this.var = var;
        this.blockVarName = blockVarName;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode){
        return String.format("read %s %s %s",
                getVarNameWithPrefix(var, nameSpaceIndex, uncompiledCode),
                getVarNameWithPrefix(blockVarName, nameSpaceIndex, uncompiledCode),
                getVarNameWithPrefix(index, nameSpaceIndex, uncompiledCode)
        ) + "\n";
    }
}
