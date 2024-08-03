package compilers.codeParts;

import compilers.UncompiledCode;

public abstract class CodePart {
    public int linesCount; //должно быть определено в дочернем классе
    public abstract String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode compilerData);
}
