import compilers.UncompiledCode;
import compilers.codeParts.blockControls.GetLink;
import compilers.codeParts.methods.Method;
import compilers.codeParts.methods.ReturnValueFromMethod;
import compilers.mathEngine.MathData;
import compilers.mathEngine.MathematicalExpressionReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
                                                "arg1 + arg2",
                                                "myAwesomeMethod",
                                                new MathData()
                                        )
                                )
                        )
                )),
                new ArrayList<>(Arrays.asList(
                        MathematicalExpressionReader.readExpression("x = myAwesomeMethod(90 + 90, myAwesomeMethod(9, 1) + myAwesomeMethod(1, 2)) - myAwesomeMethod(1, 2)", new MathData())
                )),
                new MathData()
        );
        System.out.println(code.compile());
    }
}