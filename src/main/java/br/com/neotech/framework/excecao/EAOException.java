package br.com.neotech.framework.excecao;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class EAOException extends SistemaException {

    private static final long serialVersionUID = 4509269150650458084L;

    public static final String ERR_PROBLEMA_INESPERADO = "sistema.erro.problemainesperado";

    public EAOException() {
        super();
    }

    public EAOException(String msg) {
        super(msg);
    }

    public EAOException(Throwable cause) {
        super(cause);
    }

    public EAOException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
