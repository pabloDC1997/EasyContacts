package com.example.pablo.easycontacts.utils;

/**
 * Created by Pablo on 12/02/2017.
 */

public  class FormatterNumberUtils {
    private static final String F8 = "####-####";
    private static final String F9 = "# ####-####";
    private static final String F10 = "(##) ####-####";
    private static final String F11 = "(##) # ####-####";
    private static final String F13 = "### (##) ####-####";
    private static final String F14 = "### (##) # ####-####";

    public  static String formatterPhone(String number){

        switch (number.length()){
            case 8:
                return formattedEight(number);
            case 9:
                return formatterNine(number);
            case 10:
                return formattedTen(number);
            case 11:
                return formatterEleven(number);
            case 13:
                return formatterThirteen(number);
            case 14:
                return formatterFourteen(number);
            default:
                return number;
        }
    }

    private static String formatterEleven(String old) {
        char newNumber[] = F11.toCharArray();
        char oldNumber[] = old.toCharArray();

        for (int i=0, j=0; j< old.length() ; i++) {
            if(newNumber[i] == '#') {
                newNumber[i] = oldNumber[j];
                j++;
            }
        }

        return String.valueOf(newNumber);
    }

    private static String formatterNine(String old) {
        char newNumber[] = F9.toCharArray();
        char oldNumber[] = old.toCharArray();

        for (int i=0, j=0; j< old.length() ; i++) {
            if(newNumber[i] == '#') {
                newNumber[i] = oldNumber[j];
                j++;
            }
        }

        return String.valueOf(newNumber);
    }

    private static String formattedEight(String old) {
        char newNumber[] = F8.toCharArray();
        char oldNumber[] = old.toCharArray();

        for (int i=0, j=0; j< old.length() ; i++) {
            if(newNumber[i] == '#') {
                newNumber[i] = oldNumber[j];
                j++;
            }
        }
        return String.valueOf(newNumber);
    }

    private static String formattedTen(String old) {
        char newNumber[] = F10.toCharArray();
        char oldNumber[] = old.toCharArray();

        for (int i=0, j=0; j< old.length() ; i++) {
            if(newNumber[i] == '#') {
                newNumber[i] = oldNumber[j];
                j++;
            }
        }
        return String.valueOf(newNumber);
    }

    private static String formatterThirteen(String old) {
        char newNumber[] = F13.toCharArray();
        char oldNumber[] = old.toCharArray();

        for (int i=0, j=0; j< old.length() ; i++) {
            if(newNumber[i] == '#') {
                newNumber[i] = oldNumber[j];
                j++;
            }
        }
        return String.valueOf(newNumber);
    }

    private static String formatterFourteen(String old) {
        char newNumber[] = F14.toCharArray();
        char oldNumber[] = old.toCharArray();

        for (int i=0, j=0; j< old.length() ; i++) {
            if(newNumber[i] == '#') {
                newNumber[i] = oldNumber[j];
                j++;
            }
        }
        return String.valueOf(newNumber);
    }

}
