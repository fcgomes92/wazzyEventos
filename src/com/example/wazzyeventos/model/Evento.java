package com.example.wazzyeventos.model;

import com.example.wazzyeventos.sqlite.MySQLiteHelper;

import android.util.Log;
 
public class Evento {
	public MySQLiteHelper db;

	String nome;
	String local;
	String descricao;
	String login;
	int id;
 
    public Evento(){
    }
    public Evento(String nome, String local, String descricao) {
        super();
    	this.setNome(nome);
		this.setLocal(local);
		this.setDescricao(descricao);
    }
    //objeto temporario pra funcao de pegar eventos e jogar em uma lista
    public Evento(String nome, String local, String descricao,String login) {
        super();
    	this.setNome(nome);
		this.setLocal(local);
		this.setDescricao(descricao);
		this.setLogin(login);
    }
 
    //getters & setters
 
    public void setId(int id){
		this.id = id;
	}
	
	public void setNome(String nome){
		this.nome = nome;
	}
	public void setLocal(String local){
		this.local = local;
	}
	public void setDescricao(String descricao){
		this.descricao = descricao;
	}
	public void setLogin(String login){
		this.login = login;
	}

	
	//Getters
	public int getId(){
		return this.id;
	}
	
	public String getNome() {
		return this.nome;
	}
	public String getLocal(){
		return this.local;
	}
	public String getDescricao(){
		return this.descricao;
	}
	public String getLogin(){
		return this.login;
	}
	

    
    @Override
    public String toString() {
        return "Evento [id=" + this.id + ", nome=" + this.getNome() + ", local=" + this.getLocal()
                + ", descricao="+ this.getDescricao() + ", login = "+login+"]";
    }

}