package br.com.weblayer.weblayer_logistica.VIEW;

import br.com.weblayer.weblayer_logistica.DTO.*;
import br.com.weblayer.weblayer_logistica.DTO.Login;

import java.util.ArrayList;

/**
 * Created by Guilherme on 27/01/2015.
 */
public class SingletonWeblayer {

    private static SingletonWeblayer _instancia = new SingletonWeblayer();

    private SingletonWeblayer()
    {

    }

    public static SingletonWeblayer getInstance()
    {
        if(_instancia == null)
            _instancia = new SingletonWeblayer();

        return _instancia;
    }

    private boolean estaSincronizando;
    private Login perfil;
    private Nota nota;
    private ArrayList<Nota> lstNotas;
    private boolean atualizaCombosSimulacaoFrete;
    private boolean carregaTelaSimulacaoFrete;
    private ArrayList<TB_CIDADE> lstCidades;


    public boolean getEstaSincronizando() { return this.estaSincronizando; }
    public void setEstaSincronizando(boolean valor) { this.estaSincronizando = valor; }

    public Login getPerfil() { return this.perfil; }
    public void setPerfil(Login valor) { this.perfil = valor; }

    public Nota getNota() { return this.nota; }
    public void setNota(Nota valor) { this.nota = valor; }

    public ArrayList<Nota> getLstNotas() { return this.lstNotas; }
    public void setLstNotas(ArrayList<Nota> valor) { this.lstNotas = valor; }

    public boolean getAtualizaCombosSimulacaoFrete() { return this.atualizaCombosSimulacaoFrete; }
    public void setAtualizaCombosSimulacaoFrete(boolean valor) { this.atualizaCombosSimulacaoFrete = valor; }

    public boolean getCarregaTelaSimulacaoFrete() { return this.carregaTelaSimulacaoFrete; }
    public void setCarregaTelaSimulacaoFrete(boolean valor) { this.carregaTelaSimulacaoFrete = valor; }

    public ArrayList<TB_CIDADE> getLstCidades() { return this.lstCidades; }
    public void setLstCidades(ArrayList<TB_CIDADE> valor) { this.lstCidades = valor; }
}
