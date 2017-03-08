package br.com.neotech.framework.modelo;

import java.io.Serializable;

import lombok.Getter;
import lombok.NonNull;

public class JPAParameter implements Serializable {

    private static final long serialVersionUID = 324176362422969108L;

    @Getter
    private int index;

    @Getter
    private String name;

    @Getter
    private Serializable value;

    public JPAParameter(@NonNull String name, @NonNull Serializable value) {
        this.name = name;
        this.value = value;
    }

    public JPAParameter(@NonNull Integer index, @NonNull Serializable value) {
        this.index = index;
        this.value = value;
    }

}