package model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class RegisterPresenceDAO {

	private EntityManager em;

	public RegisterPresenceDAO(EntityManager em) {
		this.em = em;
	}
		
	public List<Presence> getList() {
		//Case sensitive!!!
		return em.createQuery("select pre from Presence pre", model.Presence.class).getResultList();
	}

	public boolean save(Presence presence){

		EntityTransaction tx = em.getTransaction(); 
		tx.begin();
		
		if(em.find(Presence.class, presence.getIdWorker()) != null){
			em.merge(presence);
		}else{
			em.persist(presence); 
		}

		tx.commit();
		return true;
	}

	public boolean delete(Integer id){

		EntityTransaction tx = em.getTransaction(); 
		tx.begin();
		em.remove(em.find(Presence.class, id));
		tx.commit();
		return true;
	}

	public Presence find(Integer id){

		return em.find(Presence.class, id);

	}

	public void showAll(){ 
		Query query = em.createNativeQuery("select * from Presence", model.Presence.class);
		@SuppressWarnings("unchecked")
		List<Presence> resultados = query.getResultList();

		for(Presence pre : resultados) { 
			pre.print();
		} 
	}
}

