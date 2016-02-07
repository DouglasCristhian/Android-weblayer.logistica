package br.com.weblayer.weblayer_logistica.DTO;

/**
 * Created by Guilherme on 22/01/2015.
 */
public class TB_CIDADE {
    @Override
    public String toString()
    {
        return ds_nome;
    }

    private int id;
    private String cd_uf;
    private String ds_nome;
    private int cd_localizacao;
    private Double vl_aliquota_iss;
    private String cd_cepinicial;
    private String cd_cepfinal;
    private String cd_codmun;
    private String cd_long;
    private String cd_lat;

    public int getId()
    {
        return this.id;
    }
    public void setId(int id1)
    {
        this.id = id1;
    }

    public String getCd_uf()
    {
        return this.cd_uf;
    }
    public void setCd_uf(String cd_uf1)
    {
        this.cd_uf = cd_uf1;
    }

    public String getDs_nome()
    {
        return this.ds_nome;
    }
    public void setDs_nome(String ds_nome1)
    {
        this.ds_nome = ds_nome1;
    }

    public int getCd_localizacao()
    {
        return this.cd_localizacao;
    }
    public void setCd_localizacao(int cd_localizacao1)
    {
        this.cd_localizacao = cd_localizacao1;
    }

    public Double getVl_aliquota_iss()
    {
        return this.vl_aliquota_iss;
    }
    public void setVl_aliquota_iss(Double vl_aliquota_iss1)
    {
        this.vl_aliquota_iss = vl_aliquota_iss1;
    }

    public String getCd_cepinicial()
    {
        return this.cd_cepinicial;
    }
    public void setCd_cepinicial(String cd_cepinicial1)
    {
        this.cd_cepinicial = cd_cepinicial1;
    }

    public String getCd_cepfinal()
    {
        return this.cd_cepfinal;
    }
    public void setCd_cepfinal(String cd_cepfinal1)
    {
        this.cd_cepfinal = cd_cepfinal1;
    }

    public String getCd_codmun()
    {
        return this.cd_codmun;
    }
    public void setCd_codmun(String cd_codmun1)
    {
        this.cd_codmun = cd_codmun1;
    }

    public String getCd_long()
    {
        return this.cd_long;
    }
    public void setCd_long(String cd_long1)
    {
        this.cd_long = cd_long1;
    }

    public String getCd_lat()
    {
        return this.cd_lat;
    }
    public void setCd_lat(String cd_lat1)
    {
        this.cd_lat = cd_lat1;
    }
}
