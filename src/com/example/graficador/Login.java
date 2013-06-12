package com.example.graficador;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import controlador.JsonCont;

public class Login extends Activity {

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
                
 
                
            } catch (Exception e) {
            	
                Log.d("ReadWeatherJSONFeedTask", e.getLocalizedMessage());
                Toast.makeText(getBaseContext(), "Imposible Conectar a la Red",Toast.LENGTH_LONG).show();
            }          
        }
    }
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
//        EditText usuario = (EditText) findViewById(R.id.usuario);
//        usuario.clearFocus();
        
//        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        Button b = (Button)findViewById(R.id.btnLogin);
        b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), Principal.class);
				startActivity(i);
				
			}
		});
//       
//        //JSONObject jb = JSONManager.getJSONfromURL("http://200.23.107.50:8083/siiecon.asmx/lstSistemasPlantel");
//        //new ReadJSON().execute("http://200.23.107.50:8083/siiecon.asmx/indicadorEstatal?IdIndicador=77");
//        
//        
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent i = new Intent(getApplicationContext(), Principal.class);
//				startActivity(i);
//			}
//		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
    
}
