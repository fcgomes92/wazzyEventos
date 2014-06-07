package com.example.wazzyeventos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.wazzyeventos.model.Cliente;
import com.example.wazzyeventos.sqlite.MySQLiteHelper;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class telaBuscaUsuario extends ActionBarActivity {
	
	private Button bt_pesquisar;
	private EditText nome, endereco, email;
	public Intent menu;
	public ListView lista;
	private MySQLiteHelper db = new MySQLiteHelper(this);
	private Context context;

	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pesquisausuario);
		
		menu = new Intent(this,mainScreen.class);
	
		bt_pesquisar = (Button) findViewById(R.id.bt_pesquisar_pesquisa);
		
		nome = (EditText) findViewById(R.id.field_nome_pesquisar);
		endereco = (EditText) findViewById(R.id.field_endereco_pesquisar);
		email = (EditText) findViewById(R.id.field_email_pesquisar);
		
		lista = (ListView) findViewById(R.id.lista_usuarios);
        
       
		
		bt_pesquisar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String pnome, pendereco, pemail;
					pnome = nome.getText().toString();
					pendereco = endereco.getText().toString();
					pemail = email.getText().toString();
					
					
			}
		});
		
		
		
		
	} 
	
	

	
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu;to the action bar if it is present.
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
