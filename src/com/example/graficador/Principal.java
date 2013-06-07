package com.example.graficador;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Principal extends Activity {

	private Dialog crearDialogoSeleccionEstatal()
	{
	    final String[] items = {"Estado de México", "Aguas Calientes", "Hidalgo"};
	 
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	 
	    builder.setTitle("Seleccione un Estado");
	    builder.setItems(items, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int item) {
	        
	        	Intent i = new Intent(getApplicationContext(), IndPorSist.class);
	        	startActivity(i);
	        
	        
	        }
	    });
	 
	    return builder.create();
	}
	private Dialog crearDialogoSeleccionPlantel()
	{
	    final String[] items = {"Estado de México", "Aguas Calientes", "Hidalgo"};
	 
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	 
	    builder.setTitle("Seleccione un Estado");
	    builder.setItems(items, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int item) {
	            Dialog d = crearDialogoSeleccionPlantelEstado();
	            d.show();
	        }
	    });
	 
	    return builder.create();
	}
	
	private Dialog crearDialogoSeleccionPlantelEstado(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccione un Plantel");
        String [] it = {"CBTIS 15", "CETIS 99", "CBTIS 8"};
        builder.setItems(it, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), IndPorSist.class);
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
				Dialog d = crearDialogoSeleccionEstatal();
				d.show();
				//d.setContentView(R.layout.estados_layout);
				
				
			}
		});
        Button btnPlantel = (Button)findViewById(R.id.btnPlantel);
        btnPlantel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Dialog d = crearDialogoSeleccionPlantel();
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
