package chinook.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Invoice database table.
 * 
 */
@Entity
@NamedQuery(name="Invoice.findAll", query="SELECT i FROM Invoice i")
public class Invoice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="InvoiceId")
	private int invoiceId;

	@Column(name="BillingAddress")
	private String billingAddress;

	@Column(name="BillingCity")
	private String billingCity;

	@Column(name="BillingCountry")
	private String billingCountry;

	@Column(name="BillingPostalCode")
	private String billingPostalCode;

	@Column(name="BillingState")
	private String billingState;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="InvoiceDate")
	private Date invoiceDate;

	@Column(name="Total")
	private BigDecimal total;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	@JoinColumn(name="CustomerId")
	private Customer customer;

	//bi-directional many-to-one association to InvoiceLine
	@OneToMany(mappedBy="invoice")
	private List<InvoiceLine> invoiceLines;

	public Invoice() {
	}

	public int getInvoiceId() {
		return this.invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getBillingAddress() {
		return this.billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getBillingCity() {
		return this.billingCity;
	}

	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}

	public String getBillingCountry() {
		return this.billingCountry;
	}

	public void setBillingCountry(String billingCountry) {
		this.billingCountry = billingCountry;
	}

	public String getBillingPostalCode() {
		return this.billingPostalCode;
	}

	public void setBillingPostalCode(String billingPostalCode) {
		this.billingPostalCode = billingPostalCode;
	}

	public String getBillingState() {
		return this.billingState;
	}

	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}

	public Date getInvoiceDate() {
		return this.invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<InvoiceLine> getInvoiceLines() {
		return this.invoiceLines;
	}

	public void setInvoiceLines(List<InvoiceLine> invoiceLines) {
		this.invoiceLines = invoiceLines;
	}

	public InvoiceLine addInvoiceLine(InvoiceLine invoiceLine) {
		getInvoiceLines().add(invoiceLine);
		invoiceLine.setInvoice(this);

		return invoiceLine;
	}

	public InvoiceLine removeInvoiceLine(InvoiceLine invoiceLine) {
		getInvoiceLines().remove(invoiceLine);
		invoiceLine.setInvoice(null);

		return invoiceLine;
	}

}