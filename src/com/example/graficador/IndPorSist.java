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
     String[] semaforos1;
	public String origen;
	public int estado;
	public String plantel;
	public double avance;
	public static final int[] sist = {1,2,4,7,8,12,15,17,19};
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
			if(this.sistema == -1) return;
        	progresBar.setIndeterminate(true);
			progresBar.setTitle("Descargando Información");
			progresBar.setMessage("Por favor espere");
			progresBar.setCancelable(false);
			progresBar.show();
        	
        }
		
        protected void onPostExecute(String result) {
            try {
     
            	if(this.sistema == -1){
            		
            		avance = Double.parseDouble(result);            		
            		
            		return;
            	}
            	
            	
            	
            	
            	
                JSONArray array = new JSONArray(result);
                String[] semaforos = new String[array.length()];
                semaforos1 = new String[array.length()];
                String sistema = "";
                for(int i=0;i<array.length(); i++){
                	
                	JSONObject b = array.getJSONObject(i);
                	semaforos[i] = b.getString("Descripcion") + "%" + b.getString("Valor")+ "%" +
                	b.getString("Base")+"%" + b.getString("Meta") + "%" +b.getString("bml");
                	semaforos1[i] = b.getString("IdIndicador") +  "%" + b.getString("bml")
                			+ "%" + b.getString("Descripcion");
                	
                	sistema =  b.getString("Sistema");
                	
                }
                progresBar.dismiss();
//                semaforos1 = semaforos;
                Dialog d = crearDialogoSemaforos(semaforos, sistema);
                d.show();
                
            } catch (Exception e) {
            	
                Log.d("ReadWeatherJSONFeedTask", e.getLocalizedMessage());
                Toast.makeText(getBaseContext(), "No se pudo descargar la información",Toast.LENGTH_LONG).show();
                progresBar.dismiss();
            }          
        }
    }
	private Dialog crearDialogoSemaforos(String[] semaforos, String sistema)
	{
	    final Item[] items = new Item[semaforos.length];

		for(int i=0; i<semaforos.length; i++){
			String[] tmp = new String[semaforos.length];
			tmp = semaforos[i].split("%");
			int bml = Integer.parseInt(tmp[4]);
			if (bml == 1){
				
				Double valor = Double.parseDouble(tmp[1]);
				if( valor < 51.00)
					items[i] = new Item(tmp[0] + "\nLogro:" + tmp[1] + " Base:" + tmp[2] + " Meta:" + tmp[3], R.drawable.semar);
				else if(valor>=51.00 && valor<=75.00)
					items[i] = new Item(tmp[0] + "\nLogro:" + tmp[1] + " Base:" + tmp[2] + " Meta:" + tmp[3], R.drawable.semaa);
				else if(valor> 75.00)
					items[i] = new Item(tmp[0] + "\nLogro:" + tmp[1] + " Base:" + tmp[2] + " Meta:" + tmp[3], R.drawable.semav);
				else
					items[i] = new Item(tmp[0] + "\nLogro:" + tmp[1] + " Base:" + tmp[2] + " Meta:" + tmp[3], 0);
				
			}
			else{
				Double valor = Double.parseDouble(tmp[1]);
				if( valor < 51.00)
					items[i] = new Item(tmp[0] + "-" + tmp[1] + "%", R.drawable.semar);
				else if(valor>=51.00 && valor<=75.00)
					items[i] = new Item(tmp[0] + "-" + tmp[1] + "%", R.drawable.semaa);
				else if(valor> 75.00)
					items[i] = new Item(tmp[0] + "-" + tmp[1] + "%", R.drawable.semav);
				else
					items[i] = new Item(tmp[0] + "-" + tmp[1] + "%", 0);
			}

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
	 
	    builder.setTitle(this.origen + " " + sistema + " " + (this.avance != -1.0 ? (this.avance + "%") : ""));
	    builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int item) {
	        	Intent i = new Intent(getApplicationContext(), GraficaTemp.class);
	        	i.putExtra("Origen", origen);
	        	i.putExtra("Estado", estado);
	        	i.putExtra("Plantel", plantel);
	        	i.putExtra("Semaforo", Integer.parseInt(semaforos1[item].split("%")[0]));
	        	i.putExtra("semString",	semaforos1[item].split("%")[2]);
	        	i.putExtra("bml", Integer.parseInt(semaforos1[item].split("%")[1]));
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
		this.plantel = bundle.getString("Plantel");
		
		gv.setAdapter(new MyAdapter(this));
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
//				if(position == 2 || position==3||position==4||position==5||position==6||position==7||position==8){ 
//	                Toast.makeText(getBaseContext(), "Resultados no disponibles",Toast.LENGTH_LONG).show();
//
//					return; }
				
				if(position>8) return;
				progresBar = new ProgressDialog(v.getContext());
				if(origen.equals("Nacional")){
					new ReadJSON(-1).execute("http://200.23.107.50:8083/siiecon.asmx/avanseSistema?pIdSistema=" + sist[position]);
					new ReadJSON(position).execute("http://200.23.107.50:8083/siiecon.asmx/nacionalIndicadores?pIdSistema=" + sist[position]);
				}else if(origen.equals("Estatal")){
					new ReadJSON(-1).execute("http://200.23.107.50:8083/siiecon.asmx/avanseSistema?pIdSistema=" + sist[position]);
					new ReadJSON(position).execute("http://200.23.107.50:8083/siiecon.asmx/estatalIndicadores?pIdEntidad="+estado+"&pIdSistema=" + sist[position] );
				}else{
					new ReadJSON(-1).execute("http://200.23.107.50:8083/siiecon.asmx/avanseSistema?pIdSistema=" + sist[position]);
					new ReadJSON(position).execute("http://200.23.107.50:8083/siiecon.asmx/plantelIndicadores?pCt="+plantel+"&pIdSistema=" + sist[position] );
					
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
