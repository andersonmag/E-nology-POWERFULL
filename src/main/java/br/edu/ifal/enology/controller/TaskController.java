package br.edu.ifal.enology.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import br.edu.ifal.enology.model.Licao;

@RestController
public class TaskController {
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

    @RequestMapping("/corrigir/{palavra}")
    public ModelAndView verificar(@PathVariable("palavra") String palavra) {
        ModelAndView pagina;
        if (terminouPergunta1) {
            if (licao.conferirResposta1(palavra)) {
                pagina = new ModelAndView("task/correto");
                terminouPergunta1 = false;
            } else {
                pagina = new ModelAndView("task/errado");
                terminouPergunta1 = false;
            }

        } else if (terminouPergunta2) {
            if (licao.conferirResposta2(palavra)) {
                pagina = new ModelAndView("task/correto");
                terminouPergunta2 = false;
            } else {
                pagina = new ModelAndView("task/errado");
                terminouPergunta2 = false;
            }
        } else if (terminouPergunta3) {
            if (licao.conferirResposta3(palavra)) {
                pagina = new ModelAndView("task/correto");
                terminouPergunta3 = false;
            } else {
                pagina = new ModelAndView("task/errado");
                terminouPergunta3 = false;
            }
        } else if (terminouPergunta4) {
            if (licao.conferirResposta4(palavra)) {
                pagina = new ModelAndView("task/correto");
                terminouPergunta4 = false;
            } else {
                pagina = new ModelAndView("task/errado");
                terminouPergunta4 = false;
            }
        } else if (terminouPergunta5) {
            if (licao.conferirResposta5()) {
                pagina = new ModelAndView("task/correto");
                terminouPergunta4 = false;
            } else {
                pagina = new ModelAndView("task/errado");
                terminouPergunta4 = false;
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

    @RequestMapping("/finalizar")
    public ModelAndView finalizar() {
        i = 0;
        terminouPergunta1 = false;
        terminouPergunta2 = false;
        terminouPergunta3 = false;
        terminouPergunta4 = false;
        return new ModelAndView("redirect:/mapa");
    }

    // @RequestMapping("/licao2")
    // public ModelAndView licao2() {
    //     return new ModelAndView("task/licao2.html");
    // }

    // @RequestMapping("/licao3")
    // public ModelAndView licao3() {
    //     return new ModelAndView("task/licao3.html");
    // }

}