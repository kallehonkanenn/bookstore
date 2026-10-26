package hh.projekti.bookstore.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
// ---- Spring Boot 4.x (pom.xml:n parent-versio 4.x) ----
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import hh.projekti.bookstore.domain.Book;
import hh.projekti.bookstore.domain.Category;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    // ---------- CREATE ----------

    @Test
    public void createNewBook() {
        Category category = categoryRepository.save(new Category("Testikategoria"));
        Book book = new Book("Testikirja", "Testi Kirjailija", 2020, "1234567890", 12.50);
        book.setCategory(category);

        repository.save(book);

        assertThat(book.getId()).isNotNull();
        assertThat(repository.findById(book.getId())).isPresent();
    }

    // ---------- SEARCH ----------

    @Test
    public void findByTitleShouldReturnBook() {
        repository.save(new Book("Hakutestikirja", "Matti Meikäläinen", 2001, "111", 9.90));

        List<Book> books = repository.findByTitle("Hakutestikirja");

        assertThat(books).hasSize(1);
        assertThat(books.get(0).getAuthor()).isEqualTo("Matti Meikäläinen");
    }

    @Test
    public void findByAuthorShouldReturnAllBooksOfAuthor() {
        repository.save(new Book("Ensimmäinen testikirja", "Uniikki Kirjailija", 2001, "111", 9.90));
        repository.save(new Book("Toinen testikirja", "Uniikki Kirjailija", 2005, "222", 14.90));
        repository.save(new Book("Muun kirjailijan kirja", "Joku Toinen", 2010, "333", 19.90));

        List<Book> books = repository.findByAuthor("Uniikki Kirjailija");

        assertThat(books).hasSize(2);
        assertThat(books).extracting(Book::getTitle)
                .containsExactlyInAnyOrder("Ensimmäinen testikirja", "Toinen testikirja");
    }

    @Test
    public void findByTitleShouldReturnEmptyListWhenBookDoesNotExist() {
        assertThat(repository.findByTitle("Kirjaa ei ole olemassa")).isEmpty();
    }

    // ---------- DELETE ----------

    @Test
    public void deleteBook() {
        long countBefore = repository.count();
        Book book = repository.save(new Book("Poistettava kirja", "Poisto Kirjailija", 1999, "999", 5.00));
        assertThat(repository.count()).isEqualTo(countBefore + 1);

        repository.deleteById(book.getId());

        assertThat(repository.findById(book.getId())).isEmpty();
        assertThat(repository.count()).isEqualTo(countBefore);
    }
}