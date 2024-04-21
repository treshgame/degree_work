package dev.university.degree.util;

public class CheckErrors {
    public static boolean isThereAnError(String[] errors){
        for(String error : errors){
            if(error != null && error.trim().length() > 0) return true;
        }
        return false;
    }
}
