package hh.projekti.bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hh.projekti.bookstore.domain.Book;
import hh.projekti.bookstore.domain.Category;
import hh.projekti.bookstore.repository.BookRepository;
import hh.projekti.bookstore.repository.CategoryRepository;
import hh.projekti.bookstore.repository.UserRepository;
import hh.projekti.bookstore.domain.User;

@SpringBootApplication
public class BookstoreApplication {

	private static final Logger logger = LoggerFactory.getLogger(BookstoreApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(BookRepository bookRepository, CategoryRepository categoryRepository,
			UserRepository userRepository) {
		return (args) -> {

			logger.info("Save some sample categories");
			Category fiction = categoryRepository.save(new Category("Fiction"));
			Category classic = categoryRepository.save(new Category("Classic"));
			Category fantasy = categoryRepository.save(new Category("Fantasy"));

			logger.info("Save some sample books");
			Book book1 = new Book("Pikku Prinssi", "Antoine de Saint-Exupéry", 1943, "9789510069851", 15.90);
			book1.setCategory(classic);
			bookRepository.save(book1);

			Book book2 = new Book("Tuntematon sotilas", "Väinö Linna", 1954, "9789510430866", 22.50);
			book2.setCategory(fiction);
			bookRepository.save(book2);

			Book book3 = new Book("Harry Potter ja viisasten kivi", "J.K. Rowling", 1997, "9789513184872", 9.00);
			book3.setCategory(fantasy);
			bookRepository.save(book3);

			logger.info("Save some sample users");
			User user1 = new User("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6",
					"user@example.com", "USER");
			User user2 = new User("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C",
					"admin@example.com", "ADMIN");
			userRepository.save(user1);
			userRepository.save(user2);

			logger.info("Fetch all the categories");
			for (Category c : categoryRepository.findAll()) {
				logger.info(c.toString());
			}

			logger.info("Fetch all the books");
			for (Book b : bookRepository.findAll()) {
				logger.info(b.toString());
			}
		};
	}

}