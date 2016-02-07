package br.com.weblayer.weblayer_logistica.SYNC;

import android.content.Context;
import br.com.weblayer.weblayer_logistica.DAO.DAOParametros;
import br.com.weblayer.weblayer_logistica.DTO.Nota;
import br.com.weblayer.weblayer_logistica.DTO.Resultado;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalDate;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Guilherme on 05/02/2015.
 */
public class SYNCEntregarNota {
    public final static String SOAP_ACTION = "http://www.weblayer.com.br/";
    public final static String WSDL_TARGET_NAMESPACE = "http://www.weblayer.com.br/";

    //public final static String SOAP_ADDRESS = "http://server.weblayer.com.br/gdc_logistica_teste/mobile/service.asmx";
    //public final static String SOAP_ADDRESS = "http://10.0.2.2:52634/Vendas.asmx";

    public SYNCEntregarNota() {}

    public static ArrayList<Resultado> RetornaLista(Context context, String SOAP_ADDRESS,
                                               Integer id_empresa, Integer id_nota, Integer id_usuario,
                                               Date dt_entrega,
                                               String ds_numeronota, String ds_versao_so, String ds_ip, String ds_modelo_dispositivo, String ds_resolucao) throws Exception {

        String OPERATION_NAME = "InformarEntrega";

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("id_empresa");
        pi.setValue(id_empresa);
        pi.setType(Integer.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("id_nota");
        pi.setValue(id_nota);
        pi.setType(Integer.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("dt_entrega");
        pi.setValue(dt_entrega);
        pi.setType(Date.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("id_usuario");
        pi.setValue(id_usuario);
        pi.setType(Integer.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("ds_versao_so");
        pi.setValue(ds_versao_so);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("ds_ip");
        pi.setValue(ds_ip);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("ds_modelo_dispositivo");
        pi.setValue(ds_modelo_dispositivo);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("ds_resolucao");
        pi.setValue(ds_resolucao);
        pi.setType(String.class);
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
        gsonb.registerTypeAdapter(Date.class, new JsonDateDeserializer()).create();
        Gson gson = gsonb.create();
        ArrayList<Resultado> Objetos;

        try
        {

            Type collectionType = new TypeToken<ArrayList<Resultado>>(){}.getType();
            Objetos = gson.fromJson(response.toString(), collectionType);

        }
        catch(Exception e)
        {
            throw e;
        }

        return Objetos;
    }

    public static ArrayList<Resultado> Sincronizar(Context context,
                                                   Integer id_empresa, Integer id_nota, Integer id_usuario,
                                                   Date dt_entrega,
                                                   String ds_numeronota, String ds_versao_so, String ds_ip, String ds_modelo_dispositivo, String ds_resolucao) throws Exception
    {
        DAOParametros daoParam = new DAOParametros(context);

        daoParam.open();

        String SOAP_ADDRESS = daoParam.GetParametroWebservice("ds_webservice").getDs_valor();

        daoParam.close();

        ArrayList<Resultado> itens = RetornaLista(context, SOAP_ADDRESS, id_empresa, id_nota, id_usuario, dt_entrega, ds_numeronota, ds_versao_so, ds_ip, ds_modelo_dispositivo, ds_resolucao);

        if (itens==null)
            return null;

        return itens;
    }

    public static class JsonDateDeserializer implements JsonDeserializer<Date> {
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String s = json.getAsJsonPrimitive().getAsString();
            long l = Long.parseLong(s.substring(6, s.length() - 2));
            Date d = new Date(l);
            return d;
        }
    }
}
