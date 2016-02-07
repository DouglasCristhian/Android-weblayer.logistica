package br.com.weblayer.weblayer_logistica.ADAPTERS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.weblayer.weblayer_logistica.DTO.TB_DADOS;
import br.com.weblayer.weblayer_logistica.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guilherme on 02/02/2015.
 */
public class PesquisaPerformanceAdapter extends ArrayAdapter<TB_DADOS> {

    public PesquisaPerformanceAdapter(Context context, int resource, int textViewResourceId, List<TB_DADOS> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        TB_DADOS _data = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pesquisaperformance_menu, parent, false);

        TextView _txvText = (TextView)convertView.findViewById(R.id.txvPesquisaPerformanceNome);

        _txvText.setText(_data.getDs_titulo());
        _txvText.setTag(R.id.tagIdPerformancePesquisa, _data.getId());

        return convertView;
    }
}
