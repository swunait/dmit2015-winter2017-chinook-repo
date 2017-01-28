package chinook.controller;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import chinook.entity.Album;
import chinook.service.AlbumService;

@Named
@RequestScoped
public class ViewAlbumsController {

	@Inject
	private AlbumService albumService;
	
	public List<Album> getAllAlbums() {
		return albumService.findAllOrderByTitle();
	}
	
}
