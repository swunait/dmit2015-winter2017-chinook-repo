package chinook.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
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
public class CreateAlbumController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private AlbumService albumService;
	@Inject
	private ArtistService artistService;
	private Album currentAlbum = new Album();
	private List<Artist> artists;
	private Integer selectedArtistId;
	
	
	@PostConstruct
	public void init() {
		artists = artistService.findAllOrderByName();
	}
	
	public void createAlbum() {
		if( selectedArtistId == null || selectedArtistId <= 0 ) {
			Messages.addGlobalError("An valid artist must be selected");
			return;
		}
		// find the Artist of the currentAlbum
		Artist selectedArtist = artistService.findOneById(selectedArtistId);
		// set the Artist of the currentAlbum
		currentAlbum.setArtist(selectedArtist);
		albumService.add(currentAlbum);
		currentAlbum = new Album();
		Messages.addGlobalInfo("Successfully created new album");
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
