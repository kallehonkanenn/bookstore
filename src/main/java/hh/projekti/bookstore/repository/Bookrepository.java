package hh.projekti.bookstore.repository;

import org.springframework.data.repository.CrudRepository;

import hh.projekti.bookstore.domain.Book;

public interface Bookrepository extends CrudRepository<Book, Long> {
}
