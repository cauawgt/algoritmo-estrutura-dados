package formacao_caracteres;

import java.util.Scanner;

public class FormacaoCaracteres {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        boolean parar = false;
        PilhaSeq x = new PilhaSeq();
        while (!parar) {

            System.out.println("Digite a expressão: ");
            String expressao = input.nextLine();

            if (x.verificandoSeEstaBenFormada(expressao)) {
                System.out.println("Expressão bem formada");
            } else {
                System.out.println("Expressão mal formada");
            }

            System.out.println("Deseja continuar? (s/n)");
            parar = input.nextLine().equals("n");


        }
    }
}

class PilhaSeq {
    int maximo;
    int topo;
    char[] dados;

    public PilhaSeq(int max) {
        maximo = max;
        topo = -1;
        dados = new char[maximo];
    }

    public PilhaSeq() {
    }

    void push(char elem) {
        if (topo == maximo - 1) {
            System.out.println("Overflow");
        } else {
            topo++;
            dados[topo] = elem;
        }
    }

    void pop() {
        if (topo == -1) {
            System.out.println("Underflow");
        } else {
            topo--;
        }
    }

    boolean verificandoComplemento(char a, char b) {
        if (a == '{' && b == '}') {
            return true;
        } else if (a == '(' && b == ')') {
            return true;
        } else if (a == '[' && b == ']') {
            return true;
        } else {
            return false;
        }
    }

    boolean verificandoSeEstaBenFormada(String s) {
        char[] x = s.toCharArray();
        int j = 0;
        for (char c : x) {
            j++;
        }


        PilhaSeq pilha = new PilhaSeq(j);


        for (int i = 0; i < j; i++) {
            if (x[i] == '{' || x[i] == '(' || x[i] == '[') {
                pilha.push(x[i]);
            } else if (x[i] == '}' || x[i] == ')' || x[i] == ']') {
                if (pilha.topo != -1 && verificandoComplemento(pilha.dados[pilha.topo], x[i])) {
                    pilha.pop();
                } else {
                    return false;
                }
            }
        }

        if (pilha.topo > -1) {
            return false;
        }

        return true;

    }


}

