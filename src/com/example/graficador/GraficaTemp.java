package com.example.graficador;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class GraficaTemp extends Activity {
	private WebView mWebView;
	public String origen;
	public int estado;
	public int plantel;
	public int semaforo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grafica_temp);
		Bundle bundle = getIntent().getExtras();
		this.origen = bundle.getString("Origen");
		this.estado = bundle.getInt("Estado");
		this.plantel = bundle.getInt("Plantel");
		this.semaforo = bundle.getInt("Semaforo");
		mWebView = (WebView) findViewById(R.id.mybrowser);
        
        // Activo JavaScript
        mWebView.getSettings().setJavaScriptEnabled(true);
        
        
        // Cargamos la url que necesitamos    
        mWebView.loadUrl("file:///android_asset/Prueba_dist/index.html");
        Button btnSendMsg;
        
        btnSendMsg = (Button)findViewById(R.id.sendmsg);
        btnSendMsg.setOnClickListener(new Button.OnClickListener(){

       @Override
       public void onClick(View arg0) {
        // TODO Auto-generated method stub
        String msgToSend = "Veracruz, 45 - Campeche, 56 - Distrito Federal, 60 - Tamaulipas, 80";
        mWebView.loadUrl("javascript:grafica(\""+msgToSend+"\")");
        
       }});

}
	}
