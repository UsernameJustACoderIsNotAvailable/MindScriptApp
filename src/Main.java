import compilers.Compiler;
import compilers.CompilerData;
import compilers.UncompiledCode;
import compilers.codeParts.CodePart;
import compilers.codeParts.loops.IfCycle;
import compilers.codeParts.math.ComplexOperation;
import compilers.codeParts.loops.ComplexForCycle;
import compilers.codeParts.math.Set;
import compilers.mathEngine.MathData;
import compilers.mathEngine.MathematicalExpressionReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UncompiledCode code;
        code = new UncompiledCode(
                new ArrayList<>(),
                new ArrayList<>(Arrays.asList(
                        new Set("c", "10"),
                        new IfCycle(
                                MathematicalExpressionReader.readBracketsExpression("c <= 11", new MathData()),
                                new ArrayList<>(List.of(MathematicalExpressionReader.readBracketsExpression("c += 1", new MathData())))
                        ),
                        new Set("c", "10")
                ))
        );
        Set setCodePart = new Set("var1", "112");
        System.out.println(Compiler.compile(code));
    }
}