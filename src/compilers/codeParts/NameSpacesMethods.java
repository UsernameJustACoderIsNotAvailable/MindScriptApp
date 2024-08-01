package compilers.codeParts;

import java.util.List;

import static compilers.Settings.*;

public interface NameSpacesMethods {
    public static String getVarNameWithPrefix(String varName, int nameSpaceIndex, List<String> globalVars){
        varName = varName.replace(" ", "");
        if(isVarName(varName, globalVars)){
            return nameSpacePrefix + nameSpaceIndex + varName;
        }
        else {
            return varName;
        }
    }
    public static boolean isVarName(String name, List<String> globalVars){
        name = name.replace(" ", "");
        return !isLinkedBlockName(name) && !isNumeric(name) && !globalVars.contains(name) && !isSystemWord(name) && !mindustrySystemWords.contains(name);
    }
    public static boolean isLinkedBlockName(String name){
        name = name.replace(" ", "");
        if(!numbers.contains(String.valueOf(name.charAt(name.length()-1)))){return false;}
        int index = name.length() - 1;
        for (int i = name.length() - 1; i >= 0; i--){
            if(!numbers.contains(String.valueOf(name.charAt(i)))){
                index = i;
                break;
            }
        }
        StringBuilder word = new StringBuilder();
        for (int i = 0; i <= index; i ++){
            word.append(name.charAt(i));
        }
        return blockNames.contains(word.toString());
    }

    public static boolean isNumeric(String name){
        name = name.replace(" ", "");
        for (int i = 0; i < name.length(); i ++){
            if (!numbers.contains(String.valueOf(name.charAt(i)))){
                return false;
            }
        }
        return true;
    }
    public static boolean isSystemWord(String name){
        name = name.replace(" ", "");
        if(!numbers.contains(String.valueOf(name.charAt(name.length()-1)))){return false;}
        int index = name.length() - 1;
        for (int i = name.length() - 1; i >= 0; i--){
            if(!numbers.contains(String.valueOf(name.charAt(i)))){
                index = i;
                break;
            }
        }
        StringBuilder word = new StringBuilder();
        for (int i = 0; i <= index; i ++){
            word.append(name.charAt(i));
        }
        return systemWords.contains(word.toString());
    }
}
