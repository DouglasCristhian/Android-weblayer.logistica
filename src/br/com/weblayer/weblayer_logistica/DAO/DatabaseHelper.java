package br.com.weblayer.weblayer_logistica.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Guilherme on 22/01/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "weblayer_logistica";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {

        String DATABASE_CREATE;

        //Tabela cidade
        DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS TB_CIDADE ("
                + "id INT PRIMARY KEY, "
                + "cd_uf VARCHAR(20),"
                + "ds_nome VARCHAR(100),"
                + "cd_localizacao VARCHAR(100),"
                + "vl_aliquota_iss DECIMAL(18,2),"
                + "cd_cepinicial VARCHAR(80),"
                + "cd_cepfinal VARCHAR(80),"
                + "cd_codmun VARCHAR(80),"
                + "cd_long VARCHAR(80),"
                + "cd_lat VARCHAR(80)"
                + ");";
        database.execSQL(DATABASE_CREATE);

        //Tabela dados
        DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS TB_DADOS ("
                + "id INTEGER PRIMARY KEY, "
                + "ds_visao VARCHAR(20), "
                + "ds_titulo VARCHAR(100), "
                + "ds_cor VARCHAR(15), "
                + "ds_percentual_performance_minima VARCHAR(20), "
                + "ds_percentual_performance VARCHAR(20), "
                + "ds_percentual_notas_entregues VARCHAR(20), "
                + "ds_numero_notas_total VARCHAR(20), "
                + "ds_dias_media_atraso VARCHAR(20), "
                + "nr_data_referencia INT, "
                + "dt_entrega DATETIME(20), "
                + "ds_destino VARCHAR(70)"
                + ");";

        database.execSQL(DATABASE_CREATE);

        //Tabela parâmetros
        DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS TB_PARAMETROS ("
                + "id_parametro INTEGER PRIMARY KEY, "
                + "ds_valor VARCHAR(100), "
                + "ds_parametro VARCHAR(100)"
                + ");";

        database.execSQL(DATABASE_CREATE);
    }

    // Method is called during an upgrade of the database,
    @Override
    public void onUpgrade(SQLiteDatabase database,int oldVersion,int newVersion){

        Log.i("weblayer.logcat",
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");

        onCreate(database);

        //Upgrade versão 2
        if (oldVersion==1 && newVersion==2) {
            //Upgrade Versão 1 para 2
        }
    }
}
