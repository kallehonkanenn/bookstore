package hh.projekti.bookstore.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import hh.projekti.bookstore.domain.Category;
import hh.projekti.bookstore.repository.CategoryRepository;

@Controller
public class CategoryController {

    private CategoryRepository repository;

    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/categorylist", method = RequestMethod.GET)
    public String categoryList(Model model) {
        model.addAttribute("categories", repository.findAll());
        return "categorylist";
    }

    @RequestMapping(value = "/addcategory", method = RequestMethod.GET)
    public String addCategory(Model model) {
        model.addAttribute("category", new Category());
        return "addcategory";
    }

    @RequestMapping(value = "/savecategory", method = RequestMethod.POST)
    public String saveCategory(Category category) {
        repository.save(category);
        return "redirect:categorylist";
    }
}