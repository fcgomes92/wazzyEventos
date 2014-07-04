package com.example.wazzyeventos;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wazzyeventos.jsonctrl.JSONParser;
import com.example.wazzyeventos.sqlite.MySQLiteHelper;

public class telaDeCadastro extends ActionBarActivity {
	
	public Button bt_cadastrar;
	public EditText field_senha, field_nome, field_email, field_endereco, field_telefone, field_data;
	private MySQLiteHelper db = new MySQLiteHelper(this);
	private Context ctx;
	
	//Variaveis de campo de entrada
	public String email,senha,nome,end,tell,data;
	
	//JSON
	private JSONParser jsonParser = new JSONParser();
	private ProgressDialog pDialog;
	
	private static String REGISTER_URL = "";
	//Tags do script php
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastrousuario);
		
		//Sets das instancias do XML
		this.bt_cadastrar = (Button) this.findViewById(R.id.bt_cadastrar_cadastro);
		
		//Sets das instancias do XML
		
		field_senha = (EditText) findViewById(R.id.field_senha_cadastro);
		field_nome = (EditText) findViewById(R.id.field_nome_cadastro);
		field_email = (EditText) findViewById(R.id.field_email_cadastro);
		field_endereco = (EditText) findViewById(R.id.field_endereco_cadastro);
		field_telefone = (EditText) findViewById(R.id.field_telefone_cadastro);
		field_data = (EditText) findViewById(R.id.field_datanascimento_cadastro);
		
		//URL Server
		this.REGISTER_URL= "http://"+getIntent().getExtras().getString("ip")+":1234/webservice/register.php";
		
		this. ctx = this;
		
		this.bt_cadastrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				email = field_email.getText().toString();
				senha = field_senha.getText().toString();
				nome = field_nome.getText().toString();
				end = field_endereco.getText().toString();
				tell = field_telefone.getText().toString();
				data = field_data.getText().toString();
				
				
				if(email.length()>0 && senha.length()>0 && nome.length()>0 && end.length()>0 && tell.length()>0 && end.length()>0){
				
					//db.addCliente (new Cliente(email,senha,nome,end,tell,data,0));
					//Notificação
					//Toast.makeText(ctx, "Usuário Cadastrado com Sucesso!", Toast.LENGTH_SHORT);
					//Thread de execução.
					new CreateUser().execute();
					//finish();			
					// db.addCliente (new Cliente("han@unifei","han123","han","ruadhan","otelldhan","1982342"));
				}
				else{
					AlertDialog.Builder alert = new AlertDialog.Builder(telaDeCadastro.this);
					alert.setMessage("Todos os campos devem ser preenchidos!");
					alert.setTitle("Aviso");
					alert.setNeutralButton("OK", null);
					alert.show();
				}
			}
		});
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	public class CreateUser extends AsyncTask<String, String, String> {

		 protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(telaDeCadastro.this);
	            pDialog.setMessage("Tentando fazer o cadastro...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	            pDialog.show();
	        }
		
		@Override
		protected String doInBackground(String... params) {
			int success;
			try{
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("email", email));
				params1.add(new BasicNameValuePair("senha", senha));
				params1.add(new BasicNameValuePair("nome", nome));
				params1.add(new BasicNameValuePair("end", end));
				params1.add(new BasicNameValuePair("tel", tell));
				params1.add(new BasicNameValuePair("data", data));
				
				Log.d("Requisição de Inserção","Iniciado...");
				
				JSONObject json = jsonParser.makeHttpRequest(REGISTER_URL, "POST", params1);
				
				Log.d("Tentativa de Cadastro", json.toString());
				
				if ((success = json.getInt(TAG_SUCCESS))==1){
					Log.d("Novo usuário", "Criado com sucesso!");
					finish();
					return json.getString(TAG_MESSAGE);
				}else{
					Log.d("Falha de cadastro", json.toString());
					return json.getString(TAG_MESSAGE);
				}
			}catch (JSONException e){
				e.printStackTrace();
			}
			
			return null;
		}
		
		protected void onPostExecute(String file_url){
			pDialog.dismiss();
			if (file_url != null) Toast.makeText(telaDeCadastro.this,file_url,Toast.LENGTH_SHORT).show();
		}
		
	}
}
