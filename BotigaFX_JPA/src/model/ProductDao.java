package model;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import java.util.Locale.Category;

public class ProductDao{
	
	static Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
	static ResourceBundle texts = ResourceBundle.getBundle("vista.Texts", localitzacioDisplay);
	
	private EntityManager em;

	public ProductDao(EntityManager em) {
		this.em = em;	
	}
	
	
	public List<Product> getList() {
		//Case sensitive!!!
		return em.createQuery("select p from product p", model.Product.class).getResultList();
	}

	public boolean save(Product product){

		EntityTransaction tx = em.getTransaction(); 
		tx.begin();
		
		if(em.find(Product.class, product.getId()) != null){
			em.merge(product);
		}else{
			em.persist(product); 
		}

		tx.commit();
		return true;
	}
	
	public boolean savePack(Pack pack){
		System.out.println("entraaaa");

		EntityTransaction tx = em.getTransaction(); 
		tx.begin();
		
		if(em.find(Pack.class, pack.getId()) != null){
			em.merge(pack);
		}else{
			em.persist(pack); 
		}

		tx.commit();
		return true;
	}

	public boolean delete(Integer id){

		EntityTransaction tx = em.getTransaction(); 
		tx.begin();
		em.remove(em.find(Product.class, id));
		tx.commit();
		return true;
	}
	
	public boolean deletePack(Integer id){

		EntityTransaction tx = em.getTransaction(); 
		tx.begin();
		em.remove(em.find(Pack.class, id));
		tx.commit();
		return true;
	}

	public Product find(Integer id){
		return em.find(Product.class, id);

	}
	
	public Pack findPack(Integer id){
		return em.find(Pack.class, id);

	}

	public void showAll(){ 
		Query query = em.createNativeQuery("select * from product", model.Product.class);
		@SuppressWarnings("unchecked")
		List<Product> resultados = query.getResultList();

		for(Product p : resultados) { 
			p.print();
		} 
		
		Query query2 = em.createNativeQuery("select * from pack", model.Pack.class);
		@SuppressWarnings("unchecked")
		List<Pack> resultados2 = query2.getResultList();

		for(Pack pack : resultados2) { 
			pack.print();
		}
	}
	
	
}
