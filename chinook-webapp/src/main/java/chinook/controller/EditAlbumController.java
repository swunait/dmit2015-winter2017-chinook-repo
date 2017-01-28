package chinook.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import chinook.entity.Album;
import chinook.entity.Artist;
import chinook.service.AlbumService;
import chinook.service.ArtistService;

@Named
@ViewScoped
public class EditAlbumController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private AlbumService albumService;
	@Inject
	private ArtistService artistService;
	private int albumId;
	private Album currentAlbum;
	private List<Artist> artists;
	private Integer selectedArtistId;
		
	
	@PostConstruct
	public void init() {
		artists = artistService.findAllOrderByName();
	}
	
	
	public void findAlbumById() {
		if (albumId <= 0) {
			Messages.addGlobalError("Bad request. Please use a link within the system");
			return;
		}
		
		Album queryResult = albumService.findOneById(albumId);
		if ( queryResult == null ) {
			Messages.addGlobalError("Bad request. Unknown albumId {0}", albumId);
			return;
		}
		
		currentAlbum = queryResult;		
		selectedArtistId = currentAlbum.getArtist().getArtistId();
	}

	public String updateAlbum() {
		if( selectedArtistId == null || selectedArtistId <= 0 ) {
			Messages.addGlobalError("An valid artist must be selected");
			return null;
		}
		// find the Artist of the currentAlbum
		Artist selectedArtist = artistService.findOneById(selectedArtistId);
		// set the Artist of the currentAlbum
		currentAlbum.setArtist(selectedArtist);
		albumService.update(currentAlbum);
		currentAlbum = null;
		Messages.addFlashGlobalInfo("Update was successful.");

		return "/public/viewAlbums?faces-redirect=true";
	}
	
	public String removeAlbum() {
		String outcome = "/public/viewAlbums?faces-redirect=true";
		try {
			albumService.delete(currentAlbum);
			currentAlbum = null;
			Messages.addFlashGlobalInfo("Delete was successful.");
		} catch (EJBTransactionRolledbackException e) {
			Messages.addGlobalError("This item cannot be deleted.");
			outcome = null;
		} catch (Exception e) {
			Messages.addGlobalError("Delete was not successful.");
			outcome = null;
		}
		return outcome;
	}
	
	public String cancel() {
		return "/public/viewAlbums?faces-redirect=true";
	}

	public int getAlbumId() {
		return albumId;
	}

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	public Album getCurrentAlbum() {
		return currentAlbum;
	}

	public void setCurrentAlbum(Album currentAlbum) {
		this.currentAlbum = currentAlbum;
	}
	
	public List<Artist> getArtists() {
		return artists;
	}

	public Integer getSelectedArtistId() {
		return selectedArtistId;
	}

	public void setSelectedArtistId(Integer selectedArtistId) {
		this.selectedArtistId = selectedArtistId;
	}
}
