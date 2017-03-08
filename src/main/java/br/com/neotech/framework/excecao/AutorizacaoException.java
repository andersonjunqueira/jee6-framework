package br.com.neotech.framework.excecao;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class AutorizacaoException extends NegocioException {

    private static final long serialVersionUID = 7932906037871518427L;

    public AutorizacaoException() {
        super();
    }

    public AutorizacaoException(String msg) {
        super(msg);
    }

    public AutorizacaoException(Throwable cause) {
        super(cause);
    }

    public AutorizacaoException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
