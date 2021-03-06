package com.example.wazzyeventos;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
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

import com.example.wazzyeventos.jsonctrl.JSONParser;

public class MainActivity extends ActionBarActivity {
	
	public Button bt_login, bt_cadastrar, bt_logout;
	public EditText et_login, et_senha;
	public Intent mainclassI, cadastroUserI;
	public static String login,senha,nome,endereco,telefone,data;
	public Context ctx;
	
	//Fase de conexão com o servidor usando JSON
	private ProgressDialog pDialog;
	private JSONParser jsonP;
	
    //testing on Emulator:
	private static final String ip = "192.168.1.4";
    private static final String LOGIN_URL = "http://"+ip+":1234/webservice/login.php";

    //JSON element ids from repsonse of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);

		//Sets das instancias do XML
		this.bt_login = (Button) this.findViewById(R.id.bt_login);
		this.bt_cadastrar = (Button) this.findViewById(R.id.bt_cadastrar);
		
		this.jsonP = new JSONParser();
		
		this.et_senha = (EditText) this.findViewById(R.id.et_senha);
		this.et_login = (EditText) this.findViewById(R.id.et_login);
		this.mainclassI = new Intent(this,mainScreen.class);
		this.cadastroUserI = new Intent(this,telaDeCadastro.class);
		this.ctx = this;
		
		this.bt_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mainclassI.putExtra("login", et_login.getText().toString());
				mainclassI.putExtra("senha", et_senha.getText().toString());
				login = et_login.getText().toString();
				senha = et_senha.getText().toString();
				//Para poder executar a thread mais de uma vez
				new AttemptLogin().execute();		
			}
		});
		this.bt_cadastrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				cadastroUserI.putExtra("ip", ip);
				startActivity(cadastroUserI);
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

	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	class AttemptLogin extends AsyncTask<String, String, String> {
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            Log.d("Login1","Entrou!");
	            pDialog = new ProgressDialog(MainActivity.this);
	            pDialog.setMessage("Tentando fazer o login...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	            pDialog.show();
	        }

			@Override
			protected String doInBackground(String... args) {
				Looper.prepare();
				int success;
				try{
					//Cria os parametros
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("username",login));
					params.add(new BasicNameValuePair("password",senha));
					Log.d("Requisicao","Começando");
					
					//requisição HTTP
					JSONObject json = JSONParser.makeHttpRequest(LOGIN_URL, "POST", params);
					
					//Log da resposta json
					Log.d("Login",json.toString());
					
					//Se o login for bem sucedido
					success = json.getInt(TAG_SUCCESS);
					if (success == 1){
						Log.d("Login","Successful! " + json.toString());
						//finish();
						startActivity(mainclassI);
						return json.getString(TAG_MESSAGE);
					}
					else{
						Log.d("Login","Error! " + json.toString());
						return json.getString(TAG_MESSAGE);
					}
				} catch (JSONException e){
					e.printStackTrace();
				}
				return null;
			}

	        protected void onPostExecute(String file_url) {
	        	//Faz sumir o informativo de download
	        	pDialog.dismiss();
	        }

		}
}

