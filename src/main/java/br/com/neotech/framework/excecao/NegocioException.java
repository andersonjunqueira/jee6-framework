package br.com.neotech.framework.excecao;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class NegocioException extends Exception {

    private static final long serialVersionUID = -7198488978214118659L;

    public NegocioException() {
        super();
    }

    public NegocioException(String msg) {
        super(msg);
    }

    public NegocioException(Throwable cause) {
        super(cause);
    }

    public NegocioException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
