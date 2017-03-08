package br.com.neotech.framework.config;

import java.io.Serializable;
import java.util.HashMap;

public class ConfigCache implements Serializable {

    private static final long serialVersionUID = -4806648601087890853L;

    private static ConfigCache instance;
    private HashMap<String, String> configs = new HashMap<String, String>();
    private transient ConfigSource source;

    private ConfigCache() {
    }

    public static ConfigCache getInstance() {
        if(instance == null) {
            instance = new ConfigCache();
        }
        return instance;
    }

    public String get(String chave) {
        String valor = configs.get(chave);
        if(valor == null && source != null) {
            valor = source.pesquisar(chave);
            configs.put("chave", valor);
        }
        return valor;
    }

    public void setSource(ConfigSource source) {
        this.source = source;
    }

    public void clear() {
        configs.clear();
    }

}
