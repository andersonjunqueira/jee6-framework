package br.com.neotech.framework.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import lombok.extern.log4j.Log4j;

@Log4j
public class Resources {

    private final String bundleName;

    public Resources(String bundleName) {
        this.bundleName = bundleName;
    }

    /**
     * Recupera uma mensagem dos arquivos de properties do sistema.
     * @param key identificação da mensagen
     * @param params valores para substituicao na mensagem
     * @return String contendo o texto da mensagem desejada
     */
    public String get(String key, Object ... params) {

        try {
            ResourceBundle rb = getBundle();
            if(rb == null) {
                return "???" + key + "???";
            }

            String saida = rb.getString(key);
            return MessageFormat.format(saida, params);

        } catch(MissingResourceException ex) {
            log.warn(ex);
            return "???" + key + "???";
        }
    }

    protected ResourceBundle getBundle() throws MissingResourceException {
        return ResourceBundle.getBundle(bundleName, Locale.getDefault());
    }

    protected String getBundleName() {
        return bundleName;
    }

}
