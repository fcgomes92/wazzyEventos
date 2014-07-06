package com.example.wazzyeventos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.example.wazzyeventos.jsonctrl.JSONParser;
import com.example.wazzyeventos.model.Cliente;
import com.example.wazzyeventos.telaBuscaEvento.loadEventos;

public class telaBuscaUsuario extends ActionBarActivity {
	
	private Button bt_pesquisar;
	private EditText et_nome, et_endereco, et_username;
	public String nome = "", endereco = "", username = "";
	public Intent usuario_escolhido;
	public ListView lista;
	private Context ctx;
	private List<Cliente> clientes = new LinkedList<Cliente>();
	private List<String> nomes_clientes = new LinkedList<String>();
	private ArrayAdapter<String> adapter; 
	
	// JSON Info
	private ProgressDialog pDialog;
	private JSONParser jsonP;
	
	//Server Info
	public static String ip = "192.168.1.4";
    private static final String BUSCA_USER_URL = "http://"+ip+":1234/webservice/listaUser.php";

    //Tags JSON
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_USERS = "users";
    private static final String TAG_USER_ID = "users_id";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_NOME = "nome";
    private static final String TAG_ENDERECO = "endereco";
    private static final String TAG_TELEFONE = "telefone";
    private static final String TAG_DATANASC = "datanasc";
    private static final String TAG_AVAL = "aval";
    
    // Array de usuarios
    private JSONArray mUsers = null;
    // Todos os usuarios recebidos do DB
    private ArrayList<HashMap<String, String>> mUserList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pesquisausuario);
	
		bt_pesquisar = (Button) findViewById(R.id.bt_pesquisar_pesquisa);
		
		et_nome = (EditText) findViewById(R.id.field_nome_pesquisar);
		et_endereco = (EditText) findViewById(R.id.field_endereco_pesquisar);
		et_username = (EditText) findViewById(R.id.field_email_pesquisar);
		
		ctx = this;
		
		usuario_escolhido = new Intent(ctx, telaConsultaUsuario.class);
		
		lista = (ListView) findViewById(R.id.lista_usuarios);
		lista.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int pos, long id) {
				String env_nome, env_username, env_endereco, env_telefone, env_datanasc, env_aval;
				env_nome = mUserList.get(pos).get(TAG_NOME).toString();
				env_username = mUserList.get(pos).get(TAG_USERNAME).toString();
				env_endereco = mUserList.get(pos).get(TAG_ENDERECO).toString();
				env_telefone = mUserList.get(pos).get(TAG_TELEFONE).toString();
				env_datanasc = mUserList.get(pos).get(TAG_DATANASC).toString();
				env_aval= mUserList.get(pos).get(TAG_AVAL).toString();
				
				usuario_escolhido.putExtra("nome", env_nome);
				usuario_escolhido.putExtra("username", env_username);
				usuario_escolhido.putExtra("endereco", env_endereco);
				usuario_escolhido.putExtra("telefone", env_telefone);
				usuario_escolhido.putExtra("datanasc", env_datanasc);
				usuario_escolhido.putExtra("aval", env_aval);
				startActivity(usuario_escolhido);
			}
		});
       
		
		bt_pesquisar.setOnClickListener(new View.OnClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				nome = et_nome.getText().toString();
				username = et_username.getText().toString();
				endereco = et_endereco.getText().toString();
				if (!nome.isEmpty()) nome = "%"+nome+"%";
				if (!username.isEmpty()) username = "%"+username+"%";
				if (!endereco.isEmpty()) endereco = "%"+endereco+"%";
				Log.d("KeyWords:",nome + " - " + username + " - " + endereco);
				new loadEventos().execute();
			}
		});	
	} 
	
	

	
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu;to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	public void updateJSONData(){
		// Instantiate the arraylist to contain all the JSON data.
    	// we are going to use a bunch of key-value pairs, referring
    	// to the json element name, and the content, for example,
    	// message it the tag, and "I'm awesome" as the content..
    	
		//Envio de parametros de filtro
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("nome", nome));
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("endereco", endereco));
		
        mUserList = new ArrayList<HashMap<String, String>>();
         
        JSONParser jParser = new JSONParser();
        JSONObject json = jParser.getJSONFromUrl(BUSCA_USER_URL, params);
        try {
            mUsers = json.getJSONArray(TAG_USERS);
            // looping through all posts according to the json object returned
            for (int i = 0; i < mUsers.length(); i++) {
                JSONObject c = mUsers.getJSONObject(i);

                //gets the content of each tag
                String nome = c.getString(TAG_NOME);
                String username = c.getString(TAG_USERNAME);
                String endereco = c.getString(TAG_ENDERECO);
                String telefone = c.getString(TAG_TELEFONE);
                String datanasc = c.getString(TAG_DATANASC);
                String aval = c.getString(TAG_AVAL);
                
	
	                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();
              
                map.put(TAG_NOME, nome);
                map.put(TAG_USERNAME, username);
                map.put(TAG_ENDERECO, endereco);
                map.put(TAG_TELEFONE, telefone);
                map.put(TAG_DATANASC, datanasc);
                map.put(TAG_AVAL, aval);
                
                mUserList.add(map);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
	}
	
	private void updateList(){
				//For a ListActivity we need to set the List Adapter, and in order to do
				//that, we need to create a ListAdapter.  This SimpleAdapter,
				//will utilize our updated Hashmapped ArrayList, 
				//use our single_post xml template for each item in our list,
				//and place the appropriate info from the list to the
				//correct GUI id.  Order is important here.
				ListAdapter adapter = new SimpleAdapter(ctx, mUserList, 
						android.R.layout.simple_list_item_1, 
						new String[] {TAG_NOME}, 
						new int[] {android.R.id.text1});
				lista.setAdapter(adapter);
	}
	
	public class loadEventos extends AsyncTask<Void, Void, Boolean>{
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(telaBuscaUsuario.this);
			pDialog.setMessage("Carregando Usuários...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected Boolean doInBackground(Void... arg0) {
			updateJSONData();
			return null;
		}
		
		@Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            updateList();
        }
	}
}
