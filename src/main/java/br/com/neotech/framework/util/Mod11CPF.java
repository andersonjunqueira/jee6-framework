package br.com.neotech.framework.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public final class Mod11CPF {

    private static final List<String> CPFS_INVALIDOS = Arrays.asList(new String[] {
        "00000000191", "00000000000", "11111111111", "22222222222",
        "33333333333", "44444444444", "55555555555", "66666666666",
        "77777777777", "88888888888", "99999999999" });

    private Mod11CPF() {
    }

    public static boolean isValido(String cpf) {

        boolean valido;

        String strCpf = cpf.trim().replace("[\\.\\-]", "");
        Pattern pattern = Pattern.compile("[0-9]{11}");
        valido = pattern.matcher(strCpf).matches();

        if(!valido) {
            return false;
        }

        valido = !CPFS_INVALIDOS.contains(strCpf);

        if(!valido) {
            return false;
        }

        String temp = strCpf.substring(0,9);
        temp += obterDV(temp);
        temp += obterDV(temp);

        return strCpf.equals(temp);

    }

    public static String obterDV(String cpf) {

        int soma = 0;
        int d = 0;
        int fator = cpf.length() + 1;
        String fonte = cpf;

        for (int i = 0; i < fonte.length(); i++) {
            d = fonte.charAt(i) - '0';
            soma += d * (fator--);
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