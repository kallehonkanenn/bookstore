package hh.projekti.bookstore.repository;

import org.springframework.data.repository.CrudRepository;

import hh.projekti.bookstore.domain.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
}