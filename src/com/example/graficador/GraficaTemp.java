package com.example.graficador;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;

public class GraficaTemp extends Activity {
	private WebView mWebView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grafica_temp);
		
		mWebView = (WebView) findViewById(R.id.mybrowser);
        
        // Activo JavaScript
        mWebView.getSettings().setJavaScriptEnabled(true);
        
        final MyJavaScriptInterface myJavaScriptInterface = new MyJavaScriptInterface(this);
        
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
