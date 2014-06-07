package com.example.wazzyeventos;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wazzyeventos.sqlite.MySQLiteHelper;

public class telaRemoverUsuario extends ActionBarActivity {
	private Button bt_remover;
	private TextView email;
	private EditText senha;
	public Intent menu;
	private MySQLiteHelper db = new MySQLiteHelper(this);
	
	public void carregaTelaRemocao(){
		setContentView(R.layout.removerusuario);
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.removerusuario);
	
		bt_remover = (Button) findViewById(R.id.bt_remover_usuario);
		email = (TextView) findViewById(R.id.text_emailcliente_remover);
		senha = (EditText) findViewById(R.id.field_senha_remover);
		email.setText(MainActivity.login);
		
		bt_remover.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String pass;
				int deletei;
				pass = senha.getText().toString();
				if(pass.equals(MainActivity.senha)){
					deletei = db.removeCliente(MainActivity.login, MainActivity.senha);
					
					
					if(deletei == 1){
						AlertDialog.Builder alert = new AlertDialog.Builder(telaRemoverUsuario.this);
						//notificação
						finish();
						
					}
					else{
						Log.d("Erro Fechamento", "Errou!");
						finish();
					}
					
				}
				else{
					AlertDialog.Builder alert = new AlertDialog.Builder(telaRemoverUsuario.this);
					alert.setMessage("Senha Inválida!");
					alert.setTitle("WARNING");
					alert.setNegativeButton(":(", null);
					alert.show();
				}
				
			}
		});
		
		
	} 
	

}
