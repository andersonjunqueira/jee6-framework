package br.com.neotech.framework.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {

    private ValidatorUtil() {
    }

    public static boolean validaData(String value) {
        Locale loc = Locale.getDefault();
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, loc);
        try {
            df.parse(value);
            return true;
        } catch(ParseException ex) {
            return false;
        }
    }

    public static boolean validaEmail(String value) {
        Pattern pattern = Pattern.compile(PadraoValidacao.PADRAO_EMAIL);
        Matcher matcher = pattern.matcher(value.toString());
        return matcher.matches();
    }

    public static boolean validaCNARH(String value) {
        return Mod11CNARH.isValido(value);
    }

    public static boolean validaCPF(String value) {
        return Mod11CPF.isValido(value);
    }

    public static boolean validaCNPJ(String value) {
        return Mod11CNPJ.isValido(value);
    }

    public static boolean validaCEP(String value) {
        Pattern pattern = Pattern.compile(PadraoValidacao.PADRAO_CEP);
        Matcher matcher = pattern.matcher(value.toString());
        return matcher.matches();
    }

    public static boolean validaTelefone(String value) {

        Pattern pattern = Pattern.compile(PadraoValidacao.PADRAO_TELEFONE10);
        Matcher matcher = pattern.matcher(value.toString());
        if(matcher.matches()) {
            return true;
        };

        pattern = Pattern.compile(PadraoValidacao.PADRAO_TELEFONE11);
        matcher = pattern.matcher(value.toString());
        if(matcher.matches()) {
            return true;
        };

        pattern = Pattern.compile(PadraoValidacao.PADRAO_TELEFONE0800);
        matcher = pattern.matcher(value.toString());
        if(matcher.matches()) {
            return true;
        };

        return false;
    }

}
