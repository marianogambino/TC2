package unimoron.ar.edu.util;

import java.util.Arrays;

/**
 * Created by mariano on 19/02/18.
 */

public class NumeroTelefonoUtil {


    public static String normalizarNumeroTel(String num){

        String numTel = "";
        num = num.trim();
        if(num.contains("-")){
            num = num.replace("-","");
        }
        if(num.contains(" ")){
            num = num.replace(" ","");
        }
        if(num.contains("+")){
            num = num.replace("+","");
        }

        if(num.length() > 8 ){
            char[] arrayNum = num.toCharArray();

            int max = num.length();
            String [] aux = new String[max];
            for(int i=1 ; i < 9 ; i++){
                aux[max-i] = String.valueOf(arrayNum[max-i]);
            }

            for(int i=0; i < max; i++){
                if(aux[i] != null){
                    numTel = numTel + aux[i];
                }
            }

        }
        return numTel;
    }

}
