package compilers.codeParts.math;

import compilers.UncompiledCode;
import compilers.codeParts.SingleLineCodePart;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;

public class Set extends SingleLineCodePart {
    String name;
    String value;
    public Set(String name, String value){
        this.name = name;
        this.value = value;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode) {
        return String.format("set %s %s",
                getVarNameWithPrefix(name, nameSpaceIndex, uncompiledCode),
                getVarNameWithPrefix(value, nameSpaceIndex, uncompiledCode)
        ) + "\n";
    }
}
