package br.com.neotech.framework.config;


public class Ambiente {

    private static Ambiente instance;

    private static enum Env {
        DESENVOLVIMENTO, HOMOLOGACAO, PRODUCAO, QA, LOCAL
    }

    private Env ambiente = Env.PRODUCAO;

    private Ambiente() {}

    public static Ambiente getInstance() {
        if(instance == null) {
            instance = new Ambiente();
        }
        return instance;
    }

    public boolean isProducao() {
        return ambiente == Env.PRODUCAO;
    }

    public void setProducao() {
        ambiente = Env.PRODUCAO;
    }

    public boolean isHomologacao() {
        return ambiente == Env.HOMOLOGACAO;
    }

    public void setHomologacao() {
        ambiente = Env.HOMOLOGACAO;
    }

    public boolean isDesenvolvimento() {
        return ambiente == Env.DESENVOLVIMENTO;
    }

    public void setDesenvolvimento() {
        ambiente = Env.DESENVOLVIMENTO;
    }

    public boolean isQA() {
        return ambiente == Env.QA;
    }

    public void setQA() {
        ambiente = Env.QA;
    }

    public boolean isLocal() {
        return ambiente == Env.LOCAL;
    }

    public void setLocal() {
        ambiente = Env.LOCAL;
    }
}
