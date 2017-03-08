package br.com.neotech.framework.excecao;


public class CriptoException extends Exception {

    private static final long serialVersionUID = 7358932343309408814L;

    public CriptoException() {
        super();
    }

    public CriptoException(String msg) {
        super(msg);
    }

    public CriptoException(Throwable cause) {
        super(cause);
    }

    public CriptoException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
