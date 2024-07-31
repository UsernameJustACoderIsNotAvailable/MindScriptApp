package compilers.codeParts;

import compilers.CompilerData;
import usefulMethods.PairIntString;

public abstract class CodePart {
    public int linesCount; //должно быть определено в дочернем классе
    public abstract String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, CompilerData compilerData);
}
