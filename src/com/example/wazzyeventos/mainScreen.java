package com.example.wazzyeventos;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Intent;
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
	
	public Button bt_criar_evento, bt_consultarEvento, bt_consultar_Usuario;
	public Intent mainActI, cadastrarEvento, consultarEventoInt, consultarUsuarioInt;
	public String login;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainscreen);
		
		//Sets das instancias do XML
		this.bt_criar_evento = (Button) this.findViewById(R.id.bt_criar_evento_ms);
		this.bt_consultarEvento = (Button) this.findViewById(R.id.bt_consultar_evento_ms);
		this.bt_consultar_Usuario = (Button) this.findViewById(R.id.bt_consultar_usuario_ms);
		
		//Atividades
		this.mainActI = new Intent(this, MainActivity.class);
		this.cadastrarEvento = new Intent(this, telaCadastroEvento.class);
		this.consultarEventoInt = new Intent(this, telaConsultaEvento.class);
		this.consultarUsuarioInt = new Intent(this, telaConsultaUsuario.class);
		
		//Login do usuario logado
		this.login = getIntent().getExtras().getString("login");
		Log.d("Login: ","Login: "+ login);
		
		this.bt_criar_evento.setOnClickListener(handler);
		this.bt_consultarEvento.setOnClickListener(handler);
		this.bt_consultar_Usuario.setOnClickListener(handler);
		
	}
		
	
	public OnClickListener handler = new OnClickListener() {
		
		public void onClick(View v) {
			if(v == bt_criar_evento){
				startActivity(cadastrarEvento);
			}
			if(v == bt_consultarEvento){
				startActivity(consultarEventoInt);
			}
			if(v == bt_consultar_Usuario){
				startActivity(consultarUsuarioInt);
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
}
