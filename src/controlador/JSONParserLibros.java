package controlador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;

public class JSONParserLibros {

	private Activity activity;
	private JSONObject jObject; 
	private ProgressDialog progressDialog = null;
	private Runnable runReadAndParseJSON;

	public JSONParserLibros(Activity a){
	   activity = a;
	}

	public void readAndParseJSONLibros() throws JSONException{
	   runReadAndParseJSON = new Runnable() {
	      @Override
	      public void run() {
	       try{
	          readJSONLibros();
	       } catch(Exception e){}
	      }
	   };
	   Thread thread = new Thread(null, runReadAndParseJSON,"bgReadJSONLibros");
	   thread.start();
	   progressDialog = ProgressDialog.show(activity, "Descargando información", "Por favor espere",true);
	}

	private void readJSONLibros() throws JSONException{
	   jObject = JSONManager.getJSONfromURL("http://192.168.1.87/android/service.libros.php");
	   if(jObject != null)
	          parseJSONLibros(jObject.getJSONArray("libros"));
	   activity.runOnUiThread(returnRes);
	}

	private void parseJSONLibros(JSONArray librosArray) throws JSONException{
	  for(int i = 0; i < librosArray.length(); i++){
//	     Libro l = new Libro();
//	     l.setIdLibro(librosArray.getJSONObject(i).getInt("id"));
//	     l.setTitulo(librosArray.getJSONObject(i).getString("libro"));
//	     l.setISBN(librosArray.getJSONObject(i).getString("isbn"));
//	     l.save(activity);
	  }
	}

	private Runnable returnRes = new Runnable(){ 
	 @Override 
	 public void run() {
	  progressDialog.dismiss();
	 }
	};
	
	
	
}
