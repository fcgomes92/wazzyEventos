package com.example.wazzyeventos.model;


public class Cliente {
 
      
	private String email;
    private String senha;
    private String nome;
    private String endereco;
    private String telefone;
    private String datanasc;
 
    public Cliente(){}
 
    public Cliente(String email, String senha, String nome, String endereco, String telefone, String datanasc) {
        super();
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.datanasc = datanasc;
    }
 
    //getters & setters
 
    @Override
    public String toString() {
        return "Cliente [email=" + email + ", nome=" + nome + ", endereco=" + endereco + ", telefone=" + telefone +", Data Nascimento=" + datanasc+ "]";
    }
	
	public void setEmail(String t){
		email = t;
		}
	public String getEmail(){
		return email;
	}
	
	public void setSenha(String t){
		senha = t;
	}
	public String getSenha(){
		return senha;
	}
	public void setNome(String t){
		nome = t;
	}
	public String getNome(){
		return nome;
	}
	public void setEndereco(String t){
		endereco = t;
	}
	public String getEndereco(){
		return endereco;
	}
	public void setTelefone(String t){
		telefone = t;
	}
	public String getTelefone(){
		return telefone;
	}
	public void setData(String t){
		datanasc = t;
	}
	public String getData(){
		return datanasc;
	}

	
	
}
