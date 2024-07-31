package compilers.codeParts.unitControls;

import compilers.CompilerData;
import compilers.codeParts.CodePart;
import compilers.codeParts.SingleLineCodePart;
import usefulMethods.PairIntString;

public class Bind extends SingleLineCodePart {
    String bindType;
    Bind(String bindType){
        this.bindType = bindType;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, CompilerData compilerData){
        return String.format("bind %s", bindType) + "\n";
    }
}
