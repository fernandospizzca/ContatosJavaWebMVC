package com.fernandospizzca.ContatosJavaWebMVC.Controllers;

import com.fernandospizzca.ContatosJavaWebMVC.Entidades.Contato;
import com.fernandospizzca.ContatosJavaWebMVC.Repositorios.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import java.util.Optional;


@Controller
public class ContatoController{

    @Autowired
    private ContactRepository repositorio;

    @GetMapping("/contatos")
    public ModelAndView contatos(){
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("contatos");

        modelAndView.addObject("allContatos", repositorio.findAll());

        return modelAndView;
    }


    @GetMapping("/search")
    public ModelAndView contatoSearch(@RequestParam(value = "nome") final String nome) {

        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("contatos");
        modelAndView.addObject("search", repositorio.findByNomeContainingIgnoreCase(nome));

        return modelAndView;
    }


    @GetMapping("/novo-contato")
    public String createConato(Model model) {

        model.addAttribute("contato", new Contato());

        return "novo-contato";
    }

    @PostMapping("/novo-contato")
    public String create(@Valid @ModelAttribute Contato contato, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()){
            if (contato.getId()== 0){
                return "novo-contato";
            }
            else {
                return "update-contato";
            }
        }
        repositorio.save(contato);
        redirect.addFlashAttribute("message", "Contato adicionado com sucesso");
        return "redirect:contatos";
    }

    @GetMapping("/contatos/{id}/delete")
    public String deleteContato(@PathVariable("id") final Long id, RedirectAttributes redirect) {

        final Optional<Contato> contato = repositorio.findById(id);
        repositorio.delete(contato.get());

        redirect.addFlashAttribute("message", "Contato Deletado.");

        return "redirect:/contatos";
    }

    @GetMapping("update-contato/{id}")
    public ModelAndView updateContatos(@PathVariable("id") final Long id) {

        Optional<Contato> contato = repositorio.findById(id);
        ModelAndView modelAndView = new ModelAndView("update-contato");
        modelAndView.addObject("contato",contato);
        return modelAndView;
    }

    @PostMapping("update-contato")
    public String update(@Valid Contato contato, BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()) {
            attributes.addFlashAttribute("message", "Dados não alterados!");
            return "redirect:/contatos";
        }
        repositorio.save(contato);
        attributes.addFlashAttribute("message", "Dados alterados com sucesso!!");
        return "redirect:/contatos";
    }
}

