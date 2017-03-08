package br.com.neotech.framework.modelo;

import java.io.Serializable;

/**
 * Classe abstrata para extensão pelas entidades utilizadas pelo sistema.
 * Possui um parâmetro genérico ID que indica o tipo de dado da coluna chave primária da tabela.
 */
public abstract class GenericEntity<K extends Serializable> implements Serializable {

    private static final long serialVersionUID = -4833492543335464195L;

    /**
     * Retorna o <code>Id</code> da entidade
     * @return id da entidade
     */
    public abstract K getId();

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {

        if(this == obj) {
            return true;
        }

        if (getId() == null) {
            return false;
        }

        if (obj == null || !getClass().isAssignableFrom(obj.getClass())) {
            return false;
        }

        GenericEntity<K> other = (GenericEntity<K>)obj;

        return getId().equals(other.getId());

    }

    @Override
    public int hashCode() {
        final int prime = getClass().getName().hashCode();
        int result = prime + (getId() == null ? super.hashCode() : getId().hashCode());
        return result;
    }

}
