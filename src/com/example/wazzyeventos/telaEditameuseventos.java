package com.example.wazzyeventos;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.wazzyeventos.jsonctrl.JSONParser;

public class telaEditameuseventos extends ActionBarActivity{
	public String nNome,nLocal,nDescricao, id;
	public int res;
	public Button bt_alterar;
	public EditText eNome,eLocal,eDescricao;
	public String auxNome,auxLocal,auxDescricao;
	
	private ProgressDialog pDialog;
	private JSONParser jsonP;
	
	//Server info 
		public static String ip = "192.168.1.4";
	    private static final String ALTERA_MEUS_EVENTOS_URL = "http://"+ip+":1234/webservice/alterarEvento.php";

	    //JSON element ids from repsonse of php script:
	    private static final String TAG_SUCCESS = "success";
	    private static final String TAG_MESSAGE = "message";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.telaeditameuseventos);
		
	//Instanciando widgets do layout
		bt_alterar = (Button) this.findViewById(R.id.bt_edita_alterar);
		eNome = (EditText) this.findViewById(R.id.field_edita_nome);
		eLocal = (EditText) this.findViewById(R.id.field_edita_local);
		eDescricao  = (EditText) this.findViewById(R.id.field_edita_descricao);
		
		//Colocando ja os campos do objeto clicado
		eNome.setText(getIntent().getExtras().getString("Edita_evento_nome"));
		eLocal.setText(getIntent().getExtras().getString("Edita_evento_local"));
		eDescricao.setText(getIntent().getExtras().getString("Edita_evento_descricao"));
		
		//instanciando o id
		id = getIntent().getExtras().getString("Edita_evento_id");
		bt_alterar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//Verifica se os campos estão todos completos
				if(eNome.length()>0&&eLocal.length()>0&&eDescricao.length()>0){
					//chama funcao altera a partir de id				
					auxNome = eNome.getText().toString();
					auxLocal = eLocal.getText().toString();
					auxDescricao = eDescricao.getText().toString();
					
					new alterEvento().execute();
					
				}else{ //se existe algum campo em branco
					AlertDialog.Builder msgerro = new AlertDialog.Builder(telaEditameuseventos.this);
					msgerro.setTitle("ERRO!")
					.setMessage("Todos os campos devem ser preenchidos!")
					.setNeutralButton("OK", null)
					.show();
				}
			}
		});
	}
	
	public class alterEvento extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(telaEditameuseventos.this);
			pDialog.setMessage("Atualizando Evento...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected Boolean doInBackground(Void... arg0) {
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id",id));
			params.add(new BasicNameValuePair("nome",auxNome));
			params.add(new BasicNameValuePair("local",auxLocal));
			params.add(new BasicNameValuePair("desc",auxDescricao));
	         
	        JSONParser jParser = new JSONParser();
	        JSONObject json = jParser.getJSONFromUrl(ALTERA_MEUS_EVENTOS_URL, params);
			return null;
		}
		
		@Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            finish();
        }
	}
	
}