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
import android.widget.RadioButton;
import android.widget.TextView;

public class telaConsultaEvento extends ActionBarActivity {
	
	public Button bt_editar, bt_deletar;
	public TextView nome_evento, local_evento, desc_evento, dono_evento;
	public RadioButton e1, e2, e3, e4, e5;
	public String evento_score;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telaconsultaevento);
		
		//Sets das instancias do XML
		this.bt_editar = (Button) this.findViewById(R.id.bt_editar_consltaEvento);
		this.bt_deletar = (Button) this.findViewById(R.id.bt_deletar_consultaEvento); //Somente funciona se o usuario for dono do evento
		this.nome_evento = (TextView) this.findViewById(R.id.text_nome_evento_consultaEvento);
		this.local_evento = (TextView) this.findViewById(R.id.text_local_evento_consultaEvento);
		this.desc_evento = (TextView) this.findViewById(R.id.text_desc_evento_consultaEvento);
		this.dono_evento = (TextView) this.findViewById(R.id.text_nome_dono_consultaEvento);
		
		//setando valores dos campos
		this.nome_evento.setText("Nome do Evento: "+getIntent().getExtras().getString("Evento_nome"));  
		this.local_evento.setText("Local do Evento: "+getIntent().getExtras().getString("Evento_local"));  
		this.desc_evento.setText("Descrição do Evento: "+getIntent().getExtras().getString("Evento_descricao"));  
		this.dono_evento.setText("Dono: "+getIntent().getExtras().getString("Evento_dono"));  
		
		
		//RadioBtsde avaliacao
		this.e1 = (RadioButton) this.findViewById(R.id.rb_1estrela_consultaEvento);
		this.e2 = (RadioButton) this.findViewById(R.id.rb_2estrela_consultaEvento);
		this.e3 = (RadioButton) this.findViewById(R.id.rb_3estrela_consultaEvento);
		this.e4 = (RadioButton) this.findViewById(R.id.rb_4estrela_consultaEvento);
		this.e5 = (RadioButton) this.findViewById(R.id.rb_5estrela_consultaEvento);
		
		this.evento_score = "0";
		
		this.bt_editar.setOnClickListener(handler);
		this.bt_deletar.setOnClickListener(handler);
		
		this.e1.setOnClickListener(rb_handler);
		this.e2.setOnClickListener(rb_handler);
		this.e3.setOnClickListener(rb_handler);
		this.e4.setOnClickListener(rb_handler);
		this.e5.setOnClickListener(rb_handler);
		
	}
		
	
	public OnClickListener handler = new OnClickListener() {
		
		public void onClick(View v) {
			if(v == bt_editar){
				
			}
			if(v == bt_deletar){
				
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
				evento_score = "1";
			}
			if (v == e2){
				e1.setChecked(true);
				e2.setChecked(true);
				evento_score = "2";
			}
			if (v == e3){
				e1.setChecked(true);
				e2.setChecked(true);
				e3.setChecked(true);
				evento_score = "3";
			}
			if (v == e4){
				e1.setChecked(true);
				e2.setChecked(true);
				e3.setChecked(true);
				e4.setChecked(true);
				evento_score = "4";
			}
			if (v == e5){
				e1.setChecked(true);
				e2.setChecked(true);
				e3.setChecked(true);
				e4.setChecked(true);
				e5.setChecked(true);
				evento_score = "5";
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