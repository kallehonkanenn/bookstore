
package hh.projekti.bookstore.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.test.context.support.WithMockUser;

import hh.projekti.bookstore.domain.Book;
import hh.projekti.bookstore.domain.Category;
import hh.projekti.bookstore.repository.BookRepository;
import hh.projekti.bookstore.repository.CategoryRepository;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
public class WebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Book saveTestBook() {
        return bookRepository.save(
                new Book("Testikirja", "Testi Kirjailija",
                        2020, "1234567890", 12.50));
    }

    // ---------------------------------------------------------------
    // BookController
    // ---------------------------------------------------------------

    @Test
    public void indexPageLoads() throws Exception {
        mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void booklistShowsBooks() throws Exception {
        saveTestBook();

        mockMvc.perform(get("/booklist"))
                .andExpect(status().isOk())
                .andExpect(view().name("booklist"))
                .andExpect(model().attributeExists("books"))
                .andExpect(content().string(containsString("Testikirja")));
    }

    @Test
    public void addBookFormLoads() throws Exception {
        mockMvc.perform(get("/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addbook"))
                .andExpect(model().attributeExists("book", "categories"));
    }

    @Test
    public void editBookFormLoadsBookData() throws Exception {
        Book book = saveTestBook();

        mockMvc.perform(get("/edit/" + book.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("addbook"))
                .andExpect(model().attribute(
                        "book", hasProperty("title", is("Testikirja"))));
    }

    @Test
    public void saveBookRedirectsToBooklistAndStoresBook() throws Exception {
        mockMvc.perform(post("/save")
                .param("title", "Uusi kirja")
                .param("author", "Uusi Kirjailija")
                .param("publicationYear", "2021")
                .param("isbn", "987654321")
                .param("price", "15.90"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("booklist"));

        assertThat(bookRepository.findByTitle("Uusi kirja"))
                .hasSize(1);
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN" })
    public void deleteBookRedirectsAndRemovesBook() throws Exception {
        Book book = saveTestBook();
        Long bookId = book.getId();

        mockMvc.perform(get("/delete/" + bookId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("../booklist"));

        assertThat(bookRepository.findById(bookId)).isEmpty();
    }

    // ---------------------------------------------------------------
    // CategoryController
    // ---------------------------------------------------------------

    @Test
    public void categorylistShowsCategories() throws Exception {
        categoryRepository.save(new Category("Testikategoria"));

        mockMvc.perform(get("/categorylist"))
                .andExpect(status().isOk())
                .andExpect(view().name("categorylist"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(content().string(containsString("Testikategoria")));
    }

    @Test
    public void addCategoryFormLoads() throws Exception {
        mockMvc.perform(get("/addcategory"))
                .andExpect(status().isOk())
                .andExpect(view().name("addcategory"))
                .andExpect(model().attributeExists("category"));
    }

    @Test
    public void saveCategoryRedirectsAndStoresCategory() throws Exception {
        mockMvc.perform(post("/savecategory")
                .param("name", "Uusi kategoria"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("categorylist"));

        assertThat(categoryRepository.findAll())
                .extracting(Category::getName)
                .contains("Uusi kategoria");
    }

    // ---------------------------------------------------------------
    // BookRestController
    // ---------------------------------------------------------------

    @Test
    public void restBooksReturnsJsonList() throws Exception {
        saveTestBook();

        mockMvc.perform(get("/rest/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(
                        MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("Testikirja")));
    }

    @Test
    public void restBookByIdReturnsJson() throws Exception {
        Book book = saveTestBook();

        mockMvc.perform(get("/rest/books/" + book.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(
                        MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("Testikirja")));
    }
}
