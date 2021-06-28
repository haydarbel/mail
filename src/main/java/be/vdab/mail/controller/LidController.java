package be.vdab.mail.controller;

import be.vdab.mail.domain.Lid;
import be.vdab.mail.exceptions.KanMailNietZendenException;
import be.vdab.mail.service.LidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("leden")
public class LidController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final LidService lidService;


    public LidController(LidService lidService) {
        this.lidService = lidService;
    }

    @GetMapping("registratieform")
    public ModelAndView registratieForm() {
        return new ModelAndView("registratieform")
                .addObject(new Lid("", "", ""));
    }

    @PostMapping
    public String regstreer(@Valid Lid lid, Errors errors,
                            RedirectAttributes redirect, HttpServletRequest request) {
        if (errors.hasErrors()) {
            return "registratieform";
        }
        try {
            lidService.registreer(lid, request.getRequestURL().toString());
            redirect.addAttribute("id", lid.getId());
            return "redirect:/leden/geregistreerd/{id}";
        } catch (KanMailNietZendenException ex) {
            logger.error("Kan mail niet zenden", ex);
            return "redirect:/leden/nietgeregistreerd";
        }
    }

    @GetMapping("geregistreerd/{id}")
    public String geregistreerd(@PathVariable long id) {
        return "geregistreerd";

    }

    @GetMapping("nietgeregistreerd")
    public String nietGeregistreerd() {
        return "nietgeregistreerd";
    }


}