package chinook.service;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import chinook.entity.Album;;

@Stateful
public class AlbumService {

	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	private EntityManager entityManager;
	
	public void add(Album currentAlbum) {
		entityManager.persist(currentAlbum);
	}
	
	public Album findOneById(int albumId) {
		return entityManager.find(Album.class, albumId);
	}
	
	@SuppressWarnings("unchecked")
	public List<Album> findAll() {
		return entityManager.createQuery("SELECT a FROM Album a").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Album> findAllOrderByTitle() {
		return entityManager.createQuery("SELECT a FROM Album a ORDER by a.title").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Album> findAllByArtistId(int artistId) {
		return entityManager.createQuery(
			"SELECT a FROM Album a WHERE a.artist.artistId = :artistIdValue ORDER by a.title"
			).setParameter("artistIdValue", artistId)
			.getResultList();
	}
	
	public void update(Album currentAlbum) {
		entityManager.merge(currentAlbum);
		entityManager.flush();
	}
	
	public void delete(Album currentAlbum) {
		entityManager.remove(currentAlbum);
	}
	
	public void delete(int albumId) {
		Album currentAlbum = findOneById(albumId);
		delete(currentAlbum);
	}
	
 }
