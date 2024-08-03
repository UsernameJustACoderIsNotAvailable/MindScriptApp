package compilers.codeParts.blockControls;

import compilers.UncompiledCode;
import compilers.codeParts.SingleLineCodePart;

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
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode){
        return String.format("getlink %s %s", getVarNameWithPrefix(returnVarName, nameSpaceIndex, uncompiledCode), blockIndex) + "\n";
    }
}
