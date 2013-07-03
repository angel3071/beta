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
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import controlador.JsonCont;

public class Principal extends Activity {
	ProgressDialog progresBar;
	public int estado;
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
        	//if(this.id==3) return;
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
                if(this.id==1 || this.id==2)
	                for(int i=0;i<array.length(); i++){
	                	
	                	JSONObject b = array.getJSONObject(i);
	                	estados[i] = b.getString("Nombre");
	                	
	                	
	                }
                else
                	for(int i=0;i<array.length(); i++){
	                	
	                	JSONObject b = array.getJSONObject(i);
	                	estados[i] = b.getString("Nombre") + "%" + b.getString("ct");
	                	
	                	
	                }
                progresBar.dismiss();
                Dialog d;
                if(this.id==1)
                	d = crearDialogoSeleccionEstatal(estados);
                else if(this.id==2)
                	d = crearDialogoSeleccionPlantel(estados);
                else 
                	d = crearDialogoSeleccionPlantelEstado(estados);
                d.show();
                
            } catch (Exception e) {
            	
                Log.d("ReadWeatherJSONFeedTask", e.getLocalizedMessage());
                Toast.makeText(getBaseContext(), "Imposible Conectar a la Red",Toast.LENGTH_LONG).show();
                if(progresBar.isShowing()) progresBar.dismiss();
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
	        
	        	Intent i = new Intent(getApplicationContext(), IndPorSist.class);
	        	i.putExtra("Origen", "Estatal");
	        	i.putExtra("Estado", item +1);
	        	i.putExtra("Plantel", -1);
	        	startActivity(i);
	        
	        
	        }
	    });
	 
	    return builder.create();
	}
	private Dialog crearDialogoSeleccionPlantel(String[] estados)
	{
	    final String[] items = estados;
	 
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	 
	    builder.setTitle("Seleccione un Estado");
	    
	    builder.setItems(items, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int item) {
//	            Dialog d = crearDialogoSeleccionPlantelEstado(item);
//	            d.show();
	        	estado = item +1;
	        	new LeerJSON(3).execute("http://200.23.107.50:8083/siiecon.asmx/lstCentrosDeTrabajo?pIdEntidad="+ estado);
	        }
	    });
	 
	    return builder.create();
	}
	
	private Dialog crearDialogoSeleccionPlantelEstado(final String[] planteles){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccione un Plantel");
        final String[] a = new String[planteles
                                      .length];
        for(int i=0; i< a.length; i++) a[i] = planteles[i].split("%")[0];
        builder.setItems(a, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), IndPorSist.class);
				i.putExtra("Origen", "Plantel");
	        	i.putExtra("Estado", estado);
	        	i.putExtra("Plantel", planteles[which].split("%")[1]);
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
				i.putExtra("Origen", "Nacional");
				i.putExtra("Estado", -1);
				i.putExtra("Plantel", -1);
				startActivity(i);
			}
		});
        Button btnEstatal = (Button)findViewById(R.id.btnEstatal);
        btnEstatal.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				progresBar = new ProgressDialog(v.getContext());
				new LeerJSON(1).execute("http://200.23.107.50:8083/siiecon.asmx/lstEntidadesFederativas");
				

				//progresBar.dismiss();
//				Dialog d = crearDialogoSeleccionEstatal();
//				d.show();
				//d.setContentView(R.layout.estados_layout);
				
				
			}
		});
        Button btnPlantel = (Button)findViewById(R.id.btnPlantel);
        btnPlantel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				progresBar = new ProgressDialog(v.getContext());
//				new LeerJSON(2).execute("http://200.23.107.50:8083/siiecon.asmx/indicadorEstatal?IdIndicador=1");
//				
				progresBar = new ProgressDialog(v.getContext());
				new LeerJSON(2).execute("http://200.23.107.50:8083/siiecon.asmx/lstEntidadesFederativas");
				
				//Toast.makeText(getApplicationContext(), "No disponible por el momento", Toast.LENGTH_LONG).show();
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
