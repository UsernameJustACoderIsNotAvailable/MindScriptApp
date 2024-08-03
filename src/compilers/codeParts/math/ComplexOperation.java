package compilers.codeParts.math;

import compilers.UncompiledCode;
import compilers.codeParts.CodePart;

import java.util.ArrayList;
import java.util.List;

public class ComplexOperation extends CodePart {
    public List<Operation> insideOperations = new ArrayList<Operation>();
    public String finalVarName;
    public int helpVarLastIndexPlusOne;

    public ComplexOperation(List<Operation> insideOperations, String finalVarName, int helpVarLastIndex){
        this.insideOperations = insideOperations;
        this.finalVarName = finalVarName;
        this.helpVarLastIndexPlusOne = helpVarLastIndex;
        linesCount = 0;
        for (CodePart insideOperation : insideOperations) {
            linesCount += insideOperation.linesCount;
        }
    }

    public void Add(ComplexOperation complexOperation){
        if(this.helpVarLastIndexPlusOne == complexOperation.helpVarLastIndexPlusOne && this.helpVarLastIndexPlusOne != 0){
            System.out.println("cannot add complexOperation with same helpVarLastIndexPlusOne");
            return;
        }
        if(this.helpVarLastIndexPlusOne >= complexOperation.helpVarLastIndexPlusOne){
            List<Operation> newInsideOperations = new ArrayList<>(complexOperation.insideOperations);
            newInsideOperations.addAll(insideOperations);
            insideOperations = newInsideOperations;
        }
        if(this.helpVarLastIndexPlusOne < complexOperation.helpVarLastIndexPlusOne){
            insideOperations.addAll(complexOperation.insideOperations);
            this.helpVarLastIndexPlusOne = complexOperation.helpVarLastIndexPlusOne;
            this.finalVarName = complexOperation.finalVarName;
        }
    }

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode) {
        StringBuilder resultCode = new StringBuilder();
        for(int i = 0; i < insideOperations.size(); i++){
            CodePart codePart = insideOperations.get(i);
            resultCode.append(codePart.getAsCompiledCode(previousCPLastLineIndex + i - 1, nameSpaceIndex, uncompiledCode));
        }
        return resultCode.toString();
    }
}
