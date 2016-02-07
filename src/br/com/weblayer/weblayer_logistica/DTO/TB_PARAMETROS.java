package br.com.weblayer.weblayer_logistica.DTO;

/**
 * Created by Guilherme on 22/01/2015.
 */
public class TB_PARAMETROS {
    private int id_parametro;
    private String ds_valor;
    private String ds_parametro;

    public int getId_parametro()
    {
        return this.id_parametro;
    }
    public void setId_parametro(int id_parametro1)
    {
        this.id_parametro = id_parametro1;
    }

    public String getDs_valor()
    {
        return this.ds_valor;
    }
    public void setDs_valor(String ds_valor)
    {
        this.ds_valor = ds_valor;
    }

    public String getDs_parametro()
    {
        return this.ds_parametro;
    }
    public void setDs_parametro(String ds_parametro1)
    {
        this.ds_parametro = ds_parametro1;
    }
}
