package chinook.service;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import chinook.domain.GenreInvoiceData;
import chinook.entity.Genre;

@Stateful
public class GenreService {

	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	private EntityManager entityManager;
	
	public void add(Genre currentGenre) {
		entityManager.persist(currentGenre);
	}
	
	public Genre findOneById(int genreId) {
		return entityManager.find(Genre.class, genreId);
	}
	@SuppressWarnings("unchecked")
	public List<Genre> findAll() {
		return entityManager.createQuery(
				"SELECT g FROM Genre g ORDER BY g.name"
			).getResultList();
	}
	
	public void update(Genre currentGenre) {
		entityManager.merge(currentGenre);
		entityManager.flush();
	}
	
	public void delete(int genreId) {
		Genre currentGenre = findOneById(genreId);
		entityManager.remove(currentGenre);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<GenreInvoiceData> findGenreInvoiceData() {
		return entityManager.createQuery(
			"SELECT new chinook.domain.GenreInvoiceData( g.name, SUM(il.unitPrice * il.quantity) As InvoiceTotal) "
			+ " FROM InvoiceLine il, IN (il.track) t, IN (t.genre) g "
			+ " GROUP BY g.name "
			+ " ORDER BY g.name "
			).getResultList();
	}
}
