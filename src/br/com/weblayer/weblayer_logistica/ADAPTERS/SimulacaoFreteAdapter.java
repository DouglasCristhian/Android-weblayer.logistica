package br.com.weblayer.weblayer_logistica.ADAPTERS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.weblayer.weblayer_logistica.DTO.Nota;
import br.com.weblayer.weblayer_logistica.DTO.Resultado_SimulacaoFrete;
import br.com.weblayer.weblayer_logistica.R;
import br.com.weblayer.weblayer_logistica.VIEW.SimulacaoFrete_LayoutResultado;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Guilherme on 22/06/2015.
 */
public class SimulacaoFreteAdapter extends ArrayAdapter<Resultado_SimulacaoFrete> {
    public SimulacaoFreteAdapter(Context context, int resource, int textViewResourceId, List<Resultado_SimulacaoFrete> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Resultado_SimulacaoFrete _data = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.simulacaofrete_layoutresultado, parent, false);

        TextView _txvTransportadora = (TextView)convertView.findViewById(R.id.txvSimulacaoFreteLayoutResultado_Transportadora);
        TextView _txvFrete = (TextView)convertView.findViewById(R.id.txvSimulacaoFreteLayoutResultado_FreteValor);
        TextView _txvFreteImposto = (TextView)convertView.findViewById(R.id.txvSimulacaoFreteLayoutResultado_FreteImpostoValor);

        try {
            Double _valorFrete = _data.getVl_frete();
            Double _valorFreteImposto = _data.getVl_frete_imposto();

            _txvTransportadora.setText(_data.getDs_transportadora());
            //_txvTransportadora.setTag(R.id.tagObjetoSimulacaoFrete, "aaaa");
            _txvFrete.setText("Frete: R$ " + _valorFrete.toString());
            _txvFreteImposto.setText("Frete + Imposto: R$ " + _valorFreteImposto.toString());
        }
        catch (Exception ex)
        {
            String msg = ex.getMessage();
        }

        return convertView;
    }
}
