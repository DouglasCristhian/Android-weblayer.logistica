package br.com.weblayer.weblayer_logistica.VIEW;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import br.com.weblayer.weblayer_logistica.DAO.DAOParametros;
import br.com.weblayer.weblayer_logistica.DTO.TB_PARAMETROS;
import br.com.weblayer.weblayer_logistica.SYNC.SYNCCidade;
import br.com.weblayer.weblayer_logistica.SYNC.SYNCDados;
import br.com.weblayer.weblayer_logistica.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Handler;
import java.lang.Thread;

/**
 * Created by Guilherme on 28/01/2015.
 */
public class Principal extends Activity {
    SingletonWeblayer sTon = SingletonWeblayer.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);
    }

    @Override
    public void onResume()
    {
        try {
            super.onResume();

            CarregarActivity();
        }
        catch (Exception e)
        {
            String msg = e.getMessage();
        }
    }

    public void Sair()
    {
        AlertDialog.Builder msg = new AlertDialog.Builder(Principal.this);
        msg.setMessage("Deseja sair da aplicação?");
        msg.setNegativeButton("Não", null);
        msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.exit(0);
            }
        });

        msg.show();
    }

    public void Sincronizar() throws Exception {
        ProgressDialog _progress;

        _progress = ProgressDialog.show(this, "Aguarde...", "Sincronizando...", true);

        int _transp = 0;

        if(sTon.getPerfil().getDs_perfil().toLowerCase().equals("admin"))
            _transp = 0;
        else
            _transp = sTon.getPerfil().getId_transportadora();

        try {
            SYNCCidade.Sincronizar(getApplicationContext());
            SYNCDados.SincronizarAtuais(getApplicationContext(), sTon.getPerfil().getId_empresa(), _transp, "TRANSPORTADORA");
            SYNCDados.SincronizarPassado(getApplicationContext(), sTon.getPerfil().getId_empresa(), _transp, "TRANSPORTADORA");
        } catch (Exception e) {
            e.printStackTrace();
        }

        DAOParametros _daoParam = new DAOParametros(getApplicationContext());
        TB_PARAMETROS _param = new TB_PARAMETROS();

        _daoParam.open();

        _param = _daoParam.GetParametroWebservice("dt_ultima_sincronizacao");

        _daoParam.close();

        if(_param == null)
            _param = new TB_PARAMETROS();

        Calendar _c = Calendar.getInstance();

        String _data = _c.getTime().toString();

        _param.setDs_parametro("dt_ultima_sincronizacao");
        _param.setDs_valor(_data);

        _daoParam.open();

        _daoParam.insertorupdate(_param);

        _daoParam.close();

        String _dataFinal = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(_c.getTime());

        _progress.dismiss();
    }

    public void CarregarActivity()
    {
        String _perfil = sTon.getPerfil().getDs_perfil();

        ArrayList<String> lstMenu = new ArrayList<String>();

        if(_perfil.toLowerCase().equals("transportador"))
            lstMenu.add("Informar Entrega");
        else
            lstMenu.add("Simulção de Frete");

        lstMenu.add("Performance do Transportador");
        lstMenu.add("Sincronizar");
        lstMenu.add("Sair");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.principal_menu, R.id.txvPrincipalNome, lstMenu);

        ListView lst = (ListView)findViewById(R.id.lstPrincipal);

        lst.setAdapter(adapter);

        lst.setTextFilterEnabled(true);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(i == 0)
                {
                    if(sTon.getPerfil().getDs_perfil().toLowerCase().equals("transportador"))
                    {
                        Intent _int = new Intent(getApplicationContext(), PesquisarNota.class);
                        startActivity(_int);

                        return;
                    }
                    else
                    {
                        Intent _int = new Intent(getApplicationContext(), SimulacaoFrete.class);
                        startActivity(_int);

                        return;
                    }
                }

                if(i == 1)
                {
                    Intent _int = new Intent(getApplicationContext(), PesquisaPerformance.class);
                    startActivity(_int);

                    return;
                }

                if(i == 2)
                {
                    try {
                        Sincronizar();

                        Toast.makeText(getBaseContext(), "Aplicativo sincronizado com sucesso!", Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return;
                }

                if(i == 3)
                {
                    Sair();

                    return;
                }
            }
        });
    }
}
