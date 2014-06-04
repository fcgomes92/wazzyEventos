package com.example.wazzyeventos;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class mainScreen extends ActionBarActivity {
	
	public Button bt_criar_evento, bt_consultarEvento, bt_consultar_Usuario, bt_acessarSiteProj;
	public Intent mainActI, cadastrarEvento, consultarEventoInt, consultarUsuarioInt;
	public String login, url_siteProj;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainscreen);
		
		//Sets das instancias do XML
		this.bt_criar_evento = (Button) this.findViewById(R.id.bt_criar_evento_ms);
		this.bt_consultarEvento = (Button) this.findViewById(R.id.bt_consultar_evento_ms);
		this.bt_consultar_Usuario = (Button) this.findViewById(R.id.bt_consultar_usuario_ms);
		this.bt_acessarSiteProj = (Button) this.findViewById(R.id.bt_acesso_site);
		
		//URL site do projeto
		this.url_siteProj = "http://wazzyevenos.blogspot.com.br/2014/06/wazzy-eventos.html";
		
		//Atividades
		this.mainActI = new Intent(this, MainActivity.class);
		this.cadastrarEvento = new Intent(this, telaCadastroEvento.class);
		this.consultarEventoInt = new Intent(this, telaConsultaEvento.class);
		this.consultarUsuarioInt = new Intent(this, telaConsultaUsuario.class);
		//Cria a atividade que chamará o site do projeto
		
		//Login do usuario logado
		this.login = getIntent().getExtras().getString("login");
		Log.d("Login: ","Login: "+ login);
		
		this.bt_criar_evento.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(cadastrarEvento);
			}
		});
		this.bt_consultarEvento.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(consultarEventoInt);
			}
		});
		this.bt_consultar_Usuario.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(consultarUsuarioInt);
			}
		});
		this.bt_acessarSiteProj.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent siteProjI = new Intent(Intent.ACTION_VIEW, Uri.parse(url_siteProj));
				startActivity(siteProjI);
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
}
