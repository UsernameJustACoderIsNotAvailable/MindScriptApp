package compilers.codeParts.math;

import compilers.CompilerData;
import compilers.codeParts.CodePart;
import compilers.codeParts.SingleLineCodePart;
import usefulMethods.PairIntString;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;

public class Set extends SingleLineCodePart {
    String name;
    String value;
    public Set(String name, String value){
        this.name = name;
        this.value = value;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, CompilerData compilerData) {
        return String.format("set %s %s",
                getVarNameWithPrefix(name, nameSpaceIndex, compilerData.globalVars),
                getVarNameWithPrefix(value, nameSpaceIndex, compilerData.globalVars)
        ) + "\n";
    }
}
