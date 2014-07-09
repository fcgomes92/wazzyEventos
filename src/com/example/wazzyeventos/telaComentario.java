package com.example.wazzyeventos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.wazzyeventos.jsonctrl.JSONParser;

public class telaComentario extends ActionBarActivity{
	
	private Context ctx;
	public Button bt_comentar;
	public EditText comment;
	public String id_ev, username, msg;
	public ListView listacomments;
	
    //An array of all of our comments
    private JSONArray mComments = null;
    //manages all of our comments in a list.
    private ArrayList<HashMap<String, String>> mCommentList;
	
	//JSON para update de avaliação de evento
	private ProgressDialog pDialog;
	private JSONParser jsonP;

	public static String ip = "192.168.1.4";
	private static final String COMMENT_URL = "http://"+ip+":1234/webservice/registracomment.php";
	private static final String UPDATE_COMMENT_URL = "http://"+ip+":1234/webservice/listaComments.php";

	//JSON element ids from repsonse of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_COMMENTS = "comments";
    private static final String TAG_ID = "id_post";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_MSG = "msg";
	
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.telacomentarios);
			
			//Sets das instancias do XML
			
			this.bt_comentar = (Button) findViewById(R.id.bt_comentar);
			this.comment = (EditText) findViewById(R.id.ed_comentarios);
			
			//setando valores dos campos  
			this.id_ev = getIntent().getExtras().getString("id_ev");
			this.username = getIntent().getExtras().getString("username");
			this.listacomments = (ListView) this.findViewById(R.id.list_comentarios);
			this.ctx = this;
			
			new getComments().execute();
			
			this.bt_comentar.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					msg = comment.getText().toString();
					new registerComment().execute();
					finish();
				}
			});
		}
	
		public void updateList(){
			ListAdapter adapter = new SimpleAdapter(ctx, mCommentList, 
					android.R.layout.simple_list_item_1, 
					new String[] {TAG_MSG,TAG_USERNAME}, 
					new int[] {android.R.id.text1});
			listacomments.setAdapter(adapter);
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
		@SuppressLint("NewApi") 
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
		
		//Conexão avaliação banco
		public class registerComment extends AsyncTask<Void, Void, Boolean>{

			@Override
			protected void onPreExecute(){
				super.onPreExecute();
				pDialog = new ProgressDialog(ctx);
				pDialog.setMessage("Comentando...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}
			
			@Override
			protected Boolean doInBackground(Void... arg0) {
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("id_evento", id_ev));
				params1.add(new BasicNameValuePair("username", username));
				params1.add(new BasicNameValuePair("msg", msg));
				
		        JSONParser jParser = new JSONParser();
		        JSONObject json = jParser.getJSONFromUrl(COMMENT_URL, params1);	                
				return null;
			}
			
			@Override
	        protected void onPostExecute(Boolean result) {
	            super.onPostExecute(result);
	            pDialog.dismiss();
	        }
		}
		
		public class getComments extends AsyncTask<Void, Void, Boolean>{

			@Override
			protected void onPreExecute(){
				super.onPreExecute();
				pDialog = new ProgressDialog(ctx);
				pDialog.setMessage("Comentando...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}
			
			@Override
			protected Boolean doInBackground(Void... arg0) {
				//Envio de parametros de filtro
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("id_ev", id_ev));
				
		        mCommentList = new ArrayList<HashMap<String, String>>();
		         
		        JSONParser jParser = new JSONParser();
		        JSONObject json = jParser.getJSONFromUrl(UPDATE_COMMENT_URL, params);
		        try {
		            mComments = json.getJSONArray(TAG_COMMENTS);
		            // looping through all posts according to the json object returned
		            for (int i = 0; i < mComments.length(); i++) {
		                JSONObject c = mComments.getJSONObject(i);

		                //gets the content of each tag
		                String id  = c.getString(TAG_ID);
		                String username  = c.getString(TAG_USERNAME);
		                String msg  = c.getString(TAG_MSG);
		                
		                HashMap<String, String> map = new HashMap<String, String>();
		              
		                map.put(TAG_ID, id);
		                map.put(TAG_USERNAME, username);
		                map.put(TAG_MSG, msg);
		                
		                mCommentList.add(map);
		            }

		        }catch (JSONException e) {
		            e.printStackTrace();
		        }
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
