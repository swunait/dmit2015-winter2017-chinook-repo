package chinook.jaxrs;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import chinook.entity.Artist;
import chinook.service.ArtistService;


@Path("webapi")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChinookWebAPI {

	@Inject
	private ArtistService artistService;
	
	@Path("artists")
	@GET
	public List<Artist> findAllArtists() {
		return artistService.findAllOrderByName();
	}
	
	@Path("artists/{id}")
	@GET
	public Artist findOneArtist(@PathParam("id") int artistId) {
		return artistService.findOneById(artistId);
	}
	
	@Path("artists")
	@POST
	public void createArtist(Artist currentArtist) {
		artistService.add(currentArtist);
	}
	
	@Path("artists")
	@PUT
	public void updateArtist(Artist currentArtist) {
		artistService.update(currentArtist);
	}
	
	@Path("artists")
	@DELETE
	public void deleteArtist(int artistId) {
		artistService.delete(artistId);
	}
	
}













