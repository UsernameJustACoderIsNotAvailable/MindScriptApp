import compilers.mindScriptCode.LineOfCode;
import mlogConstructors.UncompiledCode;
import mlogConstructors.codeParts.methods.Method;
import mlogConstructors.codeParts.methods.ReturnValueFromMethod;
import mlogConstructors.mathEngine.MathData;
import mlogConstructors.mathEngine.MathematicalExpressionReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static compilers.mindScriptCode.MindScriptCompiler.convertCodeIntoUncompiledCode;
import static mlogConstructors.mathEngine.MathematicalExpressionReader.readExpression;

public class Main {
    public static void main(String[] args) {
        UncompiledCode uncompiledCode;
        try {
            uncompiledCode = convertCodeIntoUncompiledCode("sensor(uTI, @unit, @totalItems)\n" +
                    "if(uTI == 0):\n" +
                    "  ucontrol(move, outx, outy)\n" +
                    "  ucontrol(itemTake, core, @copper, 999)\n" +
                    "  1 + ll(ds)");
            System.out.println(uncompiledCode.compile());
        }
        catch (IOException e){
            System.out.println(e.toString());
        }
    }
}
