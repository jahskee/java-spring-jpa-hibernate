package com.jaizon.spring.jpa2.service;

import java.util.List;

import com.jaizon.spring.jpa2.domain.Author;
import  com.jaizon.spring.jpa2.domain.Book;

public interface PublishingService {
	
	public List<Book> findAllBooks();
	
    public List<Book> findAllBooksWithDetail();    
    
    public Book findBookById(int bookId);
    
    public Book findAuthorById(int authorId);
    
    public Book save(Book book);
  
    public void delete(Book book);
    
    public List<Book> findAllBooksByNativeQuery();

    public List<Author> findAllAuthors();
    
}
