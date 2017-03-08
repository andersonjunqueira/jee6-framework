package br.com.neotech.framework.excecao;

import javax.ejb.ApplicationException;

import lombok.Getter;

@ApplicationException(rollback=true)
public class FormularioException extends NegocioException {

    private static final long serialVersionUID = -7363518912680887141L;

    @Getter
    private final String campo;

    public FormularioException() {
        super();
        this.campo = null;
    }

    public FormularioException(String msg) {
        super(msg);
        this.campo = null;
    }

    public FormularioException(Throwable cause) {
        super(cause);
        this.campo = null;
    }

    public FormularioException(String msg, Throwable cause) {
        super(msg, cause);
        this.campo = null;
    }

    public FormularioException(String campo, String msg) {
        super(msg);
        this.campo = campo;
    }

    public FormularioException(String campo, String msg, Throwable cause) {
        super(msg, cause);
        this.campo = campo;
    }

}
