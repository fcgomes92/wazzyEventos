package com.example.wazzyeventos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

import com.example.wazzyeventos.jsonctrl.JSONParser;

public class telaListaDenuncia extends ActionBarActivity {
	public String logado;
	public ListView listadenuncia;

	public Context ctx;
	public Intent consultaDenuncia;
	
	private ProgressDialog pDialog;
	private JSONParser jsonP;
	
	//Server info 
	public static String ip = "192.168.1.4";
    private static final String LISTA_DEN_URL = "http://"+ip+":1234/webservice/listaDen.php";

    //JSON element ids from repsonse of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_DENS = "dens";
    private static final String TAG_LOGIN_REA = "login_realiza_denuncia";
    private static final String TAG_LOGIN_REC = "login_recebe_denuncia";
    private static final String TAG_DESC_DEN = "descricao_denuncia";
    private static final String TAG_MOT_DEN = "motivo_denuncia";
    

    //An array of all of our comments
    private JSONArray mComments = null;
    //manages all of our comments in a list.
    private ArrayList<HashMap<String, String>> mCommentList;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telaminhasdenuncias);
		
		// INSTANCIANCIAS DO XML
		this.consultaDenuncia = new Intent(this, telaConsultaMinhasDenuncias.class);
		this.listadenuncia = (ListView) this.findViewById(R.id.listMinhasDenuncias);
		this.logado = getIntent().getExtras().getString("login");
		this.ctx = this;
		
		listadenuncia.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String e_login_rea = mCommentList.get(position).get(TAG_LOGIN_REA).toString();
                String e_login_rec = mCommentList.get(position).get(TAG_LOGIN_REC).toString();
                String e_mot = mCommentList.get(position).get(TAG_MOT_DEN).toString();
                String e_desc = mCommentList.get(position).get(TAG_DESC_DEN).toString();
				
				consultaDenuncia.putExtra(TAG_LOGIN_REA, e_login_rea);
				consultaDenuncia.putExtra(TAG_LOGIN_REC, e_login_rec);
				consultaDenuncia.putExtra(TAG_MOT_DEN, e_mot);
				consultaDenuncia.putExtra(TAG_DESC_DEN, e_desc);
				startActivity(consultaDenuncia);
				finish();
			}
		});
		
		new updateMinhasDen().execute();
	}
	
	public void updateList(){
		ListAdapter adapter = new SimpleAdapter(ctx, mCommentList, 
				android.R.layout.simple_list_item_1, 
				new String[] {TAG_MOT_DEN}, 
				new int[] {android.R.id.text1});
		listadenuncia.setAdapter(adapter);
	}

	public class updateMinhasDen extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(telaListaDenuncia.this);
			pDialog.setMessage("Carregando Meus Eventos...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected Boolean doInBackground(Void... arg0) {
			//Envio de parametros de filtro
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("login_realiza_denuncia",logado));
			
	        mCommentList = new ArrayList<HashMap<String, String>>();
	         
	        JSONParser jParser = new JSONParser();
	        JSONObject json = jParser.getJSONFromUrl(LISTA_DEN_URL, params);
	        try {
	            mComments = json.getJSONArray(TAG_DENS);
	            // looping through all posts according to the json object returned
	            for (int i = 0; i < mComments.length(); i++) {
	                JSONObject c = mComments.getJSONObject(i);

	                //gets the content of each tag
	                String login_rea = c.getString(TAG_LOGIN_REA);
	                String login_rec = c.getString(TAG_LOGIN_REC);
	                String mot_env = c.getString(TAG_MOT_DEN);
	                String desc_env = c.getString(TAG_DESC_DEN);
	    
	                HashMap<String, String> map = new HashMap<String, String>();
	              
	                map.put(TAG_LOGIN_REA, login_rea);
	                map.put(TAG_LOGIN_REC, login_rec);
	                map.put(TAG_MOT_DEN, mot_env);
	                map.put(TAG_DESC_DEN, desc_env);
	                
	                mCommentList.add(map);
	            }

	        } catch (JSONException e) {
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