package compilers.codeParts.inputOutput;

import compilers.CompilerData;
import compilers.codeParts.CodePart;
import compilers.codeParts.SingleLineCodePart;
import usefulMethods.PairIntString;

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
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, CompilerData compilerData){
        return String.format("read %s %s %s",
                getVarNameWithPrefix(var, nameSpaceIndex, compilerData.globalVars),
                getVarNameWithPrefix(blockVarName, nameSpaceIndex, compilerData.globalVars),
                getVarNameWithPrefix(index, nameSpaceIndex, compilerData.globalVars)
        ) + "\n";
    }
}
