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
	private List<OHLCDataItem> data;

	private String chartTitle;
	//https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY&symbol=MSFT&apikey=demo&datatype=csv
	public CandleChart(String title, String chartTitle, List<OHLCDataItem> data) throws IOException{
     this.chartTitle = chartTitle;
     this.data = data;
	}
	public JFreeChart drawChart() {
		JFreeChart chart = ChartFactory.createCandlestickChart(chartTitle, "Time", "Price", getData(data), false);
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
	
	public OHLCDataset getData(List<OHLCDataItem> data){
		
	     
	     Collections.reverse(data);
	     OHLCDataItem[] datalist = data.toArray(new OHLCDataItem[data.size()]);
	     OHLCDataset dataset = new DefaultOHLCDataset(chartTitle, datalist);
	     
	     return dataset;
	}
}
