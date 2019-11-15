package br.edu.ifal.enology.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifal.enology.model.Aluno;
import br.edu.ifal.enology.model.Usuario;
import br.edu.ifal.enology.repository.RepositoryUser;

@RestController
public class ControllerUser {

    @Autowired
    RepositoryUser rep;

    @RequestMapping("/cadastro")
    public ModelAndView cadastro() {
        return new ModelAndView("user/cadastro");
    }

    @RequestMapping("/mapa")
    public ModelAndView mostrarMapa() {
        return new ModelAndView("map/mapa.html");
    }

    @RequestMapping("/perfil")
    public ModelAndView mostrarPerfil(@CookieValue(name = "id", defaultValue = "") Long IdCookie) {
        ModelAndView model = new ModelAndView("user/perfil");
        Optional<Usuario> opcao = rep.findById(IdCookie);

        if (opcao.isPresent()) {
            Usuario usuario = opcao.get();
            model.addObject("usuario", usuario);
            return model;
        }

        return model;
    }

    public boolean redefinirSenha(String senha) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        Usuario usuario = validarUsuario(senha);

        if (usuario != null) {

            if (senha.equals(usuario.getSenha())) {

                System.out.println("Olo");
                return true;
            }
        }

        return false;
    }

    @RequestMapping("/salvar")
    public ModelAndView salvar(@Valid Usuario usuario, HttpServletResponse response, String senhaAtual,
                                RedirectAttributes redirect, String novaSenha)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {

        if (senhaAtual != null) {

            senhaAtual = criptografarSenha(senhaAtual);

            if (redefinirSenha(senhaAtual)) {

                usuario.setSenha(novaSenha);
                rep.save(usuario);
            }

            else {

                redirect.addFlashAttribute("usuario", usuario);
                redirect.addFlashAttribute("mensagem", "Senha Invalida.");
                return new ModelAndView("redirect:/editarPerfil");

            }
        }

        usuario.setSenha(criptografarSenha(usuario.getSenha()));
        rep.save(usuario);

        Cookie cookie = new Cookie("id", usuario.getId().toString());
        response.addCookie(cookie);

        return new ModelAndView("redirect:/perfil");
    }

    private String criptografarSenha(String senha) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));

        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {

            hexString.append(String.format("%02X", 0xFF & b));

        }

        return hexString.toString();
    }

    private Usuario validarUsuario(String senha) {

        Usuario usuario = rep.findBySenha(senha);

        if (usuario != null) {

            System.out.println(usuario.getSenha() + " ||| " + senha);
            return usuario;
        }

        else {

            return null;
        }
    }

    @RequestMapping("/editarPerfil")
    public ModelAndView editarPerfil(@CookieValue(name = "id", defaultValue = "") Long IdCookie) {
        ModelAndView model = new ModelAndView("user/editarDadosPerfil");
        Optional<Usuario> opcao = rep.findById(IdCookie);

        if (opcao.isPresent()) {
            Usuario usuario = opcao.get();
            model.addObject("usuario", usuario);

            return model;
        }

        return model;
    }

    @RequestMapping("/licao")
    public ModelAndView licao() {
        return new ModelAndView("task/licao1");
    }

    @RequestMapping("/licao2")
    public ModelAndView licao2() {
        return new ModelAndView("task/licao2.html");
    }

    @RequestMapping("/licao3")
    public ModelAndView licao3() {
        return new ModelAndView("task/licao3.html");
    }
}