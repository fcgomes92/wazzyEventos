package com.example.wazzyeventos.sqlite;
 
import java.util.LinkedList;
import java.util.List;

import com.example.wazzyeventos.model.Evento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class MySQLiteHelper extends SQLiteOpenHelper {
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "WazzyDB";
 
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_EVENTO_TABLE = "CREATE TABLE evento ( " +
        		"id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "nome TEXT, "+
                "local TEXT,"
                + "descricao TEXT)";
        // create books table
        db.execSQL(CREATE_EVENTO_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
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
 
    // Books Table Columns names
	private static final String KEY_ID = "id";
    private static final String KEY_NOME = "nome";
    private static final String KEY_LOCAL = "local";
    private static final String KEY_DESCRICAO = "descricao";
    
 
    private static final String[] COLUMNS = {KEY_ID,KEY_NOME,KEY_LOCAL,KEY_DESCRICAO};
 
    public void addEvento(Evento ev){
        Log.d("addEvento", ev.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NOME, ev.getNome()); // get title 
        values.put(KEY_LOCAL, ev.getLocal()); // get author
        values.put(KEY_DESCRICAO, ev.getDescricao());
        
        
      
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
                COLUMNS, // b. column names
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
        
      
 
        Log.d("getBook("+id+")", ev.toString());
 
        // 5. return book
        return ev;
    }
 
    public List<Evento> getSelectedEvento(String nome,String local){
    	 List<Evento> lista = new LinkedList<Evento>();
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
 
        // 2. build query
        Cursor cursor = 
                db.query(TABLE_EVENTO, // a. table
                COLUMNS, // b. column names
                " nome = ?", // c. selections 
                new String[] {nome}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
 
        Evento ev = null;
        if (cursor.moveToFirst()) {
            do {
                ev = new Evento();
                ev.setId(Integer.parseInt(cursor.getString(0)));
                ev.setNome(cursor.getString(1));
                ev.setLocal(cursor.getString(2));
                ev.setDescricao(cursor.getString(3));
               
               
                // Add book to books
                lista.add(ev);
            } while (cursor.moveToNext());
        }
      
 
        Log.d("getBook()", lista.toString());
 
        // 5. return book
        return lista;
    }
 
    
    
    // Get All Books
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
               
               
                // Add book to books
                evts.add(ev);
            } while (cursor.moveToNext());
        }
 
        Log.d("getAllBooks()", evts.toString());
 
        // return books
        return evts;
    }
 
     // Updating single book
    public int updateEvento(Evento ev) {
 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NOME,"UTOUCHMYTRALALA"); // get title 
        values.put(KEY_LOCAL, "HMMMYDINGDINGDONG"); // get author
 
        // 3. updating row
        int i = db.update(TABLE_EVENTO, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(ev.getId()) }); //selection args
 
        // 4. close
        db.close();
 
        return i;
 
    }
 
    // Deleting single book
    public void deleteBook(Evento ev) {
 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. delete
        db.delete(TABLE_EVENTO,
                KEY_ID+" = ?",
                new String[] { String.valueOf(ev.getId()) });
 
        // 3. close
        db.close();
 
        Log.d("deleteBook", ev.toString());
 
    }
}