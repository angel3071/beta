package com.example.graficador;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import controlador.JsonCont;
import controlador.LineGraph;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class IndPorSist extends Activity {
	 HashMap<String, String> datosGrafica = new HashMap<String, String>();
	private class ReadJSON extends AsyncTask
    <String, Void, String> {
        protected String doInBackground(String... urls) {
            return new JsonCont().readJSONFeed(urls[0]);
        }

        protected void onPostExecute(String result) {
            try {
            	Toast.makeText(getBaseContext(), "En Línea", 
                        Toast.LENGTH_LONG).show();
            	
            	
            	
            	
            	
            	
                JSONArray array = new JSONArray(result);
                
                for(int i=0;i<array.length(); i++){
                	
                	JSONObject b = array.getJSONObject(i);
                	datosGrafica.put(b.getString("EntFed_Dsc"), b.getString("Valor"));
                	
                	
                }
                LineGraph graf = new LineGraph(datosGrafica);
            	Intent i = graf.getIntent(getApplicationContext());
            	startActivity(i);
                
 
                
            } catch (Exception e) {
            	
                Log.d("ReadWeatherJSONFeedTask", e.getLocalizedMessage());
                Toast.makeText(getBaseContext(), "Imposible Conectar a la Red",Toast.LENGTH_LONG).show();
            }          
        }
    }

	HashMap<String, String> urls = new HashMap<String, String>();
	String url;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_indicadores);
		
		urls.put("urlNacioal", "http://200.23.107.50:8083/siiecon.asmx/indicadorEstatal?IdIndicador=77");
		
		GridView gv = (GridView) findViewById(R.id.grid_view);
		
		Bundle bundle = getIntent().getExtras();
		String origen = bundle.getString("origen");
		
		url = urls.get(origen);
		//de donde vino
		gv.setAdapter(new MyAdapter(this));
		gv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
 
                // Sending image id to FullScreenActivity
            	//new ReadJSON().execute(url);
//                Intent i = new Intent(getApplicationContext(), Grafica.class);
//                // passing array index
//                i.putExtra("id", position);
//                startActivity(i);
            	HashMap<String, String> abe = new HashMap<String, String>();
            	abe.put("AGUASCALIENTES", "100");
            	abe.put("BAJA CALIFORNIA", "70");
            	abe.put("BAJA CALIFORNIA SUR", "15");
            	abe.put("CAMPECHE", "100");
            	abe.put("COAHUILA", "85");
            	abe.put("COLIMA", "91");
            	abe.put("CHIAPAS", "22");
            	abe.put("CHIHUAHUA", "100");
            	abe.put("DISTRITO FEDERAL", "23");
            	abe.put("DURANGO", "55");
            	abe.put("GUANAJUATO", "85");
            	abe.put("GUERRERO", "44");
            	
            	LineGraph graf = new LineGraph(abe);
            	Intent i = graf.getIntent(getApplicationContext());
            	i = new Intent(getApplicationContext(), GraficaTemp.class);
            	startActivity(i);
            	
            	
            	
            	
            	
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
