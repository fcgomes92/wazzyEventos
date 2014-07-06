package com.example.wazzyeventos;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wazzyeventos.jsonctrl.JSONParser;

public class telaRemoverUsuario extends ActionBarActivity {
	private Button bt_remover;
	private TextView email;
	private String username, passw;
	private EditText senha;
	public Intent menu;
	private Context ctx;
	
	// JSON Info
	private ProgressDialog pDialog;
	private JSONParser jsonP;
			
	//Server Info
	public static String ip = "192.168.1.4";
	private static final String DEL_USER_URL = "http://"+ip+":1234/webservice/delUser.php";

	//Tags JSON
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.removerusuario);
	
		bt_remover = (Button) findViewById(R.id.bt_remover_usuario);
		email = (TextView) findViewById(R.id.text_emailcliente_remover);
		senha = (EditText) findViewById(R.id.field_senha_remover);
		username = getIntent().getExtras().getString("username");
		passw = getIntent().getExtras().getString("senha");
		email.setText(username);
		ctx = this;
		
		bt_remover.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String pass;
				pass = senha.getText().toString();
				if(pass.equals(passw)){
					new deleteUser().execute();
					AlertDialog.Builder alert = new AlertDialog.Builder(telaRemoverUsuario.this);
					alert.setMessage("Conta deletada!");
					alert.setTitle("WARNING");
					alert.setNegativeButton(":)", null);
					alert.show();
					finish();
				}
				else{
					AlertDialog.Builder alert = new AlertDialog.Builder(telaRemoverUsuario.this);
					alert.setMessage("Senha Inválida!");
					alert.setTitle("WARNING");
					alert.setNegativeButton(":(", null);
					alert.show();
				}
				
			}
		});
	} 
	
	public class deleteUser extends AsyncTask<Void, Void, Boolean>{
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(telaRemoverUsuario.this);
			pDialog.setMessage("Deletando Usuário...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected Boolean doInBackground(Void... arg0) {
			//Envio de parametros para alterar o usuário
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", username));
			
	        JSONParser jParser = new JSONParser();
	        JSONObject json = jParser.getJSONFromUrl(DEL_USER_URL, params);
	        return null;
		}
		
		@Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            pDialog.dismiss();
        }
	}
}