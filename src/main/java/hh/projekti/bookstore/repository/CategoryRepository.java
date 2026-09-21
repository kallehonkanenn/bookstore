package hh.projekti.bookstore.repository;

import org.springframework.data.repository.CrudRepository;

import hh.projekti.bookstore.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}