package com.example.wazzyeventos.model;

import com.example.wazzyeventos.sqlite.MySQLiteHelper;

import android.util.Log;
 
public class Evento {
	public MySQLiteHelper db;

	private String nome;
	private String local;
	private String descricao;
	private String login;
	private int id;
	private int aval;
 
    public Evento(){
    }
    public Evento(String nome, String local, String descricao, int aval) {
        super();
    	this.setNome(nome);
		this.setLocal(local);
		this.setDescricao(descricao);
		this.setAval(aval);
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
    public void setAval(int aval){
    	this.aval = aval;
    }
    
    public int getAval(){
    	return this.aval;
    }
    
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