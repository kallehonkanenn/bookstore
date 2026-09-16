package hh.projekti.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hh.projekti.bookstore.domain.Book;
import hh.projekti.bookstore.repository.BookRepository;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(BookRepository repository) {
		return (args) -> {
			repository.save(new Book("Tuntematon sotilas", "Väinö Linna", 1954, "9789510430866", 22.50));
			repository.save(new Book("Pikku Prinssi", "Antoine de Saint-Exupéry", 1943, "9789510069851", 15.90));
		};
	}

}