package compilers.codeParts.methods;

import compilers.CompilerData;
import compilers.codeParts.CodePart;
import compilers.codeParts.SingleLineCodePart;
import usefulMethods.PairIntString;

import java.util.List;

public class RunMethod  extends CodePart {
    String returnVar, methodName;
    int methodIndex;
    List<String> args;
    protected RunMethod(String methodName, List<String> args) {
        this.methodName = methodName;
        this.args = args;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, CompilerData compilerData) {
        return null;
    }
}
