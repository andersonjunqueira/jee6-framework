package br.com.neotech.framework.excecao;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class SistemaException extends Exception {

    private static final long serialVersionUID = 7932906037871518427L;

    public SistemaException() {
        super();
    }

    public SistemaException(String msg) {
        super(msg);
    }

    public SistemaException(Throwable cause) {
        super(cause);
    }

    public SistemaException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
