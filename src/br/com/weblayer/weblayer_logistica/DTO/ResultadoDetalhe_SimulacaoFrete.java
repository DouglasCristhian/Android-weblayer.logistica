package br.com.weblayer.weblayer_logistica.DTO;

/**
 * Created by Guilherme on 22/01/2015.
 */
public class ResultadoDetalhe_SimulacaoFrete {
    private int id_elementocusto;
    private String ds_elementocusto;
    private double vl_frete;
    private double vl_frete_imposto;

    public int getId_elementocusto()
    {
        return this.id_elementocusto;
    }
    public void setId_elementocusto(int id_elementocusto1)
    {
        this.id_elementocusto = id_elementocusto1;
    }

    public String getDs_elementocusto()
    {
        return this.ds_elementocusto;
    }
    public void setDs_elementocusto(String ds_elementocusto1)
    {
        this.ds_elementocusto = ds_elementocusto1;
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
}
