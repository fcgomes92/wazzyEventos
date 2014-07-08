package com.example.wazzyeventos;

import java.util.List;

import com.example.wazzyeventos.model.Comentario;
import com.example.wazzyeventos.model.Evento;
import com.example.wazzyeventos.sqlite.MySQLiteHelper;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

public class telaConsultameuseventos extends ActionBarActivity {
	
	public Button bt_editar, bt_deletar;
	public TextView nome_evento, local_evento, desc_evento, dono_evento;
	public String evento_score;
	public int id;
	private MySQLiteHelper db = new MySQLiteHelper(this);
	public Intent editaEvento;
	public Intent removeEvento;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telaconsultameuseventos);
		id = getIntent().getExtras().getInt("eventoid");
		
		

		
		//Sets das instancias do XML
		this.bt_editar = (Button) this.findViewById(R.id.bt_editar_consltameuEvento);
		this.bt_deletar = (Button) this.findViewById(R.id.bt_deletar_consultameuEvento); //Somente funciona se o usuario for dono do evento
		this.nome_evento = (TextView) this.findViewById(R.id.text_nome_evento_consultaMeuEvento);
		this.local_evento = (TextView) this.findViewById(R.id.text_local_evento_consultameuEvento);
		this.desc_evento = (TextView) this.findViewById(R.id.text_desc_evento_consultameuEvento);
		this.dono_evento = (TextView) this.findViewById(R.id.text_nome_dono_consultameuEvento);
		
		//setando valores dos campos
		Evento ev = db.getEvento(id);
		nome_evento.setText("Nome do Evento: "+ev.getNome());
		local_evento.setText("Local do Evento: "+ev.getLocal());
		desc_evento.setText("Descrição do Evento: "+ev.getDescricao());
		dono_evento.setText("Dono: "+ev.getLogin());
		
		//Instâncias dos Intents
		editaEvento = new Intent(this,telaEditameuseventos.class);
		
		this.evento_score = "0";
		
		this.bt_editar.setOnClickListener(handler);
		this.bt_deletar.setOnClickListener(handler);
	

	}
		
	
	public OnClickListener handler = new OnClickListener() {
		
		public void onClick(View v) {
			if(v == bt_editar){
				editaEvento.putExtra("Edita_evento_nome",getIntent().getExtras().getString("Evento_nome"));  
				editaEvento.putExtra("Edita_evento_local",getIntent().getExtras().getString("Evento_local"));  
				editaEvento.putExtra("Edita_evento_descricao",getIntent().getExtras().getString("Evento_descricao"));
				editaEvento.putExtra("Edita_evento_id", id);
				startActivity(editaEvento);
				finish();// volta pra tela principal
				
			}
			if(v == bt_deletar){
				AlertDialog.Builder pergunta = new AlertDialog.Builder(telaConsultameuseventos.this);
				pergunta.setTitle("ATENÇÃO!")
				.setMessage("Você está prestes a deletar este evento! Tem certeza disso?")
				.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Evento evt = db.getEvento(id);
						db.deleteEvento(evt);
						AlertDialog.Builder resposta = new AlertDialog.Builder(telaConsultameuseventos.this);
						resposta.setTitle("Sucesso!")
						.setMessage("Evento removido!")
						.setNeutralButton("OK", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								finish();
								
							}
						})
						.show();
					}
				});
				pergunta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						finish();
					}
				});
				pergunta.show();
				
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
