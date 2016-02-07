package br.com.weblayer.weblayer_logistica.SYNC;

import android.content.Context;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import br.com.weblayer.weblayer_logistica.DAO.DAOCidade;
import br.com.weblayer.weblayer_logistica.DAO.DAOParametros;
import br.com.weblayer.weblayer_logistica.DTO.Nota;
import br.com.weblayer.weblayer_logistica.DTO.TB_CIDADE;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Guilherme on 26/01/2015.
 */
public class SYNCNotas {
    public final static String SOAP_ACTION = "http://www.weblayer.com.br/";
    public final static String WSDL_TARGET_NAMESPACE = "http://www.weblayer.com.br/";

    //public final static String SOAP_ADDRESS = "http://server.weblayer.com.br/gdc_logistica_teste/mobile/service.asmx";
    //public final static String SOAP_ADDRESS = "http://10.0.2.2:52634/Vendas.asmx";

    public SYNCNotas() {}

    public static ArrayList<Nota> RetornaLista(Context context, String SOAP_ADDRESS,
                                                    int id_empresa, int id_transportadora, String ds_numeronota) throws Exception {

        String OPERATION_NAME = "BuscarNota";

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("id_empresa");
        pi.setValue(id_empresa);
        pi.setType(Integer.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("id_transportadora");
        pi.setValue(id_transportadora);
        pi.setType(Integer.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("ds_numeronota");
        pi.setValue(ds_numeronota);
        pi.setType(String.class);
        request.addProperty(pi);

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
        gsonb.registerTypeAdapter(Date.class, new JsonDateDeserializer()).create();
        Gson gson = gsonb.create();
        ArrayList<Nota> Objetos;

        try
        {

            Type collectionType = new TypeToken<ArrayList<Nota>>(){}.getType();
            Objetos = gson.fromJson(response.toString(), collectionType);

        }
        catch(Exception e)
        {
            throw e;
        }

        return Objetos;
    }

    public static ArrayList<Nota> Sincronizar(Context context, int id_empresa, int id_transportadora, String ds_numeronota) throws Exception
    {
        String server = "";
        Integer idempresa = 0;
        Integer idvendedor= 0;

        DAOParametros daoParam = new DAOParametros(context);
        //buscar o server
        //DAOCidade paramDAO =  new DAOCidade(context);



        //parametroDTO param =paramDAO.Get("servidor");
        //if (param!=null)
        //  server = param.getds_valor();

        //server += "/mobile/Service.asmx";

        //buiscar o empresa
        //param =paramDAO.Get("idempresa");
        //if (param!=null)
        //  idempresa= Integer.parseInt(param.getds_valor());

        //buscar a senha
        //param = paramDAO.Get("idvendedor");
        //if (param!=null)
        //  idvendedor= Integer.parseInt(param.getds_valor());

        daoParam.open();

        String SOAP_ADDRESS = daoParam.GetParametroWebservice("ds_webservice").getDs_valor();

        daoParam.close();

        ArrayList<Nota> itens = RetornaLista(context, SOAP_ADDRESS, id_empresa, id_transportadora, ds_numeronota);

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
