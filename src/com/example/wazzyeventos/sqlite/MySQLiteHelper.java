// Nomeando
package com.example.wazzyeventos.sqlite;
 
import java.util.LinkedList;
import java.util.List;
import com.example.wazzyeventos.MainActivity;
import com.example.wazzyeventos.model.Cliente;
import com.example.wazzyeventos.model.Evento;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class MySQLiteHelper extends SQLiteOpenHelper {
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "WazzyDB2";
    
    private MainActivity as;
    
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
    	//Criação de tabela de cliente
    	// SQL statement to create cliente table
        String CREATE_CLIENTE_TABLE = "CREATE TABLE clientes ( " +
                "email TEXT PRIMARY KEY, " +
                "senha TEXT, "+
                "nome TEXT, "+
                "endereco TEXT, "+
                "telefone TEXT, "+
                "datanasc TEXT )";
        // create books table
        db.execSQL(CREATE_CLIENTE_TABLE);
    	
    	//Criação de tabela de tabela
        // SQL statement to create book table
        String CREATE_EVENTO_TABLE = "CREATE TABLE evento ( " +
        		"id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "nome TEXT, "+
                "local TEXT,"
                + "descricao TEXT,"
                + "login TEXT,"
                + " foreign key (login) references clientes (email)"
                +")";
        // create books table
        db.execSQL(CREATE_EVENTO_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
    	//Drop de tabelas
    	db.execSQL("DROP TABLE IF EXISTS clientes");
        db.execSQL("DROP TABLE IF EXISTS evento");
 
        // create fresh books table
        this.onCreate(db);
    }
    //---------------------------------------------------------------------
 
    /**
     * CRUD operations (create "add", read "get", update, delete) book + get all books + delete all books
     */
 
    // Books table name
    private static final String TABLE_EVENTO = "evento";
 
    // evento
    // Books Table Columns names
	private static final String KEY_ID = "id";
    private static final String KEY_NOME = "nome";
    private static final String KEY_LOCAL = "local";
    private static final String KEY_DESCRICAO = "descricao";
    private static final String KEY_LOGIN = "login";    
    // Books table name
    private static final String TABLE_CLIENTE = "clientes";
 
    // cliente
    // Books Table Columns names
    private static final String KEY_EMAIL = "email";
    private static final String KEY_SENHA = "senha";
    private static final String KEY_ENDERECO = "endereco";
    private static final String KEY_TELEFONE = "telefone";
    private static final String KEY_DATA = "datanasc";

    
    //Colunas das tabelas
    private static final String[] COLUMNS_CLIENTE = {KEY_EMAIL,KEY_SENHA,KEY_NOME,KEY_ENDERECO,KEY_TELEFONE,KEY_DATA};
    private static final String[] COLUMNS_EVENTO = {KEY_ID,KEY_NOME,KEY_LOCAL,KEY_DESCRICAO,KEY_LOGIN};
 
    public void addEvento(Evento ev,String login){
        Log.d("addEvento", ev.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NOME, ev.getNome()); // get title 
        values.put(KEY_LOCAL, ev.getLocal()); // get author
        values.put(KEY_DESCRICAO, ev.getDescricao());
        values.put(KEY_LOGIN,login);
        
        
      
        // 3. insert
        db.insert(TABLE_EVENTO, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
 
        // 4. close
        db.close(); 
    }
 
    public Evento getEvento(int id){
 
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
 
        // 2. build query
        Cursor cursor = 
                db.query(TABLE_EVENTO, // a. table
                COLUMNS_EVENTO, // b. column names
                " id = ?", // c. selections 
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
 
        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
 
        // 4. build book object
        Evento ev = new Evento();
        ev.setId(Integer.parseInt(cursor.getString(0)));
        ev.setNome(cursor.getString(1));
        ev.setLocal(cursor.getString(2));
        ev.setDescricao(cursor.getString(3));
        ev.setLogin(cursor.getString(4));
        
      
 
        Log.d("getBook("+id+")", ev.toString());
 
        // 5. return book
        return ev;
    }
 
    @SuppressLint("NewApi")
	public List<Evento> getSelectedEvento(String nome,String local){
    	 List<Evento> lista = new LinkedList<Evento>();
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "";
        // VERIFICACOES PARA SABER QUAL CAMPO FOI PREENCHIDO OU NAO
       if(nome.isEmpty()&&!local.isEmpty())
    	   query = "SELECT * FROM " + TABLE_EVENTO + " WHERE local = '"+local+"'";
       else 
    	   
    	   if (!nome.isEmpty()&&local.isEmpty())
    		  query = "SELECT * FROM "+TABLE_EVENTO+" WHERE nome = '"+nome+"'";
    	   else 
    		   
    		   query = "SELECT * FROM "+TABLE_EVENTO+" WHERE nome = '"+nome+"' AND local = '"+local+"'";
    	
       // FAZ CONSULTA
       
    		  Cursor cursor = db.rawQuery(query, null);
       //MONTA OS OBJETOS E ADICIONA NA LISTA  		
        Evento ev = null;
        if (cursor.moveToFirst()) {
            do {
                ev = new Evento();
                ev.setId(Integer.parseInt(cursor.getString(0)));
                ev.setNome(cursor.getString(1));
                ev.setLocal(cursor.getString(2));
                ev.setDescricao(cursor.getString(3));
                ev.setLogin(cursor.getString(4));
               
                //adiciona na lista
                lista.add(ev);
            } while (cursor.moveToNext());
        }
       
 
        Log.d("getBook()", lista.toString());
 
        // 5. return book
        return lista;
    }
 
    public List<Evento> getEventoByLogin(String login){
    	  List<Evento> evts = new LinkedList<Evento>();
    	  
          // 1. build the query
          String query = "SELECT  * FROM " + TABLE_EVENTO + " WHERE login = '"+login+"'";
   
          // 2. get reference to writable DB
          SQLiteDatabase db = this.getWritableDatabase();
          Cursor cursor = db.rawQuery(query, null);
   
          // 3. go over each row, build book and add it to list
          Evento ev = null;
          if (cursor.moveToFirst()) {
              do {
                  ev = new Evento();
                  ev.setId(Integer.parseInt(cursor.getString(0)));
                  ev.setNome(cursor.getString(1));
                  ev.setLocal(cursor.getString(2));
                  ev.setDescricao(cursor.getString(3));
                  ev.setLogin(cursor.getString(4));
                 
                 
                  // Add book to books
                  evts.add(ev);
              } while (cursor.moveToNext());
          }
   
          Log.d("getAllBooks()", evts.toString());
   
          // return books
          return evts;
    	
    	
    }
    
    // Get All Evento
    public List<Evento> getAllEventos() {
        List<Evento> evts = new LinkedList<Evento>();
 
        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_EVENTO;
 
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
 
        // 3. go over each row, build book and add it to list
        Evento ev = null;
        if (cursor.moveToFirst()) {
            do {
                ev = new Evento();
                ev.setId(Integer.parseInt(cursor.getString(0)));
                ev.setNome(cursor.getString(1));
                ev.setLocal(cursor.getString(2));
                ev.setDescricao(cursor.getString(3));
                ev.setLogin(cursor.getString(4));
               
               
                // Add book to books
                evts.add(ev);
            } while (cursor.moveToNext());
        }
 
        Log.d("getAllBooks()", evts.toString());
 
        // return books
        return evts;
    }
 
     // Updating single book
    public int updateEvento(String nome,String local,String descricao,int id) {
 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NOME,nome); 
        values.put(KEY_LOCAL, local);
        values.put(KEY_DESCRICAO,descricao);
 
        // 3. updating row
        int i = db.update(TABLE_EVENTO, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(id) }); //selection args
 
        // 4. close
        db.close();
 
        return i;
 
    }
 
    // Deleting single book
    public void deleteEvento(Evento ev) {
 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. delete
        db.delete(TABLE_EVENTO,
                KEY_ID+" = ?",
                new String[] { String.valueOf(ev.getId()) });
 
        // 3. close
        db.close();
 
        Log.d("deleteEvento", ev.toString());
 
    }
    
    
    // inicio do código para tratamento de cliente
    // tratar eles muito bem
    // o cliente tem sempre razão no batidão
    
    public void addCliente(Cliente cliente){
        Log.d("addCliente", cliente.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, cliente.getEmail()); // get email
        values.put(KEY_SENHA, cliente.getSenha()); // get senha
        values.put(KEY_NOME, cliente.getNome()); // get nome
        values.put(KEY_ENDERECO, cliente.getEndereco()); // get endereco
        values.put(KEY_TELEFONE, cliente.getTelefone()); // get telefone
        values.put(KEY_DATA, cliente.getData()); // get data
 
        // 3. insert
        db.insert(TABLE_CLIENTE, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
 
        // 4. close
        db.close();
    }
 

    //SÓ CHAME ESSA FUNÇÃO PARA VERIFICAR LOGIN!!!!!
    public int verificaCliente(String email, String senha){
 
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
 
        // 2. build query
        String query = "SELECT  * FROM " + TABLE_CLIENTE + " WHERE email like '"+ email +"' and senha like '" + senha +"'";
        Cursor cursor = db.rawQuery(query, null);
 
        // 3. if we got results get the first one
        try{
        if (cursor != null){
            cursor.moveToFirst();
 
        // 4. build book object
        Cliente cliente = new Cliente();
        cliente.setEmail(cursor.getString(0));
        cliente.setSenha(cursor.getString(1));
        cliente.setNome(cursor.getString(2));
        cliente.setEndereco(cursor.getString(3));
        cliente.setTelefone(cursor.getString(4));
        cliente.setData(cursor.getString(5));
        //Log.d("bla","email:"+cliente.getEmail()+"senha:"+cliente.getSenha());
        //as.endereco = cliente.getEndereco();
        //as.nome = cliente.getNome();
        //as.telefone = cliente.getTelefone();
        //as.data = cliente.getData();
        if(cliente.getEmail().equals(email) && cliente.getSenha().equals(senha)){
        	
        	return 1;
        }else return 0;
        }
        else return 0;
        }catch(Exception e){
        	return 0;
        }
    }
    
    public int removeCliente(String email, String senha){
    	 
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
 
        // 2. build query
        String query = "SELECT  * FROM " + TABLE_CLIENTE + " WHERE email like '"+ email +"' and senha like '" + senha +"'";
        Cursor cursor = db.rawQuery(query, null);
 
        // 3. if we got results get the first one
       
        if (cursor != null){
            cursor.moveToFirst();
 
        // 4. build book object
        Cliente cliente = new Cliente();
        cliente.setEmail(cursor.getString(0));
        cliente.setSenha(cursor.getString(1));
        cliente.setNome(cursor.getString(2));
        cliente.setEndereco(cursor.getString(3));
        cliente.setTelefone(cursor.getString(4));
        cliente.setData(cursor.getString(5));
        Log.d("bla","email:"+cliente.getEmail()+"senha:"+cliente.getSenha());
        
        //delete
        db.delete(TABLE_CLIENTE,
                KEY_EMAIL+" = ?",
                new String[] { cliente.getEmail()});
        Log.d("remover","removi com sucesso!");
        return 1;
        }
        else{
        	Log.d("remover","não removi :/ me desculpa plis!");
        	return 0;
        }
    }
    
 
    // Get All Clientes
    public List<Cliente> getAllClientes(String pnome, String pendereco, String pemail) {
        List<Cliente> clientes = new LinkedList<Cliente>();
 
        // 1. build the query
      //TUDO VAZIO, SELECIONA TODOS
        String query="";
		if(pnome.length()==0 && pendereco.length()==0 && pemail.length()==0){
			query = "SELECT  * FROM " + TABLE_CLIENTE;
		}
		//LISTAR BASEADO NO NOME
		if(pnome.length()>0 && pendereco.length()==0 && pemail.length()==0){
			query = "SELECT  * FROM " + TABLE_CLIENTE + " WHERE nome='"+ pnome+"'";
		}
		//LISTAR BASEADO NO ENDERECO
		if(pnome.length()==0 && pendereco.length()>0 && pemail.length()==0){
			query = "SELECT  * FROM " + TABLE_CLIENTE + " WHERE endereco='"+ pendereco+"'";
		}
		//LISTAR BASEADO NO EMAIL
		if(pnome.length()==0 && pendereco.length()==0 && pemail.length()>0){
			query = "SELECT  * FROM " + TABLE_CLIENTE + " WHERE email='"+ pemail+"'";
		}
		//LISTAR BASEADO NO NOME E NO ENDERECO
		if(pnome.length()>0 && pendereco.length()>0 && pemail.length()==0){
			query = "SELECT  * FROM " + TABLE_CLIENTE + " WHERE nome='"+ pnome+"'" +" and endereco='" + pendereco+"'";
		}
		//LISTAR BASEADO NO NOME E NO EMAIL
		if(pnome.length()>0 && pendereco.length()==0 && pemail.length()>0){
			query = "SELECT  * FROM " + TABLE_CLIENTE + " WHERE nome='"+ pnome+"'" +" and email='" + pemail+"'";
		}
		//LISTAR BASEADO NO ENDERECO E EMAIL
		if(pnome.length()==0 && pendereco.length()>0 && pemail.length()>0){
			query = "SELECT  * FROM " + TABLE_CLIENTE + " WHERE endereco='"+ pendereco+"'" +" and email='" + pemail+"'";
		}
		//LISTAR BASEADO EM TUTOOO
		if(pnome.length()>0 && pendereco.length()>0 && pemail.length()>0){
			query = "SELECT  * FROM " + TABLE_CLIENTE + " WHERE endereco='"+ pendereco+"'" +" and email='" + pemail+"'" +" and nome='"+pnome+"'";
		}
 
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
 
        // 3. go over each row, build book and add it to list
        Cliente cliente = null;
        if (cursor.moveToFirst()) {
            do {
                cliente = new Cliente();
                cliente.setEmail(cursor.getString(0));
                cliente.setSenha(cursor.getString(1));
                cliente.setNome(cursor.getString(2));
                cliente.setEndereco(cursor.getString(3));
                cliente.setTelefone(cursor.getString(4));
                cliente.setData(cursor.getString(5));
 
                // Add book to books
                clientes.add(cliente);
                
                
            } while (cursor.moveToNext());
        }
 
        //Log.d("getAllClientes", clientes.toString());
 
        // return books
        return clientes;
    }
 
     // Updating single book
    public int updateCliente(String endereco, String telefone, String email) {
 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        if(telefone.length()>0)
        	values.put("telefone", telefone); // get title
        if(endereco.length()>0)
        	values.put("endereco", endereco);
        // 3. updating row
        try{
        int i = db.update(TABLE_CLIENTE, //table
                values, // column/value
                KEY_EMAIL+" = ?", // selections
                new String[] { email }); //selection args
        }catch(SQLException e){
        	Log.d("erro","deu ruim");
        	return 0;
        }
        // 4. close
        db.close();
 
        return 1;
 
    }
 
    // Deleting single book
    public void deleteCliente(Cliente cliente) {
 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. delete
        db.delete(TABLE_CLIENTE,
                KEY_EMAIL+" = ?",
                new String[] { cliente.getEmail()});
 
        // 3. close
        db.close();
 
        Log.d("deleteCliente", cliente.toString());
 
    }
    
}