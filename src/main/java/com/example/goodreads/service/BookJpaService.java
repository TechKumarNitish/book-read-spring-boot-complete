package com.example.goodreads.service;

// import com.example.goodreads.model.Book;
// import com.example.goodreads.model.Publisher;
import com.example.goodreads.model.*;
// import com.example.goodreads.repository.BookJpaRepository;
// import com.example.goodreads.repository.PublisherJpaRepository;
// import com.example.goodreads.repository.AuthorJpaRepository;
import com.example.goodreads.repository.*;

import com.example.goodreads.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import com.example.goodreads.model.Author;

@Service
public class BookJpaService implements BookRepository {

    @Autowired
    private BookJpaRepository bookJpaRepository;

    @Autowired
    private PublisherJpaRepository publisherJpaRepository;

    @Autowired
    private AuthorJpaRepository authorJpaRepository;

    @Override
    public ArrayList<Book> getBooks() {
        List<Book> bookList = bookJpaRepository.findAll();
        ArrayList<Book> books = new ArrayList<>(bookList);
        return books;
    }

    @Override
    public Book getBookById(int bookId) {
        try {
            Book book = bookJpaRepository.findById(bookId).get();
            return book;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Book addBook(Book book) {
        List<Integer> authorIds = new ArrayList<>();
        for (Author author : book.getAuthors()) {
            authorIds.add(author.getAuthorId());
        }

        Publisher publisher = book.getPublisher();
        int publisherId = publisher.getPublisherId();
        try {

            List<Author> completeAuthors = authorJpaRepository.findAllById(authorIds);
            if (authorIds.size() != completeAuthors.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or more of the author ids are invalid");
            }
            book.setAuthors(completeAuthors);

            publisher = publisherJpaRepository.findById(publisherId).get();
            book.setPublisher(publisher);

            bookJpaRepository.save(book);

            return book;
        } catch (NoSuchElementException e) {// this catch will run when the exception NoSuchElementException is raised
                                            // and this exception will be raised when there is no element db

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong publisherId");
        }
    }

    @Override
    public Book updateBook(int bookId, Book book) {

        try {
            Book newBook = bookJpaRepository.findById(bookId).get();
            if (book.getName() != null) {
                newBook.setName(book.getName());
            }
            if (book.getImageUrl() != null) {
                newBook.setImageUrl(book.getImageUrl());
            }
            if (book.getPublisher() != null) {
                Publisher publisher = book.getPublisher();
                int publisherId = publisher.getPublisherId();
                Publisher newPublisher = publisherJpaRepository.findById(publisherId).get();
                newBook.setPublisher(newPublisher);
            }
            bookJpaRepository.save(newBook);
            return newBook;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteBook(int bookId) {
        try {
            bookJpaRepository.deleteById(bookId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public Publisher getBookPublisher(int bookId) {
        try {
            Book book = bookJpaRepository.findById(bookId).get();
            return book.getPublisher();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Author> getBookAuthors(int bookId) {
        try {
            Book book = bookJpaRepository.findById(bookId).get();
            return book.getAuthors();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}