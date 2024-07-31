package compilers;

import compilers.codeParts.CodePart;
import compilers.codeParts.methods.SetMethod;
import usefulMethods.PairIntString;

import java.util.ArrayList;
import java.util.List;

public class UncompiledCode {
    List<SetMethod> methods = new ArrayList<SetMethod>();
    List<CodePart> codeParts = new ArrayList<CodePart>();
    StringBuilder compiledCode = new StringBuilder();
    int currentLineIndex = 0;

    public UncompiledCode(ArrayList<SetMethod> methods, ArrayList<CodePart> codeParts){
        this.methods = methods;
        this.codeParts = codeParts;
    }
}
