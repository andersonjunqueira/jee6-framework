package br.com.neotech.framework.modelo;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.Query;

import br.com.neotech.framework.excecao.EAOException;
import lombok.NonNull;
import lombok.extern.log4j.Log4j;

@Log4j
public abstract class GenericJPAEAO<E extends GenericEntity<K>, K extends Serializable> implements JPAEAO<E, K> {

    private Class<E> entityClass;

    public abstract EntityManager getEntityManager();

    @Override
    @SuppressWarnings("unchecked")
    public E consultarPorPk(@NonNull K id) throws EAOException {
        try {
            final ParameterizedType type = (ParameterizedType)getClass().getGenericSuperclass();
            entityClass = (Class<E>)type.getActualTypeArguments()[0];

            return getEntityManager().find(entityClass, id);
        } catch(Exception ex) {
            log.error(ex);
            throw new EAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<E> consultarPorPk(@NonNull K... id) throws EAOException  {
        try {
            List<E> saida = new ArrayList<E>();
            for(K pk : id) {
                E registro = consultarPorPk(pk);
                if(registro != null) {
                    saida.add(registro);
                }
            }
            return saida;
        } catch(Exception ex) {
            log.error(ex);
            throw new EAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<E> consultar(@NonNull String namedQuery) throws EAOException  {
        return consultar(namedQuery, 0, 0, (JPAParameter[])null);
    }

    @Override
    public List<E> consultar(@NonNull String namedQuery, JPAParameter... params) throws EAOException  {
        return consultar(namedQuery, 0, 0, params);
    }

    @Override
    public List<E> consultar(String namedQuery, int inicio, int numRegistros, JPAParameter... params) throws EAOException  {
        Query query = getEntityManager().createNamedQuery(namedQuery);
        return consultar(query, inicio, numRegistros, params);
    }

    @Override
    public List<E> consultar(@NonNull Query query) throws EAOException  {
        return consultar(query, 0, 0, (JPAParameter[])null);
    }

    @Override
    public List<E> consultar(@NonNull Query query, JPAParameter... params) throws EAOException  {
        return consultar(query, 0, 0, params);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> consultar(@NonNull Query query, int inicio, int numRegistros, JPAParameter... params) throws EAOException  {
        try {
            if(numRegistros > 0) {
                query.setFirstResult(inicio);
                query.setMaxResults(numRegistros);
            }
            defineParameters(query, params);
            return query.getResultList();
        } catch (NoResultException e) {
            log.debug(e.getMessage(), e);
            return Collections.emptyList();
        } catch (NonUniqueResultException e) {
            log.debug(e.getMessage(), e);
            return Collections.emptyList();
        } catch(Exception ex) {
            log.error(ex);
            throw new EAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public E consultarRegistro(String namedQuery) throws EAOException {
        Query query = getEntityManager().createNamedQuery(namedQuery);
        return consultarRegistro(query, (JPAParameter[])null);
    }

    @Override
    public E consultarRegistro(String namedQuery, JPAParameter... params) throws EAOException {
        Query query = getEntityManager().createNamedQuery(namedQuery);
        return consultarRegistro(query, params);
    }

    @Override
    public E consultarRegistro(Query query) throws EAOException {
        return consultarRegistro(query, (JPAParameter[])null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public E consultarRegistro(Query query, JPAParameter... params) throws EAOException  {
        try {
            defineParameters(query, params);
            return (E)query.getSingleResult();
        } catch (NoResultException e) {
            log.warn(e.getMessage(), e);
            return null;
        } catch (NonUniqueResultException e) {
            log.warn(e.getMessage(), e);
            return null;
        } catch(Exception ex) {
            log.error(ex);
            throw new EAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public int executar(Query query, JPAParameter... params) throws EAOException  {
        try {
            if(params != null) {
                defineParameters(query, params);
            }
            return query.executeUpdate();
        } catch(Exception ex) {
            log.error(ex);
            throw new EAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public int executar(Query query) throws EAOException  {
        try {
            return executar(query, (JPAParameter[])null);
        } catch(Exception ex) {
            log.error(ex);
            throw new EAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public int executar(String namedQuery) throws EAOException  {
        try {
            Query query = getEntityManager().createNamedQuery(namedQuery);
            return executar(query, (JPAParameter[])null);
        } catch(Exception ex) {
            log.error(ex);
            throw new EAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public int executar(String namedQuery, JPAParameter... params) throws EAOException  {
        try {
            Query query = getEntityManager().createNamedQuery(namedQuery);
            return executar(query, params);
        } catch(Exception ex) {
            log.error(ex);
            throw new EAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public K salvar(@NonNull E entity) throws EAOException  {
        try {
            if(entity.getId() == null) {
                getEntityManager().persist(entity);
            } else {
                getEntityManager().merge(entity);
            }
            return entity.getId();
        } catch(Exception ex) {
            log.error(ex);
            throw new EAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public void salvar(@NonNull E... entities) throws EAOException  {
        try {
            for(E e : entities) {
                salvar(e);
            }
        } catch(Exception ex) {
            log.error(ex);
            throw new EAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public void excluir(@NonNull E entity) throws EAOException  {
        try {
            if (entity.getId() != null) {
                E e = consultarPorPk(entity.getId());
                getEntityManager().remove(e);
            }
        } catch(Exception ex) {
            log.error(ex);
            throw new EAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public void excluir(@NonNull E... entities) throws EAOException  {
        try {
            for(E e : entities) {
                excluir(e);
            }
        } catch(Exception ex) {
            log.error(ex);
            throw new EAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public void flush() throws EAOException {
        try {
            getEntityManager().flush();
        } catch(Exception ex) {
            log.error(ex);
            throw new EAOException(ex.getMessage(), ex);
        }
    }

    protected void defineParameters(Query query, JPAParameter... params) {
        if (params != null && params.length > 0) {
            for (JPAParameter parameter : params) {
                query.setParameter(parameter.getName(), parameter.getValue());
            }
        }
    }

    public void initialize(Collection<?> obj) {
        if(obj == null) {
            return;
        }

        PersistenceUnitUtil uu = getEntityManager().getEntityManagerFactory().getPersistenceUnitUtil();
        if(!uu.isLoaded(obj)) {
            obj.iterator().hasNext();
        }
    }

    protected String transformOraclePagingQuery(String query) {
        StringBuilder sql = new StringBuilder()
            .append("SELECT * FROM (SELECT ROWNUM RNUM, Q.* FROM ( ")
            .append(query)
            .append(") Q WHERE ROWNUM <= (:inicio + :pagina -1)) WHERE RNUM >= :inicio");
        return sql.toString();
    }

    protected String transformMSSqlPagingQuery(String query) {
        StringBuffer sql = new StringBuffer()
            .append("WITH PAGINATEDQUERY AS ( \n")
            .append("     SELECT Q.*, ROW_NUMBER() OVER (ORDER BY CURRENT_TIMESTAMP) as ROWNUM \n")
            .append("       FROM ( \n")
            .append(query.replaceFirst("SELECT", "SELECT TOP(?)")).append("\n")
            .append("            ) Q ) \n")
            .append("SELECT * \n")
            .append("  FROM PAGINATEDQUERY \n")
            .append(" WHERE ROWNUM >= ? \n")
            .append("   AND ROWNUM < ? \n");
        return sql.toString();
    }
}
