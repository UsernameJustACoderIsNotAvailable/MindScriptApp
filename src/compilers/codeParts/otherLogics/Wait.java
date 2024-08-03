package compilers.codeParts.otherLogics;

import compilers.UncompiledCode;
import compilers.codeParts.SingleLineCodePart;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;

public class Wait  extends SingleLineCodePart {
    String waitTime;
    Wait(String waitTime){
        this.waitTime = waitTime;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode){
        return String.format("wait %s", getVarNameWithPrefix(waitTime, nameSpaceIndex, uncompiledCode)) + "\n";
    }
}
