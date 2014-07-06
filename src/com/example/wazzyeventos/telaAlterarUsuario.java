package com.example.wazzyeventos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
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
import android.widget.Toast;

import com.example.wazzyeventos.jsonctrl.JSONParser;

public class telaAlterarUsuario extends ActionBarActivity {
		private Button bt_alterar;
		private TextView tv_nome,tv_data,tv_username;
		private EditText et_senha, et_telefone, et_endereco;
		
		public String username = "", nome = "", endereco = "", telefone = "", datanasc = "", aval = "", login = "", password = "";		
		public Intent menu;
		private Context ctx;
		private int time_toast;
		
		// JSON Info
		private ProgressDialog pDialog;
		private JSONParser jsonP;
		
		//Server Info
		public static String ip = "192.168.1.4";
	    private static final String BUSCA_USER_URL = "http://"+ip+":1234/webservice/getMeuUser.php";
	    private static final String ALTER_USER_URL = "http://"+ip+":1234/webservice/alterarUser.php";

	    //Tags JSON
	    private static final String TAG_SUCCESS = "success";
	    private static final String TAG_MESSAGE = "message";
	    private static final String TAG_USERS = "meuuser";
	    private static final String TAG_USER_ID = "users_id";
	    private static final String TAG_USERNAME = "username";
	    private static final String TAG_PASS = "password";
	    private static final String TAG_NOME = "nome";
	    private static final String TAG_ENDERECO = "endereco";
	    private static final String TAG_TELEFONE = "telefone";
	    private static final String TAG_DATANASC = "datanasc";
	    private static final String TAG_AVAL = "aval";
	    
	    // Array de usuarios
	    private JSONArray mUsers = null;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.alterarusuario);
			
			tv_nome = (TextView) findViewById(R.id.text_nome_alterar);
			tv_data = (TextView) findViewById(R.id.text_data_alterar);
			
			this.ctx = this; 	
			this.time_toast = Toast.LENGTH_SHORT;
			this.login = getIntent().getExtras().getString("login");
			
			this.tv_username = (TextView) findViewById(R.id.field_email_alterar);
			this.et_telefone = (EditText) findViewById(R.id.field_telefone_alterar);
			this.et_endereco = (EditText) findViewById(R.id.field_endereco_alterar);
			this.et_senha = (EditText) findViewById(R.id.field_senha_alterar);
			this.bt_alterar = (Button) findViewById(R.id.bt_alterar);
			
			tv_nome.setText(nome);
			tv_data.setText(datanasc);
			tv_username.setText(username);
			et_telefone.setText(telefone);
			et_endereco.setText(endereco);
			
			this.bt_alterar.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(et_senha.getText().toString().equals(password)){
						String texto = "Dados alterados com sucesso!";
						new alterUser().execute();
						Toast.makeText(ctx, texto, time_toast).show();
					}
					else{
						AlertDialog.Builder alert = new AlertDialog.Builder(telaAlterarUsuario.this);
						alert.setMessage("Senha inválida!");
						alert.setTitle("Aviso");
						alert.setNeutralButton("OK", null);
						alert.show();
					}	
				}
			});
			
			new loadUser().execute();
		}
		public void updateJSONData(){	    	
			//Envio de parametros de filtro
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", login));
			
	        JSONParser jParser = new JSONParser();
	        JSONObject json = jParser.getJSONFromUrl(BUSCA_USER_URL, params);
	        try {
	            mUsers = json.getJSONArray(TAG_USERS);
	            JSONObject c = mUsers.getJSONObject(0);
	            //gets the content of each tag
	            nome = c.getString(TAG_NOME);
	            username = c.getString(TAG_USERNAME);
	            endereco = c.getString(TAG_ENDERECO);
	            telefone = c.getString(TAG_TELEFONE);
	            datanasc = c.getString(TAG_DATANASC);
	            aval = c.getString(TAG_AVAL);
	            password = c.getString(TAG_PASS);
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
		}
		
		public void alterJSONData(){
			//Envio de parametros para alterar o usuário
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String tel = et_telefone.getText().toString();
			String end = et_endereco.getText().toString();
			params.add(new BasicNameValuePair("username", username));
			params.add(new BasicNameValuePair("telefone", tel));
			params.add(new BasicNameValuePair("endereco", end));
			
	        JSONParser jParser = new JSONParser();
	        JSONObject json = jParser.getJSONFromUrl(ALTER_USER_URL, params);
		}
		
		public class loadUser extends AsyncTask<Void, Void, Boolean>{
			@Override
			protected void onPreExecute(){
				super.onPreExecute();
				pDialog = new ProgressDialog(telaAlterarUsuario.this);
				pDialog.setMessage("Carregando Usuário...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}
			
			@Override
			protected Boolean doInBackground(Void... arg0) {
				updateJSONData();
				return null;
			}
			
			@Override
	        protected void onPostExecute(Boolean result) {
	            super.onPostExecute(result);
	            pDialog.dismiss();
	            tv_nome.setText(nome);
				tv_data.setText(datanasc);
				tv_username.setText(username);
				et_telefone.setText(telefone);
				et_endereco.setText(endereco);
	        }
		}
		
		public class alterUser extends AsyncTask<Void, Void, Boolean>{
			@Override
			protected void onPreExecute(){
				super.onPreExecute();
				pDialog = new ProgressDialog(telaAlterarUsuario.this);
				pDialog.setMessage("Alterando Usuário...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}
			
			@Override
			protected Boolean doInBackground(Void... arg0) {
				alterJSONData();
				updateJSONData();
				return null;
			}
			
			@Override
	        protected void onPostExecute(Boolean result) {
	            super.onPostExecute(result);
	            pDialog.dismiss();
	        }
		}
}