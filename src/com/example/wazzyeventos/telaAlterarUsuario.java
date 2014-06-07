package com.example.wazzyeventos;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wazzyeventos.sqlite.MySQLiteHelper;

public class telaAlterarUsuario extends ActionBarActivity {
		private Button bt_alterar;
		private TextView nome,data,email;
		private EditText senha, telefone, endereco;
		public Intent menu;
		private MySQLiteHelper db = new MySQLiteHelper(this);
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.alterarusuario);
			
			nome = (TextView) findViewById(R.id.text_nome_alterar);
			data = (TextView) findViewById(R.id.text_data_alterar);
			
			
			email = (TextView) findViewById(R.id.field_email_alterar);
			telefone = (EditText) findViewById(R.id.field_telefone_alterar);
			endereco = (EditText) findViewById(R.id.field_endereco_alterar);
			senha = (EditText) findViewById(R.id.field_senha_alterar);
			
			nome.setText(MainActivity.nome);
			data.setText(MainActivity.data);
			email.setText(MainActivity.login);
			telefone.setText(MainActivity.telefone);
			endereco.setText(MainActivity.endereco);
			bt_alterar = (Button) findViewById(R.id.bt_alterar);
			
			bt_alterar.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(senha.getText().toString().equals(MainActivity.senha)){
						db.updateCliente(endereco.getText().toString(), telefone.getText().toString(), MainActivity.login);
						AlertDialog.Builder alert = new AlertDialog.Builder(telaAlterarUsuario.this);
						alert.setMessage("Alteração feita com Sucesso!");
						alert.setTitle("Aviso");
						alert.setNeutralButton("OK", null);
						alert.show();
					}
					else{
						AlertDialog.Builder alert = new AlertDialog.Builder(telaAlterarUsuario.this);
						alert.setMessage("Senha inválida!");
						alert.setTitle("Aviso");
						alert.setNeutralButton("OK", null);
						alert.show();
					}
					
				}
			});
			
		}
		
		
		

	}
