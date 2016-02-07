package br.com.weblayer.weblayer_logistica.ADAPTERS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.weblayer.weblayer_logistica.DTO.Nota;
import br.com.weblayer.weblayer_logistica.DTO.TB_CIDADE;
import br.com.weblayer.weblayer_logistica.R;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Guilherme on 06/02/2015.
 */
public class PesquisaCidadeAdapter extends ArrayAdapter<TB_CIDADE> {
    public PesquisaCidadeAdapter(Context context, int resource, int textViewResourceId, List<TB_CIDADE> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        TB_CIDADE _data = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.simulacaofrete_layoutcidades, parent, false);

        TextView _txvCidade = (TextView)convertView.findViewById(R.id.txvSimulacaoFreteLayoutCidades);

        _txvCidade.setText(_data.getDs_nome().toString());

        return convertView;
    }
}
