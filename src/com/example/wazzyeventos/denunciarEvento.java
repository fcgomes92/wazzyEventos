package com.example.wazzyeventos;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.wazzyeventos.jsonctrl.JSONParser;

public class denunciarEvento extends ActionBarActivity  {
	
	public Button bt_denuncia;
	public EditText descricao;
	public RadioButton e1,e2,e3,e4;
	private Context ctx;
	public String logado, login_den, id_ev_den, desc, mot;
	
	//JSON para update de avaliação de evento
		private ProgressDialog pDialog;
		private JSONParser jsonP;

		public static String ip = "192.168.1.4";
		private static final String REGISTER_DEN_URL = "http://"+ip+":1234/webservice/registerdenuncia.php";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.denuncia);
		
		//RadioBtsde avaliacao
		this.e1 = (RadioButton) this.findViewById(R.id.rd_eventoErrado);
		this.e2 = (RadioButton) this.findViewById(R.id.rd_eventoFalso);
		this.e3 = (RadioButton) this.findViewById(R.id.rd_eventoIlegal);
		this.e4 = (RadioButton) this.findViewById(R.id.rd_outros);

		//public String login_realiza, login_den, id_ev_den, desc, mot;
		this.logado = getIntent().getExtras().getString("logado");
		this.login_den = getIntent().getExtras().getString("login_den");
		this.id_ev_den = getIntent().getExtras().getString("id_ev_den");
		
		this.bt_denuncia = (Button) findViewById(R.id.bt_denunciar);
		this.descricao = (EditText) findViewById(R.id.edt_MotivoDenuncia);
		this. ctx = this;
		
		bt_denuncia.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(e1.isChecked()){
					mot="Evento Errado";
				}
				else if(e2.isChecked()){
					mot="Evento Falso";
				}
				else if(e3.isChecked()){
					mot="Evento Ilegal";
				}
				else if(e4.isChecked()){
					mot="Outros";
				}
				
				desc = descricao.getText().toString();
				new registerDenuncia().execute();
				
				Toast.makeText(ctx, "Denuncia realizada com sucesso!", Toast.LENGTH_LONG).show();
				finish();
			}
		});		
	}
	
	public class registerDenuncia extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(ctx);
			pDialog.setMessage("Denunciando...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected Boolean doInBackground(Void... arg0) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("login_faz", logado));
			params.add(new BasicNameValuePair("login_recebe", login_den));
			params.add(new BasicNameValuePair("id_ev_recebe", id_ev_den));
			params.add(new BasicNameValuePair("desc_den", desc));
			params.add(new BasicNameValuePair("mot_den", mot));
			
	        JSONParser jParser = new JSONParser();
	        JSONObject json = jParser.getJSONFromUrl(REGISTER_DEN_URL, params);	                
			return null;
		}
		
		@Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            pDialog.dismiss();
        }
	}
}
