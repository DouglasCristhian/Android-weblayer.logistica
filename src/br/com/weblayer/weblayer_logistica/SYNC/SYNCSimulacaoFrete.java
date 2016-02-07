package br.com.weblayer.weblayer_logistica.SYNC;

import android.content.Context;
import br.com.weblayer.weblayer_logistica.DAO.DAOCidade;
import br.com.weblayer.weblayer_logistica.DAO.DAOParametros;
import br.com.weblayer.weblayer_logistica.DTO.Nota;
import br.com.weblayer.weblayer_logistica.DTO.Resultado_SimulacaoFrete;
import br.com.weblayer.weblayer_logistica.DTO.TB_CIDADE;
import br.com.weblayer.weblayer_logistica.VIEW.SingletonWeblayer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import org.ksoap2.serialization.Marshal;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guilherme on 22/06/2015.
 */
public class SYNCSimulacaoFrete {
    public final static String SOAP_ACTION = "http://www.weblayer.com.br/";
    public final static String WSDL_TARGET_NAMESPACE = "http://www.weblayer.com.br/";

    //public final static String SOAP_ADDRESS = "http://server.weblayer.com.br/gdc_logistica_teste/mobile/service.asmx";
    //public final static String SOAP_ADDRESS = "http://10.0.2.2:52634/Vendas.asmx";
    public SYNCSimulacaoFrete() {}

    public ArrayList<Resultado_SimulacaoFrete> RetornaLista(Context context, String SOAP_ADDRESS,
                                                    int id_empresa, int volumenf, Double valornf, Double pesonf, String ds_origemcodmun, String ds_destinocodmun) throws Exception {

        String OPERATION_NAME = "SimularFrete";

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("id_empresa");
        pi.setValue(id_empresa);
        pi.setType(int.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("volumenf");
        pi.setValue(volumenf);
        pi.setType(int.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("valornf");
        pi.setValue(valornf);
        pi.setType(Double.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("pesonf");
        pi.setValue(pesonf);
        pi.setType(Double.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("origemcodmun");
        pi.setValue(ds_origemcodmun);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("destinocodmun");
        pi.setValue(ds_destinocodmun);
        pi.setType(String.class);
        request.addProperty(pi);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS,10000);
        Object response = null;

        try {
            MarshalDouble marshaldDouble = new MarshalDouble();
            marshaldDouble.register(envelope);

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
        ArrayList<Resultado_SimulacaoFrete> Objetos;

        try
        {

            Type collectionType = new TypeToken<ArrayList<Resultado_SimulacaoFrete>>(){}.getType();
            Objetos = gson.fromJson(response.toString(), collectionType);

        }
        catch(Exception e)
        {
            throw e;
        }

        return Objetos;
    }

    public ArrayList<Resultado_SimulacaoFrete> Sincronizar(Context context, int id_empresa, int volumenf, Double valornf, Double pesonf, String ds_origemcodmun, String ds_destinocodmun) throws Exception
    {
        String server = "";
        Integer idempresa = 0;
        Integer idvendedor= 0;

        DAOParametros daoParam = new DAOParametros(context);

        daoParam.open();

        String SOAP_ADDRESS = daoParam.GetParametroWebservice("ds_webservice").getDs_valor();

        daoParam.close();

        ArrayList<Resultado_SimulacaoFrete> itens = RetornaLista(context, SOAP_ADDRESS, id_empresa, volumenf, valornf, pesonf, ds_origemcodmun, ds_destinocodmun);

        if (itens == null)
            return null;

        return itens;
    }

    public class MarshalDouble implements Marshal {
        public Object readInstance(XmlPullParser parser, String namespace, String name,
                                   PropertyInfo expected) throws IOException, XmlPullParserException {
            return Double.parseDouble(parser.nextText());
        }


        public void register(SoapSerializationEnvelope cm) {
            cm.addMapping(cm.xsd, "double", Double.class, this);
        }


        public void writeInstance(XmlSerializer writer, Object obj) throws IOException {
            writer.text(obj.toString());
        }
    }
}