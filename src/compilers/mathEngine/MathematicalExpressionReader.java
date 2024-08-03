package compilers.mathEngine;

import compilers.codeParts.math.ComplexOperation;
import compilers.codeParts.math.Operation;

import java.util.*;

import static compilers.Settings.helpVarString;

// все методы/функции должны быть удалены из выражения
public interface MathematicalExpressionReader {
    //line expression
    static ComplexOperation readLineExpression(String expression, MathData mathData, int helpVarCount){
        List<Operation> operations = new ArrayList<Operation>();
        List<MathUnit> mathUnits = divideLineExpression(expression, mathData);
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
    private static List<MathUnit> divideLineExpression(String expression, MathData mathData){
        List<MathUnit> mathUnits = new ArrayList<MathUnit>();

        StringBuilder newExpression = putSpacesBetweenParts(expression, mathData);
        expression = newExpression.toString();
        while (expression.contains("  ")){ expression = expression.replace("  ",  " ");}
        
        List<String> expressionParts = Arrays.asList(expression.split(" "));
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

    //brackets expression
    static ComplexOperation readBracketsExpression(String expression, MathData mathData){
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
    public static String getFirstInBracketsExpression(String expression){
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
}