package com.example.wazzyeventos;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wazzyeventos.jsonctrl.JSONParser;

public class telaConsultaEvento extends ActionBarActivity {
	
	
	public TextView nome_evento, local_evento, desc_evento, dono_evento;
	public Button bt_aval_evento, bt_gps;
	public RatingBar rtbar_aval_geral_evento;
	public RadioButton e1, e2, e3, e4, e5;
	public int realScore, evento_score;
	public String id, lat, lon, logado, login_recebe;
	private Context ctx;
	public Button bt_denuncia;
	public Button bt_comentar;
	public Intent denuncia, comentario, telagps;
	
	//JSON para update de avaliação de evento
	private ProgressDialog pDialog;
	private JSONParser jsonP;

	public static String ip = "192.168.1.4";
    private static final String AVAL_EVENT_URL = "http://"+ip+":1234/webservice/updateAvalEvento.php";

    //JSON element ids from repsonse of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telaconsultaevento);
		
		//Sets das instancias do XML
		
		this.nome_evento = (TextView) this.findViewById(R.id.text_nome_evento_consultaEvento);
		this.local_evento = (TextView) this.findViewById(R.id.text_local_evento_consultaEvento);
		this.desc_evento = (TextView) this.findViewById(R.id.text_desc_evento_consultaEvento);
		this.dono_evento = (TextView) this.findViewById(R.id.text_nome_dono_consultaEvento);
		this.denuncia = new Intent(this,denunciarEvento.class);
		this.comentario = new Intent(this,telaComentario.class);
		this.telagps = new Intent(this,GpsControle.class);
		this.bt_comentar = (Button) findViewById(R.id.bt_comentar);
		this.bt_denuncia = (Button) findViewById(R.id.bt_denunciar_evento);
		this.bt_gps = (Button) findViewById(R.id.bt_gps_consulta_evento);
		
		this.login_recebe = getIntent().getExtras().getString("login");
		this.logado = getIntent().getExtras().getString("logado");
		
		//setando valores dos campos
		this.nome_evento.setText("Nome do Evento: "+getIntent().getExtras().getString("nome"));  
		this.local_evento.setText("Local do Evento: "+getIntent().getExtras().getString("local"));  
		this.desc_evento.setText("Descrição do Evento: "+getIntent().getExtras().getString("desc"));  
		this.dono_evento.setText("Dono: "+getIntent().getExtras().getString("login")); 
		this.id = getIntent().getExtras().getString("id");
		this.lat = getIntent().getExtras().getString("lat");
		this.lon = getIntent().getExtras().getString("lon");
		
		this.ctx = this;
		
		//RadioBtsde avaliacao
		this.e1 = (RadioButton) this.findViewById(R.id.rb_1estrela_consultaEvento);
		this.e2 = (RadioButton) this.findViewById(R.id.rb_2estrela_consultaEvento);
		this.e3 = (RadioButton) this.findViewById(R.id.rb_3estrela_consultaEvento);
		this.e4 = (RadioButton) this.findViewById(R.id.rb_4estrela_consultaEvento);
		this.e5 = (RadioButton) this.findViewById(R.id.rb_5estrela_consultaEvento);
		
		this.bt_aval_evento = (Button) this.findViewById(R.id.bt_aval_evento);
		this.rtbar_aval_geral_evento = (RatingBar) this.findViewById(R.id.rtbar_geral_evento);
		this.realScore = Integer.parseInt(getIntent().getExtras().getString("aval"));
		this.rtbar_aval_geral_evento.setRating(realScore);
		this.evento_score = 0;
		
		this.bt_gps.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					telagps.putExtra("marcador", false);
					telagps.putExtra("localiza", true);
					telagps.putExtra("latitude", Double.parseDouble(lat));
					telagps.putExtra("longitude", Double.parseDouble(lon));
					startActivity(telagps);		
			}
		});
		
		this.bt_denuncia.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				denuncia.putExtra("id_ev_den", id);
				denuncia.putExtra("login_den", login_recebe);
				denuncia.putExtra("logado", logado);
				startActivity(denuncia);
			}
		});
		this.bt_comentar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				comentario.putExtra("id_ev", id);
				comentario.putExtra("username", logado);
				startActivity(comentario);
			}
		});
		this.bt_aval_evento.setOnClickListener(handler); 
			
		this.e1.setOnClickListener(rb_handler);
		this.e2.setOnClickListener(rb_handler);
		this.e3.setOnClickListener(rb_handler);
		this.e4.setOnClickListener(rb_handler);
		this.e5.setOnClickListener(rb_handler);
		
	
		
		
	}
	
	public OnClickListener handler = new OnClickListener() {
		@Override
		public void onClick(View v){
			if (v == bt_aval_evento){
				if(evento_score != 0){
					if (realScore == 0) realScore=evento_score;
					else realScore = (evento_score + realScore)/2;
					rtbar_aval_geral_evento.setRating(realScore);
					new updateAval().execute();
					finish();
				}
				else{
					Toast.makeText(ctx, "Avalie utilizando a barra acima!" , Toast.LENGTH_SHORT).show();
					rtbar_aval_geral_evento.setRating(realScore);
				}
			}
		}
	};
	
	public OnClickListener rb_handler = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			e1.setChecked(false);
			e2.setChecked(false);
			e3.setChecked(false);
			e4.setChecked(false);
			e5.setChecked(false);
			if (v == e1){
				e1.setChecked(true);
				evento_score = 1;
			}
			if (v == e2){
				e1.setChecked(true);
				e2.setChecked(true);
				evento_score = 2;
			}
			if (v == e3){
				e1.setChecked(true);
				e2.setChecked(true);
				e3.setChecked(true);
				evento_score = 3;
			}
			if (v == e4){
				e1.setChecked(true);
				e2.setChecked(true);
				e3.setChecked(true);
				e4.setChecked(true);
				evento_score = 4;
			}
			if (v == e5){
				e1.setChecked(true);
				e2.setChecked(true);
				e3.setChecked(true);
				e4.setChecked(true);
				e5.setChecked(true);
				evento_score = 5;
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
	
	//Conexão avaliação banco
	public class updateAval extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(ctx);
			pDialog.setMessage("Avaliando a avaliação do evento...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected Boolean doInBackground(Void... arg0) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("aval",""+realScore));
			params.add(new BasicNameValuePair("id", id));
	         
	        JSONParser jParser = new JSONParser();
	        JSONObject json = jParser.getJSONFromUrl(AVAL_EVENT_URL, params);	                
			return null;
		}
		
		@Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            pDialog.dismiss();
        }
	}
}
