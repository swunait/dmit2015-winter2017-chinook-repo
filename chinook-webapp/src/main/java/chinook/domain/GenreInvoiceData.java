package chinook.domain;

import java.math.BigDecimal;

public class GenreInvoiceData {
	
	private String genreName;
	private BigDecimal genreInvoiceTotal;
	
	public GenreInvoiceData() {
		super();
	}
	public GenreInvoiceData(String genreName, BigDecimal genreInvoiceTotal) {
		super();
		this.genreName = genreName;
		this.genreInvoiceTotal = genreInvoiceTotal;
	}

	public String getGenreName() {
		return genreName;
	}
	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}
	public BigDecimal getGenreInvoiceTotal() {
		return genreInvoiceTotal;
	}
	public void setGenreInvoiceTotal(BigDecimal genreInvoiceTotal) {
		this.genreInvoiceTotal = genreInvoiceTotal;
	}

}
