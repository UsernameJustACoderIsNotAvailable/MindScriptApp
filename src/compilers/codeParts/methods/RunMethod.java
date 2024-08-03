package compilers.codeParts.methods;

import compilers.UncompiledCode;
import compilers.codeParts.CodePart;
import compilers.codeParts.ComplexCodePart;
import compilers.codeParts.math.ComplexOperation;
import compilers.codeParts.math.Operation;
import compilers.codeParts.math.Set;

import java.util.List;

import static compilers.Settings.methodReturnLineString;
import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;
import static compilers.codeParts.math.Operation.operatorType;

public class RunMethod  extends ComplexCodePart {
    String returnVar, methodName;
    int methodIndex;
    List<ComplexOperation> ComplexOperationInArgs;
    public RunMethod(String methodName, List<ComplexOperation> ComplexOperationInArgs) {
        this.methodName = methodName;
        this.ComplexOperationInArgs = ComplexOperationInArgs;
        for(ComplexOperation complexOperation: ComplexOperationInArgs){
            linesCount += complexOperation.linesCount;
            linesCount += 1; // строчка чтобы приравнять аргумент к итоговому значению
        }
        linesCount += 2; // еще 2 строчки для вызова метода
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode) {
        Method methodToCall = uncompiledCode.getMethodByName(methodName);
        if(ComplexOperationInArgs.size() != methodToCall.args.size()){
            System.out.println(methodToCall.args.size() + " needed, provided " + ComplexOperationInArgs.size() + " for method, Named: " + methodName);
        }
        for(int i = 0; i < ComplexOperationInArgs.size(); i++){
            ComplexOperation complexOperation = ComplexOperationInArgs.get(i);
            allCycleCodeParts.add(complexOperation);
            allCycleCodeParts.add(new Set(getVarNameWithPrefix(methodToCall.args.get(i), methodToCall.methodNameSpace, uncompiledCode), complexOperation.finalVarName));
        }
        allCycleCodeParts.add(new Operation(
                methodReturnLineString + methodToCall.methodIndex,
                "@counter",
                operatorType.add,
                "1"
        ));
        allCycleCodeParts.add(new Set("@counter", String.valueOf(methodToCall.firstLine)));
        return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode);
    }
}
