package br.com.weblayer.weblayer_logistica.DAO;

import br.com.weblayer.weblayer_logistica.DTO.TB_DADOS;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
/**
 * Created by Guilherme on 22/01/2015.
 */
public class DAODados {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private static String tableName="TB_DADOS";

    public DAODados(Context context)
    {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {

        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


    private boolean isTableEmpty(String table) {
        Cursor cursor = null;
        try {

            cursor = db.rawQuery("SELECT count(*) FROM " + table, null);

            int countIndex = cursor.getColumnIndex("count(*)");
            cursor.moveToFirst();
            int rowCount = cursor.getInt(countIndex);
            if (rowCount > 0) {
                return false;
            }

            return true;

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void ClearTable()
    {

        try {

            // ClearTable
            String tableSql = "Delete from " + tableName + ";";
            db.execSQL(tableSql);

        }
        finally
        {
            //database.endTransaction();
        }

    }

    public void insertorupdate(TB_DADOS objeto)
    {

        TB_DADOS param = Get(objeto.getId());

        if (param==null)
            insert(objeto);
        else
            update(objeto);

    }

    public void insert(TB_DADOS objeto) {
        try {

            //db.beginTransaction();

            String ds_visao = objeto.getDs_visao();
            String ds_titulo = objeto.getDs_titulo();
            String ds_cor = objeto.getDs_cor();
            String ds_percentual_performance_minima = objeto.getDs_percentual_performance_minima();
            String ds_percentual_performance = objeto.getDs_percentual_performance();
            String ds_percentual_notas_entregues = objeto.getDs_percentual_notas_entregues();
            String ds_numero_notas_total = objeto.getDs_numero_notas_total();
            String ds_dias_media_atraso = objeto.getDs_dias_media_atraso();
            int nr_data_referencia = objeto.getNr_data_referencia();
            Date dt_entrega = objeto.getDt_entrega();
            String ds_destino = objeto.getDs_destino();

            String comandosql = "INSERT INTO  " + tableName + " "
                    + " (ds_visao, ds_titulo, ds_cor, ds_percentual_performance_minima, ds_percentual_performance, ds_percentual_notas_entregues, ds_numero_notas_total, ds_dias_media_atraso, nr_data_referencia, dt_entrega, ds_destino) VALUES "
                    + " (?,?,?,?,?,?,?,?,?,?,?);";

            db.execSQL(comandosql, new Object[] { ds_visao, ds_titulo, ds_cor, ds_percentual_performance_minima, ds_percentual_performance, ds_percentual_notas_entregues, ds_numero_notas_total, ds_dias_media_atraso, nr_data_referencia, dt_entrega, ds_destino });

            //db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e("weblayer.log.dados", "insert", e); // log the error
        } finally {
            //db.endTransaction();
        }
    }

    public void update(TB_DADOS objeto) {
        try {

            //db.beginTransaction();


            int id = objeto.getId();
            String ds_visao = objeto.getDs_visao();
            String ds_titulo = objeto.getDs_titulo();
            String ds_cor = objeto.getDs_cor();
            String ds_percentual_performance_minima = objeto.getDs_percentual_performance_minima();
            String ds_percentual_performance = objeto.getDs_percentual_performance();
            String ds_percentual_notas_entregues = objeto.getDs_percentual_notas_entregues();
            String ds_numero_notas_total = objeto.getDs_numero_notas_total();
            String ds_dias_media_atraso = objeto.getDs_dias_media_atraso();
            int nr_data_referencia = objeto.getNr_data_referencia();
            Date dt_entrega = objeto.getDt_entrega();
            String ds_destino = objeto.getDs_destino();

            String comandosql = "UPDATE  " + tableName + " SET " + " ds_visao=?, "
                    + " ds_titulo=?, "
                    + " ds_cor=?, "
                    + " ds_percentual_performance_minima=?, "
                    + " ds_percentual_performance=?, "
                    + " ds_percentual_notas_entregues=?, "
                    + " ds_numero_notas_total=?, "
                    + " ds_dias_media_atraso=?, "
                    + " nr_data_referencia=?, "
                    + " dt_entrega=?, "
                    + " ds_destino=? "
                    + "WHERE id=?";

            db.execSQL(comandosql, new Object[] { ds_visao, ds_titulo, ds_cor, ds_percentual_performance_minima, ds_percentual_performance, ds_percentual_notas_entregues, ds_numero_notas_total, ds_dias_media_atraso, nr_data_referencia, dt_entrega, ds_destino, id });

            //db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e("weblayer.log.Dados", "update", e); // log the error
        } finally {
            //db.endTransaction();
        }

    }

    public void delete(TB_DADOS objeto) {
        try {

            //db.beginTransaction();
            String delete = "DELETE FROM  " + tableName + " WHERE id='" + objeto.getId() + "'";
            db.execSQL(delete);
            //db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e("weblayer.log.dados.Dados", "delete", e); // log the error
        } finally {
            //db.endTransaction();
        }
    }

    public TB_DADOS Get(int id) {

        Cursor cursor = null;

        try {

            cursor = db.rawQuery("SELECT * FROM  " + tableName + " Where id='" + id + "'", null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                return (cursorToObject(cursor));
            }

            return null;
        } catch (Exception e) {
            Log.e("weblayer.log.dados.Dados", "Get", e); // log the error
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public  List<TB_DADOS> fillAll() {
        Cursor cursor = null;
        try {
            List<TB_DADOS> all = new ArrayList<TB_DADOS>();

            cursor = db.rawQuery("SELECT * FROM  " + tableName + " ", null);

            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                do {
                    all.add(cursorToObject(cursor));
                    cursor.moveToNext();

                } while (!cursor.isAfterLast());
            }

            return all;
        }
        catch (Exception e)
        {
            Log.e("weblayer.log.visitas.Visita", "fill", e);  // log the error
            return null;
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public  List<TB_DADOS> fillByDate(int dateType) {
        Cursor cursor = null;
        try {
            List<TB_DADOS> all = new ArrayList<TB_DADOS>();

            cursor = db.rawQuery("SELECT * FROM  " + tableName + " where nr_data_referencia = ? ", new String[]{ String.valueOf(dateType) });

            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                do {
                    all.add(cursorToObject(cursor));
                    cursor.moveToNext();

                } while (!cursor.isAfterLast());
            }

            return all;
        }
        catch (Exception e)
        {
            Log.e("weblayer.log.visitas.Visita", "fill", e);  // log the error
            return null;
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private TB_DADOS cursorToObject(Cursor cursor) {

        TB_DADOS objeto = new TB_DADOS();

        objeto.setId(cursor.getInt(cursor.getColumnIndex("id")));
        objeto.setNr_data_referencia(cursor.getInt(cursor.getColumnIndex("nr_data_referencia")));
        //objeto.setDt_entrega(cursor.getInt(cursor.getColumnIndex("dt_entrega")));
        objeto.setDs_visao(cursor.getString(cursor.getColumnIndex("ds_visao")));
        objeto.setDs_titulo(cursor.getString(cursor.getColumnIndex("ds_titulo")));
        objeto.setDs_percentual_performance_minima(cursor.getString(cursor.getColumnIndex("ds_percentual_performance_minima")));
        objeto.setDs_percentual_notas_entregues(cursor.getString(cursor.getColumnIndex("ds_percentual_notas_entregues")));
        objeto.setDs_numero_notas_total(cursor.getString(cursor.getColumnIndex("ds_numero_notas_total")));
        objeto.setNr_data_referencia(cursor.getInt(cursor.getColumnIndex("nr_data_referencia")));
        //objeto.setDt_entrega((Date)cursor.get(cursor.getColumnIndex("dt_entrega")));
        objeto.setDs_visao(cursor.getString(cursor.getColumnIndex("ds_visao")));
        objeto.setDs_dias_media_atraso(cursor.getString(cursor.getColumnIndex("ds_dias_media_atraso")));
        objeto.setDs_percentual_performance(cursor.getString(cursor.getColumnIndex("ds_percentual_performance")));
        objeto.setDs_cor(cursor.getString(cursor.getColumnIndex("ds_cor")));

        return objeto;
    }
}
