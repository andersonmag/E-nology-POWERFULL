package br.edu.ifal.enology.controller;

import br.edu.ifal.enology.model.Imagem;
import br.edu.ifal.enology.model.RedefinicaoSenha;
import br.edu.ifal.enology.model.Usuario;
import br.edu.ifal.enology.repository.ImagemRepository;
import br.edu.ifal.enology.repository.RedefinicaoSenhaRepository;
import br.edu.ifal.enology.service.EmailService;
import br.edu.ifal.enology.service.ImagemService;
import br.edu.ifal.enology.service.SolucaoService;
import br.edu.ifal.enology.service.UsuarioService;
import org.springframework.scheduling.annotation.Scheduled;
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

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final UsuarioService usuarioService;
    private final ImagemRepository imagemRepository;
    private final EmailService emailService;
    private final RedefinicaoSenhaRepository redefinicaoSenhaRepository;
    private final SolucaoService solucaoService;
    private final ImagemService imagemService;

    public UserController(
            UsuarioService usuarioService, ImagemRepository imagemRepository, EmailService emailService,
            RedefinicaoSenhaRepository redefinicaoSenhaRepository, SolucaoService solucaoService,
            ImagemService imagemService
    ) {
        this.usuarioService = usuarioService;
        this.imagemRepository = imagemRepository;
        this.emailService = emailService;
        this.redefinicaoSenhaRepository = redefinicaoSenhaRepository;
        this.solucaoService = solucaoService;
        this.imagemService = imagemService;
    }

    @RequestMapping("/perfil")
    public ModelAndView mostrarPerfil(Authentication authentication, @AuthenticationPrincipal Usuario usuarioLogado) {
        ModelAndView model = new ModelAndView("user/perfil");

        authentication = pegarNovoAuthentication(authentication, usuarioLogado);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        model.addObject("usuario", authentication.getPrincipal());

        return model;
    }

    private Authentication pegarNovoAuthentication(Authentication authentication, Usuario usuarioLogado) {
        usuarioLogado = usuarioService.findById(usuarioLogado.getId());
        authentication = new UsernamePasswordAuthenticationToken(usuarioLogado, authentication.getCredentials(),
                authentication.getAuthorities());

        return authentication;
    }

    @RequestMapping("/upload")
    public ModelAndView upload(
            @RequestParam(name = "imagem", required = false) MultipartFile file,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) throws IOException {

        if (file != null) {
            usuarioLogado.setImagem(salvarImagem(file, usuarioLogado));
            usuarioService.save(usuarioLogado);
        }

        return new ModelAndView("redirect:/perfil");
    }

    private Imagem salvarImagem(MultipartFile file, Usuario usuarioLogado) throws IOException {

        // Atualização
        if (usuarioLogado.getImagem() != null) {
            usuarioLogado.getImagem().setDados(file.getBytes());
            usuarioLogado.getImagem().setTipo(file.getContentType());
            usuarioLogado.getImagem().setNome(file.getOriginalFilename());
            imagemRepository.save(usuarioLogado.getImagem());

            return usuarioLogado.getImagem();
        }

        return imagemService.salvar(file);
    }

    @RequestMapping("/localCloud/{id}")
    public byte[] retornarImagem(@PathVariable("id") Long idImagem) {
        return imagemRepository.findById(idImagem).orElseThrow(() -> new RuntimeException("Imagem não encontrada!"))
                .getDados();
    }

    @RequestMapping("/salvarUsuario")
    public ModelAndView salvarUsuario(@Valid Usuario usuario, RedirectAttributes redirect) {
        if (usuarioService.existsByEmail(usuario.getEmail())) {
            redirect.addFlashAttribute("mensagem", "Email já cadastrado!");

            return new ModelAndView("redirect:/cadastro");
        } else {
            usuario.setCodigoVerificacao(usuarioService.gerarCodigoAtivacao());
            usuario.setCodigoVerificacao(0);
            usuario.setAtivouConta(true);
            usuario.setFaseAtual(1);
            usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
            emailService.enviarEmailConfirmacao(usuario);
            usuarioService.save(usuario);
            return new ModelAndView("redirect:/login");
        }
    }

    @Scheduled(cron = "0 0 12 1/1 * *")
    public void deletarRedefinicaoSenhaExpirada() {

        List<RedefinicaoSenha> expirados = redefinicaoSenhaRepository.findAll().stream()
                .filter(expirado -> expirado.getTimeout().isBefore(LocalDateTime.now())).collect(Collectors.toList());

        if (!expirados.isEmpty()) {
            redefinicaoSenhaRepository.deleteAll(expirados);
        }
    }

    @RequestMapping("/redefinir-email")
    public ModelAndView redefinirEmail(@RequestParam("tk") String token, @RequestParam("email") String novoEmail) {
        Optional<RedefinicaoSenha> redefinirOptional = redefinicaoSenhaRepository.findByToken(token);

        if (redefinirOptional.isPresent()) {
            Usuario usuario = redefinirOptional.get().getUsuario();
            usuario.setEmail(novoEmail);

            usuarioService.save(usuario);
            redefinicaoSenhaRepository.delete(redefinirOptional.get());
        }

        return new ModelAndView("redirect:/perfil");
    }

    @RequestMapping("/envio-email-redefinir-email")
    public ModelAndView mandarEmailRedefinirEmail(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @RequestParam("email") String novoEmail, RedirectAttributes redirect
    ) {
        RedefinicaoSenha redefinir = new RedefinicaoSenha();
        redefinir.setTimeout(LocalDateTime.now());
        redefinir.setUsuario(usuarioLogado);
        redefinicaoSenhaRepository.save(redefinir);
        usuarioLogado.setEmail(novoEmail);

        emailService.enviarEmailRedefinirEmail(redefinir.getToken(), usuarioLogado);
        redirect.addFlashAttribute("resultado", "E-mail Enviado! Verifique seu e-mail, por favor.");

        return new ModelAndView("redirect:/perfil");
    }

    @RequestMapping("/envio-email-redefinir-senha")
    public ModelAndView mandarEmailRedefinirSenha(
            @AuthenticationPrincipal Usuario usuarioLogado, @RequestParam("email") String email,
            RedirectAttributes redirectAttributes
    ) {
        Usuario usuario = usuarioService.findByEmail(email);
        String resultado = "Este email não está cadastrado!";
        boolean enviouEmail = false;
        ModelAndView model = new ModelAndView("redirect:/login");

        if (Optional.ofNullable(usuarioLogado).isPresent())
            model.setViewName("redirect:/perfil");

        if (Optional.ofNullable(usuario).isPresent()) {
            RedefinicaoSenha redefinicaoSenha = new RedefinicaoSenha();
            redefinicaoSenha.setTimeout(LocalDateTime.now());
            redefinicaoSenha.setUsuario(usuario);
            redefinicaoSenhaRepository.save(redefinicaoSenha);

            enviouEmail = emailService.enviarEmailRedefinirSenha(redefinicaoSenha.getToken(), usuario);
            resultado = "Não foi possivel concluir o envio de e-mail.";

            if (enviouEmail) {
                resultado = "E-mail Enviado! Verifique seu e-mail, por favor.";
            }

            redirectAttributes.addFlashAttribute("teveSucesso", enviouEmail);
            redirectAttributes.addFlashAttribute("resultado", resultado);

            return model;
        }

        redirectAttributes.addFlashAttribute("teveSucesso", enviouEmail);
        redirectAttributes.addFlashAttribute("resultado", resultado);
        return model;
    }

    @RequestMapping("/redefinir-senha")
    public ModelAndView mostrarPaginaRedefinirSenha(@RequestParam(name = "tk", required = false) String tokenUsuario) {
        ModelAndView model = new ModelAndView("user/alteracao-senha");
        Optional<RedefinicaoSenha> recuperarOptional = redefinicaoSenhaRepository.findByToken(tokenUsuario);

        if (recuperarOptional.isPresent() && recuperarOptional.get().getTimeout().isAfter(LocalDateTime.now())) {
            model.addObject("tk", recuperarOptional.get().getToken());
            return model;
        }

        model.setViewName("error");
        model.addObject("msgErro", "O Link ou Recurso que você está buscando não existe, ou pode estar expirado!");
        return model;
    }

    @RequestMapping("/salvar-nova-senha")
    public ModelAndView salvarRedefinicaoSenha(
            @RequestParam("tk") String tokenUsuario,
            @RequestParam("senha") String novaSenha
    ) {
        Optional<RedefinicaoSenha> recuperarOptional = redefinicaoSenhaRepository.findByToken(tokenUsuario);

        if (recuperarOptional.isPresent()) {
            Usuario usuario = recuperarOptional.get().getUsuario();
            usuario.setSenha(new BCryptPasswordEncoder().encode(novaSenha));
            redefinicaoSenhaRepository.delete(recuperarOptional.get());
            usuarioService.save(usuario);

            return new ModelAndView("redirect:/login");
        }

        return new ModelAndView("redirect:/redefinir-senha");
    }

    @RequestMapping("/verificacao-email")
    public ModelAndView verificarEmail() {
        return new ModelAndView("user/verificacao-email");
    }

    @RequestMapping("/ativar-conta/")
    public ModelAndView verificarEmail(int codigoVerificacao, RedirectAttributes redirect) {
        if (usuarioService.verificarCodigo(codigoVerificacao)) {
            usuarioService.ativarConta(codigoVerificacao);

            redirect.addFlashAttribute("mensagem", "Conta ativada com sucesso.");
            return new ModelAndView("redirect:/login");
        }

        redirect.addFlashAttribute("mensagem", "Código Incorreto ou Já Utilizado!");
        return new ModelAndView("redirect:/verificacao-email");
    }

    @RequestMapping("/reenviar-email")
    public ModelAndView reenviarEmail(String email, RedirectAttributes redirect) {
        if (usuarioService.existsByEmail(email)) {
            if (usuarioService.findByEmail(email).getCodigoVerificacao() == 0) {
                redirect.addFlashAttribute("mensagem", "Está conta já está ativada!");
                return new ModelAndView("redirect:/login");
            }

            emailService.enviarEmailConfirmacao(usuarioService.findByEmail(email));
            redirect.addFlashAttribute("mensagem", "Email reenviado!");
        } else {
            redirect.addFlashAttribute("mensagem", "Erro! Este email não está cadastrado!");
        }

        return new ModelAndView("redirect:/verificacao-email");
    }

    @RequestMapping("/atualizar")
    public ModelAndView salvar(
            @Valid Usuario usuario, @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        usuario.setImagem(usuarioLogado.getImagem());
        usuario.setSenha(usuarioLogado.getSenha());
        usuario.setEmail(usuarioLogado.getEmail());
        usuario.setPontuacaoDoAluno(usuarioLogado.getPontuacaoDoAluno());
        usuario.setRoles(usuarioLogado.getRoles());
        usuario.setAtivouConta(usuarioLogado.isAtivouConta());

        usuarioService.save(usuario);
        return new ModelAndView("redirect:/perfil");
    }

    @RequestMapping("/progresso")
    public ModelAndView mostrarProgresso(@AuthenticationPrincipal Usuario usuarioLogado) {
        ModelAndView model = new ModelAndView("user/progresso");

        model.addObject("usuario", usuarioLogado)
                .addObject("quantidadeErros", solucaoService.getQuantidadeRespostasAlunoPorCondicao(usuarioLogado, false))
                .addObject("quantidadeAcertos", solucaoService.getQuantidadeRespostasAlunoPorCondicao(usuarioLogado, true))
                .addObject("quantidadeFasesConcluidas", usuarioLogado.getFaseAtual() - 1);
        return model;
    }

    @RequestMapping("/ranking")
    public ModelAndView mostrarPaginaRanking(@AuthenticationPrincipal Usuario usuarioLogado) {
        ModelAndView model = new ModelAndView("user/ranking");

        model.addObject("usuario", usuarioLogado)
                .addObject("posicaoRankingUsuarioLogado", usuarioService.buscaPosicaoRankingUsuario(usuarioLogado.getId()))
                .addObject("usuarios", usuarioService.buscarTop5UsuariosComMaioresPontuacoes())
                .addObject("media", usuarioService.getMediaPontuacaoUsuarios())
                .addObject("ordem", usuarioService.buscarPosicoesTop5UsuariosComMaioresPontuacoes());
        return model;
    }
}