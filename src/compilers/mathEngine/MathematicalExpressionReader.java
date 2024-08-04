package compilers.mathEngine;

import compilers.UncompiledCode;
import compilers.codeParts.CodePart;
import compilers.codeParts.math.ComplexOperation;
import compilers.codeParts.math.Operation;
import compilers.codeParts.methods.CallMethod;
import compilers.codeParts.methods.Method;

import java.util.*;

import static compilers.Settings.helpVarString;
import static compilers.Settings.methodReturnVarNameString;

public interface MathematicalExpressionReader {
    public static ComplexOperation readExpression(String expression, MathData mathData){
        ComplexOperation finalComplexOperation = null;
        //methods
        List<CodePart> finalCodePartsList = new ArrayList<>();
        for(String methodString: findMethodsInExpression(expression, mathData)){
            CallMethod callMethod = getCallMethodCodePartFromString(methodString, mathData);
            finalCodePartsList.add(callMethod);
            expression = expression.replace(methodString, methodReturnVarNameString + callMethod.methodName);
        }
        //brackets expression
        ComplexOperation braketsExpressionComplexOperation = readBracketsExpression(expression, mathData);
        finalCodePartsList.addAll(braketsExpressionComplexOperation.insideOperations);
        finalComplexOperation = new ComplexOperation(
                finalCodePartsList,
                braketsExpressionComplexOperation.finalVarName,
                braketsExpressionComplexOperation.helpVarLastIndexPlusOne
                );
        return finalComplexOperation;
    }
    public static List<String> findMethodsInExpression(String expression, MathData mathData){
        List<String> methods =  new ArrayList<>();
        StringBuilder word = new StringBuilder();//потенциально имя метода
        boolean lastElementIsWord = mathData.wordsAndValuesChars.contains(String.valueOf(expression.charAt(0)));
        boolean currentCharIsInWord;
        if(lastElementIsWord){word.append(expression.charAt(0));}
        int i = 1;
        while(i < expression.length()){
            currentCharIsInWord = mathData.wordsAndValuesChars.contains(String.valueOf(expression.charAt(i)));
            if(currentCharIsInWord || expression.charAt(i) == ' '){
                if(currentCharIsInWord){lastElementIsWord = true;}
                word.append(expression.charAt(i));
                i++;
            }
            else if(expression.charAt(i) == '(' && lastElementIsWord){
                word.append(expression.charAt(i));
                int openedBracketsCount = 1;
                int closedBracketsCount = 0;
                i++;
                while (openedBracketsCount != closedBracketsCount && i < expression.length()){
                    if (expression.charAt(i) == '('){
                        openedBracketsCount ++;
                    }
                    else if (expression.charAt(i) == ')'){
                        closedBracketsCount ++;
                    }
                    word.append(expression.charAt(i));
                    i++;
                }
                if(openedBracketsCount == closedBracketsCount){
                    methods.add(word.toString());
                }
                else {
                    System.out.println("closedBracketsCount < openedBracketsCount for expression: " + expression);return null;/*error*/
                }
            }
            else {
                lastElementIsWord = false;
                word = new StringBuilder();
                i++;
            }
        }
        return methods;
    }
    static CallMethod getCallMethodCodePartFromString(String callMethodString, MathData mathData){
        //method name
        StringBuilder methodNameStringBuilder = new StringBuilder();
        int i = 0;
        while (callMethodString.charAt(i) != '(' && i < callMethodString.length()){
            methodNameStringBuilder.append(callMethodString.charAt(i));
            i++;
        }
        if(methodNameStringBuilder.isEmpty()){
            System.out.println("method name in empty for callMethodString: " + callMethodString);return null;
        }
        String methodName = methodNameStringBuilder.toString();
        while (methodName.contains(" ")){ methodName = methodName.replace(" ",  "");}
        //args
        StringBuilder argsStringBuilder = new StringBuilder();
        while (i < callMethodString.length()){
            argsStringBuilder.append(callMethodString.charAt(i));
            i++;
        }
        //delete left "("
        for(int i1 = 0; i1 < argsStringBuilder.length(); i1++){
            if(argsStringBuilder.charAt(i1) == '('){
                argsStringBuilder.deleteCharAt(i1);
                break;
            }
        }
        //delete right ")"
        for(int i1 = argsStringBuilder.length() - 1; i1 >= 0; i1--){
            if(argsStringBuilder.charAt(i1) == ')'){
                argsStringBuilder.deleteCharAt(i1);
                break;
            }
        }
        //split into args
        List<String> argsListString = new ArrayList<>();
        StringBuilder arg = new StringBuilder();
        int openedBracketsCount = 0;
        int closedBracketsCount = 0;
        for(int i1 = 0; i1 < argsStringBuilder.length(); i1++){
            if (argsStringBuilder.charAt(i1) == '('){
                openedBracketsCount ++;
                arg.append(argsStringBuilder.charAt(i1));
            }
            else if (argsStringBuilder.charAt(i1) == ')'){
                closedBracketsCount ++;
                arg.append(argsStringBuilder.charAt(i1));
            }
            else if(argsStringBuilder.charAt(i1) == ',') {
                if (openedBracketsCount == closedBracketsCount) {
                    argsListString.add(arg.toString());
                    arg = new StringBuilder();
                }
                else {
                    arg.append(argsStringBuilder.charAt(i1));
                }
            }
            else {
                arg.append(argsStringBuilder.charAt(i1));
            }
        }
        if(!arg.isEmpty()){argsListString.add(arg.toString());}
        //convert into ComplexOperation list
        List<ComplexOperation> args = new ArrayList<>();
        for(String argString: argsListString){
            args.add(readExpression(argString, mathData));
        }
        return new CallMethod(methodName, args);
    }
    //brackets expression
    private static ComplexOperation readBracketsExpression(String expression, MathData mathData){
        int helpVarCount = 0;
        ComplexOperation finalComplexOperation = null;
        while (expression.contains("(") || expression.contains(")")){
            String reviewExpression = expression;
            while (reviewExpression.contains("(") || reviewExpression.contains(")")){
                reviewExpression = getFirstInBracketsExpression(reviewExpression);
            }
            ComplexOperation complexOperation = readLineExpression(reviewExpression, mathData, helpVarCount);
            assert complexOperation != null;
            helpVarCount = complexOperation.helpVarLastIndexPlusOne;
            expression = expression.replace("(" + reviewExpression + ")", " " + complexOperation.finalVarName + " ");
            if(finalComplexOperation == null){finalComplexOperation = complexOperation;}
            else {finalComplexOperation.Add(complexOperation);}
        }
        ComplexOperation complexOperation = readLineExpression(expression, mathData, helpVarCount);
        assert complexOperation != null;
        helpVarCount = complexOperation.helpVarLastIndexPlusOne;
        if(finalComplexOperation == null){finalComplexOperation = complexOperation;}
        else {finalComplexOperation.Add(complexOperation);}
        return finalComplexOperation;
    }
    private static String getFirstInBracketsExpression(String expression){
        boolean firstBracketFound = false;
        int openedBracketsCount = 0;
        int closedBracketsCount = 0;
        StringBuilder currentExpression = new StringBuilder();
        for(int i = 0; i < expression.length(); i++){
            if (expression.charAt(i) == '('){
                if(!firstBracketFound){firstBracketFound = true;}
                else {currentExpression.append(expression.charAt(i));}
                openedBracketsCount ++;
            }
            else if (expression.charAt(i) == ')'){
                closedBracketsCount ++;
                if(firstBracketFound && openedBracketsCount == closedBracketsCount){
                    return currentExpression.toString();
                }
                else {currentExpression.append(expression.charAt(i));}
            }
            else if(firstBracketFound){
                currentExpression.append(expression.charAt(i));
            }
            if(firstBracketFound && openedBracketsCount < closedBracketsCount){
                System.out.println("openedBracketsCount < closedBracketsCount");return null;/*error*/
            }
        }
        return expression;
    }
    //line expression
    private static ComplexOperation readLineExpression(String expression, MathData mathData, int helpVarCount){
        List<CodePart> operations = new ArrayList<CodePart>();
        List<MathUnit> mathUnits = divideLineExpressionIntoMathUnits(expression, mathData);
        String resultVarName = null;
        if(mathUnits.size() == 1 && mathUnits.get(0).type == MathUnit.mathUnitType.wordOrValue){
            resultVarName = mathUnits.get(0).stringValue;
        }
        for (int priority = 0; priority <= mathData.operationsPrioritiesCount; priority++) {
            int operationIndex = 0;
            while (operationIndex < mathUnits.size()) {
                MathUnit mathUnit = mathUnits.get(operationIndex);
                if (mathUnit.type == MathUnit.mathUnitType.operator && mathUnit.operatorPriority == priority) {
                    //assignment
                    if (mathUnit.operationIsAssignment == Operation.operationIsAssignment.assignment) {
                        if (operationIndex == 0 || operationIndex == mathUnits.size() - 1) {
                            System.out.println("no args for operator: "+mathUnit.stringValue);return null;/*error*/
                        }
                        if (mathUnits.get(operationIndex - 1).type != MathUnit.mathUnitType.wordOrValue &&
                                mathUnits.get(operationIndex + 1).type != MathUnit.mathUnitType.wordOrValue) {
                            System.out.println("no args for operator: "+mathUnit.stringValue);return null;/*error*/
                        }
                        resultVarName = helpVarString + helpVarCount;
                        operations.add(new Operation(
                                mathUnits.get(operationIndex - 1).stringValue,
                                mathUnits.get(operationIndex + 1).stringValue,
                                mathUnit.operatorType,
                                Operation.operationIsAssignment.assignment
                        ));
                        mathUnits.remove(operationIndex + 1);
                        mathUnits.remove(operationIndex);
                        //operationIndex does not change
                    }
                    //binary
                    else if (mathUnit.operatorArgAmount == Operation.operatorArgAmount.binary) {
                        if (operationIndex == 0 || operationIndex == mathUnits.size() - 1) {
                            System.out.println("no args for operator: "+mathUnit.stringValue);return null;/*error*/
                        }
                        if (mathUnits.get(operationIndex - 1).type != MathUnit.mathUnitType.wordOrValue &&
                                mathUnits.get(operationIndex + 1).type != MathUnit.mathUnitType.wordOrValue) {
                            System.out.println("no args for operator: "+mathUnit.stringValue);return null;/*error*/
                        }
                        resultVarName = helpVarString + helpVarCount;
                        operations.add(new Operation(
                                resultVarName,
                                mathUnits.get(operationIndex - 1).stringValue,
                                mathUnit.operatorType,
                                mathUnits.get(operationIndex + 1).stringValue
                        ));
                        mathUnits.remove(operationIndex + 1);
                        mathUnits.remove(operationIndex);
                        mathUnits.set(operationIndex - 1, new MathUnit(resultVarName));
                        helpVarCount++;
                        //operationIndex does not change
                    }
                    //unary
                    else if (mathUnit.operatorArgAmount == Operation.operatorArgAmount.unary) {
                        if (mathUnit.operatorType == Operation.operatorType.not) {
                            if (operationIndex == mathUnits.size() - 1) {
                                System.out.println("no args for operator: "+mathUnit.stringValue);return null;/*error*/
                            }
                            if (mathUnits.get(operationIndex + 1).type != MathUnit.mathUnitType.wordOrValue) {
                                System.out.println("no args for operator: "+mathUnit.stringValue);return null;/*error*/
                            }
                            resultVarName = helpVarString + helpVarCount;
                            operations.add(new Operation(
                                    resultVarName,
                                    mathUnits.get(operationIndex + 1).stringValue,
                                    mathUnit.operatorType,
                                    Operation.operationIsAssignment.nonAssignment
                            ));
                            mathUnits.remove(operationIndex + 1);
                            mathUnits.set(operationIndex, new MathUnit(resultVarName));
                            helpVarCount++;
                            operationIndex++;
                        }
                    }
                }
                else {
                    operationIndex++;
                }
            }
        }
        return new ComplexOperation(operations, resultVarName, helpVarCount);
    }
    private static List<MathUnit> divideLineExpressionIntoMathUnits(String expression, MathData mathData){
        List<MathUnit> mathUnits = new ArrayList<MathUnit>();

        StringBuilder newExpression = putSpacesBetweenParts(expression, mathData);
        expression = newExpression.toString();
        while (expression.contains("  ")){ expression = expression.replace("  ",  " ");}
        
        String[] expressionParts = expression.split(" ");
        for (String mathUnitStr: expressionParts) {
            if(!mathUnitStr.isEmpty() && mathData.operators.contains(mathUnitStr)){
                mathUnits.add(new MathUnit(mathData.operatorsMap.get(mathUnitStr)));
            }//если это оператор, то получаем его копию из mathData по ключу (mathUnitStr)
            else if(!mathUnitStr.isEmpty()){
                mathUnits.add(new MathUnit(mathUnitStr));
            }//если нет, пользуемся альтернативным коструктором для MathUnit
        }
        return mathUnits;
    }

    private static StringBuilder putSpacesBetweenParts(String expression, MathData mathData) {
        StringBuilder newExpression = new StringBuilder();
        boolean previousCharIsInWord = mathData.wordsAndValuesChars.contains(String.valueOf(expression.charAt(0)));
        boolean currentCharIsInWord;
        newExpression.append(expression.charAt(0));
        for(int i = 1; i < expression.length(); i++){
            currentCharIsInWord = mathData.wordsAndValuesChars.contains(String.valueOf(expression.charAt(i)));
            if(previousCharIsInWord == currentCharIsInWord || expression.charAt(i) == ' '){
                newExpression.append(expression.charAt(i));
            }
            else {
                newExpression.append(" ");
                newExpression.append(expression.charAt(i));
            }
            previousCharIsInWord = currentCharIsInWord;
        }
        return newExpression;
    }
}