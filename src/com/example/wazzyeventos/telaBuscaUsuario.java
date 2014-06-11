package com.example.wazzyeventos;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.wazzyeventos.model.Cliente;
import com.example.wazzyeventos.sqlite.MySQLiteHelper;

public class telaBuscaUsuario extends ActionBarActivity {
	
	private Button bt_pesquisar;
	private EditText nome, endereco, email;
	public Intent usuario_escolhido;
	public ListView lista;
	private MySQLiteHelper db = new MySQLiteHelper(this);
	private Context ctx;
	private List<Cliente> clientes = new LinkedList<Cliente>();
	private List<String> nomes_clientes = new LinkedList<String>();
	private ArrayAdapter<String> adapter; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pesquisausuario);
	
		bt_pesquisar = (Button) findViewById(R.id.bt_pesquisar_pesquisa);
		
		nome = (EditText) findViewById(R.id.field_nome_pesquisar);
		endereco = (EditText) findViewById(R.id.field_endereco_pesquisar);
		email = (EditText) findViewById(R.id.field_email_pesquisar);
		
		ctx = this;
		
		usuario_escolhido = new Intent(ctx, telaConsultaUsuario.class);
		
		lista = (ListView) findViewById(R.id.lista_usuarios);
		lista.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int pos, long id) {
				// Add dados do usuario escolhido no extras
				usuario_escolhido.putExtra("usuario_escolhido_nome", clientes.get(pos).getNome());
				usuario_escolhido.putExtra("usuario_escolhido_email", clientes.get(pos).getEmail());
				usuario_escolhido.putExtra("usuario_escolhido_end", clientes.get(pos).getEndereco());
				usuario_escolhido.putExtra("usuario_escolhido_tel", clientes.get(pos).getTelefone());
				usuario_escolhido.putExtra("usuario_escolhido_dtnsc", clientes.get(pos).getData());
				usuario_escolhido.putExtra("usuario_escolhido_aval", clientes.get(pos).getAval());
				Log.d("user_aval_1", ""+clientes.get(pos).getAval());
				startActivity(usuario_escolhido);
				finish();
			}
		});
       
		
		bt_pesquisar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				clientes.clear();
				nomes_clientes.clear();
				String pnome, pendereco, pemail;
					pnome = nome.getText().toString();
					pendereco = endereco.getText().toString();
					pemail = email.getText().toString();
				clientes = db.getAllClientes(pnome, pendereco, pemail);
				for(int i = 0 ; i < clientes.size() ; i++){
					nomes_clientes.add(clientes.get(i).getNome());
					Log.d("Nome: ", nomes_clientes.get(i));
					adapter = new ArrayAdapter(ctx, android.R.layout.simple_list_item_1, nomes_clientes);
					lista.setAdapter(adapter);
				}
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
