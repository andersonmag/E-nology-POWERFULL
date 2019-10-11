package br.edu.ifal.enology.controller;

import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import br.edu.ifal.enology.model.Usuario;
import br.edu.ifal.enology.repository.RepositoryUser;

@RestController
public class Controller{

    @Autowired
    RepositoryUser rep;

	@RequestMapping("/")
    public ModelAndView index(){

        return new ModelAndView("index");        
    }

    @RequestMapping("/cadastro")
    public ModelAndView cadastro(){

        return new ModelAndView("user/cadastro");
    }

    @RequestMapping("/mapa")
    public ModelAndView mostrarMapa(){

        return new ModelAndView("map/mapa.html");
    }

    @RequestMapping("/perfil")
    public ModelAndView mostrarPerfil(@CookieValue(name = "id", defaultValue = "") Long IdCookie){
        ModelAndView model = new ModelAndView("user/perfil");
        Optional<Usuario> opcao = rep.findById(IdCookie);

        if(opcao.isPresent()){

            Usuario usuario = opcao.get();
            model.addObject("usuario", usuario);

            return model;
        }

        return model;
    }

    @RequestMapping("/salvar")
    public ModelAndView salvar(@Valid Usuario usuario, HttpServletResponse response){

        rep.save(usuario);
        Cookie cookie = new Cookie("id", usuario.getId().toString());
        response.addCookie(cookie);

        return new ModelAndView("redirect:/perfil");
    }

    @RequestMapping("/editarPerfil")
    public ModelAndView editarPerfil(@CookieValue(name = "id", defaultValue = "") Long IdCookie){
        ModelAndView model = new ModelAndView("user/editarDadosPerfil");
        Optional<Usuario> opcao = rep.findById(IdCookie);

        if(opcao.isPresent()){

            Usuario usuario = opcao.get();
            model.addObject("usuario", usuario);

            return model;
        }

        return model;
    }

    @RequestMapping("/licao")
    public ModelAndView licao(){

        return new ModelAndView("task/licao1");
    }

    @RequestMapping("/licao2")
    public ModelAndView licao2(){

        return new ModelAndView("task/licao2.html");
    }

}