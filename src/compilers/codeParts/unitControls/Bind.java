package compilers.codeParts.unitControls;

import compilers.UncompiledCode;
import compilers.codeParts.SingleLineCodePart;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;

public class Bind extends SingleLineCodePart {
    String bindType;
    Bind(String bindType){
        this.bindType = bindType;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode){
        return String.format("bind %s", getVarNameWithPrefix(bindType, nameSpaceIndex, uncompiledCode)) + "\n";
    }
}
