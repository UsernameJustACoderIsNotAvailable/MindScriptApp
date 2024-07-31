package compilers.codeParts;

import java.util.ArrayList;
import java.util.List;

public abstract class ComplexCodePart extends CodePart{
    protected List<CodePart> insideCode = new ArrayList<CodePart>();
    protected List<CodePart> allCycleCodeParts = new ArrayList<CodePart>();
    protected ComplexCodePart(List<CodePart> insideCode)
    {
        this.insideCode = insideCode;
    }
}
