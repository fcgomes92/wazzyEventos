package com.example.wazzyeventos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.wazzyeventos.jsonctrl.JSONParser;
import com.example.wazzyeventos.model.Evento;
import com.example.wazzyeventos.sqlite.MySQLiteHelper;


public class telaListaEvento extends ActionBarActivity{
	private String login;
	public ListView listaevento;

	public Context ctx;
	public Intent consultaEvento;
	

	private ProgressDialog pDialog;
	private JSONParser jsonP;
	
	//Server info 
	public static String ip = "192.168.1.4";
    private static final String BUSCA_MEUS_EVENTOS_URL = "http://"+ip+":1234/webservice/listarMeusEventos.php";

    //JSON element ids from repsonse of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_MEUSEVS = "meusevs";
    private static final String TAG_ID = "id";
    private static final String TAG_LOGIN = "login";
    private static final String TAG_NOME = "nome";
    private static final String TAG_LOCAL = "local";
    private static final String TAG_AVAL = "avalevento";
    private static final String TAG_DESC = "desc";
    

    //An array of all of our comments
    private JSONArray mComments = null;
    //manages all of our comments in a list.
    private ArrayList<HashMap<String, String>> mCommentList;

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telameuseventos);
		login = getIntent().getExtras().getString("login");
		
		// INSTANCIANCIAS DO XML
		consultaEvento = new Intent(this, telaConsultameuseventos.class);
		
		listaevento = (ListView) this.findViewById(R.id.list_meuseventos);
		
		// Cria um contexto pra add no ArrayAdapter
		ctx = this;
		
		new loadEventos().execute();
		
		listaevento.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String env_nome, env_local, env_login, env_desc, env_aval, env_id;
				env_id = mCommentList.get(position).get(TAG_ID).toString();
				env_nome = mCommentList.get(position).get(TAG_NOME).toString();
				env_local = mCommentList.get(position).get(TAG_LOCAL).toString();
				env_login = mCommentList.get(position).get(TAG_LOGIN).toString();
				env_desc = mCommentList.get(position).get(TAG_DESC).toString();
				env_aval = mCommentList.get(position).get(TAG_AVAL).toString();
				consultaEvento.putExtra("id", env_id);
				consultaEvento.putExtra("nome", env_nome);
				consultaEvento.putExtra("local", env_local);
				consultaEvento.putExtra("login", env_login);
				consultaEvento.putExtra("desc", env_desc);
				consultaEvento.putExtra("aval", env_aval);
				startActivity(consultaEvento);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
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
	
	// Mantem os eventos atualizados
		public void updateJSONData(){
			// Instantiate the arraylist to contain all the JSON data.
	    	// we are going to use a bunch of key-value pairs, referring
	    	// to the json element name, and the content, for example,
	    	// message it the tag, and "I'm awesome" as the content..
	    	
			//Envio de parametros de filtro
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("login",login));
			
			Log.d("Info:",login);
			
	        mCommentList = new ArrayList<HashMap<String, String>>();
	         
	        JSONParser jParser = new JSONParser();
	        JSONObject json = jParser.getJSONFromUrl(BUSCA_MEUS_EVENTOS_URL, params);
	        try {
	            mComments = json.getJSONArray(TAG_MEUSEVS);
	            // looping through all posts according to the json object returned
	            for (int i = 0; i < mComments.length(); i++) {
	                JSONObject c = mComments.getJSONObject(i);

	                //gets the content of each tag
	                String nome = c.getString(TAG_NOME);
	                String local = c.getString(TAG_LOCAL);
	                String login = c.getString(TAG_LOGIN);
	                String aval = c.getString(TAG_AVAL);
	                String desc = c.getString(TAG_DESC);
	                String id  = c.getString(TAG_ID);
	                
		
		                // creating new HashMap
	                HashMap<String, String> map = new HashMap<String, String>();
	              
	                map.put(TAG_ID, id);
	                map.put(TAG_NOME, nome);
	                map.put(TAG_LOCAL, local);
	                map.put(TAG_LOGIN, login);
	                map.put(TAG_AVAL, aval);
	                map.put(TAG_DESC, desc);
	                
	                mCommentList.add(map);
	            }

	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
		}
		
		private void updateList(){
			// For a ListActivity we need to set the List Adapter, and in order to do
					//that, we need to create a ListAdapter.  This SimpleAdapter,
					//will utilize our updated Hashmapped ArrayList, 
					//use our single_post xml template for each item in our list,
					//and place the appropriate info from the list to the
					//correct GUI id.  Order is important here.
					ListAdapter adapter = new SimpleAdapter(ctx, mCommentList, 
							android.R.layout.simple_list_item_1, 
							new String[] {TAG_NOME, TAG_LOCAL}, 
							new int[] {android.R.id.text1, android.R.id.text2});
					listaevento.setAdapter(adapter);
		}
		
		public class loadEventos extends AsyncTask<Void, Void, Boolean>{

			@Override
			protected void onPreExecute(){
				super.onPreExecute();
				pDialog = new ProgressDialog(telaListaEvento.this);
				pDialog.setMessage("Carregando Meus Eventos...");
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

