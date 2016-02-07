package br.com.weblayer.weblayer_logistica.SYNC;

import java.lang.Integer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.widget.Toast;

import br.com.weblayer.weblayer_logistica.DAO.DAOCidade;
import br.com.weblayer.weblayer_logistica.DAO.DAODados;
import br.com.weblayer.weblayer_logistica.DAO.DAOParametros;
import br.com.weblayer.weblayer_logistica.DTO.TB_CIDADE;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import br.com.weblayer.weblayer_logistica.VIEW.SingletonWeblayer;

/**
 * Created by Guilherme on 23/01/2015.
 */
public class SYNCCidade {
    public final static String SOAP_ACTION = "http://www.weblayer.com.br/";
    public final static String WSDL_TARGET_NAMESPACE = "http://www.weblayer.com.br/";

    //public final static String SOAP_ADDRESS = "http://server.weblayer.com.br/gdc_logistica_teste/mobile/service.asmx";
    //public final static String SOAP_ADDRESS = "http://10.0.2.2:52634/Vendas.asmx";
    public SYNCCidade() {}

    public static ArrayList<TB_CIDADE> RetornaLista(Context context, String SOAP_ADDRESS,
                                                                              Integer id_empresa, Integer id_vendedor) throws Exception {

        String OPERATION_NAME = "BuscarCidades";

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS,10000);
        Object response = null;

        try {

            httpTransport.call(SOAP_ACTION + OPERATION_NAME, envelope);
            response = envelope.getResponse();

        } catch (Exception e) {

            //if (!weblayer.logistica.weblayer_logistica.TOOLBOX.Common.isNetWorkActive(context))
              //  throw new Exception("Rede não disponível");

            throw e;

        }

        if (response==null)
            return null;

        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        ArrayList<TB_CIDADE> Objetos;

        try
        {

            Type collectionType = new TypeToken<ArrayList<TB_CIDADE>>(){}.getType();
            Objetos = gson.fromJson(response.toString(), collectionType);

        }
        catch(Exception e)
        {
            throw e;
        }

        return Objetos;
    }

    public static void Sincronizar(Context context) throws Exception
    {
        SingletonWeblayer sTon = SingletonWeblayer.getInstance();

        DAOCidade tabelaCidade = new DAOCidade(context);
        tabelaCidade.open();

        List<TB_CIDADE> lstCidadesCad = tabelaCidade.fillAll();

        String perfil = sTon.getPerfil().getDs_perfil().toLowerCase();

        if(!perfil.equals("transportador")) {
            if (lstCidadesCad == null || lstCidadesCad.size() == 0) {
                String server = "";
                Integer idempresa = 0;
                Integer idvendedor = 0;

                DAOParametros tabelaParametros = new DAOParametros(context);
                tabelaParametros.open();
                String SOAP_ADDRESS = new DAOParametros(context).GetParametroWebservice("ds_webservice").getDs_valor();
                tabelaParametros.close();

                ArrayList<TB_CIDADE> itens = RetornaLista(context, SOAP_ADDRESS, idempresa, idvendedor);

                if (itens == null)
                    return;

                tabelaCidade.ClearTable();

                for (TB_CIDADE item : itens) {
                    tabelaCidade.insertorupdate(item);
                }
            }
        }

        tabelaCidade.close();

    }
}
