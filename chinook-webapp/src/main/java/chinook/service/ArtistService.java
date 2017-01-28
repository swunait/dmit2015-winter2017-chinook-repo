package chinook.service;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import chinook.entity.Artist;;

@Stateful
public class ArtistService {

	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	private EntityManager entityManager;
	
	public void add(Artist currentArtist) {
		entityManager.persist(currentArtist);
	}
	
	public Artist findOneById(int artistId) {
		return entityManager.find(Artist.class, artistId);
	}
	
	@SuppressWarnings("unchecked")
	public List<Artist> findAll() {
		return entityManager.createQuery("SELECT a FROM Artist a").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Artist> findAllOrderByName() {
		return entityManager.createQuery("SELECT a FROM Artist a ORDER by a.name").getResultList();
	}
	
	public void update(Artist currentArtist) {
		entityManager.merge(currentArtist);
		entityManager.flush();
	}
	
	public void delete(Artist currentArtist) {
		entityManager.remove(currentArtist);
	}
	
	public void delete(int artistId) {
		Artist currentArtist = findOneById(artistId);
		delete(currentArtist);
	}
	
 }
