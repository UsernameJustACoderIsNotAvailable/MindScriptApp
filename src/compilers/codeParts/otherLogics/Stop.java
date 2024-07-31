package compilers.codeParts.otherLogics;

import compilers.CompilerData;
import compilers.codeParts.CodePart;
import compilers.codeParts.SingleLineCodePart;
import usefulMethods.PairIntString;

public class Stop  extends SingleLineCodePart {
    Stop(){

    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, CompilerData compilerData){
        return "stop" + "\n";
    }
}
