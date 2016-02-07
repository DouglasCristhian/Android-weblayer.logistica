package br.com.weblayer.weblayer_logistica.SYNC;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import br.com.weblayer.weblayer_logistica.DAO.DAOParametros;
import br.com.weblayer.weblayer_logistica.DTO.Login;
import br.com.weblayer.weblayer_logistica.DTO.Nota;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Guilherme on 27/01/2015.
 */
public class SYNCLogin {
    public final static String SOAP_ACTION = "http://www.weblayer.com.br/";
    public final static String WSDL_TARGET_NAMESPACE = "http://www.weblayer.com.br/";

    //public final static String SOAP_ADDRESS = "http://server.weblayer.com.br/gdc_logistica_teste/mobile/service.asmx";
    //public final static String SOAP_ADDRESS = "http://10.0.2.2:52634/Vendas.asmx";

    public SYNCLogin() {}

    public static ArrayList<Login> RetornaLista(Context context, String SOAP_ADDRESS,
                                               String ds_usuario, String ds_senha) throws Exception {

        String OPERATION_NAME = "Login";

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("usuario");
        pi.setValue(ds_usuario);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("senha");
        pi.setValue(ds_senha);
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
        Gson gson = gsonb.create();
        ArrayList<Login> Objetos;

        try
        {

            Type collectionType = new TypeToken<ArrayList<Login>>(){}.getType();
            Objetos = gson.fromJson(response.toString(), collectionType);

        }
        catch(Exception e)
        {
            throw e;
        }

        return Objetos;
    }

    public static ArrayList<Login> Sincronizar(Context context, String ds_usuario, String ds_senha) throws Exception
    {
        String server = "";
        Integer idempresa = 0;
        Integer idvendedor= 0;

        //buscar o server
        //DAOCidade paramDAO =  new DAOCidade(context);

        //paramDAO.open();

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

        //paramDAO.close();

        DAOParametros _daoParam = new DAOParametros(context);

        _daoParam.open();

        String SOAP_ADDRESS = _daoParam.GetParametroWebservice("ds_webservice").getDs_valor();

        _daoParam.close();

        ArrayList<Login> itens = RetornaLista(context, SOAP_ADDRESS, ds_usuario, ds_senha);

        if (itens==null)
            return null;

        return itens;
    }
}
