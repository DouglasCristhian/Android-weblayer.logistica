package br.com.weblayer.weblayer_logistica.VIEW;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import br.com.weblayer.weblayer_logistica.DAO.DAOParametros;
import br.com.weblayer.weblayer_logistica.DTO.Login;
import br.com.weblayer.weblayer_logistica.DTO.TB_PARAMETROS;
import br.com.weblayer.weblayer_logistica.SYNC.SYNCLogin;
import br.com.weblayer.weblayer_logistica.R;
import java.util.ArrayList;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

/**
 * Created by Guilherme on 27/01/2015.
 */
public class TelaLogin extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.mnuConfiguracoes:

                Intent _i = new Intent(this.getApplicationContext(), Configuracoes.class);
                startActivity(_i);

                break;

            case R.id.mnuSobre:
                //Intent sobre = new Intent(this, Sobre.class);
                //startActivity(sobre);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private SingletonWeblayer _sTon = SingletonWeblayer.getInstance();

    private void Login() throws Exception {
        DAOParametros _daoParam = new DAOParametros(this.getApplicationContext());
        TB_PARAMETROS _objParam = new TB_PARAMETROS();
        SYNCLogin _syncLogin = new SYNCLogin();

        String ds_usuario = ((EditText)findViewById(R.id.edtLoginUsuario)).getText().toString();
        String ds_senha = ((EditText)findViewById(R.id.edtLoginSenha)).getText().toString();

        _daoParam.open();
        _objParam = _daoParam.GetParametroWebservice("param_login");
        _daoParam.close();

        if(_objParam != null && _objParam.getDs_valor() != "") {
            if (ds_usuario != null && ds_usuario != "") {
                if (_objParam.getDs_valor().toString() != ds_usuario) {
                    //Excluir os dados antes do login
                }
            }
        }
        else {
            _objParam = new TB_PARAMETROS();
            _objParam.setDs_parametro("param_login");
        }

        _objParam.setDs_valor(ds_usuario.toString());

        _daoParam.open();

        _daoParam.insertorupdate(_objParam);

        _daoParam.close();

        ArrayList<Login> _lista = _syncLogin.Sincronizar(this.getApplicationContext(), ds_usuario, ds_senha);

        if(_lista != null && _lista.size() > 0)
        {
            Login _res = ((Login)((_lista.toArray())[0]));

            if(_res.getId_empresa() > 0) {
                this._sTon.setPerfil(_lista.get(0));

                this._sTon.setEstaSincronizando(false);

                Login _perfil = (Login) this._sTon.getPerfil();

                _objParam = new TB_PARAMETROS();
                _objParam.setDs_parametro("id_empresa");
                _objParam.setDs_valor(String.valueOf(_perfil.getId_empresa()));
                _daoParam.insertorupdate(_objParam);

                _objParam = new TB_PARAMETROS();
                _objParam.setDs_parametro("ds_empresa");
                _objParam.setDs_valor(_perfil.getDs_empresa());
                _daoParam.insertorupdate(_objParam);

                Intent i = new Intent(getBaseContext(), Principal.class);
                startActivity(i);

            }
            else
            {
                Toast.makeText(getBaseContext(), "Usuário ou senha inválidos.", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getBaseContext(), "Usuário ou senha inválidos.", Toast.LENGTH_LONG).show();
        }
    }

    public void btnLogin_Click(View v) throws Exception {
        Login();
    }
}
