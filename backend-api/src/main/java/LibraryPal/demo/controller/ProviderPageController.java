
package LibraryPal.demo.controller;

import LibraryPal.demo.entity.Coordinator;
import LibraryPal.demo.service.CoordinatorService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Controller
@RequestMapping("/provider")
public class ProviderPageController {

    private final CoordinatorService coordinatorService;

    public ProviderPageController(CoordinatorService coordinatorService) {
        this.coordinatorService = coordinatorService;
    }

    @GetMapping("/profile")
    public String profile(Model model) {

        Coordinator coordinator = coordinatorService
                .getCoordinatorById(2L)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Coordinator not found"));

        model.addAttribute("coordinator", coordinator);
        model.addAttribute("library", null);
        model.addAttribute("totalBooks", 0);
        model.addAttribute("availableBooks", 0);
        model.addAttribute("checkedOutBooks", 0);

        return "provider/profile";
    }
}