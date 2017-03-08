package br.com.neotech.framework.util.diasuteis;

import java.util.Calendar;
import java.util.Date;

public class DataWrapper {

    private Calendar data;

    private Calendar inicio = Calendar.getInstance();
    private Calendar fim = Calendar.getInstance();
    private Calendar inicioIntervalo = Calendar.getInstance();
    private Calendar fimIntervalo = Calendar.getInstance();

    public DataWrapper(long millis) {
        data = Calendar.getInstance();
        data.setTimeInMillis(millis);
        prepararLimites();
    }

    public DataWrapper(Date date) {
        data = Calendar.getInstance();
        data.setTime(date);
        prepararLimites();
    }

    public DataWrapper(Calendar cal) {
        data = cal;
        prepararLimites();
    }

    private void prepararLimites() {

        inicio.setTime(data.getTime());
        inicio.set(Calendar.HOUR_OF_DAY, 8);
        inicio.set(Calendar.MINUTE, 0);
        inicio.set(Calendar.SECOND, 0);
        inicio.set(Calendar.MILLISECOND, 0);

        fim.setTime(inicio.getTime());
        fim.set(Calendar.HOUR_OF_DAY, 18);

        inicioIntervalo.setTime(inicio.getTime());
        inicioIntervalo.set(Calendar.HOUR_OF_DAY, 12);

        fimIntervalo.setTime(inicio.getTime());
        fimIntervalo.set(Calendar.HOUR_OF_DAY, 14);

    }

    public boolean isUtil() {
        return isUtil(false);
    }

    public boolean isUtil(boolean ignorarIntervalo) {
        if(ignorarIntervalo) {
            return isHoraUtil() && !isFimSemana();
        } else {
            return isHoraUtil() && !isHoraIntervalo() && !isFimSemana();
        }
    }

    private boolean isFimSemana() {
        return data.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
            data.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    private boolean isHoraIntervalo() {
        return data.after(inicioIntervalo) && data.before(fimIntervalo);
    }

    private boolean isHoraUtil() {
        return data.after(inicio) && data.before(fim);
    }

}
