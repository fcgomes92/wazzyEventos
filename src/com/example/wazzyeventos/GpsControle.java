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
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.wazzyeventos.jsonctrl.JSONParser;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GpsControle extends Activity {
	
	private ProgressDialog pDialog;
	private JSONParser jsonP;
	
	//Server info 
	public static String ip = "192.168.1.4";
    private static final String EVENTOS_URL = "http://"+ip+":1234/webservice/eventosProx.php";

    //JSON element ids from repsonse of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_PROX = "prox";
    private static final String TAG_ID = "id";
    private static final String TAG_LOGIN = "login";
    private static final String TAG_NOME = "nome";
    private static final String TAG_LOCAL = "local";
    private static final String TAG_AVAL = "avalevento";
    private static final String TAG_DESC = "desc";
    private static final String TAG_LAT= "latitude";
    private static final String TAG_LON = "longitude";
	public int  la = 0;
  //An array of all of our comments
    private JSONArray mComments = null;
    //manages all of our comments in a list.
    private ArrayList<HashMap<String, String>> mCommentList;
    
//**********DECLARAÇÃO DE VARIÁVEIS**********
  private GoogleMap map;
  double latitude,lataux;
  double longitude,longaux;
  Location minhaloc;
  LocationManager lm;
  //se for falso, nao pode adicionar marcador
  boolean podeadd = false;
  //booleano usado para ver se irá localizar um evento ou a sua localização
  boolean localizaMarcador = false;//se falsa -> sua localizacao,se verdadeira, localizacao do evento
  
  String evNome,evLocal;
//******************************************* 
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.gpslayout);
    this.localizaMarcador = getIntent().getExtras().getBoolean("localiza");
    this.podeadd = getIntent().getExtras().getBoolean("marcador");
    criamapa();
    if(localizaMarcador==false) setaMarcador();
    else moveParaEvento();
    //**********RECEBENDO VALORES DA CLASSE DE INSERE EVENTO************
    
    if(podeadd==true){
    	this.evNome = getIntent().getExtras().getString("nome");
    	Log.d("nominho",""+evNome);
    	this.evLocal = getIntent().getExtras().getString("local");
    }
    //******************************************************************
    
    //clicando em algum lugar no mapa
    map.setOnMapClickListener(new OnMapClickListener() {
		
    	@Override
		public void onMapClick(LatLng point) {
    	if(podeadd){
    	Marker novoponto = map.addMarker(new MarkerOptions().position(point).title(evNome).snippet(evLocal));
    	lataux = point.latitude;
    	longaux = point.longitude;
    	//monta pergunta final antes de voltar pro evento
    	AlertDialog.Builder alerta = new AlertDialog.Builder(GpsControle.this);
    	alerta.setTitle("Aviso!")
    	.setMessage("O marcador foi adicionado no ponto escolhido.\nEstá correto?")
    	//se sim
    	.setPositiveButton("Sim", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				podeadd = false;
		    	Intent coordenadas = new Intent();
		    	coordenadas.putExtra("latitude", lataux);
		    	coordenadas.putExtra("longitude", longaux);
		    	setResult(Activity.RESULT_OK,coordenadas);
		    	finish();
				
			}
		})
		//se nao
    	.setNegativeButton("Não",null)
    	.show();
    	novoponto.remove();
    	}
    	}
    });
    
  }
@SuppressLint("NewApi") 
public void criamapa(){
	map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
	        .getMap();
	    map.setMyLocationEnabled(true);
}
public void minhalocalizacao(){
 //tutorial da moça com voz bonita
    
    lm = (LocationManager) getSystemService(LOCATION_SERVICE);
    //cria criteria pra usar no provider
    Criteria criteria = new Criteria();
    //get the name of the best provider
    String provider = lm.getBestProvider(criteria, true);
    //pega localizacao atual
    minhaloc = lm.getLastKnownLocation(provider);
    minhaloc.setAccuracy(100);
    
    //seta o tipo do mapa
    //map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    //esse tipo de mapa é o mapa do google earth
    
    //pega latitude e longitude
    latitude = minhaloc.getLatitude();
    longitude = minhaloc.getLongitude();
    new getPontos().execute();
    //cria um objeto latlng para a localizacao atual
    LatLng latlng =  new LatLng(latitude,longitude);
    //mostra a localizacao no google maps
    map.moveCamera(CameraUpdateFactory.newLatLng(latlng));
    //da zoom
    map.animateCamera(CameraUpdateFactory.zoomTo(18));
    
}
public void moveParaEvento(){
	latitude = getIntent().getExtras().getDouble("latitude");
	longitude = getIntent().getExtras().getDouble("longitude");
	LatLng meueventopos = new LatLng(latitude,longitude);
	new getPontos().execute();
	do{	
	}while(la == 0);
	if (mCommentList.isEmpty()){
	}
	else{
	for(int i = 0; i< mComments.length();i++){
		Marker var = map.addMarker(new MarkerOptions()
		.position(new LatLng(Double.parseDouble(mCommentList.get(i).get(TAG_LAT)),Double.parseDouble((mCommentList.get(i).get(TAG_LON)))))
		.title((mCommentList.get(i).get(TAG_NOME)))
		.snippet((mCommentList.get(i).get(TAG_LOCAL))));	
	}
	}
	//move para local do evento
	map.moveCamera(CameraUpdateFactory.newLatLng(meueventopos));
	map.animateCamera(CameraUpdateFactory.zoomTo(18));
	
}
public void setaMarcador(){
	minhalocalizacao();
	do{
	}while(la == 0);
	if (mCommentList.isEmpty()){
	}
	else{
	for(int i = 0; i< mComments.length();i++){
		Marker var = map.addMarker(new MarkerOptions()
				.position(new LatLng(Double.parseDouble(mCommentList.get(i).get(TAG_LAT)),Double.parseDouble((mCommentList.get(i).get(TAG_LON)))))
				.title((mCommentList.get(i).get(TAG_NOME)))
				.snippet((mCommentList.get(i).get(TAG_LOCAL))));		
	}
	}
}

public class getPontos extends AsyncTask<Void, Void, Boolean>{

	@Override
	protected void onPreExecute(){
		super.onPreExecute();
		pDialog = new ProgressDialog(GpsControle.this);
		pDialog.setMessage("Carregando Eventos...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();
	}
	
	@Override
	protected Boolean doInBackground(Void... arg0) {
		//Envio de parametros de filtro
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(TAG_LAT,""+latitude));
		params.add(new BasicNameValuePair(TAG_LON,""+longitude));
		
        mCommentList = new ArrayList<HashMap<String, String>>();
         
        JSONParser jParser = new JSONParser();
        JSONObject json = jParser.getJSONFromUrl(EVENTOS_URL, params);
        try {
            mComments = json.getJSONArray(TAG_PROX);
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
                String lat  = c.getString(TAG_LAT);
                String lon  = c.getString(TAG_LON);
                
	
	                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();
              
                map.put(TAG_ID, id);
                map.put(TAG_NOME, nome);
                map.put(TAG_LOCAL, local);
                map.put(TAG_LOGIN, login);
                map.put(TAG_AVAL, aval);
                map.put(TAG_DESC, desc);
                map.put(TAG_LAT, lat);
                map.put(TAG_LON, lon);
                
                mCommentList.add(map);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        la = 1;
		return null;
	}
	
	@Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        pDialog.dismiss();
    }
}

} 