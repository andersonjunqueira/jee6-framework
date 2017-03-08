package br.com.neotech.framework.util;

import java.util.regex.Pattern;

public final class Mod11CNARH {

    private Mod11CNARH() {
    }

    public static String gerarCNARH(String cnarh) {
        String saida = cnarh;
        saida += obterDV(saida);
        saida += obterDV(saida);
        return saida;
    }

    public static boolean isValido(String cnarh) {

        boolean valido;

        String strCnarh = cnarh.trim().replace("[\\.\\-]", "");
        Pattern pattern = Pattern.compile("[0-9]{12}");
        valido = pattern.matcher(strCnarh).matches();

        if(!valido) {
            return false;
        }

        String temp = strCnarh.substring(0,10);
        temp += obterDV(temp);
        temp += obterDV(temp);

        return strCnarh.equals(temp);

    }

    public static String obterDV(String cnarh) {

        int soma = 0;
        int d = 0;
        int fator = cnarh.length() == 10 ? 0 : 1;

        String fonte = "00" + cnarh;

        for (int i = 0; i < 12; i++ ) {
            d = fonte.charAt(i) - '0';
            soma += d * ((11 + fator - i) % 8 + 2);
        }

        if (fator == 1) {
            int digito = Integer.parseInt(obterDV(cnarh.substring(0, 10)));
            soma += digito * 2;
        }

        d = 11 - soma  % 11;

        if ( d > 9 ) {
            d = 0;
        }

        return Integer.toString(d);

    }

}