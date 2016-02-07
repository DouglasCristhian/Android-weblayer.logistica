package br.com.weblayer.weblayer_logistica.DTO;

import java.util.Date;

/**
 * Created by Guilherme on 22/01/2015.
 */
public class TB_DADOS {
    private int id;
    private String ds_visao;
    private String ds_titulo;
    private String ds_cor;
    private String ds_percentual_performance_minima;
    private String ds_percentual_performance;
    private String ds_percentual_notas_entregues;
    private String ds_numero_notas_total;
    private String ds_dias_media_atraso;
    private int nr_data_referencia;
    private Date dt_entrega;
    private String ds_destino;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Date getDt_entrega() {
        return dt_entrega;
    }
    public void setDt_entrega(Date dt_entrega) {
        this.dt_entrega = dt_entrega;
    }

    public int getNr_data_referencia() {
        return nr_data_referencia;
    }
    public void setNr_data_referencia(int nr_data_referencia) {
        this.nr_data_referencia = nr_data_referencia;
    }

    public String getDs_cor() {
        return ds_cor;
    }
    public void setDs_cor(String ds_cor) {
        this.ds_cor = ds_cor;
    }

    public String getDs_destino() {
        return ds_destino;
    }
    public void setDs_destino(String ds_destino) {
        this.ds_destino = ds_destino;
    }

    public String getDs_dias_media_atraso() {
        return ds_dias_media_atraso;
    }
    public void setDs_dias_media_atraso(String ds_dias_media_atraso) {
        this.ds_dias_media_atraso = ds_dias_media_atraso;
    }

    public String getDs_numero_notas_total() {
        return ds_numero_notas_total;
    }
    public void setDs_numero_notas_total(String ds_numero_notas_total) {
        this.ds_numero_notas_total = ds_numero_notas_total;
    }

    public String getDs_percentual_notas_entregues() {
        return ds_percentual_notas_entregues;
    }
    public void setDs_percentual_notas_entregues(String ds_percentual_notas_entregues) {
        this.ds_percentual_notas_entregues = ds_percentual_notas_entregues;
    }

    public String getDs_percentual_performance() {
        return ds_percentual_performance;
    }
    public void setDs_percentual_performance(String ds_percentual_performance) {
        this.ds_percentual_performance = ds_percentual_performance;
    }

    public String getDs_percentual_performance_minima() {
        return ds_percentual_performance_minima;
    }
    public void setDs_percentual_performance_minima(String ds_percentual_performance_minima) {
        this.ds_percentual_performance_minima = ds_percentual_performance_minima;
    }

    public String getDs_titulo() {
        return ds_titulo;
    }
    public void setDs_titulo(String ds_titulo) {
        this.ds_titulo = ds_titulo;
    }

    public String getDs_visao() {
        return ds_visao;
    }
    public void setDs_visao(String ds_visao) {
        this.ds_visao = ds_visao;
    }
}
