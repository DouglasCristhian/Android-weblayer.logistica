package br.com.weblayer.weblayer_logistica.DTO;

/**
 * Created by Guilherme on 22/01/2015.
 */
public class Login {
    private int id_empresa;
    private String ds_perfil;
    private int id_transportadora;
    private String ds_transportadora;
    private String ds_empresa;
    private int id_usuario;

    public int getId_empresa()
    {
        return this.id_empresa;
    }
    public void setId_empresa(int id_empresa1)
    {
        this.id_empresa = id_empresa1;
    }

    public String getDs_perfil()
    {
        return this.ds_perfil;
    }
    public void setDs_perfil(String ds_perfil1)
    {
        this.ds_perfil = ds_perfil1;
    }

    public int getId_transportadora()
    {
        return this.id_transportadora;
    }
    public void setId_transportadora(int id_transportadora1)
    {
        this.id_transportadora = id_transportadora1;
    }

    public String getDs_transportadora()
    {
        return this.ds_transportadora;
    }
    public void setDs_transportadora(String ds_transportadora1)
    {
        this.ds_transportadora = ds_transportadora1;
    }

    public String getDs_empresa()
    {
        return this.ds_empresa;
    }
    public void setDs_empresa(String ds_empresa1)
    {
        this.ds_empresa = ds_empresa1;
    }

    public int getId_usuario()
    {
        return this.id_usuario;
    }
    public void setId_usuario(int id_usuario1)
    {
        this.id_usuario = id_usuario1;
    }
}
