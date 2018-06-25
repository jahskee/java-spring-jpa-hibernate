package com.jaizon.spring.jpa2.service;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaizon.spring.jpa2.domain.Author;
import com.jaizon.spring.jpa2.domain.Book;
import org.apache.log4j.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service("publishingService")
@Repository
@Transactional
public class PublishingServiceImpl implements PublishingService {
	final static String ALL_BOOK_NATIVE_QUERY = "select id, category_id, isbn, title, price_decimal from book";
	
	
	static Logger log = Logger.getLogger(PublishingServiceImpl.class);


	@PersistenceContext
	private EntityManager em;
	
    @SuppressWarnings("unchecked")
    @Override
	@Transactional(readOnly=true)
	public List<Book> findAllBooks() {
    	List<Book> books = em.createNamedQuery("Book.findAll", Book.class).getResultList();
    	return books;
	}

	@Override
	public List<Book> findAllBooksWithDetail() {
		List<Book> books = em.createNamedQuery("Book.findAllWithDetail", Book.class).getResultList();
		return books;
	}

	@Override
	public Book findBookById(int bookId) {
		return (Book) em.createNamedQuery("Book.findById").setParameter("bookId", bookId).getSingleResult();
	}
	
	@Override
	public Book findAuthorById(int authorId) {
		return (Book) em.createNamedQuery("Author.findById").setParameter("authorId", authorId).getSingleResult();
	}
	
	@Override
	public Book save(Book book) {
		
		// primitive integer default value is 0
		if (book.getId() == 0 ) {
			log.info("Inserting new contact");
			em.persist(book);
		} else {
			em.merge(book);
			log.info("bookId: "+book.getId() +", updating existing book.");
		}			 
		log.info("Book saved with id: " + book.getId());		 
		
		return book;
	}

	@Override
	public void delete(Book book) {
		Book mergedBook = em.merge(book);
		em.remove(mergedBook);
		 
		log.info("Contact with id: " + book.getId()
		+ " deleted successfully");	
	}	
	
	@Override
	public List<Book> findAllBooksByNativeQuery() {
		return em.createNativeQuery(ALL_BOOK_NATIVE_QUERY,
				Book.class).getResultList();
	}

	@Override
	public List<Author> findAllAuthors() {
		List<Author> authors = em.createNamedQuery("Author.findAll", Author.class).getResultList();
    	return authors;		
	}	


  

}
