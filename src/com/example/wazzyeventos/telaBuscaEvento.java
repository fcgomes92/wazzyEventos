package com.example.wazzyeventos;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
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
import android.widget.Toast;

import com.example.wazzyeventos.model.Evento;
import com.example.wazzyeventos.sqlite.MySQLiteHelper;

public class telaBuscaEvento extends ActionBarActivity {
	private Button bt_pesquisar;
	private EditText et_nome, et_local;
	private String nome;
	private String local;
	public MySQLiteHelper db;
	public ListView listaevento;

	public Context ctx;

	public Intent consultaEvento;
	
	
	private String auxNome;
	private ArrayList<String> valores = new ArrayList<String>();
	private ArrayList<Integer> ids = new ArrayList<Integer>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pesquisaevento);
		db = new MySQLiteHelper(this);
		
		// INSTANCIANCIAS DO XML
		consultaEvento = new Intent(this, telaConsultaEvento.class);
		
		bt_pesquisar = (Button) this
				.findViewById(R.id.bt_pesquisar_pesquisarevento);
		et_nome = (EditText) this.findViewById(R.id.field_nome_pesquisarevento);
		et_local = (EditText) this
				.findViewById(R.id.field_local_pesquisarevento);
		listaevento = (ListView) this.findViewById(R.id.list_evento);

		// Cria um contexto pra add no ArrayAdapter
		ctx = this;
		
		bt_pesquisar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				nome = et_nome.getText().toString();
				local = et_local.getText().toString();
				
				//limpa array valores a cada vez que clicar no botão pesquisar para não acumular valores
				valores.clear();
				ids.clear();
				if (nome.isEmpty() && local.isEmpty()) {
					List<Evento> lista = db.getAllEventos();
					// se a lista estiver vazia mostra q não há eventos
					// disponíveis

					if (lista.isEmpty()) {

						//mostra aviso sobre o fato de nao existir eventos
						AlertDialog.Builder alerta = new AlertDialog.Builder(
								telaBuscaEvento.this);
						alerta.setTitle("AVISO!");
						alerta.setMessage("Não há eventos disponíveis!");
						alerta.setNeutralButton("OK", null);
						alerta.show();
					} //caso ache algum evento
					else{
				
						for (int i = 0; i < lista.size(); i++) {
							ids.add(lista.get(i).getId());
							auxNome = lista.get(i).getNome();
							valores.add(auxNome);
						}
					}
				} else {
					//se algum campo for preenchido
					List<Evento> lista = db.getSelectedEvento(nome, local);
					if (lista.isEmpty()) {
						//limpa a lista de eventos caso não encontre nenhum
				
					
						//mostra aviso sobre o fato de nao existir eventos
						AlertDialog.Builder alerta = new AlertDialog.Builder(
								telaBuscaEvento.this);
						alerta.setTitle("AVISO!");
						alerta.setMessage("Não há eventos disponíveis!");
						alerta.setNeutralButton("OK", null);
						alerta.show();
					} //caso ache algum evento
					else{
			
						for (int i = 0; i < lista.size(); i++) {
							ids.add(lista.get(i).getId());
							auxNome = lista.get(i).getNome();
							valores.add(auxNome);
						}
					}
		
						
				}

				// Define a new Adapter
				// First parameter - Context
				// Second parameter - Layout for the row
				// Third parameter - ID of the TextView to which the data is
				// written
				// Forth - the Array of data
				
				//Limpa a tela antes de mostrar uma nova lista
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx,
						android.R.layout.simple_list_item_1,
						android.R.id.text1, valores);
				listaevento.setAdapter(adapter);



			}
		});
		
		listaevento.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// ListView Clicked item index
				int itemPosition = position;
				
				// ListView Clicked item value
				String itemValue = (String) listaevento
						.getItemAtPosition(position);

				// Show Alert
				//Toast.makeText(getApplicationContext(),
				//		"Nome evento: " + itemValue, Toast.LENGTH_LONG)
				//		.show();

				// Pega o itemValue -> Puxa do banco as informações
				// segundo o nome -> Cria activity de consulta com os
				// dados do banco
				Evento ev = new Evento();
				ev = db.getEvento(ids.get(itemPosition));
				consultaEvento.putExtra("Evento_nome", ev.getNome());
				consultaEvento.putExtra("Evento_local", ev.getLocal());
				consultaEvento.putExtra("Evento_descricao", ev.getDescricao());
				consultaEvento.putExtra("Evento_dono", ev.getLogin());
				startActivity(consultaEvento);
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
