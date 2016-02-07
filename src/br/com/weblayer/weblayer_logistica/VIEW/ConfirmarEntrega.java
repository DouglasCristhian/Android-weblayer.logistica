package br.com.weblayer.weblayer_logistica.VIEW;

import android.annotation.TargetApi;
import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Display;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import br.com.weblayer.weblayer_logistica.DTO.Resultado;
import br.com.weblayer.weblayer_logistica.R;
import br.com.weblayer.weblayer_logistica.SYNC.SYNCEntregarNota;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

/**
 * Created by Guilherme on 26/01/2015.
 */
public class ConfirmarEntrega extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmarentrega);

        Carregar();
    }

    SingletonWeblayer sTon = SingletonWeblayer.getInstance();

    public void Carregar()
    {
        TextView _txvNomeCliente = (TextView)findViewById(R.id.txvConfirmarEntregaNome);
        TextView _txvValor = (TextView)findViewById(R.id.txvConfirmarEntregaValor);
        TextView _txvNotaSerie = (TextView)findViewById(R.id.txvConfirmarEntregaNotaSerie);

        _txvNomeCliente.setText(sTon.getNota().getDs_cliente());
        _txvValor.setText(sTon.getNota().getDs_valor());
        _txvNotaSerie.setText(sTon.getNota().getDs_numeronota() + "/" + sTon.getNota().getDs_serienota());
    }

    public void btnConfirmarEntrega(View v) throws Exception {
        DatePicker datePicker = (DatePicker)findViewById(R.id.dtpConfirmarEntregaDataEntrega);

        Date _hoje = new Date();

        int year = datePicker.getYear();

        Date data = new Date((year - 1900), datePicker.getMonth(), datePicker.getDayOfMonth(), _hoje.getHours(), _hoje.getMinutes(), _hoje.getSeconds());

        AlertDialog.Builder msg = new AlertDialog.Builder(ConfirmarEntrega.this);
        msg.setMessage("Confirma a entrega da nota " + sTon.getNota().getDs_numeronota() + ", na data " + data + "??");
        msg.setNegativeButton("Não", null);
        msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    ConfirmarEntrega();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        msg.show();


    }

    private void ConfirmarEntrega() throws Exception {
        SYNCEntregarNota syncEntregarNota = new SYNCEntregarNota();
        Resultado objResult = new Resultado();

        DatePicker datePicker = (DatePicker)findViewById(R.id.dtpConfirmarEntregaDataEntrega);

        Date _hoje = new Date();

        int year = datePicker.getYear();

        Date data = new Date((year - 1900), datePicker.getMonth(), datePicker.getDayOfMonth(), _hoje.getHours(), _hoje.getMinutes(), _hoje.getSeconds());

        ArrayList<Resultado> _result = syncEntregarNota.Sincronizar(
                getApplicationContext(),
                sTon.getPerfil().getId_empresa(),
                sTon.getNota().getId_nota(),
                sTon.getPerfil().getId_usuario(),
                data,
                sTon.getNota().getDs_numeronota(),
                Build.VERSION.CODENAME,
                getIp(),
                getDeviceName(),
                getScreenSize()
        );

        Resultado resultado = (Resultado )_result.get(0);

        if(resultado != null) {
            if (resultado.getDs_resultado().toLowerCase().equals("ok")) {
                Toast.makeText(getBaseContext(), "Nota enviada com sucesso.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getBaseContext(), "Erro ao processar a nota. Mensagem: " + resultado.getDs_observacao(), Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(getBaseContext(), "Erro ao processar a nota. Não foram retornados resultados do WebService. " + resultado.getDs_observacao(), Toast.LENGTH_LONG).show();
        }

        Intent _i = new Intent(getApplicationContext(), PesquisarNota.class);
        startActivity(_i);
    }

    public String getScreenSize()
    {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        //display.getSize(size);
        int width = size.x;
        int height = size.y;

        return String.valueOf(width) + "x" + String.valueOf(height);
    }

    private static final String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        final char[] arr = str.toCharArray();
        boolean capitalizeNext = true;
        String phrase = "";
        for (final char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c);
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase += c;
        }
        return phrase;
    }

    /** Returns the consumer friendly device name */
    public static String getDeviceName() {
        final String manufacturer = Build.MANUFACTURER;
        final String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        if (manufacturer.equalsIgnoreCase("HTC")) {
            // make sure "HTC" is fully capitalized.
            return "HTC " + model;
        }
        return capitalize(manufacturer) + " " + model;
    }

    public String getIp()
    {
        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        return android.text.format.Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
    }
}
