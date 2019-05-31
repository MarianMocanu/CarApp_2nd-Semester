package dk.kea.dat18i.spring.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class OwnerController {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private OwnerRepository ownerRepo;

    @GetMapping("/showOwners/{id}")
    public String showOwners(@PathVariable("id") int id, Model model) {
        List<Owner> ownersList = ownerRepo.findAllOwnersByCarId(id);
        model.addAttribute("owners", ownersList);
        return "show-owner";
    }

    @GetMapping("/ownerview")
    public String owner(Model model) {
        List<Owner> ownerList = ownerRepo.findAllOwners();
        model.addAttribute("owners", ownerList);
        return "show-owner";
    }


}
