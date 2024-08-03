import compilers.UncompiledCode;
import compilers.codeParts.loops.IfCycle;
import compilers.codeParts.math.Set;
import compilers.codeParts.methods.Method;
import compilers.codeParts.methods.ReturnValueFromMethod;
import compilers.codeParts.methods.RunMethod;
import compilers.mathEngine.MathData;
import compilers.mathEngine.MathematicalExpressionReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static compilers.Settings.methodReturnVarNameString;

public class Main {
    public static void main(String[] args) {
        UncompiledCode code;
        code = new UncompiledCode(
                new ArrayList<>(Arrays.asList(
                        new Method(
                                "myAwesomeMethod",
                                List.of("arg1", "arg2"),
                                List.of(
                                        MathematicalExpressionReader.readBracketsExpression("c = arg1 + arg2", new MathData()),
                                        new RunMethod(
                                                "myAnotherMethod",
                                                List.of()
                                        ),
                                        new Set("h", methodReturnVarNameString + 1),
                                        new ReturnValueFromMethod(
                                                MathematicalExpressionReader.readBracketsExpression("c + h", new MathData()),
                                                "myAwesomeMethod"
                                        )
                                )
                        ),
                        new Method(
                                "myAnotherMethod",
                                List.of(),
                                List.of(
                                        new ReturnValueFromMethod(
                                                MathematicalExpressionReader.readBracketsExpression("40", new MathData()),
                                                "myAnotherMethod"
                                        )
                                )
                        )
                )),
                new ArrayList<>(Arrays.asList(
                        new Set("a", "10"),
                        new RunMethod(
                                "myAwesomeMethod",
                                List.of(
                                        MathematicalExpressionReader.readBracketsExpression("a + 10", new MathData()),
                                        MathematicalExpressionReader.readBracketsExpression("a + 20", new MathData())
                                )
                                ),
                        new Set("c", methodReturnVarNameString + 0)
                ))
        );
        System.out.println(code.compile());
    }
}