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
    private UserRepository userRepository;
    private Usuario usuarioLogado;

    private boolean compararSenha(String senha) {
        if (new BCryptPasswordEncoder().matches(senha, usuarioLogado.getSenha()))
            return true;
        return false;
    }

    private boolean verificarSeEmailExiste(String email) {
        if (userRepository.findByEmail(email) != null)
            return true;
        return false;
    }

    @RequestMapping("/perfil")
    public ModelAndView mostrarPerfil(Authentication authentication) {
        ModelAndView model = new ModelAndView("user/perfil");
        if (usuarioLogado == null)
            usuarioLogado = (Usuario) authentication.getPrincipal();

        authentication = new UsernamePasswordAuthenticationToken(usuarioLogado, authentication.getCredentials(),
                authentication.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        model.addObject("usuario", authentication.getPrincipal());
        return model;
    }

    @RequestMapping("/salvar")
    public ModelAndView salvar(@Valid Usuario usuario, String novaSenha, RedirectAttributes redirect,
            HttpServletRequest request) {
        // Cadastro
        if (usuario.getId() == null) {
            if (verificarSeEmailExiste(usuario.getEmail())) {
                redirect.addFlashAttribute("mensagem", "Email já cadastrado!");

                return new ModelAndView("redirect:/cadastro");
            } else {

                usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
                userRepository.save(usuario);
                return new ModelAndView("redirect:/login");
            }
        }

        // Atualizar Cadastro
        if (!usuario.getSenha().equals("")) {
            if (compararSenha(usuario.getSenha())) {

                usuario.setSenha(new BCryptPasswordEncoder().encode(novaSenha));
                usuario.setPontuacaoDoAluno(usuarioLogado.getPontuacaoDoAluno());
                usuarioLogado = usuario;
                userRepository.save(usuario);
                return new ModelAndView("redirect:/perfil");
            } else {

                redirect.addFlashAttribute("mensagem", "Senha Incorreta.");
                return new ModelAndView("redirect:/perfil");
            }
        } else {

            usuario.setSenha(usuarioLogado.getSenha());
            usuario.setPontuacaoDoAluno(usuarioLogado.getPontuacaoDoAluno());
            usuarioLogado = usuario;
            userRepository.save(usuario);
            return new ModelAndView("redirect:/perfil");
        }
    }
}