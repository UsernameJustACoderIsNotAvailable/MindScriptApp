package compilers.codeParts.blockControls;

import compilers.CompilerData;
import compilers.codeParts.CodePart;
import compilers.codeParts.SingleLineCodePart;
import usefulMethods.PairIntString;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;

public class GetLink  extends SingleLineCodePart {
    String returnVarName;
    String blockIndex;
    GetLink(String blockIndex, String returnVarName)
    {
        this.blockIndex = blockIndex;
        this.returnVarName = returnVarName;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, CompilerData compilerData){
        return String.format("getlink %s %s", getVarNameWithPrefix(returnVarName, nameSpaceIndex, compilerData.globalVars), blockIndex) + "\n";
    }
}