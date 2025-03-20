package tabelas_hash;

import java.util.Scanner;

public class TabelasHashExemplo {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Informe o tamanho: ");
        int tamanho = input.nextInt();
        TabelaHash tabelaHash = new TabelaHash(tamanho);

        boolean parar = false;
        while (!parar) {
            System.out.println("----------------------------------");
            tabelaHash.imprimir(tabelaHash);
            System.out.println("----------------------------------");
            System.out.print("Informe um valor: ");
            int valor = input.nextInt();
            tabelaHash.inserirTabelaHashOpen(new NoListaDuplaEnc(valor), tabelaHash);
        }
    }
}

class TabelaHash {
    int tamanho;
    ListaDuplaEnc[] tabela;

    TabelaHash(int tamanho) {
        this.tamanho = tamanho;
        this.tabela = new ListaDuplaEnc[tamanho];

    }

    int hashFunction(int x, int tamanho) {
        return x % tamanho;
    }

    int inserirTabelaHashOpen(NoListaDuplaEnc no, TabelaHash H) {
        ListaDuplaEnc X = new ListaDuplaEnc();
        Controle.inserirListaDuplaEnc(no, X);

        int indice = -1;
        int k = hashFunction(X.cabeca.chave, H.tamanho);
        if (H.tabela[k] == null) {
            H.tabela[k] = X;
            indice = k;
        } else {
            int i = (k + 1) % H.tamanho;
            while (i != k) {
                if (H.tabela[i] == null) {
                    H.tabela[i] = X;
                    indice = i;
                    i = k;
                } else {
                    i = (i + 1) % H.tamanho;
                }
            }

            if (indice == -1) {
                inserirTabelaHashClosed(X.cabeca, H);
            }
        }
        return indice;
    }

    void inserirTabelaHashClosed(NoListaDuplaEnc X, TabelaHash H) {
        int k = hashFunction(X.chave, H.tamanho);
        ListaDuplaEnc L = H.tabela[k];
        Controle.inserirListaDuplaEnc(X, L);
    }

    void imprimir(TabelaHash tabelaHash) {
        for (int i = 0; i < tabelaHash.tamanho; i++) {
            if (tabelaHash.tabela[i] != null) {
                System.out.print("{ ");
                NoListaDuplaEnc pt = tabelaHash.tabela[i].cabeca;
                while (pt != null) {
                    System.out.print(pt.chave + " ");
                    pt = pt.proximo;
                }
                System.out.print("} ");
            } else {
                System.out.print("null ");
            }
        }
        System.out.println();
    }
}

class NoListaDuplaEnc {
    int chave;
    NoListaDuplaEnc proximo;
    NoListaDuplaEnc anterior;

    NoListaDuplaEnc(int chave) {
        this.chave = chave;
        this.proximo = null;
        this.anterior = null;
    }
}

class ListaDuplaEnc {
    NoListaDuplaEnc cabeca;

    ListaDuplaEnc() {
        this.cabeca = null;
    }
}

class Controle {
    static void inserirListaDuplaEnc(NoListaDuplaEnc X, ListaDuplaEnc L) {
        X.proximo = L.cabeca;
        if (L.cabeca != null) {
            L.cabeca.anterior = X;
        }
        L.cabeca = X;
    }
}
