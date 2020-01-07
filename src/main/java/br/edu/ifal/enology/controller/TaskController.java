package br.edu.ifal.enology.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifal.enology.model.Licao;
import br.edu.ifal.enology.repository.TarefaRepository;

@RestController
public class TaskController {

    @Autowired
    TarefaRepository tar;

    Licao licao = new Licao();
    boolean terminouPergunta1 = false;
    boolean terminouPergunta2 = false;
    boolean terminouPergunta3 = false;
    boolean terminouPergunta4 = false;
    boolean terminouPergunta5 = false;
    int i = 0;

    @RequestMapping("/licao")
    public ModelAndView licao() {
        ModelAndView pagina = new ModelAndView("task/licao1");

        switch (i) {
        case 0:
            pagina.addObject("enunciado", licao.getEnunciados().get(i));
            pagina.addObject("palavras", licao.getPalavrasPergunta1());
            terminouPergunta1 = true;
            break;
        case 1:
            pagina.addObject("enunciado", licao.getEnunciados().get(i));
            pagina.addObject("palavras", licao.getPalavrasPergunta2());
            terminouPergunta2 = true;
            break;
        case 2:
            pagina.addObject("enunciado", licao.getEnunciados().get(i));
            pagina.addObject("palavras", licao.getPalavrasPergunta3());
            terminouPergunta3 = true;
            break;
        case 3:
            pagina.addObject("enunciado", licao.getEnunciados().get(i));
            pagina.addObject("palavras", licao.getPalavrasPergunta4());
            terminouPergunta4 = true;
            break;
        case 4:
            pagina.addObject("enunciado", licao.getEnunciados().get(i));
            pagina.addObject("palavras", licao.getPalavrasPergunta5());
            terminouPergunta5 = true;
            break;
        }

        if (i > 4) {
            pagina = new ModelAndView("task/final");
            terminouPergunta1 = false;
            terminouPergunta2 = false;
            terminouPergunta3 = false;
            terminouPergunta4 = false;
            i = 0;
        }
        i++;
        return pagina;
    }

    @RequestMapping("/corrigir")
    public ModelAndView verificar(String palavra, RedirectAttributes redirect) {
        ModelAndView pagina;
        System.out.println(palavra);
        if (terminouPergunta1) {
            if (licao.conferirResposta1(palavra)) {
                redirect.addFlashAttribute("respostaC", "Resposta Correta");
                terminouPergunta1 = false;
                return new ModelAndView("redirect:/licao");
            } else {
                redirect.addFlashAttribute("respostaR", "Resposta Errada");
                terminouPergunta1 = false;
                return new ModelAndView("redirect:/licao");
            }

        } else if (terminouPergunta2) {
            if (licao.conferirResposta2(palavra)) {
                redirect.addFlashAttribute("respostaC", "Resposta Correta");
                terminouPergunta2 = false;
                return new ModelAndView("redirect:/licao");
            } else {
                redirect.addFlashAttribute("respostaR", "Resposta Errada");
                terminouPergunta1 = false;
                return new ModelAndView("redirect:/licao");
            }
        } else if (terminouPergunta3) {
            if (licao.conferirResposta3(palavra)) {
                redirect.addFlashAttribute("respostaC", "Resposta Correta");
                terminouPergunta3 = false;
                return new ModelAndView("redirect:/licao");
            } else {
                redirect.addFlashAttribute("respostaR", "Resposta Errada");
                terminouPergunta3 = false;
                return new ModelAndView("redirect:/licao");
            }
        } else if (terminouPergunta4) {
            if (licao.conferirResposta4(palavra)) {
                redirect.addFlashAttribute("respostaC", "Resposta Correta");
                terminouPergunta4 = false;
                return new ModelAndView("redirect:/licao");
            } else {
                redirect.addFlashAttribute("respostaR", "Resposta Errada");
                terminouPergunta4 = false;
                return new ModelAndView("redirect:/licao");
            }
        } else if (terminouPergunta5) {
            if (licao.conferirResposta5()) {
                redirect.addFlashAttribute("respostaC", "Resposta Correta");
                terminouPergunta4 = false;
                return new ModelAndView("redirect:/licao");
            } else {
                redirect.addFlashAttribute("respostaR", "Resposta Errada");
                terminouPergunta4 = false;
                return new ModelAndView("redirect:/licao");
            }
        } else {
            pagina = new ModelAndView("redirect:/mapa");
        }

        return pagina;
    }

    @RequestMapping("/cancelar")
    public ModelAndView cancelar() {
        i = 0;
        terminouPergunta1 = false;
        terminouPergunta2 = false;
        terminouPergunta3 = false;
        terminouPergunta4 = false;
        return new ModelAndView("redirect:/mapa");
    }


    // @RequestMapping("/licao2")
    // public ModelAndView licao2() {
    // return new ModelAndView("task/licao2.html");
    // }

    // @RequestMapping("/licao3")
    // public ModelAndView licao3() {
    // return new ModelAndView("task/licao3.html");
    // }

    // @RequestMapping("/aaa/{palavra}")
    // public String aaaa(@PathVariable("palavra") String palavra){

    // Iterable<Tarefa> tarefas = tar.findByRespostaPortugues(palavra);

    // String rodo = "";
    // for (Tarefa tarefa : tarefas) {

    // rodo += tarefa + " , ";
    // }

    // return rodo;
    // }
}