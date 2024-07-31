package compilers;

import compilers.codeParts.CodePart;
import compilers.codeParts.otherLogics.End;
import usefulMethods.PairIntString;

import java.util.ArrayList;
import java.util.List;

public abstract class Compiler {
    public static String compile(UncompiledCode uncompiledCode){
        CompilerData compilerData = new CompilerData(new ArrayList<String>());
        StringBuilder compiledCode = new StringBuilder();
        int previousCPLastLineIndex = -1;
        //methods compile
        //...
        //codeParts compile
        for(int i = 0; i < uncompiledCode.codeParts.size(); i++){
            CodePart codePart = uncompiledCode.codeParts.get(i);
            compiledCode.append(codePart.getAsCompiledCode(previousCPLastLineIndex, 1, compilerData));
            previousCPLastLineIndex += codePart.linesCount;
        }
        //final "end" codePart (for codeParts like jump)
        compiledCode.append(new End().getAsCompiledCode(previousCPLastLineIndex, 1, compilerData));
        //return
        return compiledCode.toString();
    }
}
