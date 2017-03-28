package chinook.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.omg.CORBA.DynAnyPackage.Invalid;

import chinook.entity.Invoice;
import chinook.entity.InvoiceLine;
import chinook.exception.IllegalQuantityException;
import chinook.exception.NoInvoiceLinesException;

@Stateful
public class InvoiceService {
	
	@Resource
	private EJBContext context;
	
	public int create(Invoice newInvoice, List<InvoiceLine> invoiceLines) throws NoInvoiceLinesException, IllegalQuantityException {
		int invoiceId = 0;
		if (invoiceLines == null || invoiceLines.size() == 0 ) {
			throw new NoInvoiceLinesException("There no items in the invoice");
		}
		// assign the invoiceDate and total
		Date today = Calendar.getInstance().getTime();
		newInvoice.setInvoiceDate(today);
		// calculate the invoice total
		double invoiceTotal = 0;
		for(InvoiceLine item : invoiceLines ) {
			invoiceTotal += item.getQuantity() * item.getUnitPrice().doubleValue();
		}
		newInvoice.setTotal(new BigDecimal(invoiceTotal) );
		// save the newInvoice entity
		entityManager.persist(newInvoice);
		invoiceId = newInvoice.getInvoiceId();
		
		// iterate through each InvoiceLine and persist the entity
		for (InvoiceLine item : invoiceLines ) {
			if (item.getQuantity() < 1 ) {
				context.setRollbackOnly();
				throw new IllegalQuantityException("Invalid quantity ordered.");
			}
			item.setInvoice(newInvoice);
			entityManager.persist(item);
		}
		
		return invoiceId;
	}
	
	
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
