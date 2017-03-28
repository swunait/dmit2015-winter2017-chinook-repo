package chinook.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.omnifaces.util.Messages;

import chinook.entity.Customer;
import chinook.entity.Invoice;
import chinook.entity.InvoiceLine;
import chinook.entity.Track;
import chinook.exception.IllegalQuantityException;
import chinook.exception.NoInvoiceLinesException;
import chinook.service.CustomerService;
import chinook.service.InvoiceService;
import chinook.service.TrackService;

@Named
@SessionScoped
public class TrackShoppingCartController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<InvoiceLine> invoiceLines = new ArrayList<>();		// getter
	
	@NotNull(message="TrackId value is required")
	private Integer trackId;	// getter/setter
	
	@Inject
	private TrackService trackService;
	
	@Inject
	private CustomerService customerService;
	private List<Customer> customers;		// getter
	private Integer selectedCustomerId;		// getter/setter
	
	@Inject
	private InvoiceService invoiceService;
	
	@PostConstruct
	public void init() {
		customers = customerService.findAllOrderByLastnameFirstname();
	}
	
	public void submitOrder() {
		try {
			int customerId = selectedCustomerId;
			Customer invoiceCustomer = customerService.findOneByCustomerId(customerId);
			Invoice newInvoice = new Invoice();
			newInvoice.setCustomer(invoiceCustomer);
			newInvoice.setBillingAddress(invoiceCustomer.getAddress());
			newInvoice.setBillingCity(invoiceCustomer.getCity());
			newInvoice.setBillingCountry(invoiceCustomer.getCompany());
			newInvoice.setBillingPostalCode(invoiceCustomer.getPostalCode());
			newInvoice.setBillingState(invoiceCustomer.getState());
			
			int invoiceId = invoiceService.create(newInvoice, invoiceLines);
			Messages.addGlobalInfo("Successfully created invoice #{0}", invoiceId);
			
			// empty the shopping cart
			invoiceLines.clear();			
		} catch( NoInvoiceLinesException | IllegalQuantityException e ) {
			Messages.addGlobalError(e.getMessage());
		} catch( Exception e ) {
			Messages.addGlobalError("Create invoice was not successful");
		}
	}
	
	public void add() {
		Track currentTrack = trackService.findOneById(trackId);
		if (currentTrack != null) {
			boolean alreadyInCart = false;
			for (InvoiceLine il : invoiceLines ) {
				if( currentTrack.getTrackId() == il.getTrack().getTrackId() ) {
					alreadyInCart = true;
					il.setQuantity( il.getQuantity() + 1 );
					break;
				}
			}
			if( !alreadyInCart ) {
				InvoiceLine currentInvoiceLine = new InvoiceLine();			
				currentInvoiceLine.setQuantity(1);
				currentInvoiceLine.setTrack(currentTrack);
				currentInvoiceLine.setUnitPrice( currentTrack.getUnitPrice() );
				// add track to cart
				invoiceLines.add(currentInvoiceLine);
				
			}
						
			Messages.addGlobalInfo("Successfully added track to shopping cart.");
		} else {
			Messages.addGlobalError("Invalid trackId {0}", trackId);
		}
	}
	
	public void remove(InvoiceLine currentInvoiceLine) {
		invoiceLines.remove(currentInvoiceLine);
	}
	
	public void emptyCart() {
		invoiceLines.clear();
	}

	public Integer getTrackId() {
		return trackId;
	}

	public void setTrackId(Integer trackId) {
		this.trackId = trackId;
	}

	public List<InvoiceLine> getInvoiceLines() {
		return invoiceLines;
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

	
	
}
