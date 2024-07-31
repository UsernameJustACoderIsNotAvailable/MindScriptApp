package compilers.codeParts.otherLogics;

import compilers.CompilerData;
import compilers.codeParts.CodePart;
import compilers.codeParts.SingleLineCodePart;
import usefulMethods.PairIntString;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;

public class Wait  extends SingleLineCodePart {
    String waitTime;
    Wait(String waitTime){
        this.waitTime = waitTime;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, CompilerData compilerData){
        return String.format("wait %s", getVarNameWithPrefix(waitTime, nameSpaceIndex, compilerData.globalVars)) + "\n";
    }
}
