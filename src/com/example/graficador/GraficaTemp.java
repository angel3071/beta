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
		//this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		mWebView = (WebView) findViewById(R.id.webview);
        
        // Activo JavaScript
        mWebView.getSettings().setJavaScriptEnabled(true);
        
        // Cargamos la url que necesitamos    
        mWebView.loadUrl("file:///android_asset/Prueba_dist/index.html");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.grafica_temp, menu);
		return true;
	}

}
