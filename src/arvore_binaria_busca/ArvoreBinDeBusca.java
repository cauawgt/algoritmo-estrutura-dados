package arvore_binaria_busca;

import java.util.Scanner;

public class ArvoreBinDeBusca {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        boolean parar = false;

        ArvoreBin arvoreBin = new ArvoreBin();

        while (!parar) {
            System.out.println("-----------------------------");
            arvoreBin.percursoEmOrdemRec(arvoreBin.raiz);
            System.out.println("\n-----------------------------");
            System.out.println("MENU");
            System.out.println("1 - Busca");
            System.out.println("2 - Inserir");
            System.out.println("3 - Sucessor");
            System.out.println("4 - Remover");
            System.out.println("5 - Finalizar\n");

            int escolha = input.nextInt();

            switch (escolha) {
                case 1:
                    System.out.print("Informe um valor: ");
                    int valor = input.nextInt();
                    NoArvoreBin saida = arvoreBin.buscarArvoreBinIter(valor, arvoreBin.raiz);
                    if (saida != null) {
                        System.out.println("O Nó " + saida.chave + " foi encontrado.");
                    } else {
                        System.out.println("Valor não encontrado.");
                    }
                    break;
                case 2:
                    System.out.print("Informe um valor: ");
                    valor = input.nextInt();
                    arvoreBin.inserirArvoreBin(new NoArvoreBin(valor), arvoreBin);
                    break;
                case 3:
                    System.out.print("Informe um valor: ");
                    valor = input.nextInt();
                    saida = arvoreBin.encotrarSucessor(new NoArvoreBin(valor));
                    if (saida != null) {
                        System.out.println("O sucessor de " + valor + " é " + saida.chave + ".");
                    } else {
                        System.out.println("Sucessor não encontrado");
                    }
                    break;
                case 4:
                    System.out.print("Informe um valor: ");
                    valor = input.nextInt();
                    arvoreBin.removerArvoreBin(valor, arvoreBin);
                    break;
                case 5:
                    parar = true;
                    break;
            }
            System.out.println(" ");

        }
    }
}

class NoArvoreBin {
    int chave;
    NoArvoreBin pai;
    NoArvoreBin esquerda;
    NoArvoreBin direita;

    NoArvoreBin(int chave) {
        this.chave = chave;
        this.pai = null;
        this.esquerda = null;
        this.direita = null;
    }
}

class ArvoreBin {
    NoArvoreBin raiz;

    ArvoreBin() {
        this.raiz = null;
    }

    NoArvoreBin buscarArvoreBinIter(int x, NoArvoreBin pt) {
        while (pt != null && pt.chave != x) {
            if (x < pt.chave) {
                pt = pt.esquerda;
            } else {
                pt = pt.direita;
            }
        }
        return pt;
    }

    void inserirArvoreBin(NoArvoreBin X, ArvoreBin T) {
        NoArvoreBin pai = null;
        NoArvoreBin pt = T.raiz;

        while (pt != null) {
            pai = pt;
            if (X.chave <= pt.chave) {
                pt = pt.esquerda;
            } else {
                pt = pt.direita;
            }
        }

        X.pai = pai;
        if (T.raiz == null) {
            T.raiz = X;
        } else if (X.chave <= pai.chave) {
            pai.esquerda = X;
        } else {
            pai.direita = X;
        }
    }

    NoArvoreBin encontrarMinimo(NoArvoreBin pt) {
        while (pt.esquerda != null) {
            pt = pt.esquerda;
        }
        return pt;
    }

    NoArvoreBin encotrarSucessor(NoArvoreBin pt) {
        if (pt.direita != null) {
            return encontrarMinimo(pt.direita);
        } else {
            NoArvoreBin pai = pt.pai;
            while (pai != null && pt.chave == pai.direita.chave) {
                pt = pai;
                pai = pai.pai;
            }
            return pai;
        }
    }

    NoArvoreBin removerArvoreBin(int x, ArvoreBin T) {
        NoArvoreBin pt = buscarArvoreBinIter(x, T.raiz);

        if (pt == null) {
            System.out.println("Nó " + x + " Inexistente!");
        } else {
            if (pt.esquerda == null && pt.direita == null) {
                removerSemFilhos(pt, T);
            } else if (pt.esquerda != null && pt.direita != null) {
                removerDoisFilhos(pt, T);
            } else {
                removerUnicoFilho(pt, T);
            }
        }

        return pt;
    }

    void removerSemFilhos(NoArvoreBin pt, ArvoreBin T) {
        NoArvoreBin pai = pt.pai;
        if (pai != null) {
            if (pai.chave >= pt.chave) {
                pai.esquerda = null;
            } else {
                pai.direita = null;
            }
        } else {
            T.raiz = null;
        }
        pt.pai = null;
    }

    void removerUnicoFilho(NoArvoreBin pt, ArvoreBin T) {
        NoArvoreBin pai = pt.pai;
        NoArvoreBin filho = null;
        if (pt.esquerda != null) {
            filho = pt.esquerda;
            pt.esquerda = null;
        } else {
            filho = pt.direita;
            pt.direita = null;
        }

        if (pai != null) {
            filho.pai = pai;
            if (filho.chave <= pai.chave) {
                pai.esquerda = filho;
            } else {
                pai.direita = filho;
            }
        } else {
            T.raiz = filho;
        }
        pt.pai = null;
    }

    void removerDoisFilhos(NoArvoreBin pt, ArvoreBin T) {
        NoArvoreBin sucessor = encotrarSucessor(pt);
        sucessor = removerArvoreBin(sucessor.chave, T);

        sucessor.pai = pt.pai;
        pt.pai = null;

        sucessor.esquerda = pt.esquerda;
        pt.esquerda = null;

        sucessor.direita = pt.direita;
        pt.direita = null;

        NoArvoreBin pai = sucessor.pai;

        if (pai != null) {
            if (pai.chave >= sucessor.chave) {
                pai.esquerda = sucessor;
            } else {
                pai.direita = sucessor;
            }
        } else {
            T.raiz = sucessor;
        }
        sucessor.esquerda.pai = sucessor;
        if (sucessor.direita != null) {
            sucessor.direita.pai = sucessor;
        }
    }

    void percursoEmOrdemRec(NoArvoreBin pt) {
        if (pt == null) {
            System.out.print("Vazio");
            return;
        }
        if (pt.esquerda != null) {
            percursoEmOrdemRec(pt.esquerda);
        }
        System.out.print(pt.chave + " ");
        if (pt.direita != null) {
            percursoEmOrdemRec(pt.direita);
        }
    }
}
