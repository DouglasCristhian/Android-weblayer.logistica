package br.com.weblayer.weblayer_logistica.DTO;

import java.util.List;

/**
 * Created by Guilherme on 22/01/2015.
 */
public class Resultado_SimulacaoFrete {
    private int id_transportadora;
    private String ds_transportadora;
    private double vl_frete;
    private double vl_frete_imposto;
    private String ds_memoriacalculo;
    private List<ResultadoDetalhe_SimulacaoFrete> Detalhes;

    public int getId_transportadora()
    {
        return this.id_transportadora;
    }
    public void setId_transportadora(int id_transportador1)
    {
        this.id_transportadora = id_transportador1;
    }

    public String getDs_transportadora()
    {
        return this.ds_transportadora;
    }
    public void setDs_transportadora(String ds_transportadora1)
    {
        this.ds_transportadora = ds_transportadora1;
    }

    public double getVl_frete()
    {
        return this.vl_frete;
    }
    public void setVl_frete(double vl_frete1)
    {
        this.vl_frete = vl_frete1;
    }

    public double getVl_frete_imposto()
    {
        return this.vl_frete_imposto;
    }
    public void setVl_frete_imposto(double vl_frete_imposto1)
    {
        this.vl_frete_imposto = vl_frete_imposto1;
    }

    public String getDs_memoriacalculo()
    {
        return this.ds_memoriacalculo;
    }
    public void setDs_memoriacalculo(String ds_memoriacalculo1)
    {
        this.ds_memoriacalculo = ds_memoriacalculo1;
    }

    public List<ResultadoDetalhe_SimulacaoFrete> getDetalhes()
    {
        return this.Detalhes;
    }
    public void setDetalhes(List<ResultadoDetalhe_SimulacaoFrete> Detalhes1)
    {
        this.Detalhes = Detalhes1;
    }
}
