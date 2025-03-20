package arvores_avl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ArvoreAvlAplicacao {
    public static void main(String[] args) {
        File fileIn = null;
        File fileOut = new File("L6Q1_out.txt");
        if (fileOut.exists()) {
            fileOut.delete();
        }
        Scanner input = new Scanner(System.in);
        System.out.println("--- AVL ---");
        System.out.println("Informe o caminho (exemplo: /home/usuario/documentos/entrada.txt):");
        System.out.print(">> ");
        String caminho = input.nextLine();
        fileIn = new File(caminho);

        if (fileIn == null) {
            System.out.println("Inválido.");
            return;
        }

        try (Scanner scanner = new Scanner(fileIn)) {
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                if (linha.trim().isEmpty()) {
                    continue; // Ignora linhas em branco
                }

                ArvoreAVL arvore = new ArvoreAVL(); // Nova árvore para cada linha

                Scanner linhaScanner = new Scanner(linha);  // Lê números da linha
                while (linhaScanner.hasNextInt()) {
                    int chave = linhaScanner.nextInt();
                    arvore.inserirArvoreAVL(new NoArvoreAVL(chave));
                }

                EscritaFileDeSaida.escrever("");
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Programa finalizado! Verifique a saida no arquivo L6Q1_out.txt");
    }
}


class NoArvoreAVL {
    int chave;
    int altura;
    int fatorBalanceamento;
    NoArvoreAVL pai;
    NoArvoreAVL esquerda;
    NoArvoreAVL direita;

    public NoArvoreAVL(int chave) {
        this.chave = chave;
        this.altura = 1;
        this.pai = null;
        this.esquerda = null;
        this.direita = null;
    }
}

class ArvoreAVL {
    NoArvoreAVL raiz;

    public ArvoreAVL() {
        this.raiz = null;
    }

    void inserirArvoreBin(NoArvoreAVL X) {
        NoArvoreAVL pai = null;
        NoArvoreAVL pt = raiz;

        while (pt != null) {
            pai = pt;
            if (X.chave < pt.chave) {
                pt = pt.esquerda;
            } else {
                pt = pt.direita;
            }
        }

        X.pai = pai;
        if (pai == null) {
            raiz = X;
        } else if (X.chave < pai.chave) {
            pai.esquerda = X;
        } else {
            pai.direita = X;
        }
    }

    void inserirArvoreAVL(NoArvoreAVL X) {
        inserirArvoreBin(X);
        atualizarERebalancear(X);
    }

    void atualizarERebalancear(NoArvoreAVL pt) {
        NoArvoreAVL no = pt;
        boolean balanceada = true;

        while (pt != null) {
            atualizarAlturaEFatorBalanceamento(pt);
            if (pt.fatorBalanceamento > 1 || pt.fatorBalanceamento < -1) {
                balanceada = false;
            }

            pt = pt.pai;
        }

        if (balanceada) {
            EscritaFileDeSaida.escrever("arvore ja balanceada.");
            EscritaFileDeSaida.escrever(percursoEmOrdem(raiz));
            EscritaFileDeSaida.escrever("" + raiz.altura);
            return;
        }

        pt = no;

        while (pt != null) {
            atualizarAlturaEFatorBalanceamento(pt);

            if (pt.fatorBalanceamento > 1) {
                EscritaFileDeSaida.escrever("no responsavel: " + pt.chave);
                EscritaFileDeSaida.escrever(percursoEmOrdem(raiz));
                if (fatorBalanceamento(pt.direita) < 0) {
                    pt.direita = rotacaoDireita(pt.direita);
                    EscritaFileDeSaida.escrever("rotacao esquerda dupla.");
                } else {
                    EscritaFileDeSaida.escrever("rotacao esquerda.");
                }
                pt = rotacaoEsquerda(pt);
            } else if (pt.fatorBalanceamento < -1) {
                EscritaFileDeSaida.escrever("no responsavel: " + pt.chave);
                EscritaFileDeSaida.escrever(percursoEmOrdem(raiz));
                System.out.println(" ");
                if (fatorBalanceamento(pt.esquerda) > 0) {
                    pt.esquerda = rotacaoEsquerda(pt.esquerda);
                    EscritaFileDeSaida.escrever("rotacao direita dupla.");
                } else {
                    EscritaFileDeSaida.escrever("rotacao direita");
                }
                pt = rotacaoDireita(pt);
            }

            pt = pt.pai;
        }
        EscritaFileDeSaida.escrever(percursoEmOrdem(raiz));
        EscritaFileDeSaida.escrever("" + raiz.altura);
    }

    void atualizarAlturaEFatorBalanceamento(NoArvoreAVL pt) {
        int alturaEsquerda = 0;

        if (pt.esquerda != null) {
            alturaEsquerda = pt.esquerda.altura;
        }

        int alturaDireita = 0;
        if (pt.direita != null) {
            alturaDireita = pt.direita.altura;
        }

        pt.altura = maximo(alturaEsquerda, alturaDireita) + 1;
        pt.fatorBalanceamento = alturaDireita - alturaEsquerda;
    }

    int maximo(int alturaEsquerda, int alturaDireita) {
        if (alturaDireita > alturaEsquerda) {
            return alturaDireita;
        }
        return alturaEsquerda;
    }

    int fatorBalanceamento(NoArvoreAVL pt) {
        if (pt == null) {
            return 0;
        }
        int alturaEsquerda = 0;
        if (pt.esquerda != null) {
            alturaEsquerda = pt.esquerda.altura;
        }
        int alturaDireita = 0;
        if (pt.direita != null) {
            alturaDireita = pt.direita.altura;
        }

        return alturaDireita - alturaEsquerda;
    }

    NoArvoreAVL rotacaoEsquerda(NoArvoreAVL pt) {
        NoArvoreAVL novaRaiz = pt.direita;
        pt.direita = novaRaiz.esquerda;

        if (novaRaiz.esquerda != null) {
            novaRaiz.esquerda.pai = pt;
        }

        novaRaiz.pai = pt.pai;

        if (pt.pai == null) {
            raiz = novaRaiz;
        } else if (pt == pt.pai.esquerda) {
            pt.pai.esquerda = novaRaiz;
        } else {
            pt.pai.direita = novaRaiz;
        }

        novaRaiz.esquerda = pt;
        pt.pai = novaRaiz;

        atualizarAlturaEFatorBalanceamento(pt);
        atualizarAlturaEFatorBalanceamento(novaRaiz);

        return novaRaiz;
    }

    NoArvoreAVL rotacaoDireita(NoArvoreAVL pt) {
        NoArvoreAVL novaRaiz = pt.esquerda;
        pt.esquerda = novaRaiz.direita;

        if (novaRaiz.direita != null) {
            novaRaiz.direita.pai = pt;
        }

        novaRaiz.pai = pt.pai;

        if (pt.pai == null) {
            raiz = novaRaiz;
        } else if (pt == pt.pai.direita) {
            pt.pai.direita = novaRaiz;
        } else {
            pt.pai.esquerda = novaRaiz;
        }

        novaRaiz.direita = pt;
        pt.pai = novaRaiz;

        atualizarAlturaEFatorBalanceamento(pt);
        atualizarAlturaEFatorBalanceamento(novaRaiz);

        return novaRaiz;
    }

    String percursoEmOrdem(NoArvoreAVL pt) {
        if (pt == null) {
            return "";
        }

        String resultado = "";


        resultado += percursoEmOrdem(pt.esquerda);

        if (pt.fatorBalanceamento > 0) {
            resultado += pt.chave + ("(+" + pt.fatorBalanceamento + ") ");
        } else {
            resultado += pt.chave + "(" + pt.fatorBalanceamento + ") ";
        }

        resultado += percursoEmOrdem(pt.direita);

        return resultado;
    }
}

class EscritaFileDeSaida {
    static void escrever(String s) {
        File fileOut = new File("L6Q1_out.txt");
        try (FileWriter fw = new FileWriter(fileOut, true)) {
            fw.write(s + "\n");
            fw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

