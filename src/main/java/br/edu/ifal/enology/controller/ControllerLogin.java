package br.edu.ifal.enology.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.edu.ifal.enology.model.Usuario;
import br.edu.ifal.enology.repository.RepositoryUser;

@RestController
public class ControllerLogin {

    @Autowired
    RepositoryUser rep;
    ControllerUser control;

    @RequestMapping("/")
    public ModelAndView index() {

        return new ModelAndView("index.html");
    }

    @RequestMapping("/login")
    public ModelAndView login() {

        return new ModelAndView("user/login.html");
    }

    @RequestMapping("/autenticacao")
    public ModelAndView autenticar(String email, String senha, RedirectAttributes redirect, HttpServletRequest request)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {

        senha = control.criptografarSenha(senha);
        Usuario user = rep.findByEmailAndSenha(email, senha);

        if (user != null) {

            request.getSession().setAttribute("usuario", user);
            System.out.println(user.getId());

            return new ModelAndView("redirect:/mapa");
        } else {

            redirect.addFlashAttribute("erroUser", "Email ou Senha Incorreto!");
            return new ModelAndView("redirect:/login");
        }
    }
}
