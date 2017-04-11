package chinook.jaxrs;

import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import chinook.entity.Artist;

public class ChinookWebAPIClient {

	public static void main(String[] args) {
		final String APPLICATION_URL = "http://localhost:8080/chinook-webapp/rest/webapi/artists";
		Client restClient = ClientBuilder.newClient();
		WebTarget artistResource = restClient.target(APPLICATION_URL);
		// fetch and display a list of all Artist
		GenericType<List<Artist>> artistsType = new GenericType<List<Artist>>() {};
		List<Artist> artists = artistResource.request().get(artistsType);
		for( Artist currentArtist : artists ) {
			System.out.println( currentArtist.toString() );
		}
		// create a new artist
		Artist newArtist = new Artist();
		System.out.println("Enter artist name:");
		Scanner keyboard = new Scanner(System.in);
		String artistName = keyboard.nextLine();
		newArtist.setName(artistName);
		Response response = artistResource.request().post( Entity.json( newArtist ) );
		newArtist = response.readEntity(Artist.class);
		System.out.println("Successfully created new artist with ArtistID = " + newArtist.getArtistId() );
		
		// fetch the artist that was added
		WebTarget findOneArtistResource = artistResource.path( 
				MessageFormat.format("{0}", newArtist.getArtistId() ) );
		GenericType<Artist> artistType = new GenericType<Artist>() {} ;
		Artist findOneArtist = findOneArtistResource.request().get(artistType);
		System.out.println("Successfully found the following artist:");
		System.out.println( findOneArtist );
		
		// change the artist name
		System.out.println("Enter name for artist: ");
		String newArtistName = keyboard.nextLine();
		findOneArtist.setName(newArtistName);
		Response updateResponse = artistResource.request().put( Entity.json(findOneArtist) );
		updateResponse.close();
		System.out.println("Successfully updated artist");
		
		// delete the artist
		Response deleteResponse = findOneArtistResource.request().delete();
		deleteResponse.close();
		System.out.println("Successfully deleted artist");
		
		

	}

}
