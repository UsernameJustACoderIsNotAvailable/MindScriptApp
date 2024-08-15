import compilers.mindScriptCode.LineOfCode;
import mlogConstructors.UncompiledCode;
import mlogConstructors.codeParts.methods.Method;
import mlogConstructors.codeParts.methods.ReturnValueFromMethod;
import mlogConstructors.mathEngine.MathData;
import mlogConstructors.mathEngine.MathematicalExpressionReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static compilers.mindScriptCode.MindScriptCompiler.convertCodeIntoUncompiledCode;
import static mlogConstructors.mathEngine.MathematicalExpressionReader.readExpression;

public class Main {
    public static void main(String[] args) {
        System.out.println(convertCodeIntoUncompiledCode("").compile());
    }
}
