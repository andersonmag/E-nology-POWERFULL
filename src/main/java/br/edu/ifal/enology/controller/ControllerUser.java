package br.edu.ifal.enology.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    public ModelAndView perfil(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("user/perfil");

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        System.err.println(usuario.getEmail());

        if (usuario.equals(null)) {

            usuario = new Usuario();
        }

        model.addObject("usuario", usuario);
        return model;
    }

    public boolean redefinirSenha(String email, String senha)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {

        Usuario usuario = rep.findByEmailAndSenha(email, senha);

        if (usuario != null) {

            return true;
        }

        return false;
    }

    @RequestMapping("/salvar")
    public ModelAndView salvar(@Valid Usuario usuario, HttpServletResponse response, String senhaAtual,
            RedirectAttributes redirect, String novaSenha, HttpServletRequest request)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {


        if (senhaAtual == "") {

            return new ModelAndView("redirect:/perfil");
        }

        if (senhaAtual != null) {

            senhaAtual = criptografarSenha(senhaAtual);

            if (redefinirSenha(usuario.getEmail(), senhaAtual)) {

                novaSenha = criptografarSenha(novaSenha);
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
        
        usuario.setSenha(criptografarSenha(usuario.getSenha()));
        rep.save(usuario);

        return new ModelAndView("redirect:/login");
    }

    static String criptografarSenha(String senha) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));

        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {

            hexString.append(String.format("%02X", 0xFF & b));

        }

        return hexString.toString();
    }

    @RequestMapping("/editarPerfil")
    public ModelAndView editarPerfil(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("user/editarDadosPerfil");

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

        if (usuario.equals(null)) {

            usuario = new Usuario();
        }

        model.addObject("usuario", usuario);
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