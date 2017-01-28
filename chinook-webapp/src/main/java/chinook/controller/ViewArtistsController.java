package chinook.controller;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import chinook.entity.Artist;
import chinook.service.ArtistService;

@Named
@RequestScoped
public class ViewArtistsController {

	@Inject
	private ArtistService artistService;
	
	public List<Artist> getAllArtists() {
		return artistService.findAllOrderByName();
	}
	
}
