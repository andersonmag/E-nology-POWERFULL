package br.edu.ifal.enology.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ModelAndView mostrarMapa(Authentication authentication) {
        ModelAndView model = new ModelAndView("map/mapa.html");
        Usuario usuario = (Usuario) authentication.getPrincipal();

        if (usuario.equals(null)) {

            usuario = new Usuario();
        }

        model.addObject("usuario", usuario);
        return model;
    }

    @RequestMapping("/perfil")
    public ModelAndView perfil(Authentication authentication, HttpServletRequest redirect) {
        ModelAndView model = new ModelAndView("user/perfil");

        Usuario usuario2 = (Usuario) authentication.getPrincipal();
        Usuario usuario = rep.findByEmail(usuario2.getEmail());

        Authentication newAuth = new UsernamePasswordAuthenticationToken(usuario, authentication.getCredentials(),
                authentication.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(newAuth);

        usuario2 = (Usuario) newAuth.getPrincipal();

        model.addObject("usuario", usuario2);
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
    public ModelAndView salvar(@Valid Usuario usuario, String senhaAtual, String novaSenha, RedirectAttributes redirect,
            HttpServletRequest request) {

        if (senhaAtual == "" && novaSenha != "") {

            redirect.addFlashAttribute("mensagem", "Senha Incorreta.");
            return new ModelAndView("redirect:/editarPerfil");
        }

        if (senhaAtual == "") {

            Usuario usuario2 = rep.findByEmail(usuario.getEmail());

            usuario.setSenha(usuario2.getSenha());
            rep.save(usuario);

            return new ModelAndView("redirect:/perfil");
        }

        if (senhaAtual != null) {

            if (compararSenha(usuario.getEmail(), senhaAtual)) {

                if (novaSenha != "") {

                    novaSenha = new BCryptPasswordEncoder().encode(novaSenha);

                    usuario.setSenha(novaSenha);
                    rep.save(usuario);

                    return new ModelAndView("redirect:/perfil");
                }

                else {

                    redirect.addFlashAttribute("mensagem2", "Nova Senha não pode ser vazia!");
                    return new ModelAndView("redirect:/editarPerfil");
                }
            }

            else {

                redirect.addFlashAttribute("mensagem", "Senha Incorreta.");
                return new ModelAndView("redirect:/editarPerfil");
            }
        }

        Usuario usuario2 = rep.findByEmail(usuario.getEmail());

        if (usuario2 != null) {

            redirect.addFlashAttribute("mensagem", "Email já existe!");
            return new ModelAndView("redirect:/cadastro");
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