package com.example.wazzyeventos;

import com.example.wazzyeventos.model.Evento;
import com.example.wazzyeventos.sqlite.MySQLiteHelper;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;






public class telaEditameuseventos extends ActionBarActivity{
	public String nNome,nLocal,nDescricao;
	public int id;
	public int res;
	public Button bt_alterar;
	public EditText eNome,eLocal,eDescricao;
	public String auxNome,auxLocal,auxDescricao;
	private MySQLiteHelper db = new MySQLiteHelper(this);
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telaeditameuseventos);
		
		//Instanciando widgets do layout
		bt_alterar = (Button) this.findViewById(R.id.bt_edita_alterar);
		eNome = (EditText) this.findViewById(R.id.field_edita_nome);
		eLocal = (EditText) this.findViewById(R.id.field_edita_local);
		eDescricao  = (EditText) this.findViewById(R.id.field_edita_descricao);
		
		//Colocando ja os campos do objeto clicado
		eNome.setText(getIntent().getExtras().getString("Edita_evento_nome"));
		eLocal.setText(getIntent().getExtras().getString("Edita_evento_local"));
		eDescricao.setText(getIntent().getExtras().getString("Edita_evento_descricao"));
		
		//instanciando o id
		id = getIntent().getExtras().getInt("Edita_evento_id");
		bt_alterar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//Verifica se os campos estão todos completos
				if(eNome.length()>0&&eLocal.length()>0&&eDescricao.length()>0){
				//chama funcao altera a partir de id
				
					auxNome = eNome.getText().toString();
					auxLocal = eLocal.getText().toString();
					auxDescricao = eDescricao.getText().toString();
					
					res = db.updateEvento(auxNome,auxLocal,auxDescricao, id);
					AlertDialog.Builder atualizou = new AlertDialog.Builder(telaEditameuseventos.this);
						if(res == 1){//atualizou
							atualizou.setTitle("AVISO!")
							.setMessage("Evento atualizado com Sucesso!")
							.setNeutralButton("OK", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									finish();
									
								}
							})
							.show();
						}else{//nao conseguiu atualizar
							atualizou.setTitle("AVISO!")
							.setMessage("ERRO ao Atualizar Evento!")
							.setNeutralButton("OK", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									finish();
									
								}
							})
							.show();
						}
				
				}else{ //se existe algum campo em branco
					AlertDialog.Builder msgerro = new AlertDialog.Builder(telaEditameuseventos.this);
					msgerro.setTitle("ERRO!")
					.setMessage("Todos os campos devem ser preenchidos!")
					.setNeutralButton("OK", null)
					.show();
					
					
				}
			}
		});
		
			
			
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
