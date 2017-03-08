package br.com.neotech.framework.lazy;

import java.util.ArrayList;
import java.util.List;


/**
 * Classe usada para manter uma lista paginada de objetos selecionáveis.
 * A classe genérica utilizada DEVE implementar o método equals() para a seleção funcionar corretamente.
 * @author Anderson Junqueira
 */
public abstract class SelectablePaginatedArrayList<T extends SelectableObject>
    extends PaginatedArrayList<T>
    implements SelectablePaginatedList<T> {

    private List<T> selection = new ArrayList<T>();

    /**
     * Construtor padrão da lista
     * @param datasource objeto usado para executar a consulta no método load.
     * @param pageSize tamanho da página. Valor padrão: 10.
     */
    public SelectablePaginatedArrayList(Object datasource, int pageSize) {
        super(datasource, pageSize);
    }

    @Override
    public void init() {
        super.init();
        clearSelection();
    }

    /**
     * Define o status de selecionado para os objetos retornados pelo método load().
     * Esse método deve ser invocado ao final do método load().
     * @param list classes que devem ter o seu status definido de acordo com a seleção.
     */
    public void checkSelection(List<T> list) {
        for(T o : list) {
            o.setSelected(selection.contains(o));
        }
    }

    /**
     * Reinicia a lista de objetos selecionados.
     */
    @Override
    public void clearSelection() {
        selection = new ArrayList<T>();
    }

    /**
     * Recupera a lista de objetos selecionados
     */
    @Override
    public List<T> getSelection() {
        return selection;
    }

    /**
     * Seleciona o objeto e o carrega na lista
     */
    @Override
    public void select(T obj) {
        if(obj.isSelected()) {
           selection.add(obj);
        } else {
           selection.remove(obj);
        }

    }

}
