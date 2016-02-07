package br.com.weblayer.weblayer_logistica.VIEW;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import br.com.weblayer.weblayer_logistica.ADAPTERS.SimulacaoFreteAdapter;
import br.com.weblayer.weblayer_logistica.DAO.DAOParametros;
import br.com.weblayer.weblayer_logistica.DTO.Resultado_SimulacaoFrete;
import br.com.weblayer.weblayer_logistica.R;
import br.com.weblayer.weblayer_logistica.SYNC.SYNCSimulacaoFrete;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Guilherme on 23/06/2015.
 */
public class SimulacaoFrete_Resultado  extends Activity {

    SingletonWeblayer sTon = SingletonWeblayer.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulacaofrete_resultado);

        GsonBuilder jSonBuilder = new GsonBuilder();
        Gson jSon = new Gson();

        SimulacaoFrete.SimulacaoFrete_Transf obj = jSon.fromJson(getQueryString().toString(), SimulacaoFrete.SimulacaoFrete_Transf.class);

        if(obj != null)
        {
            CarregarSimulacaoFrete(obj.getId_empresa(), obj.getNr_volume(), obj.getVl_nf(), obj.getPeso_nf(), obj.getOrigem(), obj.getDestino());
        }
    }

    public String getQueryString()
    {
        Intent _in = this.getIntent();

        String _ret = "";

        String _ret1 = _in.getExtras().get("obj").toString();

        if(_ret1 != null)
        {
            _ret = _ret1;
        }

        return _ret;
    }

    public void CarregarSimulacaoFrete(int id_empresa, int nr_volumenf, Double vl_nf, Double peso_nf, String origem, String destino)
    {
        try {
            SYNCSimulacaoFrete _syncFrete = new SYNCSimulacaoFrete();

            DAOParametros tabelaParametros = new DAOParametros(this.getApplicationContext());
            tabelaParametros.open();
            String SOAP_ADDRESS = new DAOParametros(this.getApplicationContext()).GetParametroWebservice("ds_webservice").getDs_valor();
            tabelaParametros.close();

            try {
                ArrayList<Resultado_SimulacaoFrete> _resultado = _syncFrete.Sincronizar(this.getApplicationContext(), id_empresa, nr_volumenf, vl_nf, peso_nf, origem, destino);

                SimulacaoFreteAdapter _adapter = new SimulacaoFreteAdapter(this.getApplicationContext(), R.layout.simulacaofrete_layoutresultado, R.id.txvSimulacaoFreteLayoutResultado_Transportadora, _resultado);

                ListView _listView = (ListView) findViewById(R.id.lstSimulacaoFreteResultado);
                _listView.setAdapter(_adapter);

                _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Resultado_SimulacaoFrete _obj = (Resultado_SimulacaoFrete)parent.getItemAtPosition(position);

                        String param = _obj.getDs_memoriacalculo();

                        Intent _intent = new Intent(getApplicationContext(), SimulacaoFrete_ResultadoDetalhes.class);
                        _intent.putExtra("obj", param);
                        startActivity(_intent);
                    }
                });
            }
            catch (Exception e)
            {
                if (e.getMessage().contains("Não existe tabela de preço vinculada."))
                {
                    Toast.makeText(getBaseContext(), "Não existe tabela de preço vinculada.", Toast.LENGTH_LONG).show();

                    Intent _i = new Intent(this.getApplicationContext(), SimulacaoFrete.class);
                    startActivity(_i);
                }
            }
        }
        catch (Exception ex) {

        }
    }
}
