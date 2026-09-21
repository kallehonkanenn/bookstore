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

@SpringBootApplication
public class BookstoreApplication {

	private static final Logger logger = LoggerFactory.getLogger(BookstoreApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(BookRepository bookRepository, CategoryRepository categoryRepository) {
		return (args) -> {

			logger.info("Save some sample categories");
			categoryRepository.save(new Category("Fiction"));
			categoryRepository.save(new Category("Classic"));
			categoryRepository.save(new Category("Fantasy"));

			logger.info("Save some sample books");
			bookRepository.save(new Book("Pikku Prinssi", "Antoine de Saint-Exupéry", 1943, "9789510069851", 15.90));
			bookRepository.save(new Book("Tuntematon sotilas", "Väinö Linna", 1954, "9789510430866", 22.50));
			bookRepository
					.save(new Book("Harry Potter ja viisasten kivi", "J.K. Rowling", 1997, "9789513184872", 9.00));

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