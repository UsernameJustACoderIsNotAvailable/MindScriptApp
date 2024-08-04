import compilers.UncompiledCode;
import compilers.codeParts.math.Set;
import compilers.codeParts.methods.Method;
import compilers.codeParts.methods.ReturnValueFromMethod;
import compilers.codeParts.methods.CallMethod;
import compilers.mathEngine.MathData;
import compilers.mathEngine.MathematicalExpressionReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static compilers.Settings.methodReturnVarNameString;
import static compilers.mathEngine.MathematicalExpressionReader.findMethodsInExpression;
import static compilers.mathEngine.MathematicalExpressionReader.getCallMethodCodePartFromString;

public class Main {
    public static void main(String[] args) {
        UncompiledCode code;
        code = new UncompiledCode(
                new ArrayList<>(Arrays.asList(
                        new Method(
                                "myAwesomeMethod",
                                List.of("arg1", "arg2"),
                                List.of(
                                        new ReturnValueFromMethod(
                                                MathematicalExpressionReader.readExpression("arg1 + arg2", new MathData()),
                                                "myAwesomeMethod"
                                        )
                                )
                        )
                )),
                new ArrayList<>(Arrays.asList(
                        MathematicalExpressionReader.readExpression("x = myAwesomeMethod(90, myAwesomeMethod(9, 1))", new MathData())
                ))
        );
        System.out.println(code.compile());
    }
}