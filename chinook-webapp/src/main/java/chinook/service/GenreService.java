package chinook.service;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import chinook.domain.GenreInvoiceData;

@Stateful
public class GenreService {

	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	private EntityManager entityManager;
	
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
