import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.OHLCDataItem;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.ui.ApplicationFrame;

public class CandleChart{
	//https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY&symbol=MSFT&apikey=demo&datatype=csv
	public CandleChart(String title, String chartTitle) throws IOException{
     
	}
	public JFreeChart drawChart() {
		JFreeChart chart = ChartFactory.createCandlestickChart("MSFT", "Time", "Price", getData(), false);
	     chart.setBackgroundPaint(Color.white);

	     // 4. Set a few custom plot features
	     XYPlot plot = (XYPlot) chart.getPlot();
	     plot.setBackgroundPaint(Color.WHITE); // light yellow = new Color(0xffffe0)
	     plot.setDomainGridlinesVisible(true);
	     plot.setDomainGridlinePaint(Color.lightGray);
	     plot.setRangeGridlinePaint(Color.lightGray);
	     ((NumberAxis) plot.getRangeAxis()).setAutoRangeIncludesZero(false);

	     // 5. Skip week-ends on the date axis
	     ((DateAxis) plot.getDomainAxis()).setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());

	     // 6. No volume drawn
	     ((CandlestickRenderer) plot.getRenderer()).setDrawVolume(false);
		return chart;
	}
	
	public OHLCDataset getData(){
		List<OHLCDataItem> dataItems = new ArrayList<OHLCDataItem>();
	     try {
	         String strUrl = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=MSFT&apikey=demo&datatype=csv";
	         URL url = new URL(strUrl);
	         BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
	         DateFormat df = new SimpleDateFormat("y-M-d");

	         String inputLine;
	         in.readLine();
	         while ((inputLine = in.readLine()) != null) {
	             StringTokenizer st = new StringTokenizer(inputLine, ",");

	             Date date = df.parse(st.nextToken());
	             double open = Double.parseDouble(st.nextToken());
	             double high = Double.parseDouble(st.nextToken());
	             double low = Double.parseDouble(st.nextToken());
	             double close = Double.parseDouble(st.nextToken());
	             double adjClose = Double.parseDouble(st.nextToken());
	             double volume = Double.parseDouble(st.nextToken());
	             
	             open = open * adjClose / close;
	             high = high * adjClose / close;
	             low = low * adjClose / close;

	             OHLCDataItem item = new OHLCDataItem(date, open, high, low, adjClose, volume);
	             dataItems.add(item);
	         }
	         in.close();
	         
	     }catch(Exception e) {
	    	 e.printStackTrace();
	     }
	     
	     Collections.reverse(dataItems);
	     OHLCDataItem[] data = dataItems.toArray(new OHLCDataItem[dataItems.size()]);
	     OHLCDataset dataset = new DefaultOHLCDataset("MSFT", data);
	     
	     return dataset;
	}
}
