package lu.dans.insideapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@EntityScan(basePackages = {"lu.dans.inside24.models"})
@ComponentScan(basePackages = {"lu.dans.inside24.services", "lu.dans.inside24.controllers"})
@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
@RestController
public class Inside24Application {

	public static void main(String[] args) {
		SpringApplication.run(Inside24Application.class, args);
	}

	@RequestMapping("/")
	public ModelAndView index(ModelMap model) {
		model.addAttribute("attribute", "forwardWithForwardPrefix");
		return new ModelAndView("forward:/index.html", model);
	}
}
