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
import javax.ws.rs.core.Response;

import chinook.entity.Artist;
import chinook.entity.Genre;
import chinook.service.ArtistService;
import chinook.service.GenreService;


@Path("webapi")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChinookWebAPI {

	@Inject
	private ArtistService artistService;
	
	@Inject
	private GenreService genreService;
	
	@Path("genres")
	@POST
	public Response createGenre(Genre currentGenre) {
		genreService.add(currentGenre);
		return Response.ok().build();
	}
	
	@Path("genres")
	@GET
	public Response findAllGenres() {		
		return Response.ok( genreService.findAll() ).build();
	}
	@Path("genres/{id}")
	@GET
	public Response findOneGenre(@PathParam("id") int genreId) {
		return Response.ok( genreService.findOneById(genreId) ).build();
	}
	
	@Path("genres")
	@PUT
	public Response updateGenre(Genre currentGenre) {
		genreService.update(currentGenre);
		return Response.ok().build();
	}
	
	@Path("genres/{id}")
	@DELETE
	public Response deleteGenre(@PathParam("id") int genreId) {
		genreService.delete(genreId);
		return Response.ok().build();
	}
	
	
	
	@Path("artists")
	@GET
	public Response findAllArtists() {
		return Response.ok( artistService.findAllOrderByName() ).build();
	}
	
	@Path("artists/{id}")
	@GET
	public Response findOneArtist(@PathParam("id") int artistId) {
		Artist currentArtist = artistService.findOneById(artistId);
		if ( currentArtist == null ) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			return Response.ok(currentArtist).build();
		}
	}
	
	@Path("artists")
	@POST
	public Response createArtist(Artist currentArtist) {
		artistService.add(currentArtist);
		return Response.ok(currentArtist).build();
	}
	
	@Path("artists")
	@PUT
	public Response updateArtist(Artist currentArtist) {
		artistService.update(currentArtist);
		return Response.ok().build();
	}
	
	@Path("artists")
	@DELETE
	public Response deleteArtist(int artistId) {
		artistService.delete(artistId);
		return Response.ok().build();
	}
	
}













