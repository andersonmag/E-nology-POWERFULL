package br.edu.ifal.enology.model;

import java.util.Arrays;
import java.util.List;

public class Licao {
    private List<String> palavras;
    private List<String> enunciados;
    private List<String> respostas;
    String[] opcoes;

    public Licao() {
        inicializarListaPalavras();
        inicializarListaEnunciados();
        inicializarListaRespostas();
        opcoes = new String[4];
    }

    private void inicializarListaPalavras() {
        palavras = Arrays.asList("WHILE", "IF", "FOR", "ELSE", "switch-case", "else", "break", "continue", "void",
                "int", "double", "char", "cobol", "assembly", "prolog", "html", "me", "myself", "I", "idk");
    }

    private void inicializarListaEnunciados() {
        enunciados = Arrays.asList("Which of these is a conditional structure?",
                "Which of these is used to stop a repeat loop?",
                "Using your knowledge, which of these is used for floating numbers?",
                "Which of these is not a programming language?", "which of these is the smartest student?");
    }

    private void inicializarListaRespostas() {
        respostas = Arrays.asList("IF", "break", "double", "html");
    }

    public List<String> getEnunciados() {
        return enunciados;
    }

    public String[] getPalavrasPergunta1() {
        for (int i = 0; i < opcoes.length; i++) {
            opcoes[i] = palavras.get(i);
        }
        return opcoes;
    }

    public String[] getPalavrasPergunta2() {
        int j = 4;
        for (int i = 0; i < opcoes.length; i++) {
            opcoes[i] = palavras.get(j);
            j++;
        }
        return opcoes;
    }

    public String[] getPalavrasPergunta3() {
        int j = 8;
        for (int i = 0; i < opcoes.length; i++) {
            opcoes[i] = palavras.get(j);
            j++;
        }
        return opcoes;
    }

    public String[] getPalavrasPergunta4() {
        int j = 12;
        for (int i = 0; i < opcoes.length; i++) {
            opcoes[i] = palavras.get(j);
            j++;
        }
        return opcoes;
    }

    public String[] getPalavrasPergunta5() {
        int j = 16;
        for (int i = 0; i < opcoes.length; i++) {
            opcoes[i] = palavras.get(j);
            j++;
        }
        return opcoes;
    }

    public boolean conferirResposta1(String palavra) {
        return palavra.equals(respostas.get(0));
    }

    public boolean conferirResposta2(String palavra) {
        return palavra.equals(respostas.get(1));
    }

    public boolean conferirResposta3(String palavra) {
        return palavra.equals(respostas.get(2));
    }

    public boolean conferirResposta4(String palavra) {
        return palavra.equals(respostas.get(3));
    }

    public boolean conferirResposta5() {
        return true;
    }
}