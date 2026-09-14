package hh.projekti.bookstore.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import hh.projekti.bookstore.domain.Book;
import hh.projekti.bookstore.repository.BookRepository;

@Controller
public class BookController {

    private BookRepository repository;

    // constructor injection
    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/booklist", method = RequestMethod.GET)
    public String bookList(Model model) {
        model.addAttribute("books", repository.findAll());
        return "booklist";
    }

    // Show empty form for a new book
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        return "addbook";
    }

    // Show form pre-filled with the book that is being edited
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editBook(@PathVariable("id") Long id, Model model) {
        model.addAttribute("book", repository.findById(id).orElse(new Book()));
        return "addbook";
    }

    // Handles both create (id is null) and update (id is set) because the
    // hidden id field in addbook.html is included in the submitted form
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Book book) {
        repository.save(book);
        return "redirect:booklist";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteBook(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return "redirect:../booklist";
    }
}