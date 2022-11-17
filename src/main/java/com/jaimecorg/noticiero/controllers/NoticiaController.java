package com.jaimecorg.noticiero.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jaimecorg.noticiero.model.Noticia;

@Controller
@RequestMapping("/noticias")
public class NoticiaController {
    
    @GetMapping(value="/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("noticias", getNoticias());
        modelAndView.setViewName("noticias/list");

        return modelAndView;
    }
    

    @GetMapping(path = { "/edit/{codigo}" })
    public ModelAndView edit(
            @PathVariable(name = "codigo", required = true) int codigo) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("noticia", getNoticia(codigo));
        modelAndView.setViewName("noticias/edit");

        return modelAndView;
    }

    @GetMapping(path = { "/create" })
    public ModelAndView create(Noticia noticia) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("noticia", new Noticia());
        modelAndView.setViewName("noticias/new");

        return modelAndView;
    }

    @PostMapping(path = { "/save" })
    public ModelAndView save(Noticia noticia) {

        int round = (int) (Math.random()*(100+5));

        noticia.setCodigo(round);

        List<Noticia> noticias = getNoticias();
        noticias.add(noticia);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("noticias", noticias);
        modelAndView.setViewName("noticias/list");

        return modelAndView;
    }

    @PostMapping(path = { "/update" })
    public ModelAndView update(Noticia noticia) {

        List<Noticia> noticias = getNoticias();

        int indexOf = noticias.indexOf(noticia);

        noticias.set(indexOf, noticia);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("noticias", noticias);
        modelAndView.setViewName("noticias/list");

        return modelAndView;
    }

    @GetMapping(path = { "/delete/{codigo}" })
    public ModelAndView delete(
            @PathVariable(name = "codigo", required = true) int codigo) {

        List<Noticia> noticias = getNoticias();
        noticias.remove(getNoticia(codigo));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("noticias", noticias);
        modelAndView.setViewName("noticias/list");

        return modelAndView;
    }
        

    private Noticia getNoticia(int codigo){
        List<Noticia> noticias = getNoticias();
        int indexOf = noticias.indexOf(new Noticia(codigo));

        return noticias.get(indexOf);

    }

    private List<Noticia> getNoticias() {

        List<Noticia> noticias = new ArrayList<>();

        noticias.add(new Noticia(1, "Titular1", "Descripcion1"));
        noticias.add(new Noticia(2, "Titular2", "Descripcion2"));
        noticias.add(new Noticia(3, "Titular3", "Descripcion3"));
        noticias.add(new Noticia(4, "Titular4", "Descripcion4"));

        return noticias;

    }

    @GetMapping(value = {"/welcome"})
    public String welcome(Model model, HttpSession session){
        model.addAttribute("sessionid", session.getId());
        return "index";
    }
}
