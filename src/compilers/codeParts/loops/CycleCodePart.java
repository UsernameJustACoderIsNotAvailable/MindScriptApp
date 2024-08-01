package compilers.codeParts.loops;

import compilers.codeParts.CodePart;
import compilers.codeParts.ComplexCodePart;

import java.util.ArrayList;
import java.util.List;

public abstract class CycleCodePart extends ComplexCodePart {
    protected List<CodePart> insideCode = new ArrayList<CodePart>();
    protected CycleCodePart(List<CodePart> insideCode)
    {
        this.insideCode = insideCode;
    }
}
