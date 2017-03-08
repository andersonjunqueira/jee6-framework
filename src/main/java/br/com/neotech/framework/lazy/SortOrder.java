package br.com.neotech.framework.lazy;


public enum SortOrder {

    ASCENDING, DESCENDING;

    @Override
    public String toString() {
        switch (ordinal()) {
            case 0:
                return " ASC";
            case 1:
                return " DESC";
            default:
                return " ";
        }
    }
}