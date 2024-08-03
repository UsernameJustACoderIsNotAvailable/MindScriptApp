package compilers;

import compilers.codeParts.CodePart;
import compilers.codeParts.methods.Method;
import compilers.codeParts.otherLogics.End;
import compilers.codeParts.otherLogics.Jump;

import java.util.*;

public class UncompiledCode {
    List<Method> methods = new ArrayList<Method>();
    List<CodePart> codeParts = new ArrayList<CodePart>();

    //other data
    public List<String> globalVars = new ArrayList<String>();

    public UncompiledCode(ArrayList<Method> methods, ArrayList<CodePart> codeParts){
        int CodePartsAndMethodsCurrentLine = -1;// это нужно, чтобы позже посчитать, на какую строчку нам ссылаться при вызове методов
        for(CodePart codePart: codeParts){
            CodePartsAndMethodsCurrentLine += codePart.linesCount;
        }
        CodePartsAndMethodsCurrentLine += 1; // не забываем про "end" codePart between codeParts and methods
        this.codeParts = codeParts;
        this.methods = methods;
        for(int i = 0; i < methods.size(); i++){
            methods.get(i).setMethodData(i, CodePartsAndMethodsCurrentLine);
            CodePartsAndMethodsCurrentLine += methods.get(i).linesCount;
        }
    }

    public Method getMethodByName(String methodName){
        for (Method method : methods) {
            if (Objects.equals(method.methodName, methodName)) {
                return method;
            }
        }
        System.out.println("method with name: " + methodName + " does not exists");
        return(null);
    }

    public String compile(){
        StringBuilder compiledCode = new StringBuilder();
        int previousCPLastLineIndex = -1;
        //codeParts compile
        for (CodePart codePart : codeParts) {
            compiledCode.append(codePart.getAsCompiledCode(previousCPLastLineIndex, 0, this));
            previousCPLastLineIndex += codePart.linesCount;
        }
        //"end" codePart between codeParts and methods
        compiledCode.append(new End().getAsCompiledCode(previousCPLastLineIndex, 0, this));
        //methods compile
        for (Method method : methods) {
            compiledCode.append(method.getAsCompiledCode(previousCPLastLineIndex, method.methodNameSpace, this));
            previousCPLastLineIndex += method.linesCount;
        }
        //return
        return compiledCode.toString();
    }
}
