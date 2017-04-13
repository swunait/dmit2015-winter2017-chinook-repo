package chinook.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import java.util.List;


/**
 * The persistent class for the Genre database table.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@NamedQuery(name="Genre.findAll", query="SELECT g FROM Genre g")
public class Genre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="GenreId")
	private int genreId;

	@Column(name="Name")
	private String name;

	@XmlTransient
	//bi-directional many-to-one association to Track
	@OneToMany(mappedBy="genre")
	private List<Track> tracks;

	public Genre() {
	}

	public int getGenreId() {
		return this.genreId;
	}

	public void setGenreId(int genreId) {
		this.genreId = genreId;
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
		track.setGenre(this);

		return track;
	}

	public Track removeTrack(Track track) {
		getTracks().remove(track);
		track.setGenre(null);

		return track;
	}

}