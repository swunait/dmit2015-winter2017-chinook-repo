package chinook.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import java.util.List;


/**
 * The persistent class for the Album database table.
 * 
 */
@Entity
@NamedQuery(name="Album.findAll", query="SELECT a FROM Album a")
public class Album implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="AlbumId")
	private int albumId;

	@Length(min=1,max=120,message="Title value is required and is limited to 126 characters in length.")
	@Column(name="Title")
	private String title;

	@NotNull(message="A valid artist must be assigned to the album")
	@Valid
	//bi-directional many-to-one association to Artist
	@ManyToOne
	@JoinColumn(name="ArtistId")
	private Artist artist;

	//bi-directional many-to-one association to Track
	@OneToMany(mappedBy="album")
	private List<Track> tracks;

	public Album() {
	}

	public int getAlbumId() {
		return this.albumId;
	}

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Artist getArtist() {
		return this.artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public List<Track> getTracks() {
		return this.tracks;
	}

	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}

	public Track addTrack(Track track) {
		getTracks().add(track);
		track.setAlbum(this);

		return track;
	}

	public Track removeTrack(Track track) {
		getTracks().remove(track);
		track.setAlbum(null);

		return track;
	}

}