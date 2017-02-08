package chinook.service;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.jboss.ejb3.annotation.SecurityDomain;

import chinook.entity.Artist;;

@Stateful
@SecurityDomain("chinookDomain")
@DeclareRoles({"adminUserRole","employeeUserRole","customerUserRole"})
public class ArtistService {

	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	private EntityManager entityManager;
	
	@RolesAllowed({"adminUserRole","employeeUserRole"})
	public void add(Artist currentArtist) {
		entityManager.persist(currentArtist);
	}

	@PermitAll
	public Artist findOneById(int artistId) {
		return entityManager.find(Artist.class, artistId);
	}
	
	@PermitAll
	@SuppressWarnings("unchecked")
	public List<Artist> findAll() {
		return entityManager.createQuery("SELECT a FROM Artist a").getResultList();
	}
	
	@PermitAll
	@SuppressWarnings("unchecked")
	public List<Artist> findAllOrderByName() {
		return entityManager.createQuery("SELECT a FROM Artist a ORDER by a.name").getResultList();
	}
	
	@RolesAllowed({"adminUserRole","employeeUserRole"})
	public void update(Artist currentArtist) {
		entityManager.merge(currentArtist);
		entityManager.flush();
	}
	
	@RolesAllowed("adminUserRole")
	public void delete(Artist currentArtist) {
		entityManager.remove(currentArtist);
	}
	
	@RolesAllowed("adminUserRole")
	public void delete(int artistId) {
		Artist currentArtist = findOneById(artistId);
		delete(currentArtist);
	}
	
 }
