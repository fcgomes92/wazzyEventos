package com.example.wazzyeventos;

import com.example.wazzyeventos.sqlite.MySQLiteHelper;
import com.example.wazzyeventos.model.Evento;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.EditText;


public class telaCadastroEvento extends ActionBarActivity {
	
	public Button bt_cadastrar;
	public EditText et_nome, et_local, et_desc,et_cidade;
	 MySQLiteHelper db = new MySQLiteHelper(this);
	//variaveis auxiliares
	public String nome;
	public String local;
	public String descricao;
	public Intent mainScreen;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telacadastroevento);
		
		//Sets das instancias do XML
		this.bt_cadastrar = (Button) this.findViewById(R.id.bt_cadastrar_evento);
		this.et_nome = (EditText) this.findViewById(R.id.field_nome_evento_ce);
		this.et_local = (EditText) this.findViewById(R.id.field_local_evento_ce);
		this.et_desc = (EditText) this.findViewById(R.id.field_desc_evento_ce);
		
		this.mainScreen = new Intent(this, mainScreen.class);
		
		
		bt_cadastrar.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View arg0){
				
				//ADICIONA UM EVENTO NO BANCO DE DADOS!!!
				
				nome = et_nome.getText().toString();
				local = et_local.getText().toString();
				descricao = et_desc.getText().toString();
				
			
				if(nome.length()>0&&local.length()>0&&descricao.length()>0){
					db.addEvento(new Evento(nome,local,descricao));
					Log.d("Sucesso Criação Evento", "Evento criado com sucesso!");
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
}
