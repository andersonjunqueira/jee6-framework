package br.com.neotech.framework.excecao;

public interface ExceptionController {

    void setCurrentViewId(String viewId);
    void setCausaErro(String causa);
    void setStackErro(String stack);

}
