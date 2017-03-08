package br.com.neotech.framework.permissoes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public abstract class PermissoesCache implements Serializable {

    private static final long serialVersionUID = 4098488170530594963L;

    private List<String> permissoes = new ArrayList<String>();

    public boolean pode(String permissao) {
        return permissoes.contains(permissao);
    }

    public void adicionaPermissao(String permissao) {
        if(!permissoes.contains(permissao)) {
            permissoes.add(permissao);
        }
    }

    public void adicionaPermissoes(List<String> perms) {
        for(String p : perms) {
            adicionaPermissao(p);
        }
    }

    public int tamanhoPermissoes() {
        return permissoes.size();
    }

    public void limparPermissoes() {
        permissoes.clear();
    }

}
