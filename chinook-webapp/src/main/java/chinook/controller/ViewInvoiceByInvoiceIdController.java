package chinook.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.omnifaces.util.Messages;

import chinook.entity.Invoice;
import chinook.service.InvoiceService;

@Named
@ViewScoped
public class ViewInvoiceByInvoiceIdController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private InvoiceService invoiceService;
	
	@NotNull(message="InvoiceId value is required")
	private Integer queryValue;				// getter/setter
	
	private Invoice querySingleResult;		// getter
	
	public void executeQuery() {
		int invoiceId = queryValue;
		querySingleResult = invoiceService.findOneByInvoiceId(invoiceId);
		if (querySingleResult == null) {
			Messages.addGlobalError("Unknown invoiceId \"{0}\". We found 0 results", invoiceId);
		} else {
			Messages.addGlobalInfo("Successfully found one result.");
		}
	}

	public Integer getQueryValue() {
		return queryValue;
	}

	public void setQueryValue(Integer queryValue) {
		this.queryValue = queryValue;
	}

	public Invoice getQuerySingleResult() {
		return querySingleResult;
	}
	
}
