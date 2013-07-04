package com.example.graficador;

import org.json.JSONArray;
import org.json.JSONObject;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.Toast;
import controlador.JsonCont;

public class GraficaTemp extends Activity {
	private WebView mWebView;
	public String origen;
	public int estado;
	public String plantel;
	public int semaforo;
	public int bml;
	ProgressDialog progresBar;
	
	
	
	private class ReadJSON extends AsyncTask
    <String, Void, String> {
        private String title;
        private String tag;
        private int bml;

		
        public ReadJSON(String title, String tag, int bml){
        	this.title = title;
        	this.tag = tag;
        	this.bml = bml;
        }

		protected String doInBackground(String... urls) {
            return new JsonCont().readJSONFeed(urls[0]);
        }

		protected void onPreExecute(){
        	progresBar.setIndeterminate(true);
			progresBar.setTitle("Descargando Informacion");
			progresBar.setMessage("Por favor espere");
			progresBar.setCancelable(false);
			progresBar.show();
        	
        }
		
        protected void onPostExecute(String result) {
            try {
     
            	JSONArray array = new JSONArray(result);
                String cadena = "";
                
                
               if(this.bml == 0){
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
                		Toast.makeText(getApplicationContext(), "La grafica aparecera dentro de poco", Toast.LENGTH_LONG).show();
                		mWebView.loadUrl("javascript:grafica(\""+cadena+","+this.title+"\")");
                
               }else{
            	   
            	   for(int i=0; i < array.length(); i++){
            		   JSONObject b = array.getJSONObject(i);
            		   String entidad = b.getString(this.tag);
            		   String valor = b.getString("Valor");
            		   String meta = b.getString("Meta");
            		   String base = b.getString("Base");
            		   
            		   if(i == 0){
            			   cadena = cadena + entidad + "," + valor + "," + meta + "," + base;
            		   }
            		   
            		   cadena = cadena + "-" + entidad + "," + valor + "," + meta + "," + base;
            		   
            		   
            		   
            	   }
            	   
            	   
            	   
            	   
            	   progresBar.dismiss();
                   Toast.makeText(getApplicationContext(), "La grafica aparecera dentro de poco", Toast.LENGTH_LONG).show();
                   mWebView.loadUrl("javascript:graficaBML(\""+cadena+","+this.title+"\")");
            	   
               }
                
                
                
               
                
                
                
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
		this.estado = bundle.getInt("Estado");
		this.plantel = bundle.getString("Plantel");
		this.semaforo = bundle.getInt("Semaforo");
		this.bml = bundle.getInt("bml");
		mWebView = (WebView) findViewById(R.id.mybrowser);
        
        // Activo JavaScript
        mWebView.getSettings().setJavaScriptEnabled(true);
        // Cargamos la url que necesitamos    
        mWebView.loadUrl("file:///android_asset/Prueba_dist/index.html");
        
       
        
        progresBar = new ProgressDialog(this);
        if(origen.equals("Nacional")){
        	new ReadJSON("Nacional", "EntFed_Dsc", this.bml).execute("http://200.23.107.50:8083/siiecon.asmx/indicadorEstatal?IdIndicador="+semaforo);
        }else if(origen.equals("Estatal")){
        	new ReadJSON("Estatal", "NombrePlantel", this.bml).execute("http://200.23.107.50:8083/siiecon.asmx/indicadorPlantel?pIdEntidad="+estado+"&IdIndicador="+semaforo);
        }else
        	new ReadJSON("Plantel", "Nombre", this.bml).execute("http://200.23.107.50:8083/siiecon.asmx/situacionCt?pCt=" + plantel +"&pIdIndicador=" +  semaforo);
		
       
        
      }
	

//		public boolean onKeyDown(int keyCode, KeyEvent event) {
//			// TODO Auto-generated method stub
//			if (keyCode == KeyEvent.KEYCODE_MENU){
//				
//				return true;
//			}else{
//			return super.onKeyDown(keyCode, event);
//			}
//		}
	private class LeerJSON extends AsyncTask
    <String, Void, String> {
        private int id;


		public LeerJSON(int i) {
			// TODO Auto-generated constructor stub
        	this.id = i;
		}
		protected String doInBackground(String... urls) {
            return new JsonCont().readJSONFeed(urls[0]);
        }
//        Context context;
        
        protected void onPreExecute(){
        	progresBar.setIndeterminate(true);
			progresBar.setTitle("Descargando Informacion");
			progresBar.setMessage("Por favor espere");
			progresBar.setCancelable(false);
			progresBar.show();
        	
        }
        
        
        protected void onPostExecute(String result) {
            try {
     	
                JSONArray array = new JSONArray(result);
                String[] estados = new String[array.length()];

	                for(int i=0;i<array.length(); i++){
	                	
	                	JSONObject b = array.getJSONObject(i);
	                	estados[i] = b.getString("EntFed_Dsc");
	                }

                progresBar.dismiss();
                Dialog d;

                	d = crearDialogoSeleccionEstatal(estados);

                d.show();
                
            } catch (Exception e) {
            	
                Log.d("ReadWeatherJSONFeedTask", e.getLocalizedMessage());
                Toast.makeText(getBaseContext(), "Imposible Conectar a la Red",Toast.LENGTH_LONG).show();
            }          
        }
    }



	
	private Dialog crearDialogoSeleccionEstatal(String[] estados)
	{
	    final String[] items = estados;
	 
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	 
	    builder.setTitle("Seleccione un Estado");
	    builder.setItems(items, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int item) {
	        
	        	Intent i = new Intent(getApplicationContext(), GraficaTemp.class);
	        	i.putExtra("Origen", "Estatal");
	        	i.putExtra("Estado", item + 1);
	        	i.putExtra("Plantel", -1);
	        	i.putExtra("Semaforo", semaforo);
	        	startActivity(i);
//	        	Intent i = new Intent(getApplicationContext(), IndPorSist.class);
//	        	i.putExtra("Origen", "Estatal");
//	        	i.putExtra("Estado", item +1);
//	        	i.putExtra("Plantel", -1);
//	        	startActivity(i);
	        
	        
	        }
	    });
	 
	    return builder.create();
	}
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			switch (keyCode) {
			case KeyEvent.KEYCODE_MENU:
					
//				progresBar = new ProgressDialog(this);
				if(this.origen.equals("Nacional"))
					new LeerJSON(1).execute("http://200.23.107.50:8083/siiecon.asmx/indicadorEstatal?IdIndicador=1");



					
				break;

			default:
				break;
			}
			return super.onKeyDown(keyCode, event);
		}
}
