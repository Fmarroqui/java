package model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class SupplierDao {

	private EntityManager em;

	public SupplierDao(EntityManager em) {
		this.em = em;
	}
		
	public List<Customer> getList() {
		//Case sensitive!!!
		return em.createQuery("select s from Suppier s", model.Customer.class).getResultList();
	}

	public boolean save(Supplier supplier){

		EntityTransaction tx = em.getTransaction(); 
		tx.begin();
		
		if(em.find(Supplier.class, supplier.getId()) != null){
			em.merge(supplier);
		}else{
			em.persist(supplier); 
		}

		tx.commit();
		return true;
	}

	public boolean delete(Integer id){

		EntityTransaction tx = em.getTransaction(); 
		tx.begin();
		em.remove(em.find(Supplier.class, id));
		tx.commit();
		return true;
	}

	public Supplier find(Integer id){

		return em.find(Supplier.class, id);

	}

	public void showAll(){ 
		Query query = em.createNativeQuery("select * from Supplier", model.Supplier.class);
		@SuppressWarnings("unchecked")
		List<Supplier> resultados = query.getResultList();

		for(Supplier s : resultados) { 
			s.imprimir();
		} 
	}
}

