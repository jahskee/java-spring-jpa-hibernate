package com.jaizon.spring.jpa2.app;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.jaizon.spring.jpa2.service.PublishingService;
import com.jaizon.spring.jpa2.domain.Book;
import com.jaizon.spring.jpa2.domain.Category;
import com.jaizon.spring.jpa2.domain.Author;

public class SpringJpa2App 
{

	static Logger log = Logger.getLogger(SpringJpa2App.class);
    public static void main( String[] args )
    {

		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:META-INF/spring/app-context-annotation.xml");	
		ctx.refresh();
		
		PublishingService publishingService = ctx.getBean("publishingService", PublishingService.class);		
		
		findAllBooks(publishingService);		
		findAllBooksWithDetail(publishingService);
		findBookById(publishingService);
		Book newBook = insertNewBook(publishingService);
		
		//auto delete book based on mapping in Book.java @ManyToMany(cascade= {CascadeType.REMOVE, ....})
		deleteBookAndAuthor(publishingService, newBook);
		findAllBooksByNativeQuery(publishingService);
	
		ctx.close();		
    }    
    
    /*------ #1 Requirement --------*/
    public static void findAllBooks(PublishingService publishingService) {
    	log.info("\n\n=================== 1. findAllBooks(): without Authors and Categories ======================\n");
		List<Book> allBooks = publishingService.findAllBooks();		
		for(Book book: allBooks){
			log.info(book);
		}
		log.info("===================== end ====================\n");
    }
    
    /*------ #2 Requirement --------*/    
    public static void findAllBooksWithDetail(PublishingService publishingService) {
    	log.info("\n\n=================== 2. findAllBooksWithDetail(): with Authors and Categories ======================\n");
		List<Book> allBooksWithDetail = publishingService.findAllBooksWithDetail();
		displayBooks(allBooksWithDetail);
		log.info("===================== end ====================\n");
    }
    
    /*------ #3 Requirement --------*/
	public static void findBookById(PublishingService publishingService) {
		log.info("\n\n=================== 3. findBookById(String bookId): with Authors and Categories ======================\n");
		int searchBookId = 2;
		Book book = publishingService.findBookById(searchBookId);
		log.info(book);
		log.info("\t"+book.getCategory());
		for(Author author: book.getAuthors()){
			log.info("\t"+author);
		}
		log.info("==================== end =====================\n");			
	}
    
    /*------ #4 Requirement --------*/
	public static Book insertNewBook(PublishingService publishingService) {
		log.info("\n\n=================== 4. Insert new Book ======================\n");
		Book newBook = new Book();		
		
		// create book category
		newBook.setCategory(new Category(1));
		newBook.setTitle("Spring in Action");	
		
		// create list of Authors
		Set<Author> authors = new HashSet<Author>();		
		
		authors.add(new Author(7)); // Predefined author Craig Walls
		newBook.setAuthors(authors);
		
		newBook.setIsbn("161729120X");
		newBook.setPrice(39.99f);
		
		publishingService.save(newBook);		
		// display all books with new book inserted
		List<Book>  allBooksWithDetail = publishingService.findAllBooksWithDetail();
		displayBooks(allBooksWithDetail);	
		
		log.info("\t\t======== Author was also deleted ==========");		
		List<Author> authorList = publishingService.findAllAuthors();
		displayAuthors(authorList);		
		log.info("================= end ========================\n");
		return newBook;
	}
    
	/*------ #5 Requirement --------*/
	public static void deleteBookAndAuthor(PublishingService publishingService, Book book){
		log.info("\n\n=================== 5. Delete Book ======================\n");		
		// Delete the last Inserted book
		publishingService.delete(book);
		// display all books with new book inserted
		List<Book> allBooksWithDetail = publishingService.findAllBooksWithDetail();
		displayBooks(allBooksWithDetail);
		
		log.info("\t\t======== display authors after book delete ==========");		
		List<Author> authorList = publishingService.findAllAuthors();
		displayAuthors(authorList);
		log.info("=================== end ======================\n");		
	}
	
	/*------ #6 Requirement --------*/
	public static void findAllBooksByNativeQuery(PublishingService publishingService){
		log.info("\n\n=================== 6. findAllBooksByNativeQuery(): Native Query without Authors and Categories ======================\n");
		List<Book> allBooksNQ = publishingService.findAllBooksByNativeQuery();
		
		for(Book bookNQ: allBooksNQ){
			log.info(bookNQ);
		}
		log.info("================== end =======================\n");			
	}	
	
	
	    // ----------  Utility Functions ------------------------------
    private static void displayBooks(List<Book> books) {
    	log.info("Display all Books:\n");
    	for(Book book: books){
			/*log.info(book);*/
			log.info(book);			
			log.info("\t"+book.getCategory());
			for(Author author: book.getAuthors()){
				log.info("\t"+author);
			}			
			log.info("\n");
		}
    }

    private static void displayAuthors(List<Author> authors) {
    	log.info("Display all Authors:\n");
    	for(Author author: authors){
			log.info(author);		
		}
    }

    
}
