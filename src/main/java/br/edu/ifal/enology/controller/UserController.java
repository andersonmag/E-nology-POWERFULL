package br.edu.ifal.enology.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.edu.ifal.enology.model.Usuario;
import br.edu.ifal.enology.repository.UserRepository;

@RestController
public class UserController {

    @Autowired
    UserRepository rep;

    @RequestMapping("/cadastro")
    public ModelAndView cadastro() {
        return new ModelAndView("user/cadastro");
    }

    @RequestMapping("/mapa")
    public ModelAndView mostrarMapa() {
        return new ModelAndView("map/mapa.html");
    }

    @RequestMapping("/perfil")
    public ModelAndView perfil(Authentication authentication) {
        ModelAndView model = new ModelAndView("user/perfil");

        Usuario usuario = (Usuario) authentication.getPrincipal();

        if (usuario.equals(null)) {

            usuario = new Usuario();
        }

        model.addObject("usuario", usuario);
        return model;
    }

    public boolean compararSenha(String email, String senha) {

        Usuario usuario = rep.findByEmail(email);

        if (usuario != null) {

            if (new BCryptPasswordEncoder().matches(senha, usuario.getSenha())) {

                return true;
            }
        }

        return false;
    }

    @RequestMapping("/salvar")
    public ModelAndView salvar(@Valid Usuario usuario, String senhaAtual, String novaSenha,
            RedirectAttributes redirect) {

        if (senhaAtual == "") {

            return new ModelAndView("redirect:/perfil");
        }

        if (senhaAtual != null) {

            if (compararSenha(usuario.getEmail(), senhaAtual)) {

                novaSenha = new BCryptPasswordEncoder().encode(novaSenha);

                usuario.setSenha(novaSenha);
                rep.save(usuario);

                return new ModelAndView("redirect:/perfil");
            }

            else {

                redirect.addFlashAttribute("usuario", usuario);
                redirect.addFlashAttribute("mensagem", "Senha Invalida.");
                return new ModelAndView("redirect:/editarPerfil");
            }
        }

        usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
        rep.save(usuario);

        return new ModelAndView("redirect:/login");
    }

    @RequestMapping("/editarPerfil")
    public ModelAndView editarPerfil(Authentication authentication) {
        ModelAndView model = new ModelAndView("user/editarDadosPerfil");

        Usuario usuario = (Usuario) authentication.getPrincipal();

        if (usuario.equals(null)) {

            usuario = new Usuario();
        }

        model.addObject("usuario", usuario);
        return model;
    }
}