package com.example.wazzyeventos;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wazzyeventos.sqlite.MySQLiteHelper;

public class telaConsultaUsuario extends ActionBarActivity {
	
	public TextView nome_user, email_user, end_user, tel_user, dataNasc_user;
	public int user_score;
	public Button bt_avaliar;
	public RatingBar rtbar_user;
	private Context ctx;
	private int realScore;
	public RadioButton e1, e2, e3, e4, e5;
	private MySQLiteHelper db = new MySQLiteHelper(this);
	
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
		
		//Setting dados do usuario escolhido
		this.nome_user.setText("Nome: " + getIntent().getExtras().getString("usuario_escolhido_nome"));
		this.email_user.setText("Email: " + getIntent().getExtras().getString("usuario_escolhido_email"));
		this.end_user.setText("Endereço: " + getIntent().getExtras().getString("usuario_escolhido_end"));
		this.tel_user.setText("Telefone: " + getIntent().getExtras().getString("usuario_escolhido_tel"));
		this.dataNasc_user.setText("Data Nascimento: " + getIntent().getExtras().getString("usuario_escolhido_dtnsc"));
		
		// Barra de estrelas
		this.rtbar_user = (RatingBar) this.findViewById(R.id.rtbar_geral_user);
		this.realScore =  getIntent().getExtras().getInt("usuario_escolhido_aval");
		this.rtbar_user.setRating(realScore);
		Log.d("user_aval", ""+realScore);
		
		//RadioBtsde avaliacao
		this.e1 = (RadioButton) this.findViewById(R.id.rb_1estrela_consultaEvento);
		this.e2 = (RadioButton) this.findViewById(R.id.rb_2estrela_consultaEvento);
		this.e3 = (RadioButton) this.findViewById(R.id.rb_3estrela_consultaEvento);
		this.e4 = (RadioButton) this.findViewById(R.id.rb_4estrela_consultaEvento);
		this.e5 = (RadioButton) this.findViewById(R.id.rb_5estrela_consultaEvento);
		
		this.bt_avaliar = (Button) this.findViewById(R.id.bt_avaliar_user);
		this.bt_avaliar.setOnClickListener(handler);
		
		this.ctx = this;
		
		this.user_score = 0;
		
		this.e1.setOnClickListener(rb_handler);
		this.e2.setOnClickListener(rb_handler);
		this.e3.setOnClickListener(rb_handler);
		this.e4.setOnClickListener(rb_handler);
		this.e5.setOnClickListener(rb_handler);
		
	}
		
	
	public OnClickListener handler = new OnClickListener() {
		
		public void onClick(View v) {
			if (v == bt_avaliar){
				if(user_score != 0){
					if (realScore == 0) realScore=user_score;
					else realScore = (user_score + realScore)/2;
					rtbar_user.setRating(realScore);
					db.updateClienteAval(getIntent().getExtras().getString("usuario_escolhido_email"),realScore);
					finish();
				}
				else{
					Toast.makeText(ctx, "Avalie utilizando a barra acima!" , Toast.LENGTH_SHORT).show();
					rtbar_user.setRating(realScore);
				}
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
				user_score = 1;
			}
			if (v == e2){
				e1.setChecked(true);
				e2.setChecked(true);
				user_score = 2;
			}
			if (v == e3){
				e1.setChecked(true);
				e2.setChecked(true);
				e3.setChecked(true);
				user_score = 3;
			}
			if (v == e4){
				e1.setChecked(true);
				e2.setChecked(true);
				e3.setChecked(true);
				e4.setChecked(true);
				user_score = 4;
			}
			if (v == e5){
				e1.setChecked(true);
				e2.setChecked(true);
				e3.setChecked(true);
				e4.setChecked(true);
				e5.setChecked(true);
				user_score = 5;
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
