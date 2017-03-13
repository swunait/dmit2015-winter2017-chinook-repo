package chinook.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.omnifaces.util.Messages;

import chinook.entity.Customer;
import chinook.entity.Invoice;
import chinook.service.CustomerService;
import chinook.service.InvoiceService;

@Named
@ViewScoped
public class ViewInvoiceByCustomerController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private CustomerService customerService;
	
	private List<Customer> customers;		// getter
	
	@Inject
	private InvoiceService invoiceService;
	
	@NotNull(message="You must select a valid customer")
	private Integer selectedCustomerId;				// getter/setter
	
	private List<Invoice> queryResultList;	// getter
	
	private Invoice querySingleResult;		// getter
	
	@PostConstruct
	public void init() {
		customers = customerService.findAllOrderByLastnameFirstname();
	}
	
	public void retreiveInvoicesByCustomer() {
		queryResultList = invoiceService.findAllByCustomer(selectedCustomerId);
		querySingleResult = null;
		if (queryResultList == null || queryResultList.size() == 0) {
			Messages.addGlobalError("Unknown customerId \"{0}\". We found 0 results", selectedCustomerId);
		} else {
			Messages.addGlobalInfo("We found {0} results.", queryResultList.size());
		}
	}
	
	public void retreiveInvoiceByInvoiceId(int invoiceId) {
		querySingleResult = invoiceService.findOneByInvoiceId(invoiceId);
		queryResultList = null;
	}

	public Integer getSelectedCustomerId() {
		return selectedCustomerId;
	}

	public void setSelectedCustomerId(Integer selectedCustomerId) {
		this.selectedCustomerId = selectedCustomerId;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public List<Invoice> getQueryResultList() {
		return queryResultList;
	}

	public Invoice getQuerySingleResult() {
		return querySingleResult;
	}

}
