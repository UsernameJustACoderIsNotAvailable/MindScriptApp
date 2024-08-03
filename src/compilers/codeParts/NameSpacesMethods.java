package compilers.codeParts;

import compilers.UncompiledCode;

import java.util.List;

import static compilers.Settings.*;

public interface NameSpacesMethods {
    public static String getVarNameWithPrefix(String varName, int nameSpaceIndex, UncompiledCode uncompiledCode){
        varName = varName.replace(" ", "");
        if(isVarName(varName, uncompiledCode)){
            return nameSpacePrefix + nameSpaceIndex + varName;
        }
        else {
            return varName;
        }
    }
    public static boolean isVarName(String name, UncompiledCode uncompiledCode){
        name = name.replace(" ", "");
        return !isLinkedBlockName(name) &&
                !isNumeric(name) &&
                !uncompiledCode.globalVars.contains(name) &&
                !isSystemWord(name) &&
                !mindustrySystemWords.contains(name) &&
                !hasPrefix(name);
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
    public static boolean hasPrefix(String name){
        if(name.length() <= nameSpacePrefix.length()){return false;}
        for(int i = 0; i < nameSpacePrefix.length(); i++){
            if(name.charAt(i) != nameSpacePrefix.charAt(i)){
                return false;
            }
        }
        if(!numbers.contains(String.valueOf(name.charAt(nameSpacePrefix.length())))){return false;}
        return true;
    }
}
