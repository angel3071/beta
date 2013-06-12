package com.example.graficador;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.graficador.IndPorSist.Item;


import controlador.JsonCont;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class GraficaTemp extends Activity {
	private WebView mWebView;
	public String origen;
	public int estado;
	public int plantel;
	public int semaforo;
	ProgressDialog progresBar;
	
	
	
	private class ReadJSON extends AsyncTask
    <String, Void, String> {
        private String title;
        private String tag;

		
        public ReadJSON(String title, String tag){
        	this.title = title;
        	this.tag = tag;
        }

		protected String doInBackground(String... urls) {
            return new JsonCont().readJSONFeed(urls[0]);
        }

		protected void onPreExecute(){
        	progresBar.setIndeterminate(true);
			progresBar.setTitle("Descargando Información");
			progresBar.setMessage("Por favor espere");
			progresBar.setCancelable(false);
			progresBar.show();
        	
        }
		
        protected void onPostExecute(String result) {
            try {
     
            	
            	
            	
            	
            	
            	
                JSONArray array = new JSONArray(result);
                String cadena = "";

                for(int i=0;i<array.length(); i++){
                	
                	JSONObject b = array.getJSONObject(i);
                	
                	String entidad = b.getString(this.tag);
                	String valor = b.getString("Valor");
                	
                	if(i == 0){
                		cadena = cadena + entidad + "," + valor;	
                	}
                	
                		cadena = cadena +"-"+ entidad + "," + valor;
                }
                progresBar.dismiss();
                
                
                mWebView.loadUrl("javascript:grafica(\""+cadena+","+this.title+"\")");
                
                
                
            } catch (Exception e) {
            	
                Log.d("ReadWeatherJSONFeedTask", e.getLocalizedMessage());
                Toast.makeText(getBaseContext(), "Imposible Conectar a la Red",Toast.LENGTH_LONG).show();
                progresBar.dismiss();
            }          
        }
    }
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grafica_temp);
		Bundle bundle = getIntent().getExtras();
		this.origen = bundle.getString("Origen");
		this.origen = "Estatal";
		this.estado = bundle.getInt("Estado");
		this.plantel = bundle.getInt("Plantel");
		this.semaforo = bundle.getInt("Semaforo");
		mWebView = (WebView) findViewById(R.id.mybrowser);
        
        // Activo JavaScript
        mWebView.getSettings().setJavaScriptEnabled(true);
        // Cargamos la url que necesitamos    
        mWebView.loadUrl("file:///android_asset/Prueba_dist/index.html");
        
        
        progresBar = new ProgressDialog(this);
        if(origen.equals("Nacional")){
        	new ReadJSON("Nacional", "EntFed_Dsc").execute("http://200.23.107.50:8083/siiecon.asmx/indicadorEstatal?IdIndicador=77");
        }else if(origen.equals("Estatal")){
        	new ReadJSON("Estatal", "NombrePlantel").execute("http://200.23.107.50:8083/siiecon.asmx/indicadorPlantel?pIdEntidad=1&IdIndicador=77");
        }
		
		
        
      }
	}
