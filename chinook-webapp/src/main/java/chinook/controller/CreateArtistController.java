package chinook.controller;

import java.io.Serializable;

import javax.ejb.EJBAccessException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.omnifaces.util.Messages;

import chinook.entity.Artist;
import chinook.service.ArtistService;

@Named
@ViewScoped
public class CreateArtistController implements Serializable {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(CreateArtistController.class);
	
	@Inject
	private ArtistService artistService;
	
	private Artist currentArtist = new Artist();
	
	public void createArtist() {
		try {
			artistService.add(currentArtist);
			currentArtist = new Artist();
			Messages.addGlobalInfo("Successfully created new artist");
		} catch (EJBAccessException e) {
			Messages.addGlobalError("You do not have permission to create this item.");
			log.error(e.getMessage());
		} catch (Exception e) {
			Messages.addGlobalError("Failed to create item.");
			log.error(e.getMessage());
		}
	}

	public Artist getCurrentArtist() {
		return currentArtist;
	}

	public void setCurrentArtist(Artist currentArtist) {
		this.currentArtist = currentArtist;
	}
	
}
