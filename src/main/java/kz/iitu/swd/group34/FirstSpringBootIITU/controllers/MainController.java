package kz.iitu.swd.group34.FirstSpringBootIITU.controllers;

import kz.iitu.swd.group34.FirstSpringBootIITU.entities.Items;
import kz.iitu.swd.group34.FirstSpringBootIITU.entities.Users;
import kz.iitu.swd.group34.FirstSpringBootIITU.repositories.ItemsRepository;
import kz.iitu.swd.group34.FirstSpringBootIITU.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private ItemsRepository itemsRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/")
    public String index(ModelMap model){
        List<Items> items = itemsRepository.findAllByPriceGreaterThan(100);
        model.addAttribute("itemler", items);
        return "index";
    }

    @PostMapping(value = "/add")
    public String add(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "price") int price
    ){

        Items item = new Items(null, name, price);
        itemsRepository.save(item);

        return "redirect:/";
    }

    @GetMapping(path = "/details/{id}")
    public String details(ModelMap model, @PathVariable(name = "id") Long id){

        Optional<Items> item = itemsRepository.findById(id);
        model.addAttribute("item", item.orElse(new Items(0L, "No Name", 0)));

        return "details";
    }

    @GetMapping(path = "/login")
    public String loginPage(Model model){

        return "login";

    }

    @GetMapping(path = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profilePage(Model model){

        model.addAttribute("user", getUserData());

        return "profile";
    }

    public Users getUserData(){
        Users userData = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            User secUser = (User)authentication.getPrincipal();
            userData = userRepository.findByEmail(secUser.getUsername());
        }
        return userData;
    }

}