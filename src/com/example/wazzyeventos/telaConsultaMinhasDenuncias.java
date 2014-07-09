package com.example.wazzyeventos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class telaConsultaMinhasDenuncias extends ActionBarActivity {
		
		public TextView nome_evento, motivo, desc_evento, dono_evento;
		public String evento_score;
		public int id;
		public Intent editaEvento;
		
		private static final String TAG_LOGIN_REA = "login_realiza_denuncia";
	    private static final String TAG_LOGIN_REC = "login_recebe_denuncia";
	    private static final String TAG_DESC_DEN = "descricao_denuncia";
	    private static final String TAG_MOT_DEN = "motivo_denuncia";
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.telaconsultaminhasdenuncias);
			
			//Sets das instancias do XML
			this.nome_evento = (TextView) this.findViewById(R.id.tx_nome_evento_denunciado);
			this.motivo = (TextView) this.findViewById(R.id.tx_motivo);
			this.desc_evento = (TextView) this.findViewById(R.id.tx_descricao);
			this.dono_evento = (TextView) this.findViewById(R.id.tx_donoEvento);
			
			//setando valores dos campos
			//Denuncia dn = db.getDenuncia(id);
			nome_evento.setText("Denuncia feita por: " + getIntent().getExtras().getString(TAG_LOGIN_REA));
			motivo.setText("Motivo: " + getIntent().getExtras().getString(TAG_MOT_DEN));
			desc_evento.setText("Descrição: " + getIntent().getExtras().getString(TAG_DESC_DEN));
			dono_evento.setText("Dono do evento: " + getIntent().getExtras().getString(TAG_LOGIN_REC));
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
