package controlador;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class JSONParserImplement {
	
	private static final String TAG_VALUE = "Valor";
	private static final String TAG_NAME = "name";
	private static String url = "http://api.androidhive.info/contacts/";
	private HashMap<String, String> urlValue = new HashMap<String, String>();
	
	JSONArray coord = null;
	
	public JSONParserImplement(){
		
		urlValue.put("indicadorPlantel", "http://200.23.107.50:8083/siiecon.asmx/indicadorPlantel?pIdEntidad=1&IdIndicador=77");
		
	}
	
	
	public void getStaticValuesFromJSON(String indicador){
		
		HashMap<String, Integer> values = new HashMap<String, Integer>();
		
		JSONParser jParser = new JSONParser();
		
		JSONObject json = jParser.getJSONFromUrl(url);
		
		try {
			
			coord = json.getJSONArray("contacts");
			
			for(int i = 0; i < coord.length(); i++){
				
				JSONObject o = coord.getJSONObject(i);
				
				String name = o.getString(TAG_NAME);
				
				
				
			}
			
		} catch (JSONException e) {
			e.getStackTrace();
		}
		
		
		
	}

	
}
