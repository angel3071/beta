package com.example.graficador;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import controlador.JsonCont;

public class Principal extends Activity {
	ProgressDialog progresBar;
	private class LeerJSON extends AsyncTask
    <String, Void, String> {
        protected String doInBackground(String... urls) {
            return new JsonCont().readJSONFeed(urls[0]);
        }
        Context context;
        private LeerJSON(Context context) {
            this.context = context;
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
                String[] estados = new String[array.length()];
                for(int i=0;i<array.length(); i++){
                	
                	JSONObject b = array.getJSONObject(i);
                	estados[i] = b.getString("EntFed_Dsc");
                	
                	
                }
                progresBar.dismiss();
                Dialog d = crearDialogoSeleccionEstatal(estados, context);
//                progresBar.dismiss();
                d.show();
                
            } catch (Exception e) {
            	
                Log.d("ReadWeatherJSONFeedTask", e.getLocalizedMessage());
                Toast.makeText(getBaseContext(), "Imposible Conectar a la Red",Toast.LENGTH_LONG).show();
            }          
        }
    }


	private Dialog crearDialogoSeleccionEstatal(String[] estados, final Context context)
	{
	    final String[] items = estados;
	 
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	 
	    builder.setTitle("Seleccione un Estado");
	    builder.setItems(items, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int item) {
	        
	        	Intent i = new Intent(Principal.this, IndPorSist.class);
	        	startActivity(i);
	        
	        
	        }
	    });
	 
	    return builder.create();
	}
	private Dialog crearDialogoSeleccionPlantel( final Context context)
	{
	    final String[] items = {"Estado de México", "Aguas Calientes", "Hidalgo"};
	 
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	 
	    builder.setTitle("Seleccione un Estado");
	    builder.setItems(items, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int item) {
	            Dialog d = crearDialogoSeleccionPlantelEstado(context);
	            d.show();
	        }
	    });
	 
	    return builder.create();
	}
	
	private Dialog crearDialogoSeleccionPlantelEstado(final Context context){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccione un Plantel");
        String [] it = {"CBTIS 15", "CETIS 99", "CBTIS 8"};
        builder.setItems(it, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent i = new Intent(context, IndPorSist.class);
	        	startActivity(i);
			}
		}) ;
		
        return builder.create();	
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		
		Button btnNacional = (Button) findViewById(R.id.btnNacional);
        btnNacional.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), IndPorSist.class);
				i.putExtra("origen", "urlNacioal");
				startActivity(i);
			}
		});
        Button btnEstatal = (Button)findViewById(R.id.btnEstatal);
        btnEstatal.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				progresBar = new ProgressDialog(v.getContext());
				new LeerJSON(v.getContext()).execute("http://200.23.107.50:8083/siiecon.asmx/indicadorEstatal?IdIndicador=1");
				

				//progresBar.dismiss();
				//Dialog d = crearDialogoSeleccionEstatal();
				//d.show();
				//d.setContentView(R.layout.estados_layout);
				
				
			}
		});
        Button btnPlantel = (Button)findViewById(R.id.btnPlantel);
        btnPlantel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Dialog d = crearDialogoSeleccionPlantel(getApplicationContext());
				d.show();
				//d.setContentView(R.layout.estados_layout);
				
				
			}
		});
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}

}
