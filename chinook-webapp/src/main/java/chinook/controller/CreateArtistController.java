package chinook.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import chinook.entity.Artist;
import chinook.service.ArtistService;

@Named
@ViewScoped
public class CreateArtistController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private ArtistService artistService;
	
	private Artist currentArtist = new Artist();
	
	public void createArtist() {
		artistService.add(currentArtist);
		currentArtist = new Artist();
		Messages.addGlobalInfo("Successfully created new artist");
	}

	public Artist getCurrentArtist() {
		return currentArtist;
	}

	public void setCurrentArtist(Artist currentArtist) {
		this.currentArtist = currentArtist;
	}
	
}
