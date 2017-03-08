package br.com.neotech.framework.util;

import java.io.File;
import java.net.SocketException;
import java.util.Properties;

import br.com.neotech.framework.config.Ambiente;
import br.com.neotech.framework.config.ConfigCache;
import br.com.neotech.framework.config.ConfigSource;
import lombok.extern.log4j.Log4j;

@Log4j
public class StartupUtil {

    private static final String AMBIENTE_DESENVOLVIMENTO = "DEV";
    private static final String AMBIENTE_HOMOLOGACAO = "HMG";
    private static final String AMBIENTE_LOCAL = "LOCAL";
    private static final String AMBIENTE_QA = "QA";

    private static String[] LOGDIRS = new String[] {
        "app_server1A", // PROD 27
        "app_server2", // HOMOLOG 22 - PROD 28
        "app_server1", // DESENV 46 - HOMOLOG 21
        "AdminServer" // LOCAL
    };

    private StartupUtil() {
    }

    public static void prepareLog4j(String app, Properties log4jConfig) {

        // ACERTANDO O CAMINHO DO DIR DE LOGS
        String logDir = null;
        for(String s : LOGDIRS) {
            File f = new File("./servers/" + s + "/logs");
            if(f.exists()) {
                logDir = "./servers/" + s + "/logs";
                break;
            }
        }
        logDir = (logDir == null) ? "." : logDir;
        logDir += "/" + app + ".log";
        log4jConfig.put("log4j.appender.file.File", logDir);

        // CONFIGURANDO O NOME DA APLICAÇÃO NO LOG
        String prop = "log4j.appender.console.layout.ConversionPattern";
        String cp = log4jConfig.getProperty(prop);
        cp = cp.replace("{0}", app.toUpperCase());
        log4jConfig.put(prop, cp);

        prop = "log4j.appender.file.layout.ConversionPattern";
        cp = log4jConfig.getProperty(prop);
        cp = cp.replace("{0}", app.toUpperCase());
        log4jConfig.put(prop, cp);

    }

    public static void inicializaConfiguracoes(ConfigSource source) {
        ConfigCache.getInstance().setSource(source);
    }

    public static void inicializarAmbiente(String ambienteConfig) throws SocketException {
        inicializarAmbiente(ambienteConfig, null);
    }

    public static void inicializarAmbiente(String ambienteConfig, String forcarAmbiente) throws SocketException {

        String ambiente = forcarAmbiente;
        if(ambiente == null) {
            ConfigCache cc = ConfigCache.getInstance();
            ambiente = cc.get(ambienteConfig);
        }

        if(ambiente.equals(AMBIENTE_DESENVOLVIMENTO)) {
            Ambiente.getInstance().setDesenvolvimento();
            log.debug("[AMBIENTE] Desenvolvimento");

        } else if(ambiente.equals(AMBIENTE_HOMOLOGACAO)) {
            Ambiente.getInstance().setHomologacao();
            log.debug("[AMBIENTE] Homologação");

        } else if(ambiente.equals(AMBIENTE_LOCAL)) {
            Ambiente.getInstance().setLocal();
            log.debug("[AMBIENTE] LOCAL");

        } else if(ambiente.equals(AMBIENTE_QA)) {
            Ambiente.getInstance().setQA();
            log.debug("[AMBIENTE] QA");

        } else {
            Ambiente.getInstance().setProducao();
            log.debug("[AMBIENTE] Produção");
        }
    }

}
