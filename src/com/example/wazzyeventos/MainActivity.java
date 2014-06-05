package com.example.wazzyeventos;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
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

public class MainActivity extends ActionBarActivity {
	
	public Button bt_login, bt_cadastrar, bt_logout;
	public EditText et_login, et_senha;
	public Intent mainclassI, cadastroUserI;
	public AlertDialog alertDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		
		//Sets das instancias do XML
		this.bt_login = (Button) this.findViewById(R.id.bt_login);
		this.bt_cadastrar = (Button) this.findViewById(R.id.bt_cadastrar);
		
		this.et_senha = (EditText) this.findViewById(R.id.et_senha);
		this.et_login = (EditText) this.findViewById(R.id.et_login);
		this.mainclassI = new Intent(this,mainScreen.class);
		this.cadastroUserI = new Intent(this,telaDeCadastro.class);
		
		this.bt_login.setOnClickListener(login_cadastro);
		this.bt_cadastrar.setOnClickListener(login_cadastro);
	}
	
	public OnClickListener login_cadastro = new OnClickListener() {
		
		public void onClick(View v) {
			if(v == bt_login){
				if(et_login.getText().toString().equals("admin") && et_senha.getText().toString().equals("admin123")){
					mainclassI.putExtra("login", et_login.getText().toString());
					startActivity(mainclassI);
				}
				else
					Log.d("Erro","Erro de login!");
		}
			if(v == bt_cadastrar)
				startActivity(cadastroUserI);
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

