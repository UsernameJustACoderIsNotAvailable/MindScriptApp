package compilers.codeParts.loops;

import compilers.codeParts.CodePart;
import compilers.codeParts.ComplexCodePart;
import compilers.mathEngine.MathData;

import java.util.ArrayList;
import java.util.List;

public abstract class CycleCodePart extends ComplexCodePart {
    protected List<CodePart> insideCode = new ArrayList<CodePart>();
    protected MathData mathData = new MathData();
    protected CycleCodePart(List<CodePart> insideCode, MathData mathData)
    {
        this.insideCode = insideCode;
        this.mathData = mathData;
    }
}
