package chinook.service;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import chinook.entity.Customer;

@Stateful
public class CustomerService {

	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	private EntityManager entityManager;
	
	public Customer findOneByCustomerId(int customerId) {
		return entityManager.find(Customer.class, customerId);
	}
	
	@SuppressWarnings("unchecked")
	public List<Customer> findAllOrderByLastnameFirstname() {
		return entityManager.createQuery(
			"SELECT c FROM Customer c ORDER BY c.lastName, c.firstName"
			).getResultList();
	}
}
