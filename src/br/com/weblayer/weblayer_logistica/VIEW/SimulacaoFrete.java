package br.com.weblayer.weblayer_logistica.VIEW;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import br.com.weblayer.weblayer_logistica.ADAPTERS.PesquisaCidadeAdapter;
import br.com.weblayer.weblayer_logistica.DAO.DAOCidade;
import br.com.weblayer.weblayer_logistica.DTO.ResultadoDetalhe_SimulacaoFrete;
import br.com.weblayer.weblayer_logistica.DTO.Resultado_SimulacaoFrete;
import br.com.weblayer.weblayer_logistica.DTO.TB_CIDADE;
import br.com.weblayer.weblayer_logistica.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Guilherme on 06/02/2015.
 */
public class SimulacaoFrete extends Activity {

    SingletonWeblayer sTon = SingletonWeblayer.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulacaofrete);

        CarregarTela();
    }

    private void CarregarTela()
    {
        DAOCidade daoCidade = new DAOCidade(getApplicationContext());

        daoCidade.open();

        //ArrayList<TB_CIDADE> lstCidades = daoCidade.fillAll();

        ArrayList<String> lstEstados = daoCidade.ListarEstados();

        daoCidade.close();

        //ArrayAdapter<TB_CIDADE> adapterCidades = new PesquisaCidadeAdapter(getApplicationContext(), R.layout.simulacaofrete_layoutcidades, R.id.txvSimulacaoFreteLayoutCidades, lstCidades);

        ArrayAdapter<String> adapterEstados = new ArrayAdapter<String>(getApplicationContext(), R.layout.simulacaofrete_layoutestados, R.id.txvSimulacaoFreteLayoutEstados, lstEstados);

        Spinner spinnerEstadosO = (Spinner)findViewById(R.id.spnSimulacaoFreteEstadoOrigem);
        Spinner spinnerEstadosD = (Spinner)findViewById(R.id.spnSimulacaoFreteEstadoDestino);

        spinnerEstadosO.setAdapter(adapterEstados);
        spinnerEstadosD.setAdapter(adapterEstados);

        spinnerEstadosO.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DAOCidade _daoCidade = new DAOCidade(getApplicationContext());

                _daoCidade.open();

                String _estado = (String)parent.getItemAtPosition(position);

                ArrayList<TB_CIDADE> _lstCidades = _daoCidade.fillCidadePorEstado(_estado);

                PesquisaCidadeAdapter _adapter = new PesquisaCidadeAdapter(getApplicationContext(), R.layout.simulacaofrete_layoutcidades, R.id.txvSimulacaoFreteLayoutCidades, _lstCidades);
                _adapter.setDropDownViewResource(R.layout.simulacaofrete_layoutcidades);

                _daoCidade.close();

                Spinner _cOrigem = (Spinner)findViewById(R.id.spnSimulacaoFreteCidadeOrigem);
                _cOrigem.setAdapter(_adapter);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerEstadosD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DAOCidade _daoCidade = new DAOCidade(getApplicationContext());

                _daoCidade.open();

                String _estado = (String)parent.getItemAtPosition(position);

                ArrayList<TB_CIDADE> _lstCidades = _daoCidade.fillCidadePorEstado(_estado);

                PesquisaCidadeAdapter _adapter = new PesquisaCidadeAdapter(getApplicationContext(), R.layout.simulacaofrete_layoutcidades, R.id.txvSimulacaoFreteLayoutCidades, _lstCidades);

                _daoCidade.close();

                Spinner _cDestino = (Spinner)findViewById(R.id.spnSimulacaoFreteCidadeDestino);
                _cDestino.setAdapter(_adapter);
                //_cDestino.setOnItemSelectedListener(this);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void btnSimulacaoEnviar_Click(View v)
    {
        SimulacaoFrete_Transf _t = new SimulacaoFrete_Transf();

        EditText _edtValorNf = (EditText)findViewById(R.id.edtSimulacaoFreteValorNF);
        EditText _edtPesoNf = (EditText)findViewById(R.id.edtSimulacaoFretePesoNF);
        EditText _edtVolumeNf = (EditText)findViewById(R.id.edtSimulacaoVolume);

        Spinner _cDestino = (Spinner)findViewById(R.id.spnSimulacaoFreteCidadeDestino);
        TB_CIDADE _cidadeDestino = (TB_CIDADE)_cDestino.getSelectedItem();

        Spinner _cOrigem = (Spinner)findViewById(R.id.spnSimulacaoFreteCidadeOrigem);
        TB_CIDADE _cidadeOrigem = (TB_CIDADE)_cOrigem.getSelectedItem();

        _t.setId_empresa(sTon.getPerfil().getId_empresa());
        _t.setDestino(_cidadeDestino.getCd_codmun());
        _t.setOrigem(_cidadeOrigem.getCd_codmun());
        _t.setNr_volume(Integer.parseInt(_edtVolumeNf.getText().toString()));
        _t.setVl_nf(Double.parseDouble(_edtValorNf.getText().toString()));
        _t.setPeso_nf(Double.parseDouble(_edtPesoNf.getText().toString()));

        Gson jSon = new Gson();
        String param = jSon.toJson(_t);

        Intent _intent = new Intent(getApplicationContext(), SimulacaoFrete_Resultado.class);
        _intent.putExtra("obj", param);
        startActivity(_intent);
    }

    public class SimulacaoFrete_Transf
    {
        public int id_empresa;
        public int nr_volume;
        public Double vl_nf;
        public Double peso_nf;
        public String origem;
        public String destino;

        public int getId_empresa()
        {
            return id_empresa;
        }

        public int getNr_volume()
        {
            return nr_volume;
        }

        public Double getVl_nf()
        {
            return vl_nf;
        }

        public Double getPeso_nf()
        {
            return peso_nf;
        }

        public String getOrigem()
        {
            return origem;
        }

        public String getDestino()
        {
            return destino;
        }

        public void setId_empresa(int valor)
        {
            id_empresa = valor;
        }

        public void setNr_volume(int valor)
        {
            nr_volume = valor;
        }

        public void setVl_nf(Double valor)
        {
            vl_nf = valor;
        }

        public void setPeso_nf(Double valor)
        {
            peso_nf = valor;
        }

        public void setOrigem(String valor)
        {
            origem = valor;
        }

        public void setDestino(String valor)
        {
            destino = valor;
        }
    }
}