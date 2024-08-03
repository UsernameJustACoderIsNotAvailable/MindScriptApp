package compilers.codeParts.otherLogics;

import compilers.UncompiledCode;
import compilers.codeParts.SingleLineCodePart;

public class Stop  extends SingleLineCodePart {
    Stop(){

    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode){
        return "stop" + "\n";
    }
}
