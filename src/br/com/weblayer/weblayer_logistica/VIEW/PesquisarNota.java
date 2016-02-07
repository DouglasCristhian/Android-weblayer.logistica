package br.com.weblayer.weblayer_logistica.VIEW;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.*;
import br.com.weblayer.weblayer_logistica.ADAPTERS.PesquisaNotaAdapter;
import br.com.weblayer.weblayer_logistica.DTO.Nota;
import br.com.weblayer.weblayer_logistica.SYNC.SYNCNotas;
import br.com.weblayer.weblayer_logistica.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Guilherme on 26/01/2015.
 */
public class PesquisarNota extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pesquisanota);
    }

    SingletonWeblayer sTon = SingletonWeblayer.getInstance();

    public void btnBuscarNota_Click(View v) throws Exception {
        BuscarNotas();
    }

    public void BuscarNotas() throws Exception {
        try {
            SYNCNotas _syncNotas = new SYNCNotas();

            EditText pesquisa = (EditText) findViewById(R.id.edtNumeroNota);

            ArrayList<Nota> _notas = _syncNotas.Sincronizar(this.getApplicationContext(), sTon.getPerfil().getId_empresa(), sTon.getPerfil().getId_transportadora(), pesquisa.getText().toString());

            sTon.setLstNotas(_notas);

            PesquisaNotaAdapter _adapter = new PesquisaNotaAdapter(this.getApplicationContext(), R.layout.pesquisanota_menu, R.id.txvVwCliente, _notas);

            ListView _listView = (ListView) findViewById(R.id.lstPesquisaNota);

            _listView.setAdapter(_adapter);
            _listView.setTextFilterEnabled(true);

            _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Nota _data = (Nota)parent.getItemAtPosition(position);

                    sTon.setNota(_data);

                    Date _date = _data.getDt_entrega();

                    if(_date == null) {
                        Intent _i = new Intent(getApplicationContext(), ConfirmarEntrega.class);
                        _i.putExtra("id", _data.getId_nota());
                        startActivity(_i);
                    }
                    else {
                        Toast.makeText(getBaseContext(), "Nota já entregue.", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
        catch (Exception ex)
        {
            String msg = ex.getMessage();
        }
    }
}
