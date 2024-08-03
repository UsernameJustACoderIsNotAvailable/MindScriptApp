package compilers.codeParts.otherLogics;

import compilers.UncompiledCode;
import compilers.codeParts.SingleLineCodePart;

public class End extends SingleLineCodePart {
    public End(){
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode){
        return "end" + "\n";
    }
}
