package com.example.wazzyeventos;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wazzyeventos.jsonctrl.JSONParser;


public class telaCadastroEvento extends ActionBarActivity {
	
	public Button bt_cadastrar, bt_gps;
	public boolean marcador = true;
	public Intent telagps;	
	public EditText et_nome, et_local, et_desc,et_cidade;
	//variaveis auxiliares
	public String nome;
	public String local;
	public String login;
	public String descricao;
	public Double lat, lon;
	public Context ctx;
	
	private ProgressDialog pDialog;
	private JSONParser jsonP;

	public static String ip = "192.168.1.4";
    private static final String REGISTER_EVENT_URL = "http://"+ip+":1234/webservice/registerevento.php";

    //JSON element ids from repsonse of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telacadastroevento);
		
		//Sets das instancias do XML
		this.jsonP = new JSONParser();
		
		this.bt_cadastrar = (Button) this.findViewById(R.id.bt_cadastrar_evento);
		this.bt_gps = (Button) this.findViewById(R.id.bt_gps_cadastro_evn);
		this.et_nome = (EditText) this.findViewById(R.id.field_nome_evento_ce);
		this.et_local = (EditText) this.findViewById(R.id.field_local_evento_ce);
		this.et_desc = (EditText) this.findViewById(R.id.field_desc_evento_ce);
		
		this.telagps = new Intent(this, GpsControle.class);
		
		this.ctx = this;
		
		this.login = getIntent().getExtras().getString("login");
		
		bt_gps.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			if(marcador ==true){
				telagps.putExtra("nome", et_nome.getText().toString());
				telagps.putExtra("local", et_local.getText().toString());
				telagps.putExtra("marcador", marcador);
				telagps.putExtra("localiza",false);
				startActivityForResult(telagps, 1);
				}
			}
		});
		
		bt_cadastrar.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View arg0){
				
				//ADICIONA UM EVENTO NO BANCO DE DADOS!!!
				
				nome = et_nome.getText().toString();
				local = et_local.getText().toString();
				descricao = et_desc.getText().toString();
				
			
				if(nome.length()>0&&local.length()>0&&descricao.length()>0){
					//db.addEvento(new Evento(nome,local,descricao, 0),login,0);
					new AttemptRegisterEvetno().execute();
					Log.d("Sucesso Criação Evento", "Evento criado com sucesso, pelo usuário: "+ login +"!");
					finish();
				}
				
				else{
					AlertDialog.Builder alerta = new AlertDialog.Builder(telaCadastroEvento.this);
					alerta.setTitle("AVISO!");
					alerta.setMessage("Todos os campos devem ser preenchidos.");
					alerta.setNeutralButton("OK", null);
					alerta.show();
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

	@Override 
	public void onActivityResult(int requestCode, int resultCode, Intent data) {     
		super.onActivityResult(requestCode, resultCode, data); 
	    switch(requestCode) { 
	    case (1) : { 
	    	if (resultCode == Activity.RESULT_OK) {
	    		lat= data.getDoubleExtra("latitude", 0);
	    		lon= data.getDoubleExtra("longitude",0);
	    		marcador = false;
	        	} 
	    	break; 
	    	}
	    } 
	}
	
	public class AttemptRegisterEvetno extends AsyncTask<String, String, String> {

				//three methods get called, first preExecture, then do in background, and once do
				//in back ground is completed, the onPost execture method will be called.

		        @Override
		        protected void onPreExecute() {
		            super.onPreExecute();
		            pDialog = new ProgressDialog(telaCadastroEvento.this);
		            pDialog.setMessage("Tentando criar o evento...");
		            pDialog.setIndeterminate(false);
		            pDialog.setCancelable(true);
		            pDialog.show();
		        }

				@Override
				protected String doInBackground(String... args) {
					int success;
					try{
						//Cria os parametros
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("nome",nome));
						params.add(new BasicNameValuePair("local",local));
						params.add(new BasicNameValuePair("desc",descricao));
						params.add(new BasicNameValuePair("login",login));
						params.add(new BasicNameValuePair("latitude",lat+""));
						params.add(new BasicNameValuePair("longitude",lon+""));
						//requisição HTTP
						JSONObject json = JSONParser.makeHttpRequest(REGISTER_EVENT_URL, "POST", params);
						
						//Log da resposta json
						Log.d("Login",json.toString());
						
						//Se o login for bem sucedido
						success = json.getInt(TAG_SUCCESS);
						if (success == 1){
							Log.d("Evento Register","Successful! " + json.toString());
							finish();
							return json.getString(TAG_MESSAGE);
						}
						else{
							Log.d("Evento Register","Error! " + json.toString());
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
		        	if (file_url != null){
		        		//Toast.makeText(ctx, file_url, Toast.LENGTH_SHORT).show();
						//Toast.makeText(ctx, "Evento criado com sucesso!", Toast.LENGTH_SHORT).show();
		        	}
		        }

			}
}