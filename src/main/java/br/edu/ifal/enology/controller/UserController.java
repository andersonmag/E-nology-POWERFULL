package br.edu.ifal.enology.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.edu.ifal.enology.model.Usuario;
import br.edu.ifal.enology.repository.UserRepository;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AmazonS3 amazonS3;
    @Value("${app.awsServices.bucketName}")
    private String bucketName;
    @Value("${bucket.folder}")
    private String bucketFolder;

    @RequestMapping("/perfil")
    public ModelAndView mostrarPerfil(Authentication authentication, @AuthenticationPrincipal Usuario usuarioLogado,
            HttpServletRequest request) {
        ModelAndView model = new ModelAndView("user/perfil");

        authentication = pegarNovoAuthentication(authentication, usuarioLogado);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        request.getSession().setAttribute("usuarioLogado", authentication.getPrincipal());
        model.addObject("usuario", authentication.getPrincipal());

        return model;
    }

    @RequestMapping("/upload")
    public ModelAndView upload(@RequestParam(name = "imagem", required = false) MultipartFile imagem,
            HttpServletRequest request) {
        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("usuarioLogado");

        if (imagem != null) {
            usuarioLogado.setCaminhoImagem(salvarImagem(imagem, usuarioLogado));
            userRepository.save(usuarioLogado);
        }

        return new ModelAndView("redirect:/perfil");
    }

    @RequestMapping("/salvar")
    public ModelAndView salvar(@Valid Usuario usuario, String novaSenha, RedirectAttributes redirect,
            HttpServletRequest request) {
        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("usuarioLogado");

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
        
        usuario.setCaminhoImagem(usuarioLogado.getCaminhoImagem());
        // Atualizar Cadastro
        if (!usuario.getSenha().equals("")) {
            if (compararSenha(usuario.getSenha(), usuarioLogado.getSenha())) {
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

    private boolean compararSenha(String senha, String senhaUsuarioLogado) {
        if (new BCryptPasswordEncoder().matches(senha, senhaUsuarioLogado))
            return true;
        return false;
    }

    private boolean verificarSeEmailExiste(String email) {
        if (userRepository.findByEmail(email) != null)
            return true;
        return false;
    }

    private Authentication pegarNovoAuthentication(Authentication authentication, Usuario usuarioLogado) {
        Optional<Usuario> opUsuario = userRepository.findById(usuarioLogado.getId());
        usuarioLogado = opUsuario.get();
        authentication = new UsernamePasswordAuthenticationToken(usuarioLogado, authentication.getCredentials(),
                authentication.getAuthorities());

        return authentication;
    }

    private String salvarImagem(MultipartFile imagem, Usuario usuarioLogado) {
        File arquivo = new File("imagem");

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(arquivo);
            fileOutputStream.write(imagem.getBytes());
            fileOutputStream.close();

            amazonS3.putObject(new PutObjectRequest(bucketName, bucketFolder + usuarioLogado.getId() + 665, arquivo)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            e.getMessage();
        }

        S3Object object = amazonS3.getObject(bucketName, bucketFolder + usuarioLogado.getId() + 665);
        return object.getObjectContent().getHttpRequest().getURI().toString();
    }
}