package chinook.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import chinook.entity.Invoice;

@Named
@ViewScoped
public class ViewInvoiceByDateRangeController implements Serializable {
	private static final long serialVersionUID = 1L;

	private Date startDate;		// getter/setter
	private Date endDate;		// getter/setter
	
	@Inject
	private ViewInvoiceByCustomerController viewInvoiceByCustomerController;
	
	public void doSearch() {
		List<Invoice> invoices = viewInvoiceByCustomerController
				.getInvoiceService()
				.findAllByDateRange(startDate, endDate);
		viewInvoiceByCustomerController.setQueryResultList(invoices);
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
