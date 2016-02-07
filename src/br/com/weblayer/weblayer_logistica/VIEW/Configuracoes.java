package br.com.weblayer.weblayer_logistica.VIEW;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import br.com.weblayer.weblayer_logistica.DAO.DAOCidade;
import br.com.weblayer.weblayer_logistica.DAO.DAODados;
import br.com.weblayer.weblayer_logistica.DAO.DAOParametros;
import br.com.weblayer.weblayer_logistica.DTO.TB_PARAMETROS;
import br.com.weblayer.weblayer_logistica.R;
import android.widget.Toast;
import android.content.DialogInterface;

import java.util.List;

/**
 * Created by Guilherme on 23/01/2015.
 */
public class Configuracoes extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracoes);

        CarregarDadosBasicos();
    }

    public void btnSalvar_Click(View v)
    {
        try {
            InserirDadosBasicos();
        }
        catch (Exception ex) {
            Toast.makeText(getBaseContext(), String.format("Erro: {0}", ex.getMessage()), Toast.LENGTH_LONG).show();
        }
    }

    public void btnLimpar_Click(View v)
    {
        try {
            LimparDadosBasicos();
        }
        catch (Exception ex) {
            Toast.makeText(getBaseContext(), String.format("Erro: {0}", ex.getMessage()), Toast.LENGTH_LONG).show();
        }
    }

    public void btnTestarConexao_Click(View v)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if(activeNetworkInfo.isConnected())
        {
            Toast.makeText(getBaseContext(), "Internet OK.", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getBaseContext(), "Internet indisponível.", Toast.LENGTH_LONG).show();
        }
    }

    public void HabilitarCampos(boolean valor)
    {
        EditText _web = (EditText)findViewById(R.id.edtWebservice);

        _web.setEnabled(valor);
    }

    public void CarregarDadosBasicos()
    {
        br.com.weblayer.weblayer_logistica.DTO.TB_PARAMETROS _parametros = new br.com.weblayer.weblayer_logistica.DTO.TB_PARAMETROS();
        br.com.weblayer.weblayer_logistica.DAO.DAOParametros _parametrosDao = new br.com.weblayer.weblayer_logistica.DAO.DAOParametros(this.getApplicationContext());

        _parametrosDao.open();

        _parametros = _parametrosDao.GetParametroWebservice("ds_webservice");

        _parametrosDao.close();

        if(_parametros != null) {
            EditText _param = (EditText)findViewById(R.id.edtWebservice);
            _param.setText(_parametros.getDs_valor().replace("/mobile/Service.asmx?WSDL", ""));

            _parametros = _parametrosDao.GetParametroWebservice("dt_ultima_sincronizacao");

            if(_parametros != null) {
                _param = (EditText) findViewById(R.id.edtUltimaSincronizacao);

                _param.setText(_parametros.getDs_valor());
            }

            HabilitarCampos(false);
        }
        else {
            Toast.makeText(getBaseContext(), "Atenção! Telefone ainda não sincronizado.", Toast.LENGTH_LONG).show();

            HabilitarCampos(true);
        }
    }

    public void InserirDadosBasicos()
    {
        TB_PARAMETROS _parametro = new TB_PARAMETROS();
        DAOParametros _daoParametros = new DAOParametros(this.getApplicationContext());

        EditText _edtWebservice = (EditText)findViewById(R.id.edtWebservice);

        _parametro.setDs_parametro("ds_webservice");
        _parametro.setDs_valor(_edtWebservice.getText().toString() + "/mobile/Service.asmx?WSDL");

        try {
            _daoParametros.open();

            _daoParametros.insertorupdate(_parametro);

            _daoParametros.close();

            CarregarDadosBasicos();
        }
        catch (Exception ex){
            _daoParametros.close();

            Toast.makeText(getBaseContext(), String.format("Erro: {0}", ex.getMessage()), Toast.LENGTH_LONG).show();
        }
    }

    public void LimparDadosBasicos()
    {
        AlertDialog.Builder msg = new AlertDialog.Builder(Configuracoes.this);
        msg.setMessage("Deseja limpar os dados?");
        msg.setNegativeButton("Não", null);
        msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                br.com.weblayer.weblayer_logistica.DAO.DAOParametros _daoParametros = new br.com.weblayer.weblayer_logistica.DAO.DAOParametros(getApplicationContext());

                _daoParametros.open();

                TB_PARAMETROS _parametro = new TB_PARAMETROS();

                _parametro = _daoParametros.GetParametroWebservice("ds_webservice");

                if(_parametro != null) {
                    _daoParametros.delete(_parametro.getId_parametro());

                    EditText _text = (EditText) findViewById(R.id.edtWebservice);

                    _text.setText(_parametro.getDs_valor().replace("/mobile/Service.asmx?WSDL", ""));
                }

                //Limpa a tabela de Parâmetros
                _daoParametros.ClearTable();

                _daoParametros.close();

                //Limpa a tabela de Cidades
                br.com.weblayer.weblayer_logistica.DAO.DAOCidade _daoCidades = new DAOCidade(getApplicationContext());

                _daoCidades.open();

                _daoCidades.ClearTable();

                _daoCidades.close();

                //Limpa a tabela de Dados
                br.com.weblayer.weblayer_logistica.DAO.DAODados _daoDados = new DAODados(getApplicationContext());

                _daoDados.open();

                _daoDados.ClearTable();

                _daoDados.close();

                Toast.makeText(getBaseContext(), "Sucesso ao excluir os dados básicos!", Toast.LENGTH_LONG).show();

                CarregarDadosBasicos();
                //Intent it2 = new Intent(getBaseContext(), ListaActivity.class);
                //startActivity(it2);
            }
        });

        msg.show();
    }
}
