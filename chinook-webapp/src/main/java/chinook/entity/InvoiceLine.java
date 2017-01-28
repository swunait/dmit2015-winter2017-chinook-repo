package chinook.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the InvoiceLine database table.
 * 
 */
@Entity
@NamedQuery(name="InvoiceLine.findAll", query="SELECT i FROM InvoiceLine i")
public class InvoiceLine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="InvoiceLineId")
	private int invoiceLineId;

	@Column(name="Quantity")
	private int quantity;

	@Column(name="UnitPrice")
	private BigDecimal unitPrice;

	//bi-directional many-to-one association to Invoice
	@ManyToOne
	@JoinColumn(name="InvoiceId")
	private Invoice invoice;

	//bi-directional many-to-one association to Track
	@ManyToOne
	@JoinColumn(name="TrackId")
	private Track track;

	public InvoiceLine() {
	}

	public int getInvoiceLineId() {
		return this.invoiceLineId;
	}

	public void setInvoiceLineId(int invoiceLineId) {
		this.invoiceLineId = invoiceLineId;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Invoice getInvoice() {
		return this.invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Track getTrack() {
		return this.track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

}