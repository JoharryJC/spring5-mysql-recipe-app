package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /*@GetMapping
    @RequestMapping("/recipe/{id}/show")  teacher changes that for only GetMapping because he was using both and that is not correct*/
    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findById(new Long(id)));
        return "recipe/show";

    }

    /*@GetMapping
    @RequestMapping("recipe/new") teacher changes that for only GetMapping because he was using both and that is not correct*/
    @GetMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    /*@GetMapping
    @RequestMapping("recipe/{id}/update")  teacher changes that for only GetMapping because he was using both and that is not correct*/
    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/recipeform";
    }

    //@RequestMapping(name = "recipe", method = RequestMethod.POST)  // esta es una forma
    @PostMapping
    @RequestMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    /*@GetMapping
    @RequestMapping("recipe/{id}/delete") */
    @GetMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id) {
      log.debug("Deleting id: " + id);

      recipeService.deleteById(Long.valueOf(id));
      return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound() {
        log.error("Handling not found exception");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        return  modelAndView;

    }

}