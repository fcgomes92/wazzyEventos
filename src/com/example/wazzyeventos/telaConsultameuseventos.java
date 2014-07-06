package com.example.wazzyeventos;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wazzyeventos.jsonctrl.JSONParser;

public class telaConsultameuseventos extends ActionBarActivity {
	
	public Button bt_editar, bt_deletar;
	public TextView nome_evento, local_evento, desc_evento, dono_evento;
	public String evento_score;
	public Intent editaEvento;
	public Intent removeEvento;
	public String id;
	
	// JSON Info
		private ProgressDialog pDialog;
		private JSONParser jsonP;
				
		//Server Info
		public static String ip = "192.168.1.4";
		private static final String DEL_EV_URL = "http://"+ip+":1234/webservice/delEvento.php";

		//Tags JSON
		private static final String TAG_SUCCESS = "success";
		private static final String TAG_MESSAGE = "message";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telaconsultameuseventos);
				
		//Sets das instancias do XML
		this.bt_editar = (Button) this.findViewById(R.id.bt_editar_consltameuEvento);
		this.bt_deletar = (Button) this.findViewById(R.id.bt_deletar_consultameuEvento); //Somente funciona se o usuario for dono do evento
		this.nome_evento = (TextView) this.findViewById(R.id.text_nome_evento_consultameuEvento);
		this.local_evento = (TextView) this.findViewById(R.id.text_local_evento_consultameuEvento);
		this.desc_evento = (TextView) this.findViewById(R.id.text_desc_evento_consultameuEvento);
		this.dono_evento = (TextView) this.findViewById(R.id.text_nome_dono_consultameuEvento);
		
		//setando valores dos campos
		nome_evento.setText("Nome do Evento: "+getIntent().getExtras().getString("nome"));
		local_evento.setText("Local do Evento: "+getIntent().getExtras().getString("local"));
		desc_evento.setText("Descrição do Evento: "+getIntent().getExtras().getString("desc"));
		dono_evento.setText("Dono: "+getIntent().getExtras().getString("login"));
		id = getIntent().getExtras().getString("id");
		
		//Instâncias dos Intents
		editaEvento = new Intent(this,telaEditameuseventos.class);
		
		this.evento_score = "0";
		
		this.bt_editar.setOnClickListener(handler);
		this.bt_deletar.setOnClickListener(handler);
	}
		
	
	public OnClickListener handler = new OnClickListener() {
		
		public void onClick(View v) {
			if(v == bt_editar){
				editaEvento.putExtra("Edita_evento_nome",getIntent().getExtras().getString("nome"));  
				editaEvento.putExtra("Edita_evento_local",getIntent().getExtras().getString("local"));  
				editaEvento.putExtra("Edita_evento_descricao",getIntent().getExtras().getString("desc"));
				editaEvento.putExtra("Edita_evento_id",id);
				startActivity(editaEvento);
				finish();// volta pra tela principal
				
			}
			if(v == bt_deletar){
				AlertDialog.Builder pergunta = new AlertDialog.Builder(telaConsultameuseventos.this);
				pergunta.setTitle("ATENÇÃO!")
				.setMessage("Você está prestes a deletar este evento! Tem certeza disso?")
				.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						//JSON
						new delEvento().execute();
						AlertDialog.Builder resposta = new AlertDialog.Builder(telaConsultameuseventos.this);
						resposta.setTitle("Sucesso!")
						.setMessage("Evento removido!")
						.setNeutralButton("OK", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								finish();
							}
						}).show();
					}
				});
				pergunta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						finish();
					}
				});
				pergunta.show();
			}
		}
	}; 
	
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
	
	public class delEvento extends AsyncTask<Void, Void, Boolean>{
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(telaConsultameuseventos.this);
			pDialog.setMessage("Deletando Usuário...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected Boolean doInBackground(Void... arg0) {
			//Envio de parametros para deletar evento
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id", id));
			
	        JSONParser jParser = new JSONParser();
	        JSONObject json = jParser.getJSONFromUrl(DEL_EV_URL, params);
	        return null;
		}
		
		@Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            pDialog.dismiss();
        }
	}
}
