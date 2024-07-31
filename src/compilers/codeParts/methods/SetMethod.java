package compilers.codeParts.methods;

import compilers.CompilerData;
import compilers.codeParts.CodePart;
import compilers.codeParts.ComplexCodePart;
import usefulMethods.PairIntString;

import java.util.List;

public class SetMethod  extends ComplexCodePart {
    String methodName, returnVarName;
    List<String> args;
    protected SetMethod(String methodName, List<CodePart> insideCode, List<String> args) {
        super(insideCode);
        this.methodName = methodName;
        this.args = args;
    }
    protected SetMethod(String methodName, List<CodePart> insideCode, String returnVarName, List<String> args) {
        super(insideCode);
        this.returnVarName = returnVarName;
        this.methodName = methodName;
        this.args = args;
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, CompilerData compilerData) {
        return null;
    }
}
