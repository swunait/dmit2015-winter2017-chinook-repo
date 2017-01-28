package chinook.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the MediaType database table.
 * 
 */
@Entity
@NamedQuery(name="MediaType.findAll", query="SELECT m FROM MediaType m")
public class MediaType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="MediaTypeId")
	private int mediaTypeId;

	@Column(name="Name")
	private String name;

	//bi-directional many-to-one association to Track
	@OneToMany(mappedBy="mediaType")
	private List<Track> tracks;

	public MediaType() {
	}

	public int getMediaTypeId() {
		return this.mediaTypeId;
	}

	public void setMediaTypeId(int mediaTypeId) {
		this.mediaTypeId = mediaTypeId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Track> getTracks() {
		return this.tracks;
	}

	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}

	public Track addTrack(Track track) {
		getTracks().add(track);
		track.setMediaType(this);

		return track;
	}

	public Track removeTrack(Track track) {
		getTracks().remove(track);
		track.setMediaType(null);

		return track;
	}

}