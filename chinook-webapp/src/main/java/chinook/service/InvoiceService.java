package chinook.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import chinook.entity.Invoice;

@Stateful
public class InvoiceService {
	
	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	public Invoice findOneByInvoiceId(int invoiceId) {
		return entityManager.find(Invoice.class, invoiceId);
	}
	
	@SuppressWarnings("unchecked")
	public List<Invoice> findAllByCustomer(int customerId) {
		return entityManager.createQuery(
			"SELECT i FROM Invoice i WHERE i.customer.customerId = :customerIdValue"
			)
			.setParameter("customerIdValue", customerId)
			.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Invoice> findAllByDateRange(Date startDate, Date endDate) {
		return entityManager.createQuery(
				"SELECT i FROM Invoice i WHERE i.invoiceDate BETWEEN :startDateValue AND :endDateValue"
				)
				.setParameter("startDateValue", startDate)
				.setParameter("endDateValue", endDate)
				.getResultList();
	}
	

}
