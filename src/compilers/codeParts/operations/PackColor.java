package compilers.codeParts.operations;

import compilers.UncompiledCode;
import compilers.codeParts.SingleLineCodePart;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;

public class PackColor extends SingleLineCodePart {
    String resultVarName;
    String arg1;
    String arg2;
    String arg3;
    String arg4;

    public PackColor(String resultVarName, String arg1, String arg2, String arg3, String arg4){
        this.resultVarName = resultVarName;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;
        this.arg4 = arg4;
    }

    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode){
        return String.format("packcolor %s %s %s %s",
                getVarNameWithPrefix(arg1, nameSpaceIndex, uncompiledCode),
                getVarNameWithPrefix(arg2, nameSpaceIndex, uncompiledCode),
                getVarNameWithPrefix(arg3, nameSpaceIndex, uncompiledCode),
                getVarNameWithPrefix(arg4, nameSpaceIndex, uncompiledCode)
        );
    }
}

