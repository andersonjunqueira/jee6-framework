package br.com.neotech.framework.util;

import java.util.regex.Pattern;

public final class Mod11CNPJ {

    private Mod11CNPJ() {
    }

    public static boolean isValido(String cnpj) {

        boolean valido;

        String strCnpj = cnpj.trim().replace("[\\.\\-\\/]", "");
        Pattern pattern = Pattern.compile("[0-9]{14}");
        valido = pattern.matcher(strCnpj).matches();

        if(!valido) {
            return false;
        }

        String temp = strCnpj.substring(0,12);
        temp += obterDV(temp);
        temp += obterDV(temp);

        return strCnpj.equals(temp);

    }

    public static String obterDV(String cnpj) {

        int soma = 0;
        int d = 0;
        int fator = cnpj.length() - 7;
        String fonte = cnpj;

        for (int i = 0; i < fonte.length(); i++) {
            d = fonte.charAt(i) - '0';
            soma += d * (fator--);

            if(fator == 1) {
                fator = 9;
            }
        }

        d = soma % 11;
        if (d < 2) {
            d = 0;
        } else {
            d = 11 - d;
        }

        return Integer.toString(d);

    }

}