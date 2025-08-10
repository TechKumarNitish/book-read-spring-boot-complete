package com.example.goodreads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.goodreads.model.Book;

@Repository
public interface BookJpaRepository extends JpaRepository<Book, Integer> {

}