package controlador;

import java.util.HashMap;
import java.util.Set;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class LineGraph {
	
	private String xValues[];
	private double yValues[];
	
	
	public LineGraph(HashMap<String, String> info){
		
		int tam = info.size();
		this.xValues = new String[tam];
		this.yValues = new double[tam];
		int i = 0;
		
		for(String value : info.values())
		{
			this.yValues[i] = Double.parseDouble(value);
			i++;
			
		}
		
		i = 0;
		Set<String> keys = info.keySet();
		for(String key : keys){
			this.xValues[i] = key;
			i++;
		}
		
	}
	
	
	
	public Intent getIntent(Context context){
		int tam = this.xValues.length;
		int long1 = tam/2;
		int long2 = xValues.length - long1;
		double yprimo[] = new double[long1];
		double yprimo2[] = new double[long2];
		
		for(int i = 0; i < long1; i++){
			yprimo[i] = yValues[i];
		}
		
		for(int i = 0; i < long2; i++){
			yprimo2[i] = yValues[i + long1];
		}
		
		
		//DATA1
		//int y[] = {35, 40, 45, 50, 25, 20, 42, 15, 35, 50};
		CategorySeries series = new CategorySeries("Demo Bar Graph");
		for(int i = 0; i < yprimo.length; i++)
		{
			series.add("Bar " + (i+1), yprimo[i]);
		}
		
		//DATA2
		//int y2[] = {50, 35, 15, 42, 20, 25, 50, 45, 40, 35};
		CategorySeries series2 = new CategorySeries("Demo Bar Graph 2");
		for(int i = 0; i < yprimo.length; i++)
		{
			series2.add("Bar " + (i+1), yprimo2[i]);
		}
		
		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series.toXYSeries());
		dataset.addSeries(series2.toXYSeries());
		
		//Customize 1
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setDisplayChartValues(true);
		renderer.setChartValuesSpacing((float)0.5);
		renderer.setColor(Color.BLUE);
		
		//Customize 2
		XYSeriesRenderer renderer2 = new XYSeriesRenderer();
		renderer.setDisplayChartValues(true);
		renderer.setChartValuesSpacing((float)0.5);
		renderer.setColor(Color.CYAN);
		
		
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);
		mRenderer.addSeriesRenderer(renderer2);
		mRenderer.setChartTitle("Demo Graph Title");
		mRenderer.setXTitle("X VALUE");
		mRenderer.setYTitle("Y VALUE");
		mRenderer.setZoomButtonsVisible(true);
		
		
		
		Intent intent = ChartFactory.getBarChartIntent(context, dataset, mRenderer, Type.DEFAULT);
		return intent;
		
	}
}
