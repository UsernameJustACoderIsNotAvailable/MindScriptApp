package compilers.codeParts.unitControls;

import compilers.UncompiledCode;
import compilers.codeParts.ComplexCodePart;
import compilers.codeParts.SingleLineCodePart;
import compilers.codeParts.operations.ComplexOperation;
import compilers.mathEngine.MathData;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;
import static compilers.mathEngine.MathematicalExpressionReader.readExpression;

public class Locate extends ComplexCodePart {
    public enum objectToFindType{
        building, ore, spawn, damaged
    }
    public enum groupType{
        core, storage, generator, turret, factory, repair, battery, reactor
    }

    objectToFindType objectToFind;
    groupType group = groupType.core;
    ComplexOperation enemy;
    String ore = "@copper";
    String outXVar;
    String outYVar;
    String foundVar;
    String returnedBuildingVar;
    public Locate(
            objectToFindType objectToFind,
            groupType group,
            String isEnemyExpression,
            String outXVar, String outYVar,
            String foundVar,
            String returnedBuildingVar,
            MathData mathData) {
        this.objectToFind = objectToFind;
        this.group = group;
        this.enemy = readExpression(isEnemyExpression, mathData);
        this.outXVar = outXVar;
        this.outYVar = outYVar;
        this.foundVar = foundVar;
        this.returnedBuildingVar = returnedBuildingVar;
        linesCount = 1 + enemy.linesCount;
    }//case objectToFind is building
    public Locate(
            objectToFindType objectToFind,
            String argument,
            String outXVar, String outYVar,
            String foundVar,
            MathData mathData) {
        this.objectToFind = objectToFind;
        switch (objectToFind){
            case ore -> {this.ore = argument;}
            case spawn, damaged -> {this.returnedBuildingVar = argument;}
        }
        this.enemy = readExpression("true", mathData);
        this.outXVar = outXVar;
        this.outYVar = outYVar;
        this.foundVar = foundVar;
        linesCount = 1 + enemy.linesCount;
    }//other cases


    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode) {
        allCycleCodeParts.add(enemy);
        return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                String.format("ulocate %s %s %s %s %s %s %s %s",
                        objectToFind,
                        group,
                        getVarNameWithPrefix(enemy.finalVarName, nameSpaceIndex, uncompiledCode),
                        getVarNameWithPrefix(ore, nameSpaceIndex, uncompiledCode),
                        getVarNameWithPrefix(outXVar, nameSpaceIndex, uncompiledCode),
                        getVarNameWithPrefix(outYVar, nameSpaceIndex, uncompiledCode),
                        getVarNameWithPrefix(foundVar, nameSpaceIndex, uncompiledCode),
                        getVarNameWithPrefix(returnedBuildingVar, nameSpaceIndex, uncompiledCode)
                );
    }
}
