package br.com.weblayer.weblayer_logistica.DAO;

import br.com.weblayer.weblayer_logistica.DTO.TB_CIDADE;
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
public class DAOCidade {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private static String tableName = "TB_CIDADE";

    public DAOCidade(Context context) {
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

    public void insertorupdate(TB_CIDADE objeto)
    {
        TB_CIDADE param = Get(objeto.getId());

        if (param == null)
            insert(objeto);
        else
            update(objeto);
    }

    public void insert(TB_CIDADE objeto) {
        try {
            //db.beginTransaction();

            int id = objeto.getId();
            String cd_uf = objeto.getCd_uf();
            String ds_nome = objeto.getDs_nome();
            int cd_localizacao = objeto.getCd_localizacao();
            Double vl_aliquota_iss = objeto.getVl_aliquota_iss();
            String cd_cepinicial = objeto.getCd_cepinicial();
            String cd_cepfinal = objeto.getCd_cepfinal();
            String cd_codmun = objeto.getCd_codmun();
            String cd_long = objeto.getCd_long();
            String cd_lat = objeto.getCd_lat();

            String comandosql = "INSERT INTO  " + tableName + " "
                    + " (id, cd_uf, ds_nome, cd_localizacao, vl_aliquota_iss, cd_cepinicial, cd_cepfinal, cd_codmun, cd_long, cd_lat) VALUES "
                    + " (?,?,?,?,?,?,?,?,?,?);";

            db.execSQL(comandosql, new Object[] { id, cd_uf, ds_nome, cd_localizacao, vl_aliquota_iss, cd_cepinicial, cd_cepfinal, cd_codmun, cd_long, cd_lat});

            //db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e("weblayer.log.cidade", "insert", e); // log the error
        } finally {
            //db.endTransaction();
        }
    }

    public void update(TB_CIDADE objeto) {
        try {

            //db.beginTransaction();

            //TODO Fazer retornar inteiro

            int id = objeto.getId();
            String cd_uf = objeto.getCd_uf();
            String ds_nome = objeto.getDs_nome();
            int cd_localizacao = objeto.getCd_localizacao();
            Double vl_aliquota_iss = objeto.getVl_aliquota_iss();
            String cd_cepinicial = objeto.getCd_cepinicial();
            String cd_cepfinal = objeto.getCd_cepfinal();
            String cd_codmun = objeto.getCd_codmun();
            String cd_long = objeto.getCd_long();
            String cd_lat = objeto.getCd_lat();

            String comandosql = "UPDATE  " + tableName + " SET " + " cd_uf=?, "
                    + " ds_nome=?, "
                    + " cd_localizacao=?, "
                    + " vl_aliquota_iss=?, "
                    + " cd_cepinicial=?, "
                    + " cd_cepfinal=?, "
                    + " cd_codmun=?"
                    + " cd_long=?"
                    + " cd_lat=?"
                    + "WHERE id=?";

            db.execSQL(comandosql, new Object[] { cd_uf, ds_nome, cd_localizacao, vl_aliquota_iss, cd_cepinicial, cd_cepfinal, cd_codmun, cd_long, cd_lat, id});

            //db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e("weblayer.log.cidade", "update", e); // log the error
        } finally {
            //db.endTransaction();
        }
    }

    public void delete(TB_CIDADE objeto) {
        try {

            //db.beginTransaction();
            String delete = "DELETE FROM  " + tableName + " WHERE id='" + objeto.getId() + "'";
            db.execSQL(delete);
            //db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e("weblayer.log.cidade.Cidade", "delete", e); // log the error
        } finally {
            //db.endTransaction();
        }
    }

    public TB_CIDADE Get(int id) {

        Cursor cursor = null;

        try {

            cursor = db.rawQuery("SELECT * FROM  " + tableName + " Where id='" + id + "'", null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                return (cursorToObject(cursor));
            }

            return null;
        } catch (Exception e) {
            Log.e("weblayer.log.cidade.Cidade", "Get", e); // log the error
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public  ArrayList<TB_CIDADE> fillAll() {
        Cursor cursor = null;

        try {
            ArrayList<TB_CIDADE> _retorno = new ArrayList<TB_CIDADE>();

            cursor = db.rawQuery("SELECT * FROM " + tableName + " ORDER BY ds_nome", null);

            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                do {
                    _retorno.add(cursorToObject(cursor));
                    cursor.moveToNext();

                } while (!cursor.isAfterLast());
            }

            return _retorno;
        }
        catch (Exception ex){
            Log.e("weblayer.log.cidade.Cidade", "fillAll", ex);  // log the error
            return null;
        }
        finally {
            cursor.close();
        }
    }

    public  ArrayList<TB_CIDADE> fillCidadePorEstado(String estado) {
        Cursor cursor = null;

        try {
            ArrayList<TB_CIDADE> _retorno = new ArrayList<TB_CIDADE>();

            cursor = db.rawQuery("SELECT * FROM " + tableName + " where cd_uf = ? ORDER BY ds_nome", new String[]{ String.valueOf(estado) });

            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                do {
                    _retorno.add(cursorToObject(cursor));
                    cursor.moveToNext();

                } while (!cursor.isAfterLast());
            }

            return _retorno;
        }
        catch (Exception ex){
            Log.e("weblayer.log.cidade.Cidade", "fillAll", ex);  // log the error
            return null;
        }
        finally {
            cursor.close();
        }
    }

    private TB_CIDADE cursorToObject(Cursor cursor) {

        TB_CIDADE objeto = new TB_CIDADE();

        objeto.setCd_cepfinal(cursor.getString(cursor.getColumnIndex("cd_cepfinal")));
        objeto.setCd_cepinicial(cursor.getString(cursor.getColumnIndex("cd_cepinicial")));
        objeto.setCd_codmun(cursor.getString(cursor.getColumnIndex("cd_codmun")));
        objeto.setCd_lat(cursor.getString(cursor.getColumnIndex("cd_lat")));
        objeto.setCd_long(cursor.getString(cursor.getColumnIndex("cd_long")));
        objeto.setVl_aliquota_iss(cursor.getDouble(cursor.getColumnIndex("vl_aliquota_iss")));
        objeto.setId(cursor.getInt(cursor.getColumnIndex("id")));
        objeto.setDs_nome(cursor.getString(cursor.getColumnIndex("ds_nome")));
        objeto.setCd_uf(cursor.getString(cursor.getColumnIndex("cd_uf")));
        objeto.setCd_localizacao(cursor.getInt(cursor.getColumnIndex("cd_localizacao")));

        return objeto;
    }

    public ArrayList<String> ListarEstados()
    {
        Cursor cursor = null;

        try {
            ArrayList<String> _retorno = new ArrayList<String>();

            cursor = db.rawQuery("SELECT cd_uf FROM " + tableName + " GROUP BY cd_uf ORDER BY cd_uf", null);

            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                do {
                    _retorno.add(cursor.getString(cursor.getColumnIndex("cd_uf")));
                    cursor.moveToNext();

                } while (!cursor.isAfterLast());
            }

            return _retorno;
        }
        catch (Exception ex){
            Log.e("weblayer.log.cidade.Cidade", "ListarEstados", ex);  // log the error
            return null;
        }
        finally {
            cursor.close();
        }
    }
}
