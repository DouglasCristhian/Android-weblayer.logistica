package br.com.weblayer.weblayer_logistica.ADAPTERS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.weblayer.weblayer_logistica.DTO.Nota;
import br.com.weblayer.weblayer_logistica.DTO.TB_DADOS;
import br.com.weblayer.weblayer_logistica.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guilherme on 04/02/2015.
 */
public class PesquisaNotaAdapter extends ArrayAdapter<Nota> {

    public PesquisaNotaAdapter(Context context, int resource, int textViewResourceId, List<Nota> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Nota _data = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pesquisanota_menu, parent, false);

        TextView _txvCliente = (TextView)convertView.findViewById(R.id.txvVwCliente);
        TextView _txvNota = (TextView)convertView.findViewById(R.id.txvVwNota);
        TextView _txvSerie = (TextView)convertView.findViewById(R.id.txvVwSerie);
        TextView _txvValor = (TextView)convertView.findViewById(R.id.txvVwValor);
        TextView _txvEntrega = (TextView)convertView.findViewById(R.id.txvVwEnrega);

        try {
            _txvCliente.setText(_data.getDs_cliente());
            _txvCliente.setTag(R.id.tagIdNotaParaEntrega, _data.getDs_numeronota());
            _txvNota.setText(_data.getDs_numeronota());
            _txvSerie.setText(_data.getDs_serienota());
            _txvValor.setText(_data.getDs_valor());

            if (_data.getDt_entrega() != null) {
                _txvEntrega.setText(new SimpleDateFormat("dd/MM/yyyy hh:mm").format(_data.getDt_entrega()));
                _txvEntrega.setTag(R.id.tagDataEntregaNula, "1");
            } else {
                _txvEntrega.setText("");
                _txvEntrega.setTag(R.id.tagDataEntregaNula, "0");
            }
        }
        catch (Exception ex)
        {
            String msg = ex.getMessage();
        }

        return convertView;
    }
}
