package br.com.weblayer.weblayer_logistica.DTO;

import java.util.Date;

/**
 * Created by Guilherme on 22/01/2015.
 */
public class Nota {
    private int id_nota;
    private String ds_numeronota;
    private String ds_serienota;
    private String ds_cliente;
    private String ds_valor;
    private Date dt_entrega;
    private String ds_destino;

    public int getId_nota()
    {
        return this.id_nota;
    }
    public void setId_nota(int id_nota1)
    {
        this.id_nota = id_nota1;
    }

    public String getDs_numeronota()
    {
        return this.ds_numeronota;
    }
    public void setDs_numeronota(String ds_numeronota1)
    {
        this.ds_numeronota = ds_numeronota1;
    }

    public String getDs_serienota()
    {
        return this.ds_serienota;
    }
    public void setDs_serienota(String ds_serienota1)
    {
        this.ds_serienota = ds_serienota1;
    }

    public String getDs_cliente()
    {
        return this.ds_cliente;
    }
    public void setDs_cliente(String ds_cliente1)
    {
        this.ds_cliente = ds_cliente1;
    }

    public String getDs_valor()
    {
        return this.ds_valor;
    }
    public void setDs_valor(String ds_valor1)
    {
        this.ds_valor = ds_valor1;
    }

    public Date getDt_entrega()
    {
        return this.dt_entrega;
    }
    public void setDt_entrega(Date dt_entrega1)
    {
        this.dt_entrega = dt_entrega1;
    }

    public String getDs_destino()
    {
        return this.ds_destino;
    }
    public void setDs_destino(String ds_destino1)
    {
        this.ds_destino = ds_destino1;
    }
}
