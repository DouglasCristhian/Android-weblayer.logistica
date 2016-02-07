package br.com.weblayer.weblayer_logistica.DAO;

import br.com.weblayer.weblayer_logistica.DTO.TB_PARAMETROS;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.List;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Guilherme on 22/01/2015.
 */
public class DAOParametros {
    private static SQLiteDatabase db;
    private static String tableName="TB_PARAMETROS";
    private DatabaseHelper dbHelper;

    public DAOParametros(Context context)
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

    public void insertorupdate(TB_PARAMETROS objeto)
    {
        TB_PARAMETROS param = Get(objeto.getId_parametro());

        if (param==null)
            insert(objeto);
        else
            update(objeto);
    }

    public void insert(TB_PARAMETROS objeto) {
        try {

            //db.beginTransaction();
            String ds_parametro = objeto.getDs_parametro();
            String ds_valor = objeto.getDs_valor();

            String comandosql = "INSERT INTO  " + tableName + " "
                    + " (ds_parametro, ds_valor) VALUES "
                    + " (?,?);";

            db.execSQL(comandosql, new Object[] { ds_parametro, ds_valor });

            //db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e("weblayer.log.parametros", "insert", e); // log the error
        } finally {
            //db.endTransaction();
        }
    }

    public void update(TB_PARAMETROS objeto) {
        try {

            //db.beginTransaction();

            int id = objeto.getId_parametro();
            String ds_parametro = objeto.getDs_parametro();
            String ds_valor = objeto.getDs_valor();

            String comandosql = "UPDATE  " + tableName + " SET " + " ds_parametro=?, "
                    + " ds_valor=? "
                    + "WHERE id_parametro=?";

            db.execSQL(comandosql, new Object[] { ds_parametro, ds_valor, id });

            //db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e("weblayer.log.Parametros", "update", e); // log the error
        } finally {
            //db.endTransaction();
        }

    }

    public void delete(int id_parametro) {
        try {

            //db.beginTransaction();
            String delete = "DELETE FROM  " + tableName + " WHERE id_parametro = ?";
            db.execSQL(delete, new Object[]{ id_parametro });
            //db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e("weblayer.log.parametros.Parametros", "delete", e); // log the error
        } finally {
            //db.endTransaction();
        }
    }

    public TB_PARAMETROS Get(int id) {

        Cursor cursor = null;

        try {

            cursor = db.rawQuery("SELECT * FROM  " + tableName + " Where id_parametro = ?", new String[]{ String.valueOf(id) });

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                return (cursorToObject(cursor));
            }

            return null;
        } catch (Exception e) {
            Log.e("weblayer.log.parametros.Parametros", "Get", e); // log the error
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public TB_PARAMETROS GetParametroWebservice(String parametro) {
        Cursor cursor = null;
        try {
            String _sql =  "SELECT * FROM  " + tableName + " where ds_parametro = ?; ";
            String[] _parametros = new String[]{ parametro };

            cursor = db.rawQuery(_sql, _parametros);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                return (cursorToObject(cursor));
            }

            return null;
        } catch (Exception e) {
            Log.e("weblayer.log.parametros.Parametros", "Get", e); // log the error
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public  List<TB_PARAMETROS> fillAll() {
        Cursor cursor = null;
        try {
            List<TB_PARAMETROS> all = new ArrayList<TB_PARAMETROS>();

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
            Log.e("weblayer.log.parametros.Parametros", "fill", e);  // log the error
            return null;
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private TB_PARAMETROS cursorToObject(Cursor cursor) {

        TB_PARAMETROS objeto = new TB_PARAMETROS();

        objeto.setId_parametro(cursor.getInt(cursor.getColumnIndex("id_parametro")));
        objeto.setDs_parametro(cursor.getString(cursor.getColumnIndex("ds_parametro")));
        objeto.setDs_valor(cursor.getString(cursor.getColumnIndex("ds_valor")));

        return objeto;
    }
}