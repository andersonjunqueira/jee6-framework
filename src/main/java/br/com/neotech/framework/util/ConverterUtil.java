package br.com.neotech.framework.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.neotech.framework.excecao.NegocioException;

public class ConverterUtil {

    public static final String FORMATO_DATA_DD_MM_AAAA = "dd/MM/yyyy";

    protected static final String FORMATO_CPF = "$1.$2.$3-$4";
    protected static final String FORMATO_CNPJ = "$1.$2.$3/$4-$5";
    protected static final String FORMATO_CEP = "$1.$2-$3";
    protected static final String FORMATO_CNARH = "$1.$2.$3/$4";
    protected static final String FORMATO_TELEFONE = "$1-$2";
    protected static final String FORMATO_TELEFONE_DDD = "($1) $2-$3";
    protected static final String FORMATO_TELEFONE_0800 = "$1-$2-$3";
    protected static final String FORMATO_PROCESSOS = "$1.$2/$3-$4";
    protected static final String FORMATO_RESOLUCAO = "$1/$2";

    private static final int TAMANHO_TELEFONE8 = 8;
    private static final int TAMANHO_TELEFONE9 = 9;
    private static final int TAMANHO_TELEFONE10 = 10;

    private static final String FORMATO_NUMERICO = "#,###,##0.00";

    private ConverterUtil() {
    }

    public static Date converterStringToDate(String data, String formato) throws NegocioException {
        try {
            if (data != null && formato != null) {
                DateFormat formatter = new SimpleDateFormat(formato);
                return formatter.parse(data);
            }
        } catch (ParseException e) {
            throw new NegocioException(e);
        }

        return null;
    }

    public static String converterDateToString(Date data, String formato) {
        if (data != null && formato != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
            return dateFormat.format(data);
        }

        return null;
    }

    /**
     * Encapsulamento para limpar valores de mascara.
     *
     * @param valor
     *            String para retirar a máscara
     * @return valor sem máscara
     */
    public static String limpaMascara(String valor) {
        return valor.replaceAll("[\\s\\-\\(\\)\\/\\_\\.\\,]", "");
    }

    public static String aplicaMascaraProcessos(String valor) {
        String valorLimpo = limpaMascara(valor);
        return valorLimpo.replaceAll(PadraoValidacao.PADRAO_PROCESSOS, FORMATO_PROCESSOS);
    }

    public static String aplicaMascaraResolucao(String valor) {
        String valorLimpo = limpaMascara(valor);
        return valorLimpo.replaceAll(PadraoValidacao.PADRAO_RESOLUCAO, FORMATO_RESOLUCAO);
    }

    public static String aplicaMascaraCPF(String valor) {
        String valorLimpo = limpaMascara(valor);
        return valorLimpo.replaceAll(PadraoValidacao.PADRAO_CPF, FORMATO_CPF);
    }

    public static String aplicaMascaraCEP(String valor) {
        String valorLimpo = limpaMascara(valor);
        return valorLimpo.replaceAll(PadraoValidacao.PADRAO_CEP, FORMATO_CEP);
    }

    public static String aplicaMascaraCNPJ(String valor) {
        String valorLimpo = limpaMascara(valor);
        return valorLimpo.replaceAll(PadraoValidacao.PADRAO_CNPJ, FORMATO_CNPJ);
    }

    public static String aplicaMascaraCNARH(String valor) {
        String valorLimpo = limpaMascara(valor);
        return valorLimpo.replaceAll(PadraoValidacao.PADRAO_CNARH, FORMATO_CNARH);
    }

    public static String aplicaMascaraTelefone(String valor) {
        String valorLimpo = limpaMascara(valor);

        String formato = null;
        String padrao = null;

        if (valorLimpo.startsWith("0800")) {
            formato = FORMATO_TELEFONE_0800;
            padrao = PadraoValidacao.PADRAO_TELEFONE0800;

        } else {

            if (valorLimpo.length() < 10) {
                formato = FORMATO_TELEFONE;
            } else {
                formato = FORMATO_TELEFONE_DDD;
            }

            switch (valorLimpo.length()) {
            case TAMANHO_TELEFONE8:
                padrao = PadraoValidacao.PADRAO_TELEFONE8;
                break;
            case TAMANHO_TELEFONE9:
                padrao = PadraoValidacao.PADRAO_TELEFONE9;
                break;
            case TAMANHO_TELEFONE10:
                padrao = PadraoValidacao.PADRAO_TELEFONE10;
                break;
            default:
                padrao = PadraoValidacao.PADRAO_TELEFONE11;
            }
        }
        return valorLimpo.replaceAll(padrao, formato);
    }

    public static String decimalToDMS(double dfDecimal, boolean latitude) {

        String dfHem;
        double dfFrac, dfDegree, dfMinute, dfSecond, dfSec;

        // Get degrees by chopping off at the decimal
        dfDegree = Math.floor(dfDecimal);

        // correction required since floor() is not the same as int()
        if (dfDegree < 0) {
            dfDegree = dfDegree + 1;
        }

        // Get fraction after the decimal
        dfFrac = Math.abs(dfDecimal - dfDegree);

        // Convert this fraction to seconds (without minutes)
        dfSec = dfFrac * 3600;

        // Determine number of whole minutes in the fraction
        dfMinute = Math.floor(dfSec / 60);

        // Put the remainder in seconds
        dfSecond = dfSec - dfMinute * 60;

        // Fix rounoff errors
        if (Math.rint(dfSecond) >= 60D) {
            dfMinute = dfMinute + 1;
            dfSecond = 0;
        }

        if (Math.rint(dfMinute) >= 60D) {
            if (dfDegree < 0) {
                dfDegree = dfDegree - 1;
            } else {
                dfDegree = dfDegree + 1;
            }
            dfMinute = 0;
        }

        dfHem = latitude ? "N" : "E";

        if (dfDegree < 0) {
            dfDegree *= -1;
            dfHem = latitude ? "S" : "W";
        }

        String out = dfHem + " " + (int) dfDegree + "\u00B0 " + (int) dfMinute + "' ";

        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.getDefault());
        dfs.setDecimalSeparator('.');
        dfs.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("#0.00", dfs);
        out += df.format(dfSecond) + "''";

        return out;
    }

    /*
     * Conversion DMS to decimal Input: latitude or longitude in the DMS format
     * ( example: W 79° 58' 55.903") Return: latitude or longitude in decimal
     * format hemisphereOUmeridien: W,E,S,N
     */
    public static double dmsToDecimal(String hemisphereOUmeridien, double degres, double minutes, double secondes) {

        double latOrLon = 0;
        double signe = 1.0;

        if ("W".equals(hemisphereOUmeridien) || "S".equals(hemisphereOUmeridien)) {
            signe = -1.0;
        }
        latOrLon = signe * (Math.floor(degres) + Math.floor(minutes) / 60.0 + secondes / 3600.0);

        return latOrLon;
    }

    /**
     * Formata o campo informado
     *
     * @param valor
     * @return <code>valorFormatado</code>
     */
    public static String formatarNumero(String valor) {
        return formatarNumero(valor, FORMATO_NUMERICO);
    }

    /**
     * Formata o campo, sendo informado o padrao
     *
     * @param valor
     * @param formato
     * @return <code>valorFormatado</code>
     */
    public static String formatarNumero(String valor, String formato) {
        return new DecimalFormat(formato).format(valor);
    }

}