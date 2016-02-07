package br.com.weblayer.weblayer_logistica.VIEW;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import br.com.weblayer.weblayer_logistica.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Guilherme on 24/06/2015.
 */
public class SimulacaoFrete_ResultadoDetalhes extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulacaofrete_resultadodetalhes);
        String obj = getQueryString().toString();

        if(obj != null && obj != "")
        {
            TextView _txv = (TextView)findViewById(R.id.txvSimulacaoFreteResultadoDetalhes);

            _txv.setText(obj.replace("<BR/>", "\n").replace("<br/>", "\n"));
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
}
