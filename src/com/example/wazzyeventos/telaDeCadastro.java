package com.example.wazzyeventos;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class telaDeCadastro extends ActionBarActivity {
	
	public Button bt_cadastrar, bt_cancelar;
	public EditText field_login, field_senha, field_nome;
	public Intent mainActI;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastrousuario);
		
		//Sets das instancias do XML
		this.bt_cancelar = (Button) this.findViewById(R.id.bt_cancelar_cadastro);
		this.bt_cadastrar = (Button) this.findViewById(R.id.bt_cadastrar_cadastro);
		this.mainActI = new Intent(this, MainActivity.class);
		
		this.bt_cancelar.setOnClickListener(handler);
		
	}
		
	
	public OnClickListener handler = new OnClickListener() {
		
		public void onClick(View v) {
			if(v == bt_cancelar)
					startActivity(mainActI);
			if(v == bt_cadastrar){
				//verifica campos vazios
				//insere no bd
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
