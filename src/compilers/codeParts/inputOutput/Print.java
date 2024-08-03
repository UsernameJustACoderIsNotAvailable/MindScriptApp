package compilers.codeParts.inputOutput;

import compilers.UncompiledCode;
import compilers.codeParts.SingleLineCodePart;

public class Print extends SingleLineCodePart {
    String text;
    Print(String text)
    {
        this.text = text;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode){
        return String.format("print %s", text) + "\n";
    }
}
