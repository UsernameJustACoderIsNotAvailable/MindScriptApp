package compilers.codeParts.inputOutput;

import compilers.CompilerData;
import compilers.codeParts.CodePart;
import compilers.codeParts.SingleLineCodePart;
import usefulMethods.PairIntString;

public class Print extends SingleLineCodePart {
    String text;
    Print(String text)
    {
        this.text = text;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, CompilerData compilerData){
        return String.format("print %s", text) + "\n";
    }
}
