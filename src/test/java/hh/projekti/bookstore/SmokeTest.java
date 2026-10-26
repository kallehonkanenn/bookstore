package hh.projekti.bookstore;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import hh.projekti.bookstore.web.BookController;
import hh.projekti.bookstore.web.BookRestController;
import hh.projekti.bookstore.web.CategoryController;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private BookController bookController;

    @Autowired
    private BookRestController bookRestController;

    @Autowired
    private CategoryController categoryController;

    @Test
    public void contextLoads() {
    }

    @Test
    public void bookControllerIsLoaded() throws Exception {
        assertThat(bookController).isNotNull();
    }

    @Test
    public void bookRestControllerIsLoaded() throws Exception {
        assertThat(bookRestController).isNotNull();
    }

    @Test
    public void categoryControllerIsLoaded() throws Exception {
        assertThat(categoryController).isNotNull();
    }
}