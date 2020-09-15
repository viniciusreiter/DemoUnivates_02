package tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Globais {

    public static boolean validarEmail(String email) {
    boolean isEmailIdValid = false;
    if (email != null && email.length() > 0) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            isEmailIdValid = true;
        }
    }
    return isEmailIdValid;
}
    
    public static String retornaApenasNumeros(String valor){
        return valor.replaceAll("\\D+","");
    }
    
}
