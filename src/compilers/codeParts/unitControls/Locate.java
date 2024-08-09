package compilers.codeParts.unitControls;

import compilers.UncompiledCode;
import compilers.codeParts.SingleLineCodePart;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;

public class Locate extends SingleLineCodePart {
    public enum objectToFindType{
        building, ore, spawn, damaged
    }
    public enum groupType{
        core, storage, generator, turret, factory, repair, battery, reactor
    }

    objectToFindType objectToFind;
    groupType group = groupType.core;
    String enemy = "true";
    String ore = "@copper";
    String outXVar;
    String outYVar;
    String foundVar;
    String returnedBuildingVar;
    public Locate(
            objectToFindType objectToFind,
            groupType group,
            String enemy,
            String outXVar, String outYVar,
            String foundVar,
            String returnedBuildingVar) {
        this.objectToFind = objectToFind;
        this.group = group;
        this.enemy = enemy;
        this.outXVar = outXVar;
        this.outYVar = outYVar;
        this.foundVar = foundVar;
        this.returnedBuildingVar = returnedBuildingVar;
    }//case objectToFind is building
    public Locate(
            objectToFindType objectToFind,
            String argument,
            String outXVar, String outYVar,
            String foundVar) {
        this.objectToFind = objectToFind;
        switch (objectToFind){
            case ore -> {this.ore = argument;}
            case spawn, damaged -> {this.returnedBuildingVar = argument;}
        }
        this.outXVar = outXVar;
        this.outYVar = outYVar;
        this.foundVar = foundVar;
    }//other cases


    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode) {
        return String.format("ulocate %s %s %s %s %s %s %s %s",
                objectToFind,
                group,
                getVarNameWithPrefix(enemy, nameSpaceIndex, uncompiledCode),
                getVarNameWithPrefix(ore, nameSpaceIndex, uncompiledCode),
                getVarNameWithPrefix(outXVar, nameSpaceIndex, uncompiledCode),
                getVarNameWithPrefix(outYVar, nameSpaceIndex, uncompiledCode),
                getVarNameWithPrefix(foundVar, nameSpaceIndex, uncompiledCode),
                getVarNameWithPrefix(returnedBuildingVar, nameSpaceIndex, uncompiledCode)
                );
    }
}
