package model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class CustomerDao {

	private EntityManager em;

	public CustomerDao(EntityManager em) {
		this.em = em;
	}
		
	public List<Customer> getList() {
		//Case sensitive!!!
		return em.createQuery("select c from Customer c", model.Customer.class).getResultList();
	}

	public boolean save(Customer customer){

		EntityTransaction tx = em.getTransaction(); 
		tx.begin();
		
		if(em.find(Customer.class, customer.getId()) != null){
			em.merge(customer);
		}else{
			em.persist(customer); 
		}

		tx.commit();
		return true;
	}

	public boolean delete(Integer id){

		EntityTransaction tx = em.getTransaction(); 
		tx.begin();
		em.remove(em.find(Customer.class, id));
		tx.commit();
		return true;
	}

	public Customer find(Integer id){

		return em.find(Customer.class, id);

	}

	public void showAll(){ 
		Query query = em.createNativeQuery("select * from customer", model.Customer.class);
		@SuppressWarnings("unchecked")
		List<Customer> resultados = query.getResultList();

		for(Customer c : resultados) { 
			c.imprimir();
		} 
	}
}

