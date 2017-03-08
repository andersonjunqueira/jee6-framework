package br.com.neotech.framework.util.diasuteis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

@Named
public class DiasUteis {

    private final long SEGUNDO = 1000;
    private final long MINUTO = 60 * SEGUNDO;
    private final long HORA = 60 * MINUTO;
    private final String TIME_PATTERN = "(\\d{2}):(\\d{2})";

    private long horaInicial;
    private long horaFinal;

    @Setter
    private List<Feriado> listaFeriados;

    public void setHoraInicial(Date horaInicial) {
        this.horaInicial = horaInicial.getTime();
    }

    public Date getHoraFinal() {
        return new Date(horaFinal);
    }

    public void addHorasUteis(Double horas) {

        if(horaInicial == 0) {
            horaInicial = Calendar.getInstance().getTimeInMillis();
        }

        long horasInMillis = new Double(horas * HORA).longValue();

        Calendar hora = Calendar.getInstance();
        hora.setTimeInMillis(horaInicial);
        hora.set(Calendar.SECOND, 0);

        if(getFeriado(hora) != null) {
            //horasInMillis += MINUTO;
        }

        while(horasInMillis > 0) {

            // SOMA 1 MIN NA HORA INICIAL
            hora.add(Calendar.MINUTE, 1);

            DataWrapper d = new DataWrapper(hora);
            boolean util = false;
            if(getFeriado(hora) != null) {
                util = !isFeriado(hora) && d.isUtil(true);
            } else {
                util = d.isUtil();
            }

            // SE NÃO FOR UTIL, ADICIONA MIN NO CONTADOR
            if(util) {
                horasInMillis -= MINUTO;
            }

        }

        horaFinal = hora.getTimeInMillis();

    }

    private boolean isFeriado(Calendar hora) {
        Feriado f = getFeriado(hora);
        return f != null && isHoraFeriado(hora, f);
    }

    private Feriado getFeriado(Calendar hora) {
        if(listaFeriados == null || listaFeriados.isEmpty()) {
            return null;
        }
        for(Feriado fer: listaFeriados) {
            Calendar calf = Calendar.getInstance();
            calf.setTime(fer.getData());
            if (hora.get(Calendar.DAY_OF_MONTH) == calf.get(Calendar.DAY_OF_MONTH) &&
                hora.get(Calendar.MONTH) == calf.get(Calendar.MONTH) &&
                hora.get(Calendar.YEAR) == calf.get(Calendar.YEAR)) {
                return fer;
            }
        }
        return null;
    }

    private boolean isHoraFeriado(Calendar hora, Feriado feriado) {

        Calendar inicio = null;
        Calendar fim = null;

        Calendar cal = Calendar.getInstance();
        cal.setTime(feriado.getData());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        // DIA INTEIRO
        if(feriado.getHoraInicial() == null && feriado.getHoraFinal() == null) {
            inicio = cal;

        // TEM HORA PARA COMEÇAR
        } else if(feriado.getHoraInicial() != null) {
            String hour = feriado.getHoraInicial().replaceAll(TIME_PATTERN, "$1");
            String minute = feriado.getHoraInicial().replaceAll(TIME_PATTERN, "$2");
            cal.set(Calendar.HOUR_OF_DAY, new Integer(hour));
            cal.set(Calendar.MINUTE, new Integer(minute));
            inicio = cal;

        // TEM HORA PRA TERMINAR
        } else if(feriado.getHoraFinal() != null) {
            Calendar cf = Calendar.getInstance();
            cf.setTime(cal.getTime());

            String hour = feriado.getHoraFinal().replaceAll(TIME_PATTERN, "$1");
            String minute = feriado.getHoraFinal().replaceAll(TIME_PATTERN, "$2");
            cf.set(Calendar.HOUR_OF_DAY, new Integer(hour));
            cf.set(Calendar.MINUTE, new Integer(minute));
            cf.set(Calendar.SECOND, 0);
            fim = cf;
        }

        // É FERIADO QUANDO A HORA FOR POSTERIOR À HORA DE INICIO, POR EXEMPLO: NATAL
        if(inicio != null && hora.after(inicio)) {
            return true;
        }

        // É FERIADO QUANDO A HORA FOR ANTERIOR À HORA DE FIM, POR EXEMPLO: CARNAVAL
        if(fim != null && hora.before(fim)) {
            return true;
        }

        return false;
    }

    public static void main(String[] args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm");

        // FERIADOS
        List<Feriado> fs = new ArrayList<Feriado>();

        FeriadoImpl f = new FeriadoImpl();
        f.setData(sdf.parse("10/02/2016 00:00")); // carnaval
        f.setHoraFinal("12:00");
        f.setHoraFinal(null);
        fs.add(f);

        f = new FeriadoImpl();
        f.setData(sdf.parse("01/05/2016 00:00")); // dia do trabalho
        f.setHoraInicial(null);
        f.setHoraFinal(null);
        fs.add(f);

        f = new FeriadoImpl();
        f.setData(sdf.parse("24/12/2016 00:00")); // vespera de natal
        f.setHoraInicial("12:00");
        f.setHoraFinal(null);
        fs.add(f);

        f = new FeriadoImpl();
        f.setData(sdf.parse("25/12/2016 00:00")); // natal
        f.setHoraInicial(null);
        f.setHoraFinal(null);
        fs.add(f);

        // CALCULO

        Date agora = null;
        DiasUteis h = new DiasUteis();
        h.setListaFeriados(fs);

        agora = sdf.parse("10/02/2016 11:00"); // carnaval
        h.setHoraInicial(agora);
        h.addHorasUteis(7D);

        System.out.println(agora);
        System.out.print(h.getHoraFinal());
        System.out.println(" / R: 11/02/2016 09:00");
        System.out.println("--");

        agora = sdf.parse("24/12/2016 11:30"); // vespera de natal
        h.setHoraInicial(agora);
        h.addHorasUteis(4D);

        System.out.println(agora);
        System.out.print(h.getHoraFinal());
        System.out.println(" / R: 26/12/2016 12:00");
        System.out.println("--");


    }

}

class FeriadoImpl implements Feriado {
    @Getter @Setter
    private Date data;

    @Getter @Setter
    private String horaInicial;

    @Getter @Setter
    private String horaFinal;
}