package hh.projekti.bookstore.repository;

import org.springframework.data.repository.CrudRepository;

import hh.projekti.bookstore.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}