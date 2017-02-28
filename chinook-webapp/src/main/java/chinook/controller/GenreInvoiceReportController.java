package chinook.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;

import chinook.domain.GenreInvoiceData;
import chinook.service.GenreService;

@Named
@ViewScoped
public class GenreInvoiceReportController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private GenreService genreService;	
	
	private List<GenreInvoiceData> genreInvoiceDatas;
	
	private PieChartModel genrePieModel;	// getter
	
	private HorizontalBarChartModel genreHorizontalBarModel;	// getter
		
	@PostConstruct
	public void init() {
		genreInvoiceDatas = genreService.findGenreInvoiceData();
		
		// create a new model for the PrimeFaces pie chart
		genrePieModel = new PieChartModel();
		
		// create a new model for the PrimeFaces horizontal bar chart
		genreHorizontalBarModel = new HorizontalBarChartModel();
		ChartSeries genreSeries = new ChartSeries();
		
		for( GenreInvoiceData data : genreInvoiceDatas ) {
			// populate data for the PrimeFaces pie chart
			genrePieModel.set( data.getGenreName(), data.getGenreInvoiceTotal() );
			
			// populate data for both the PrimeFaces horizontal bar chart and vertial bar chart
			genreSeries.set( data.getGenreName(), data.getGenreInvoiceTotal() );
		}
		// add the series of data to the bar models
		genreHorizontalBarModel.addSeries(genreSeries);
		
		// configure the pie chart
		genrePieModel.setTitle("Genre Invoice Pie Chart");
		genrePieModel.setLegendPosition("w");
		genrePieModel.setShowDataLabels(true);
		
		// configure the horizaontal bar chart
		genreHorizontalBarModel.setTitle("Genre Invoice Horizontal Bar Chart");
		genreHorizontalBarModel.setShowPointLabels(true);
		Axis xAxis = genreHorizontalBarModel.getAxis(AxisType.X);
		xAxis.setLabel("Invoice Total");
		// use Java 8 steams and the max method to determine the maximum genre invoice total
		BigDecimal maxInvoiceTotal = genreInvoiceDatas.stream().max(Comparator.comparing( GenreInvoiceData::getGenreInvoiceTotal)).get().getGenreInvoiceTotal();
		xAxis.setMax(maxInvoiceTotal);
		xAxis.setMin( BigDecimal.ZERO );
		xAxis.setTickCount( 10 );
		
		Axis yAxis = genreHorizontalBarModel.getAxis(AxisType.Y);
		yAxis.setLabel("Genre");
		
	}

	public PieChartModel getGenrePieModel() {
		return genrePieModel;
	}

	public HorizontalBarChartModel getGenreHorizontalBarModel() {
		return genreHorizontalBarModel;
	}
	
}
