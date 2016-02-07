package br.com.weblayer.weblayer_logistica.SYNC;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalDate;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import br.com.weblayer.weblayer_logistica.DAO.DAOCidade;
import br.com.weblayer.weblayer_logistica.DAO.DAODados;
import br.com.weblayer.weblayer_logistica.DAO.DAOParametros;
import br.com.weblayer.weblayer_logistica.DTO.TB_CIDADE;
import br.com.weblayer.weblayer_logistica.DTO.TB_DADOS;
import br.com.weblayer.weblayer_logistica.VIEW.SingletonWeblayer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Guilherme on 28/01/2015.
 */
public class SYNCDados {
    SingletonWeblayer sTon = SingletonWeblayer.getInstance();

    public final static String SOAP_ACTION = "http://www.weblayer.com.br/";
    public final static String WSDL_TARGET_NAMESPACE = "http://www.weblayer.com.br/";

    //public final static String SOAP_ADDRESS = "http://server.weblayer.com.br/gdc_logistica_teste/mobile/service.asmx";
    //public final static String SOAP_ADDRESS = "http://10.0.2.2:52634/Vendas.asmx";
    public SYNCDados() {}

    public static ArrayList<TB_DADOS> RetornaLista(Context context, String SOAP_ADDRESS, Integer id_empresa, Integer id_transportadora, String ds_visao, Date dt_inicio, Date dt_fim) throws Exception
    {
        String OPERATION_NAME = "RetornaPerformance";

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("empresa");
        pi.setValue(id_empresa);
        pi.setType(Integer.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("dt_inicio");
        pi.setValue(dt_inicio);
        pi.setType(Date.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("dt_fim");
        pi.setValue(dt_fim);
        pi.setType(Date.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("visao");
        pi.setValue(ds_visao);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("transportadora");
        pi.setValue(id_transportadora);
        pi.setType(Integer.class);
        request.addProperty(pi);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        new MarshalDate().register(envelope);

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
        gsonb.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Gson gson = gsonb.create();

        ArrayList<TB_DADOS> Objetos;

        try
        {

            Type collectionType = new TypeToken<ArrayList<TB_DADOS>>(){}.getType();
            Objetos = gson.fromJson(response.toString(), collectionType);

        }
        catch(Exception e)
        {
            throw e;
        }

        return Objetos;
    }

    public static void SincronizarAtuais(Context context, Integer id_empresa, Integer id_transportadora, String ds_visao) throws Exception
    {
        Calendar aCalendar = Calendar.getInstance();

        aCalendar.set(Calendar.DATE, 1);

        Date _inicio = aCalendar.getTime();

        aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        Date _fim = aCalendar.getTime();

        String server = "";
        Integer idempresa = 0;
        Integer idvendedor= 0;

        DAOParametros tabelaParametros = new DAOParametros(context);
        tabelaParametros.open();
        String SOAP_ADDRESS = new DAOParametros(context).GetParametroWebservice("ds_webservice").getDs_valor();
        tabelaParametros.close();


        _inicio.setHours(0);
        _inicio.setMinutes(0);
        _inicio.setSeconds(0);

        _fim.setHours(23);
        _fim.setMinutes(59);
        _fim.setSeconds(59);

        ArrayList<TB_DADOS> itens = RetornaLista(context, SOAP_ADDRESS, id_empresa, id_transportadora, ds_visao, _inicio, _fim);

        if (itens==null)
            return;

        DAODados tabelaDados = new DAODados(context);
        tabelaDados.open();
        tabelaDados.ClearTable();

        //Incluir todas as visitas..
        for (TB_DADOS item : itens) {
            item.setNr_data_referencia(0);
            tabelaDados.insert(item);
        }

        tabelaDados.close();
    }

    public static void SincronizarPassado(Context context, Integer id_empresa, Integer id_transportadora, String ds_visao) throws Exception
    {
        Calendar aCalendar = Calendar.getInstance();

        aCalendar.add(Calendar.MONTH, -1);

        aCalendar.set(Calendar.DATE, 1);

        Date _inicio = aCalendar.getTime();

        aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        Date _fim = aCalendar.getTime();

        String server = "";
        Integer idempresa = 0;
        Integer idvendedor= 0;

        DAOParametros tabelaParametros = new DAOParametros(context);
        tabelaParametros.open();
        String SOAP_ADDRESS = new DAOParametros(context).GetParametroWebservice("ds_webservice").getDs_valor();
        tabelaParametros.close();

        _inicio.setHours(0);
        _inicio.setMinutes(0);
        _inicio.setSeconds(0);

        _fim.setHours(23);
        _fim.setMinutes(59);
        _fim.setSeconds(59);

        ArrayList<TB_DADOS> itens = RetornaLista(context, SOAP_ADDRESS, id_empresa, id_transportadora, ds_visao, _inicio, _fim);

        if (itens==null)
            return;

        DAODados tabelaDados = new DAODados(context);
        tabelaDados.open();

        //Incluir todas as visitas..
        for (TB_DADOS item : itens) {
            item.setNr_data_referencia(-1);
            tabelaDados.insert(item);
        }

        tabelaDados.close();
    }
}
