package br.com.neotech.framework.permissoes;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public abstract class Profile implements Serializable {

    private static final long serialVersionUID = 7745678791885415896L;

    @Getter
    private String clientId;

    @Getter
    private String nome;

    @Getter
    private String identificador;

    @Getter
    private String nomeExibicao;

    @Getter
    private String email;

    @Getter
    private Boolean ldap;

    @Getter
    private List<String> permissoes;

    @Getter
    private List<String> restricoes;

    @Getter @Setter
    private String perfilAcesso;

    public void set(String clientId, String identificador, String nome, String nomeExibicao, String email, boolean ldap,
        List<String> permissoes, List<String> restricoes) {
        this.nome = nome;
        this.nomeExibicao = nomeExibicao;
        this.identificador = identificador;
        this.email = email;
        this.ldap = ldap;
        this.permissoes = permissoes;
        this.restricoes = restricoes;
        this.clientId = clientId;
    }

    public boolean pode(String permissao) {
        return permissoes != null && permissoes.contains(permissao);
    }

    public boolean possuiRestricao(String restricao) {
        return getRestricoes() != null && getRestricoes().contains(restricao);
    }

}