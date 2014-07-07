package com.example.wazzyeventos;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.wazzyeventos.jsonctrl.JSONParser;

@SuppressLint("NewApi") public class mainScreen extends ActionBarActivity {
	
	public Button bt_criar_evento,bt_consulta_evento,bt_meuseventos, bt_acessar_site, bt_ondeEstou;
	public Button bt_consulta_usuario, bt_deletar_Usuario, bt_alterar_usuario;
	public Intent cadastrarEvento;
	public Intent buscarEvento;
	public Intent meusEventos;
	public Intent pesquisaS,removeS,alterarS, ondeEstouS;
	public String login, url_siteProj, senha;
	public static Context ctx;
	
	// JSON Info
				private ProgressDialog pDialog;
				private JSONParser jsonP;
				
				//Server Info
				public static String ip = "192.168.1.4", countL = "0";
				private static final String TRIGGER_URL = "http://"+ip+":1234/webservice/triggerDB.php";

				//Tags JSON
				private static final String TAG_SUCCESS = "success";
				private static final String TAG_MESSAGE = "message";
				private static final String TAG_COUNT = "qtd";
			
				private JSONArray mComments = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainscreen);
		
		//Sets das instancias do XML
		
		pesquisaS = new Intent (this, telaBuscaUsuario.class);
		
		//Cliente
		this.bt_consulta_usuario = (Button) this.findViewById(R.id.bt_consultar_usuario_ms);
		this.bt_deletar_Usuario = (Button) this.findViewById(R.id.bt_del_conta_ms);
		this.bt_alterar_usuario = (Button) this.findViewById(R.id.bt_meu_perfil_ms);
		this.bt_ondeEstou = (Button) this.findViewById(R.id.bt_onde_estou);
		this.removeS = new Intent(this, telaRemoverUsuario.class);
		this.alterarS = new Intent(this, telaAlterarUsuario.class);
		this.ondeEstouS = new Intent(this, gpsControle.class);
		this.ctx = this;
		
		//Evento
		this.bt_criar_evento = (Button) this.findViewById(R.id.bt_criar_evento_ms);
		this.bt_consulta_evento = (Button) this.findViewById(R.id.bt_consultar_evento_ms);
		this.bt_meuseventos = (Button) this.findViewById(R.id.bt_meus_eventos_ms);
		this.cadastrarEvento = new Intent(this, telaCadastroEvento.class);
		this.buscarEvento = new Intent(this, telaBuscaEvento.class);
		this.meusEventos = new Intent(this, telaListaEvento.class);
		
		//Site
		this.bt_acessar_site = (Button) this.findViewById(R.id.bt_acesso_site);
		
		//URL site do projeto
		this.url_siteProj = "http://wazzyevenos.blogspot.com.br/2014/06/wazzy-eventos.html";
		
		this.login = getIntent().getExtras().getString("login");
		this.senha = getIntent().getExtras().getString("senha");
		Log.d("Login: ","Login: "+ login);
	
		Toast.makeText(this, "Não esqueça de acessar o nosso site!", Toast.LENGTH_SHORT).show();
		
		this.bt_criar_evento.setOnClickListener(login_cadastro);
		this.bt_consulta_evento.setOnClickListener(login_cadastro);
		this.bt_acessar_site.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent siteProjI = new Intent(Intent.ACTION_VIEW, Uri.parse(url_siteProj));
				startActivity(siteProjI);
			}
		});
		
		this.bt_criar_evento.setOnClickListener(login_cadastro);
		
		this.bt_ondeEstou.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(ondeEstouS);
				}
		});
		
		this.bt_alterar_usuario.setOnClickListener(new View.OnClickListener() {
			
				@Override
				public void onClick(View v) {
					alterarS.putExtra("login", login);
					startActivity(alterarS);
				}
			});
		
		this.bt_deletar_Usuario.setOnClickListener(new View.OnClickListener() {
			
				@Override
				public void onClick(View v) {
					removeS.putExtra("username", login);
					removeS.putExtra("senha", senha);
					startActivity(removeS);
					finish();
				
				}
			});
		
		this.bt_consulta_usuario.setOnClickListener(new View.OnClickListener() {
			
				@Override
				public void onClick(View v) {
					startActivity(pesquisaS);
					
				}
			});
			this.bt_meuseventos.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
				meusEventos.putExtra("login", login);
				startActivity(meusEventos);	
				}
			});
			
		new triggerUpdate().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
	}
	
	public OnClickListener login_cadastro = new OnClickListener() {
		
		public void onClick(View v) {
			if(v == bt_criar_evento){
				cadastrarEvento.putExtra("login", login);
				startActivity(cadastrarEvento);
			}
			if(v == bt_consulta_evento){
				startActivity(buscarEvento);
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
	
	public class triggerUpdate extends AsyncTask<String, String, String>{
		@Override
		protected String doInBackground(String... arg0) {
			Looper.prepare();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
	        JSONParser jParser;
	        JSONObject json;
	        while(true){
	        	jParser = new JSONParser();
	        	json = jParser.getJSONFromUrl(TRIGGER_URL, params);
	        try {
	            mComments = json.getJSONArray(TAG_COUNT);
	            // looping through all posts according to the json object returned
	                JSONObject c = mComments.getJSONObject(0);

	                String count = c.getString(TAG_COUNT);
	                if(!(countL.equals(count))) push(count);
	                else Log.d("Trigger: ", "... Sem alteração ...");
	                
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	        try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        }
		}
	}
		
		public void push(String count){
			NotificationCompat.Builder mBuilder =
			        new NotificationCompat.Builder(ctx)
			        .setSmallIcon(R.drawable.ic_launcher)
			        .setContentTitle("Novos eventos disponíveis!")
			        .setContentText("Temos "+(Integer.parseInt(count) - Integer.parseInt(countL))+"novo(s) evento(s)!")
			        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
			mBuilder.setContentIntent(null);
			NotificationManager mNotificationManager =
			    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			mNotificationManager.notify(0, mBuilder.build());
			countL = count;
		}
	
}
