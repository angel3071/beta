package com.example.graficador;

import java.util.HashMap;

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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import controlador.JsonCont;

public class IndPorSist extends Activity {
	 HashMap<String, String> datosGrafica = new HashMap<String, String>();
     ProgressDialog progresBar;
	public String origen;
	public double estado;
	public double plantel;
	public static class Item{
	    public final String text;
	    public final int icon;
	    public Item(String text, Integer icon) {
	        this.text = text;
	        this.icon = icon;
	    }
	    @Override
	    public String toString() {
	        return text;
	    }
	}
	private class ReadJSON extends AsyncTask
    <String, Void, String> {
        private int sistema;

		public ReadJSON(int position) {
			// TODO Auto-generated constructor stub
        	this.sistema = position;
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
                String[] semaforos = new String[array.length()];

                for(int i=0;i<array.length(); i++){
                	
                	JSONObject b = array.getJSONObject(i);
                	semaforos[i] = b.getString("Descripcion") + "%" + b.getString("Valor");

                	
                	
                }
                progresBar.dismiss();
                
                Dialog d = crearDialogoSemaforos(semaforos);
                d.show();
                
            } catch (Exception e) {
            	
                Log.d("ReadWeatherJSONFeedTask", e.getLocalizedMessage());
                Toast.makeText(getBaseContext(), "Imposible Conectar a la Red",Toast.LENGTH_LONG).show();
                progresBar.dismiss();
            }          
        }
    }
	private Dialog crearDialogoSemaforos(String[] semaforos)
	{
	    final Item[] items = new Item[semaforos.length];

		for(int i=0; i<semaforos.length; i++){
			Double valor = Double.parseDouble(semaforos[i].split("%")[1]);
			if( valor < 51.00)
				items[i] = new Item(semaforos[i].split("%")[0], R.drawable.semar);
			else if(valor>=51.00 && valor<=75.00)
				items[i] = new Item(semaforos[i].split("%")[0], R.drawable.semaa);
			else if(valor> 75.00)
				items[i] = new Item(semaforos[i].split("%")[0], R.drawable.semav);
			else
				items[i] = new Item(semaforos[i].split("%")[0], 0);


		}
	    
	    ListAdapter adapter = new ArrayAdapter<Item>(
	    	    this,
	    	    android.R.layout.select_dialog_item,
	    	    android.R.id.text1,
	    	    items){
	    	        public View getView(int position, View convertView, ViewGroup parent) {
	    	            //User super class to create the View
	    	            View v = super.getView(position, convertView, parent);
	    	            TextView tv = (TextView)v.findViewById(android.R.id.text1);

	    	            //Put the image on the TextView
	    	            tv.setCompoundDrawablesWithIntrinsicBounds(items[position].icon, 0, 0, 0);

	    	            //Add margin between image and text (support various screen densities)
	    	            int dp5 = (int) (5 * getResources().getDisplayMetrics().density + 0.5f);
	    	            tv.setCompoundDrawablePadding(dp5);

	    	            return v;
	    	        }
	    	    };
	 
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	 
	    builder.setTitle("Seleccione una Gráfica");
	    builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int item) {
	        	Intent i = new Intent(getApplicationContext(), GraficaTemp.class);
	        	i.putExtra("Origen", origen);
	        	i.putExtra("Estado", estado);
	        	i.putExtra("Plantel", plantel);
	        	i.putExtra("Semaforo", item + 1);
	        	startActivity(i);	        
	        	}
	    });
//	        (items, new DialogInterface.OnClickListener() {
//	        public void onClick(DialogInterface dialog, int item) {
//	        
//	        	Intent i = new Intent(getApplicationContext(), GraficaTemp.class);
//	        	i.putExtra("Semaforo", item + 1);
//	        	startActivity(i);
//	        
//	        
//	        }
//	    });
	    
	 
	    return builder.create();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_indicadores);
		
		
		GridView gv = (GridView) findViewById(R.id.grid_view);
		
		Bundle bundle = getIntent().getExtras();
		this.origen = bundle.getString("Origen");
		this.estado = bundle.getInt("Estado");
		this.plantel = bundle.getInt("Plantel");
		
		gv.setAdapter(new MyAdapter(this));
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
				if(position == 2 || position==3||position==4||position==5){ 
	                Toast.makeText(getBaseContext(), "Aún no Implementado",Toast.LENGTH_LONG).show();

					return; }
				
				
				progresBar = new ProgressDialog(v.getContext());
				if(origen.equals("Nacional")){
					new ReadJSON(position).execute("http://200.23.107.50:8083/siiecon.asmx/NacionalIndicadores?pIdSistema=" + (position + 1));
				}else if(origen.equals("Estatal")){
					new ReadJSON(position).execute("http://200.23.107.50:8083/siiecon.asmx/EstatalIndicadores?pIdSistema=" + (position + 1) );
				}else{
					new ReadJSON(position).execute("http://200.23.107.50:8083/siiecon.asmx/PlantelIndicadores?pIdSistema=" + (position + 1) );
					
				}
				
            	
            	
            	
            	
            }
        });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ind_por_sist, menu);
		return true;
	}

}
