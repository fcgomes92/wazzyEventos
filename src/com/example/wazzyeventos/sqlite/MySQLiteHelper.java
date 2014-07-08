// Nomeando
package com.example.wazzyeventos.sqlite;
 
import java.util.LinkedList;
import java.util.List;

import com.example.wazzyeventos.MainActivity;
import com.example.wazzyeventos.model.Cliente;
import com.example.wazzyeventos.model.Comentario;
import com.example.wazzyeventos.model.Denuncia;
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
    private static final String DATABASE_NAME = "WazzyDB12";
    
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
                "datanasc TEXT, "+
                "avaluser INTEGER"+
                ")";
        // create books table
        db.execSQL(CREATE_CLIENTE_TABLE);
    	
    	//Criação de tabela de tabela
        // SQL statement to create evento table
        String CREATE_EVENTO_TABLE1 = "CREATE TABLE evento ( " +
        		"id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "nome TEXT, "+
                "local TEXT, "
                + "descricao TEXT, "
                + "login TEXT, "
                + "avalevento INTEGER, "
                + "foreign key (login) references clientes (email)"
                +" )";
        // create books table
        db.execSQL(CREATE_EVENTO_TABLE1);
        
      //Criação de tabela de Denuncia
      // SQL statement to create denuncia table
        String CREATE_DENUNCIA_TABLE = "CREATE TABLE denuncia ( " +
        		"id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "emailCliente TEXT, "+
                "eventoDenunciado TEXT, "
                + "emailDenunciado TEXT, "
                + "motivo TEXT, "
                + "descricao TEXT, "
                + "foreign key (emailCliente) references clientes (email)"
//                + "foreign key (emailDenunciado) references clientes (email),"
//                + "foreign key (eventoDenunciado) references evento (nome)"
                +" )";
        // create denuncia table
        db.execSQL(CREATE_DENUNCIA_TABLE);
        
      //Criação de tabela de Denuncia
        // SQL statement to create denuncia table
          String CREATE_COMENTARIO_TABLE = "CREATE TABLE comentario ( " +
          		"id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                  "idevento INTEGER, "+
                  "comentario TEXT,"+
                  "autor TEXT,"
                  + "foreign key (idevento) references evento (id)"
                  +" )";
          // create comentario table
          db.execSQL(CREATE_COMENTARIO_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
    	//Drop de tabelas
    	db.execSQL("DROP TABLE IF EXISTS clientes");
        db.execSQL("DROP TABLE IF EXISTS evento");
        db.execSQL("DROP TABLE IF EXISTS denuncia");
        db.execSQL("DROP TABLE IF EXISTS comentario");
        
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
    // Evento Table Columns names
	private static final String KEY_ID = "id";
    private static final String KEY_NOME = "nome";
    private static final String KEY_LOCAL = "local";
    private static final String KEY_DESCRICAO = "descricao";
    private static final String KEY_LOGIN = "login";
    private static final String KEY_AVALEVENTO = "avalevento";
  
    // Books table name
    private static final String TABLE_CLIENTE = "clientes";
    // cliente
    // Cliente Table Columns names
    private static final String KEY_EMAIL = "email";
    private static final String KEY_SENHA = "senha";
    private static final String KEY_ENDERECO = "endereco";
    private static final String KEY_TELEFONE = "telefone";
    private static final String KEY_DATA = "datanasc";
    private static final String KEY_AVALCLIENTE = "avaluser";
    
    // Books table name
    private static final String TABLE_DENUNCIA = "denuncia";
    // Denuncia
    // Denuncia Table Columns names
    private static final String KEY_ID2 = "id";
    private static final String KEY_EMAILCLIENTE = "emailCliente";
    private static final String KEY_NOMEEVENTO = "eventoDenunciado";
    private static final String KEY_EMAILDENUNCIADO = "emailDenunciado";
    private static final String KEY_MOTIVO = "motivo";
    private static final String KEY_DESCRICAO2 = "descricao";
    
 // Books table name
    private static final String TABLE_COMENTARIO = "comentario";
    // Denuncia
    // Denuncia Table Columns names
    private static final String KEY_ID3 = "id";
    private static final String KEY_IDEVENTO = "idevento";
    private static final String KEY_COMENTARIO = "comentario";
    private static final String KEY_AUTOR = "autor";

    
    //Colunas das tabelas
    private static final String[] COLUMNS_CLIENTE = {KEY_EMAIL,KEY_SENHA,KEY_NOME,KEY_ENDERECO,KEY_TELEFONE,KEY_DATA, KEY_AVALCLIENTE};
    private static final String[] COLUMNS_EVENTO = {KEY_ID,KEY_NOME,KEY_LOCAL,KEY_DESCRICAO,KEY_LOGIN,KEY_AVALEVENTO};
    private static final String[] COLUMNS_DENUNCIA = {KEY_ID2, KEY_EMAILCLIENTE,KEY_NOMEEVENTO,KEY_EMAILDENUNCIADO,KEY_MOTIVO,KEY_DESCRICAO2};
    private static final String[] COLUMNS_COMENTARIO = {KEY_ID3, KEY_IDEVENTO, KEY_COMENTARIO, KEY_AUTOR};
    
    
    public void addComentario(Comentario cm){
    	Log.d("addComentario", "adicionando um comentario");
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_IDEVENTO, cm.getIdEvento()); // get title 
        values.put(KEY_COMENTARIO, cm.getComentario());// get author
        values.put(KEY_AUTOR, cm.getAutor());
      
        // 3. insert
        db.insert(TABLE_COMENTARIO, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
        // 4. close
        db.close(); 
    }
    
    public List<Comentario> getComentarioByIdEvento(int idevento){
  	  List<Comentario> evts = new LinkedList<Comentario>();
  	  
        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_COMENTARIO + " WHERE idevento = '"+idevento+"'";
 
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
 
        // 3. go over each row, build book and add it to list
        Comentario ev = null;
        try{
        if (cursor.moveToFirst()) {
            do {
                ev = new Comentario();
                ev.setId(Integer.parseInt(cursor.getString(0)));
                ev.setIdEvento(Integer.parseInt(cursor.getString(1)));
                ev.setComentario(cursor.getString(2));
                ev.setAutor(cursor.getString(3));
                // Add book to books
                evts.add(ev);
            } while (cursor.moveToNext());
        }
 
        // return books
        return evts; 	
        }catch(Exception e){
        	Log.d("bugou","deu merda! em getComentarioByIdEvento");
        	return null;
        }
}
    
    
    
    
    
    public void addEvento(Evento ev,String login,int aval){
        Log.d("addEvento", ev.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NOME, ev.getNome()); // get title 
        values.put(KEY_LOCAL, ev.getLocal()); // get author
        values.put(KEY_DESCRICAO, ev.getDescricao());
        values.put(KEY_LOGIN,login);
        values.put(KEY_AVALEVENTO, aval);
      
        // 3. insert
        db.insert(TABLE_EVENTO, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
 
        // 4. close
        db.close(); 
    }
    
    public void addDenuncia(Denuncia dn){
        Log.d("addDenuncia", dn.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_EMAILCLIENTE, dn.getEmailCliente()); // get title 
        values.put(KEY_NOMEEVENTO, dn.getEventoDenunciado()); // get author
        values.put(KEY_EMAILDENUNCIADO, dn.getEmailDenunciado());
        values.put(KEY_MOTIVO,dn.getMotivo());
        values.put(KEY_DESCRICAO2, dn.getDescricao());
      
        // 3. insert
        db.insert(TABLE_DENUNCIA, // table
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
        ev.setAval(cursor.getInt(5));
        
      
 
        Log.d("getEvento("+id+")", ev.toString());
 
        // 5. return book
        return ev;
    }
    
    public Denuncia getDenuncia(int id){
    	 
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
 
        // 2. build query
        Cursor cursor = 
                db.query(TABLE_DENUNCIA, // a. table
                COLUMNS_DENUNCIA, // b. column names
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
        Denuncia dn = new Denuncia();
        dn.setId(Integer.parseInt(cursor.getString(0)));
        dn.setEmailCliente(cursor.getString(1));
        dn.setEventoDenunciado(cursor.getString(2));
        dn.setEmailDenunciado(cursor.getString(3));
        dn.setMotivo(cursor.getString(4));
        dn.setDescricao(cursor.getString(5));
        Log.d("getDenuncia("+id+")", dn.toString());
 
        // 5. return denuncia
        return dn;
    }
    
    public Evento getEventoByName(String name){
    	 
        // 1. get reference to readable DB
    	SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. build the query
        String query = "SELECT  * FROM " + TABLE_EVENTO + " WHERE nome like '"+name+"'";
 
        // 3. get reference to writable DB
        Cursor cursor = db.rawQuery(query, null);
 
        // 4. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
 
        // 5. build book object
        try{
        Evento ev = new Evento();
        ev.setId(Integer.parseInt(cursor.getString(0)));
        ev.setNome(cursor.getString(1));
        ev.setLocal(cursor.getString(2));
        ev.setDescricao(cursor.getString(3));
        ev.setLogin(cursor.getString(4));
        ev.setAval(cursor.getInt(5));
        
      
 
        Log.d("getEventoByName("+name+")", ev.toString());
 
        // 5. return book
        return ev;
        }catch(Exception e){
        	return null;
        }
    }
    
    public int getIdOfEventoByName(String name){
   	 
        // 1. get reference to readable DB
    	SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. build the query
        String query = "SELECT id FROM " + TABLE_EVENTO + " WHERE nome like '"+name+"'";
 
        // 3. get reference to writable DB
        Cursor cursor = db.rawQuery(query, null);
 
        // 4. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
 
        // 5. build book object
        try{
        Evento ev = new Evento();
        ev.setId(Integer.parseInt(cursor.getString(0))); 
        Log.d("getEventoByName("+name+")", ev.toString());
 
        // 5. return book
        return ev.getId();
        }catch(Exception e){
        	return 0;
        }
    }
    
    public Cliente getCliente(String login){
    	 
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
 
        // 2. build query
        Cursor cursor = 
                db.query(TABLE_CLIENTE, // a. table
                COLUMNS_CLIENTE, // b. column names
                " email = ?", // c. selections 
                new String[] { String.valueOf(login) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
 
        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
 
        // 4. build book object
        Cliente cliente = new Cliente();
        cliente.setEmail(cursor.getString(0));
        cliente.setSenha(cursor.getString(1));
        cliente.setNome(cursor.getString(2));
        cliente.setEndereco(cursor.getString(3));
        cliente.setTelefone(cursor.getString(4));
        cliente.setData(cursor.getString(5));
        cliente.setAval(cursor.getInt(6));
        
      
 
        Log.d("getCliente("+login+")", cliente.toString());
 
        // 5. return book
        return cliente;
    }
    
    public Denuncia getDenuncia(String email){
   	 
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
 
        // 2. build query
        Cursor cursor = 
                db.query(TABLE_DENUNCIA, // a. table
                COLUMNS_DENUNCIA, // b. column names
                " emailCliente = ?", // c. selections 
                new String[] { String.valueOf(email) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
 
        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
 
        // 4. build book object
        try{
        Denuncia dn = new Denuncia();
        dn.setEmailCliente(cursor.getString(0));
        dn.setEventoDenunciado(cursor.getString(1));
        dn.setEmailDenunciado(cursor.getString(2));
        dn.setMotivo(cursor.getString(3));
        dn.setDescricao(cursor.getString(4));
        Log.d("getCliente("+email+")", dn.toString());
        
        // 5. return book
        return dn;
        }catch(Exception e){
        	Log.d("getCliente","Deu ruim :/");
        	return null;
        }
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
                ev.setAval(cursor.getInt(5));
               
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
                  ev.setAval(cursor.getInt(5));
                 
                 
                  // Add book to books
                  evts.add(ev);
              } while (cursor.moveToNext());
          }
   
          Log.d("getAllBooks()", evts.toString());
   
          // return books
          return evts;
    	
    	
    }
    
    public List<Denuncia> getDenunciaByLogin(String login){
  	  List<Denuncia> evts = new LinkedList<Denuncia>();
  	  
        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_DENUNCIA + " WHERE emailCliente = '"+login+"'";
 
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
 
        // 3. go over each row, build book and add it to list
        Denuncia dn = null;
        try{
        if (cursor.moveToFirst()) {
            do {
            	dn = new Denuncia();
                dn.setEmailCliente(cursor.getString(0));
                dn.setEventoDenunciado(cursor.getString(1));
                dn.setEmailDenunciado(cursor.getString(2));
                dn.setMotivo(cursor.getString(3));
                dn.setDescricao(cursor.getString(4));
               
               
                // Add book to books
                evts.add(dn);
            } while (cursor.moveToNext());
        }
 
        Log.d("getAllDenuncias()", evts.toString());
 
        // return books
        return evts;
        }catch(Exception e){
        	Log.d("getAllDenuncias()","Deu ruim :;/");
        	return null;
        }
  	
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
                ev.setAval(cursor.getInt(5));
               
               
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
    
    public int updateEventoAval(int id, int realScore) {
		// 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("ID",""+id);
        Log.d("aval_banco",""+realScore);
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_AVALEVENTO, ""+realScore);
        // 3. updating row
        int i = db.update(TABLE_EVENTO, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(id) }); //selection args
        	db.close();
        	
        	return i;
        // 4. close
 
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
    
    // Deleting single book
    public void deleteDenuncia(int id) {
 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. delete
        db.delete(TABLE_DENUNCIA,
                KEY_ID2+" = ?",
                new String[] { String.valueOf(id) });
 
        // 3. close
        db.close();
 
        Log.d("deleteDenuncia", "deletei blz véi?");
 
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
        values.put(KEY_AVALCLIENTE, cliente.getAval());
 
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
        cliente.setAval(cursor.getInt(6));
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
                cliente.setAval(cursor.getInt(6));
 
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

	public int updateClienteAval(String email, int realScore) {
		// 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("email_2",email);
        Log.d("aval_banco_2",""+realScore);
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("avaluser", realScore);
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
}