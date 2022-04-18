package model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
/**
 * Clase per gestionar la persistència de les dades de les persones
 * @author manuel
 *
 */
public class PersonesDAO {

	private EntityManager em;

	public PersonesDAO(EntityManager em) {
		this.em = em;
	}
		
	public List<Persona> getList() {
		//Case sensitive!!!
		return em.createQuery("select p from Persona p", model.Persona.class).getResultList();
	}

	public boolean save(Persona persona){

		EntityTransaction tx = em.getTransaction(); 
		tx.begin();
		
		if(em.find(Persona.class, persona.getId()) != null){
			em.merge(persona);
		}else{
			em.persist(persona); 
		}

		tx.commit();
		return true;
	}

	public boolean delete(Integer id){

		EntityTransaction tx = em.getTransaction(); 
		tx.begin();
		em.remove(em.find(Persona.class, id));
		tx.commit();
		return true;
	}

	public Persona find(Integer id){

		return em.find(Persona.class, id);

	}

	public void showAll(){ 
		Query query = em.createNativeQuery("select * from persona", model.Persona.class);
		@SuppressWarnings("unchecked")
		List<Persona> resultados = query.getResultList();

		for(Persona p : resultados) { 
			p.imprimir();
		} 
	}
}

