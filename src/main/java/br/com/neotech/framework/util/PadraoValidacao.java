package br.com.neotech.framework.util;

public interface PadraoValidacao {

    String PADRAO_CPF = "([0-9]{3})([0-9]{3})([0-9]{3})([0-9]{2})";
    String PADRAO_CNPJ = "([0-9]{2})([0-9]{3})([0-9]{3})([0-9]{4})([0-9]{2})";
    String PADRAO_CEP = "([0-9]{2})([0-9]{3})([0-9]{3})";
    String PADRAO_CNARH = "([0-9]{2})([0-9]{1})([0-9]{7})([0-9]{2})";
    String PADRAO_TELEFONE8 = "([0-9]{4})([0-9]{4})";
    String PADRAO_TELEFONE9 = "([0-9]{5})([0-9]{4})";
    String PADRAO_TELEFONE10 = "([0-9]{2})([0-9]{4})([0-9]{4})";
    String PADRAO_TELEFONE11 = "([0-9]{2})([0-9]{5})([0-9]{4})";
    String PADRAO_TELEFONE0800 = "([0-9]{4})([0-9]{2})([0-9]{4})";
    String PADRAO_PROCESSOS = "([0-9]{5})([0-9]{6})([0-9]{4})([0-9]{2})";
    String PADRAO_RESOLUCAO = "([0-9]+)([0-9]{4})";
    String PADRAO_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

}
