package aplicacao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

import dominio.Pessoa;

public class Programa {

	public static void main(String[] args) {
		
		// null pois o banco incrementa
		Pessoa p1 = new Pessoa(1, "Carlos da Silva","carlos@gmail.com" );
		Pessoa p2 = new Pessoa(2, "Nelio Alves","nelio@gmail.com" );
		Pessoa p3 = new Pessoa(3, "Julia Ferreira", "julia@gmail.com" );
		
		EntityManagerFactory emf= Persistence.createEntityManagerFactory("exemplo-jpa");
		EntityManager em = emf.createEntityManager();
		
		// executa a insercao
		em.getTransaction().begin();
		em.persist(p1);
		em.persist(p2);
		em.persist(p3);
		em.getTransaction().commit();
		
		// não remove
		//Pessoa p= new Pessoa(2, null ,null );
		//em.remove(p);
		// remove 
		em.getTransaction().begin();
		em.remove(em.find(Pessoa.class, 2));
		 //em.remove(p2);
		em.getTransaction().commit();
		
		
		// recupera pessoa por id
		Pessoa p = em.find(Pessoa.class, 1);
		JOptionPane.showMessageDialog(null, p);
		
		em.close();
		emf.close();
		
		
		System.out.println("Pronto!");
	}

}
