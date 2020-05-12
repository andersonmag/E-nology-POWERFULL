package br.edu.ifal.enology.controller;

import java.io.IOException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.edu.ifal.enology.model.Imagem;
import br.edu.ifal.enology.model.Usuario;
import br.edu.ifal.enology.repository.ImagemRepository;
import br.edu.ifal.enology.service.UsuarioService;

@RestController
public class UserController {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    ImagemRepository imagemRepository;

    @RequestMapping("/perfil")
    public ModelAndView mostrarPerfil(Authentication authentication, @AuthenticationPrincipal Usuario usuarioLogado) {
        ModelAndView model = new ModelAndView("user/perfil");

        authentication = pegarNovoAuthentication(authentication, usuarioLogado);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        model.addObject("usuario", authentication.getPrincipal());

        return model;
    }

    @RequestMapping("/upload")
    public ModelAndView upload(@RequestParam(name = "imagem", required = false) MultipartFile file,
            @AuthenticationPrincipal Usuario usuarioLogado) throws IOException {

        if (file != null) {
            usuarioLogado.setImagem(salvarImagem(file, usuarioLogado));
            usuarioService.save(usuarioLogado);
        }

        return new ModelAndView("redirect:/perfil");
    }

    @RequestMapping("/localCloud/{link}")
    public byte[] retornarImagem(@PathVariable("link") Long link) {
        return imagemRepository.findByLink(link).getDados();
    }

    @RequestMapping("/salvarUsuario")
    public ModelAndView salvarUsuario(@Valid Usuario usuario, RedirectAttributes redirect) {
        if (verificarSeEmailExiste(usuario.getEmail())) {
            redirect.addFlashAttribute("mensagem", "Email já cadastrado!");

            return new ModelAndView("redirect:/cadastro");
        } else {

            usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
            usuarioService.save(usuario);
            return new ModelAndView("redirect:/login");
        }

    }

    @RequestMapping("/atualizar")
    public ModelAndView salvar(@Valid Usuario usuario, @AuthenticationPrincipal Usuario usuarioLogado, String novaSenha,
            RedirectAttributes redirect) {

        usuario.setImagem(usuarioLogado.getImagem());

        if (!usuario.getSenha().equals("")) {
            if (compararSenha(usuario.getSenha(), usuarioLogado.getSenha())) {
                usuario.setSenha(new BCryptPasswordEncoder().encode(novaSenha));
                usuario.setPontuacaoDoAluno(usuarioLogado.getPontuacaoDoAluno());
                usuarioLogado = usuario;
                usuarioService.save(usuario);

                return new ModelAndView("redirect:/perfil");
            } else {
                redirect.addFlashAttribute("mensagem", "Senha Incorreta.");

                return new ModelAndView("redirect:/perfil");
            }
        } else {

            usuario.setSenha(usuarioLogado.getSenha());
            usuario.setPontuacaoDoAluno(usuarioLogado.getPontuacaoDoAluno());
            usuarioLogado = usuario;
            usuarioService.save(usuario);

            return new ModelAndView("redirect:/perfil");
        }
    }

    private boolean compararSenha(String senha, String senhaUsuarioLogado) {
        if (new BCryptPasswordEncoder().matches(senha, senhaUsuarioLogado))
            return true;
        return false;
    }

    private boolean verificarSeEmailExiste(String email) {
        if (usuarioService.findByEmail(email) != null)
            return true;
        return false;
    }

    private Authentication pegarNovoAuthentication(Authentication authentication, Usuario usuarioLogado) {
        usuarioLogado = usuarioService.findById(usuarioLogado.getId());
        authentication = new UsernamePasswordAuthenticationToken(usuarioLogado, authentication.getCredentials(),
                authentication.getAuthorities());

        return authentication;
    }

    private Imagem salvarImagem(MultipartFile file, Usuario usuarioLogado) throws IOException {

        Long secretPassword = 666 + usuarioLogado.getId();
        Imagem imagem = new Imagem();

        imagem.setDados(file.getBytes());
        imagem.setNome(file.getOriginalFilename());
        imagem.setTipo(file.getContentType());
        imagem.setLink(secretPassword);

        return imagem;
    }
}