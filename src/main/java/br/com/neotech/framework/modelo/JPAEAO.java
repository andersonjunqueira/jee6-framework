package br.com.neotech.framework.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import br.com.neotech.framework.excecao.EAOException;


public interface JPAEAO<E extends GenericEntity<K>, K extends Serializable> {

    E consultarPorPk(K id) throws EAOException;
    List<E> consultarPorPk(K... id) throws EAOException;

    E consultarRegistro(String namedQuery) throws EAOException;
    E consultarRegistro(String namedQuery, JPAParameter... params) throws EAOException;
    E consultarRegistro(Query query) throws EAOException;
    E consultarRegistro(Query query, JPAParameter... params) throws EAOException;

    List<E> consultar(String namedQuery) throws EAOException;
    List<E> consultar(String namedQuery, JPAParameter... params) throws EAOException;
    List<E> consultar(String namedQuery, int inicio, int numRegistros, JPAParameter... params) throws EAOException;

    List<E> consultar(Query query) throws EAOException;
    List<E> consultar(Query query, JPAParameter... params) throws EAOException;
    List<E> consultar(Query query, int inicio, int numRegistros, JPAParameter... params) throws EAOException;


    int executar(String namedQuery) throws EAOException;
    int executar(String namedQuery, JPAParameter... params) throws EAOException;
    int executar(Query query) throws EAOException;
    int executar(Query query, JPAParameter... params) throws EAOException;

    K salvar(E entity) throws EAOException;
    void salvar(E... entity) throws EAOException;

    void excluir(E entity) throws EAOException;
    void excluir(E... entities) throws EAOException;

    void flush() throws EAOException;

}
