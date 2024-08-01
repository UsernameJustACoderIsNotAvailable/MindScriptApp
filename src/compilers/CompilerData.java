package compilers;

import java.util.List;
import java.util.Map;

public class CompilerData {
    public List<String> globalVars;
    public int lastMethodIndex;
    public Map<String, Integer> methodIndexByName;

    public CompilerData(List<String> globalVars, int lastMethodIndex, Map<String, Integer> methodIndexByName){
        this.globalVars = globalVars;
        this.lastMethodIndex = lastMethodIndex;
        this.methodIndexByName = methodIndexByName;
    }
}
