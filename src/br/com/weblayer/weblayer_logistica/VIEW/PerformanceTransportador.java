package br.com.weblayer.weblayer_logistica.VIEW;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import br.com.weblayer.weblayer_logistica.DAO.DAODados;
import br.com.weblayer.weblayer_logistica.DTO.TB_DADOS;
import br.com.weblayer.weblayer_logistica.R;
import org.codeandmagic.android.gauge.GaugeView;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;

import static br.com.weblayer.weblayer_logistica.R.id;

/**
 * Created by Guilherme on 02/02/2015.
 */
public class PerformanceTransportador extends Activity {

    SingletonWeblayer sTon = SingletonWeblayer.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.performancetransportador);

            CarregarPerformanceTransportador(Integer.parseInt(GetId()));
        }
        catch (Exception ex)
        {
            String e = ex.getMessage();
        }
    }

    public String GetId()
    {
        Intent _in = this.getIntent();

        String _ret = "";

        String _ret1 = _in.getExtras().get("id").toString();

        if(_ret1 != null)
        {
            _ret = _ret1;
        }

        return _ret;
    }

    private void CarregarGauge(float valor, String cor)
    {
        Gauge(valor, cor);
    }

    private void CarregarPerformanceTransportador(int id)
    {
        br.com.weblayer.weblayer_logistica.DTO.TB_DADOS _dados = new TB_DADOS();
        br.com.weblayer.weblayer_logistica.DAO.DAODados _daoDados = new DAODados(this.getApplicationContext());

        _daoDados.open();

        _dados = _daoDados.Get(id);

        _daoDados.close();

        TextView txvNome = (TextView) findViewById(R.id.txvPerfomanceTransportadorNome);
        txvNome.setText(_dados.getDs_titulo());

        TextView txvPerformance = (TextView)findViewById(R.id.txvPerfomanceTransportadorPerformance);
        txvPerformance.setText("Performance: " + _dados.getDs_percentual_performance());

        CarregarGauge(Float.parseFloat(_dados.getDs_percentual_performance().replace("%", "").replace(",", ".")), _dados.getDs_cor());

        TextView _txvPeriodoFaturamento = (TextView)findViewById(R.id.txvPerfomanceTransportadorFaturamentoValor);

        if (_dados.getNr_data_referencia() == 0)
        {
            Calendar _c = Calendar.getInstance();

            _c.set(Calendar.DAY_OF_MONTH, 1);

            String _dataInicial = new SimpleDateFormat("dd/MM/yyyy").format(_c.getTime());

            _c.set(Calendar.DAY_OF_MONTH, _c.getActualMaximum(Calendar.DAY_OF_MONTH));

            String _dataFinal = new SimpleDateFormat("dd/MM/yyyy").format(_c.getTime());

            _txvPeriodoFaturamento.setText("Faturamento: De " + _dataInicial + " até " + _dataFinal);
        }
        else
        {
            Calendar aCalendar = Calendar.getInstance();

            aCalendar.add(Calendar.MONTH, -1);

            aCalendar.set(Calendar.DATE, 1);

            Date firstDateOfPreviousMonth = aCalendar.getTime();

            aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));

            _txvPeriodoFaturamento.setText("Faturamento: De " + new SimpleDateFormat("dd/MM/yyyy").format(firstDateOfPreviousMonth) + " até " +
                    new SimpleDateFormat("dd/MM/yyyy").format(aCalendar.getTime()));
        }

        SetarData();
    }

    private void SetarData()
    {
        br.com.weblayer.weblayer_logistica.DTO.TB_PARAMETROS _parametros = new br.com.weblayer.weblayer_logistica.DTO.TB_PARAMETROS();
        br.com.weblayer.weblayer_logistica.DAO.DAOParametros _parametrosDao = new br.com.weblayer.weblayer_logistica.DAO.DAOParametros(this.getApplicationContext());

        _parametrosDao.open();

        _parametros = _parametrosDao.GetParametroWebservice("dt_ultima_sincronizacao");

        _parametrosDao.close();

        TextView _param = (TextView)findViewById(R.id.txvPerfomanceTransportadorUltimaAtualizacaoValor);

        if(_parametros != null) {
            _param.setText("Última atualização: " + new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(Date.parse(_parametros.getDs_valor())));
        }
    }

    private void Gauge(float valor, String cor)
    {
        GaugeView ggeVisao = (GaugeView) findViewById(R.id.gauge_view2);

        //this.ggeVisao.NeedleBrush = CheckTheme.ReturnGaugePointerColor;
        //this.ggeVisao.TickBrush = new System.Windows.Media.SolidColorBrush(Colors.DarkGray);

        //this.ggeVisao.ValueBrush = CheckTheme.ReturnColor;
        //this.ggeVisao.UnitBrush = CheckTheme.ReturnColor;

        ggeVisao.setTargetValue(valor);

        if (cor.toUpperCase() == "VERMELHO") {
            //  this.ggeVisao.TrailBrush = new System.Windows.Media.SolidColorBrush(Colors.Red);
        }
        else if (cor.toUpperCase() == "VERDE") {
            //  this.ggeVisao.TrailBrush = new System.Windows.Media.SolidColorBrush(Colors.Green);
        }
        else {
            //  this.ggeVisao.TrailBrush = new System.Windows.Media.SolidColorBrush(Colors.Yellow);
        }
    }
}
