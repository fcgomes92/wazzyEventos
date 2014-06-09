package com.example.wazzyeventos;

import java.util.LinkedList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wazzyeventos.model.Cliente;
import com.example.wazzyeventos.sqlite.MySQLiteHelper;

public class telaAlterarUsuario extends ActionBarActivity {
		private Button bt_alterar;
		private TextView nome,data,email;
		private EditText senha, telefone, endereco;
		public Intent menu;
		private Context ctx;
		private int time_toast;
		private MySQLiteHelper db = new MySQLiteHelper(this);
		private List<Cliente> clienteBD = new LinkedList<Cliente>();
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.alterarusuario);
			
			nome = (TextView) findViewById(R.id.text_nome_alterar);
			data = (TextView) findViewById(R.id.text_data_alterar);
			
			this.ctx = this; 	
			this.time_toast = Toast.LENGTH_SHORT;
			
			email = (TextView) findViewById(R.id.field_email_alterar);
			telefone = (EditText) findViewById(R.id.field_telefone_alterar);
			endereco = (EditText) findViewById(R.id.field_endereco_alterar);
			senha = (EditText) findViewById(R.id.field_senha_alterar);
			
			//Pegando do BD os dados do cliente
			clienteBD = db.getAllClientes("", "", getIntent().getExtras().getString("login_cliente"));
			
			nome.setText(clienteBD.get(0).getNome());
			data.setText(clienteBD.get(0).getData());
			email.setText(clienteBD.get(0).getEmail());
			telefone.setText(clienteBD.get(0).getTelefone());
			endereco.setText(clienteBD.get(0).getEndereco());
			bt_alterar = (Button) findViewById(R.id.bt_alterar);
			bt_alterar.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(senha.getText().toString().equals(MainActivity.senha)){
						db.updateCliente(endereco.getText().toString(), telefone.getText().toString(), MainActivity.login);
						String texto = "Dados alterados com sucesso!";
						//gera notificação com toasts
						Toast.makeText(ctx, texto, time_toast).show();
						finish();
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
