package chinook.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.omnifaces.util.Messages;

import chinook.entity.InvoiceLine;
import chinook.entity.Track;
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

	
}
