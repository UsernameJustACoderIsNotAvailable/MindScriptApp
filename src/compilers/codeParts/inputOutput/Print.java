package compilers.codeParts.inputOutput;

import compilers.UncompiledCode;
import compilers.codeParts.SingleLineCodePart;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;

public class Print extends SingleLineCodePart {
    String text;
    Print(String text)
    {
        this.text = text;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode){
        return String.format("print %s", getVarNameWithPrefix(text, nameSpaceIndex, uncompiledCode)) + "\n";
    }
}
