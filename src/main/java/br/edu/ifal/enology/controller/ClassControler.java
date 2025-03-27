package br.edu.ifal.enology.controller;

import br.edu.ifal.enology.model.Turma;
import br.edu.ifal.enology.model.Usuario;
import br.edu.ifal.enology.repository.TurmaRepository;
import br.edu.ifal.enology.repository.UserRepository;
import br.edu.ifal.enology.service.UsuarioService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/admin/turmas")
public class ClassControler {

    private final TurmaRepository turmaRepository;
    private final UsuarioService usuarioService;
    private UserRepository userRepository;

    public ClassControler(
            TurmaRepository turmaRepository, UsuarioService usuarioService, UserRepository userRepository
    ) {
        this.turmaRepository = turmaRepository;
        this.usuarioService = usuarioService;
        this.userRepository = userRepository;
    }

    @RequestMapping
    public ModelAndView listarTurmas(@AuthenticationPrincipal Usuario usuario, ModelAndView model) {

        model.setViewName("turma/lista-turmas");
        model.addObject("usuario", usuario);
        model.addObject("turmas", turmaRepository.findAll());
        model.addObject("alunos", usuarioService.buscarSomenteAlunos());

        return model;
    }

    @RequestMapping(value = "/criar", method = RequestMethod.POST)
    public ModelAndView salvarTurma(Turma turma, ModelAndView model) {
        turmaRepository.save(turma);
        model.setViewName("redirect:/admin/turmas");

        return model;
    }

    @RequestMapping("/criar")
    public ModelAndView criarTurma(@AuthenticationPrincipal Usuario usuario, ModelAndView model) {

        model.setViewName("turma/turma-add");
        model.addObject("usuario", usuario).addObject("turma", new Turma());
        return model;
    }

    @RequestMapping("/{id}/add-alunos")
    public ModelAndView adicionarAlunosTurma(
            @PageableDefault(size = 5) Pageable pageable, ModelAndView model,
            @AuthenticationPrincipal Usuario usuario, @PathVariable("id") Long id
    ) {
        Turma turma = validarTurmaExistentePorId(id);

        model.addObject("alunos", userRepository.findAll(pageable));
        model.addObject("usuario", usuario);
        model.addObject("turma", turma);
        model.setViewName("add-alunos");

        return model;
    }

    private Turma validarTurmaExistentePorId(Long id) {
        return turmaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Classe não encontrada!"));
    }

    @RequestMapping("/{id}/add-alunos/{idAluno}")
    public ModelAndView salvarAlunoTurma(
            @PathVariable("idAluno") Long idAluno, @PathVariable("id") Long id,
            ModelAndView model
    ) {
        Turma turma = validarTurmaExistentePorId(id);
        turma.getUsuarios().add(usuarioService.findById(idAluno));
        turmaRepository.save(turma);

        model.setViewName("redirect:/admin/turmas/" + id + "/add-alunos");
        return model;
    }

    @RequestMapping("/{id}/remover-alunos/{idAluno}")
    public ModelAndView removerAlunoTurma(
            @PathVariable("idAluno") Long idAluno, @PathVariable("id") Long id, ModelAndView model
    ) {
        Turma turma = validarTurmaExistentePorId(id);

        turma.getUsuarios().removeIf(usuario -> usuario.getId().equals(idAluno));
        turmaRepository.save(turma);

        model.setViewName("redirect:/admin/turmas/" + id + "/add-alunos");
        return model;
    }

    @RequestMapping("/editar/{id}")
    public ModelAndView editarTurma(
            @PathVariable("id") Long id, @AuthenticationPrincipal Usuario usuario,
            ModelAndView model
    ) {
        Turma turma = validarTurmaExistentePorId(id);

        model.setViewName("turma/turma-add");
        model.addObject("turma", turma)
                .addObject("usuario", usuario);
        return model;
    }

    @RequestMapping("/excluir/{id}")
    public ModelAndView excluirTurma(
            @PathVariable("id") Long id, @AuthenticationPrincipal Usuario usuario,
            ModelAndView model
    ) {
        Turma turma = validarTurmaExistentePorId(id);
        turmaRepository.deleteById(turma.getId());

        model.setViewName("redirect:/admin/turmas");
        return model;
    }

    @RequestMapping("/{id}/alunos")
    public ModelAndView exibirAlunosTurma(
            @PathVariable("id") Long id, @AuthenticationPrincipal Usuario usuario,
            ModelAndView model
    ) {
        Turma turma = validarTurmaExistentePorId(id);
        List<Usuario> rankingAlunos = usuarioService.getRankingAlunos(turma.getUsuarios());

        model.setViewName("turma/lista-alunos");
        model.addObject("usuario", usuario)
                .addObject("turma", turma)
                .addObject("alunos", turma.getUsuarios())
                .addObject("rankingAlunos", rankingAlunos);
        return model;
    }
}