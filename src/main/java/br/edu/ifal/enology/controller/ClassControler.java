package br.edu.ifal.enology.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifal.enology.model.Turma;
import br.edu.ifal.enology.model.Usuario;
import br.edu.ifal.enology.repository.TurmaRepository;
import br.edu.ifal.enology.repository.UserRepository;
import br.edu.ifal.enology.service.TurmaService;
import br.edu.ifal.enology.service.UsuarioService;

@RestController
@RequestMapping("/admin/turmas")
public class ClassControler {

    @Autowired
    TurmaRepository turmaRepository;

    @Autowired
    UsuarioService usuarioService;
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    TurmaService turmaService;

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
    public ModelAndView adicionarAlunosTurma(@PageableDefault(size = 5, page = 0) Pageable pageable, ModelAndView model,
            @AuthenticationPrincipal Usuario usuario, @PathVariable("id") Long id) {
              Turma turmaOptional = turmaService.findById(id);

            model.addObject("alunos", userRepository.findAll(pageable));
            model.addObject("usuario", usuario);
            model.addObject("turma", turmaOptional);
            model.setViewName("add-alunos");

            return model;
        
    }

    @RequestMapping("/{id}/add-alunos/{idAluno}")
    public ModelAndView salvarAlunoTurma(@PathVariable("idAluno") Long idAluno, @PathVariable("id") Long id,
            @PageableDefault(size = 5, page = 0) Pageable pageable, ModelAndView model, RedirectAttributes redirect) {

        Turma turma = turmaRepository.findById(id).get();
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.addAll(turma.getUsuarios());
        usuarios.add(usuarioService.findById(idAluno));

        turma.setUsuarios(usuarios);
        turmaRepository.save(turma);
        model.setViewName("redirect:/admin/turmas/" + id + "/add-alunos");

        return model;
    }

    @RequestMapping("/{id}/remover-alunos/{idAluno}")
    public ModelAndView removerAlunoTurma(@PathVariable("idAluno") Long idAluno, @PathVariable("id") Long id,
            @PageableDefault(size = 5, page = 0) Pageable pageable, ModelAndView model, RedirectAttributes redirect) {

        Turma turma = turmaRepository.findById(id).get();
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.addAll(turma.getUsuarios());
        usuarios.remove(usuarioService.findById(idAluno));

        turma.setUsuarios(usuarios);
        turmaRepository.save(turma);
        model.setViewName("redirect:/admin/turmas/" + id + "/add-alunos");

        return model;
    }

    @RequestMapping("/editar/{id}")
    public ModelAndView editarTurma(@PathVariable("id") Long id, @AuthenticationPrincipal Usuario usuario,
            ModelAndView model) {

        model.setViewName("turma/turma-add");
        model.addObject("turma", turmaRepository.findById(id).get()).addObject("usuario", usuario);
        return model;
    }

    @RequestMapping("/excluir/{id}")
    public ModelAndView excluirTurma(@PathVariable("id") Long id, @AuthenticationPrincipal Usuario usuario,
            ModelAndView model) {

        model.setViewName("redirect:/admin/turmas");
        turmaRepository.deleteById(id);
        return model;
    }

    @RequestMapping("/{id}/alunos")
    public ModelAndView exibirAlunosTurma(@PathVariable("id") Long id, @AuthenticationPrincipal Usuario usuario,
            ModelAndView model) {
                
        model.setViewName("turma/lista-alunos");

        Turma turma = turmaRepository.findById(id).get();
        List<Usuario> alunosTurma = turmaRepository.findById(id).get().getUsuarios();
        List<Usuario> rankingAlunos = usuarioService.getRankingAlunos(alunosTurma);

        model.addObject("usuario", usuario)
            .addObject("turma", turma)
            .addObject("alunos", alunosTurma)
            .addObject("rankingAlunos", rankingAlunos);

        return model;
    }
}