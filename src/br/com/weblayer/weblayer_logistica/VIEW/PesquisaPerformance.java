package br.com.weblayer.weblayer_logistica.VIEW;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import br.com.weblayer.weblayer_logistica.ADAPTERS.PesquisaPerformanceAdapter;
import br.com.weblayer.weblayer_logistica.DAO.DAODados;
import br.com.weblayer.weblayer_logistica.DTO.TB_DADOS;
import br.com.weblayer.weblayer_logistica.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guilherme on 30/01/2015.
 */
public class PesquisaPerformance extends Activity {

    SingletonWeblayer sTon = SingletonWeblayer.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pesquisaperformance);

        Carregar();
    }

    public void Carregar()
    {
        Spinner _spinner = (Spinner)findViewById(R.id.spnPeriodoFaturamento);

        String _perfil = sTon.getPerfil().getDs_perfil();

        ArrayList<String> lstMenu = new ArrayList<String>();

        lstMenu.add("Mês corrente");
        lstMenu.add("Mês anterior");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.pesquisaperformance_spinner, R.id.txvPesquisaPerformanceSpinner, lstMenu);
        _spinner.setAdapter(adapter);


        try {
            _spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    br.com.weblayer.weblayer_logistica.DAO.DAODados _daoDados = new DAODados(getApplicationContext());

                    _daoDados.open();

                    int _dateType = 0;

                    if(i == 1)
                        _dateType = -1;

                    List<TB_DADOS> lstDados = _daoDados.fillByDate(_dateType);

                    _daoDados.close();

                    PesquisaPerformanceAdapter _newAdapter = new PesquisaPerformanceAdapter(getApplicationContext(), R.layout.pesquisaperformance_menu, R.id.txvPesquisaPerformanceNome, lstDados);

                    ListView _listView = (ListView)findViewById(R.id.lstPesquisaPerformance);

                    _listView.setAdapter(_newAdapter);
                    _listView.setTextFilterEnabled(true);

                    _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            TB_DADOS _item = (TB_DADOS)adapterView.getItemAtPosition(i);

                            //TextView _txvText = (TextView)adapterView.findViewById(R.id.txvPesquisaPerformanceNome);

                            int _selectedId = _item.getId();

                            Intent in = new Intent(getApplicationContext(), PerformanceTransportador.class);
                            in.putExtra("id", _selectedId);
                            startActivity(in);
                        }
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        catch (Exception ex)
        {
            String msg = ex.getMessage();
        }
    }
}
