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

public class telaConsultaUsuario extends ActionBarActivity {
	
	public TextView nome_user, email_user, end_user, tel_user, dataNasc_user;
	public String user_score;
	public RadioButton e1, e2, e3, e4, e5;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telaconsultausuario);
		
		//Sets das instancias do XML
		this.nome_user = (TextView) this.findViewById(R.id.text_nomeUser_consultaUser);
		this.email_user = (TextView) this.findViewById(R.id.text_emailUser_consultaUser);
		this.end_user = (TextView) this.findViewById(R.id.text_endUser_consultaUser);
		this.tel_user = (TextView) this.findViewById(R.id.text_telUser_consultaUser);
		this.dataNasc_user = (TextView) this.findViewById(R.id.text_dataNasc_consultaUser);
		
		//RadioBtsde avaliacao
		this.e1 = (RadioButton) this.findViewById(R.id.rb_1estrela_consultaEvento);
		this.e2 = (RadioButton) this.findViewById(R.id.rb_2estrela_consultaEvento);
		this.e3 = (RadioButton) this.findViewById(R.id.rb_3estrela_consultaEvento);
		this.e4 = (RadioButton) this.findViewById(R.id.rb_4estrela_consultaEvento);
		this.e5 = (RadioButton) this.findViewById(R.id.rb_5estrela_consultaEvento);
		
		this.user_score = "0";
		
		this.e1.setOnClickListener(rb_handler);
		this.e2.setOnClickListener(rb_handler);
		this.e3.setOnClickListener(rb_handler);
		this.e4.setOnClickListener(rb_handler);
		this.e5.setOnClickListener(rb_handler);
		
	}
		
	
	public OnClickListener handler = new OnClickListener() {
		
		public void onClick(View v) {
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
				user_score = "1";
			}
			if (v == e2){
				e1.setChecked(true);
				e2.setChecked(true);
				user_score = "2";
			}
			if (v == e3){
				e1.setChecked(true);
				e2.setChecked(true);
				e3.setChecked(true);
				user_score = "3";
			}
			if (v == e4){
				e1.setChecked(true);
				e2.setChecked(true);
				e3.setChecked(true);
				e4.setChecked(true);
				user_score = "4";
			}
			if (v == e5){
				e1.setChecked(true);
				e2.setChecked(true);
				e3.setChecked(true);
				e4.setChecked(true);
				e5.setChecked(true);
				user_score = "5";
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
