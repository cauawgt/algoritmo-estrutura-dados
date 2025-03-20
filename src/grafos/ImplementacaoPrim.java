package grafos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ImplementacaoPrim {
    public static void main(String[] args) {

        File fileIn = null;
        GrafoQ2 grafo = null;
        Scanner input = new Scanner(System.in);
        System.out.println("--- ALGORITMO DE PRIM ---");
        System.out.println("Informe o caminho (exemplo: /home/usuario/documentos/entrada.txt):");
        System.out.print(">> ");
        String caminho = input.nextLine();
        fileIn = new File(caminho);

        if (fileIn == null) {
            System.out.println("Inválido.");
            return;
        }

        try (Scanner scanner = new Scanner(fileIn)) {
            if (scanner.hasNextInt()) {
                int numVertices = scanner.nextInt();
                grafo = new GrafoQ2(numVertices);
            } else {
                System.out.println("Erro: Arquivo vazio ou formato inválido.");
                return;
            }

            for (int i = 0; i < grafo.quantidadeVertices; i++) {
                for (int j = 0; j < grafo.quantidadeVertices; j++) {
                    if (scanner.hasNextInt()) {
                        int peso = scanner.nextInt();
                        grafo.adicionarAresta(i, j, peso);
                    } else {
                        System.out.println("Erro: Dados insuficientes na matriz.");
                        return;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
            return;
        }

        grafo.exibirGrafo();
        System.out.print("\nInforme o vértice inicial para executar o algoritmo de Prim: ");
        int verticeInicial = input.nextInt();
        System.out.println("\nExecutando Prim a partir do vértice " + verticeInicial);
        PrimMatrizBasica.encontrarPrimMST(grafo, verticeInicial);
    }
}

class GrafoQ2 {
    final int[][] matrizAdjacencia;
    final int quantidadeVertices;

    public GrafoQ2(int quantidadeVertices) {
        this.quantidadeVertices = quantidadeVertices;
        this.matrizAdjacencia = new int[quantidadeVertices][quantidadeVertices];
    }

    void adicionarAresta(int i, int j, int peso) {
        matrizAdjacencia[i][j] = peso;
        if (i != j) {
            matrizAdjacencia[j][i] = peso;
        }
    }

    void exibirGrafo() {
        System.out.println("Matriz de Adjacência:");
        for (int i = 0; i < quantidadeVertices; i++) {
            for (int j = 0; j < quantidadeVertices; j++) {
                System.out.printf("%2d ", matrizAdjacencia[i][j]);
            }
            System.out.println();
        }
    }
}

class PrimMatrizBasica {
    static final int INF = 2147483647;

    static void encontrarPrimMST(GrafoQ2 grafo, int r) {
        int n = grafo.quantidadeVertices;
        int[][] w = grafo.matrizAdjacencia;

        int[] chave = new int[n];
        int[] pai = new int[n];
        boolean[] noHeap = new boolean[n];

        HeapQ2 filaPrioridade = new HeapQ2(n);

        for (int v = 0; v < n; v++) {
            chave[v] = INF;
            pai[v] = -1;
            noHeap[v] = true;
            filaPrioridade.inserirListaPrioridade(v, INF);
        }

        chave[r] = 0;
        filaPrioridade.atualizarChave(r, 0);

        while (!filaPrioridade.estaVazio()) {
            int u = filaPrioridade.extrairMinimo();
            noHeap[u] = false;

            for (int v = 0; v < n; v++) {
                if (w[u][v] != 0 && noHeap[v] && w[u][v] < chave[v]) {
                    pai[v] = u;
                    chave[v] = w[u][v];
                    filaPrioridade.atualizarChave(v, chave[v]);
                }
            }
        }

        imprimirMST(pai, w, n, r);
    }

    static void imprimirMST(int[] pai, int[][] w, int n, int r) {
        System.out.println("Árvore Geradora Mínima (MST) começando em " + r + ":");
        int pesoTotal = 0;

        for (int v = 0; v < n; v++) {
            if (v != r && pai[v] != -1) {
                System.out.println(pai[v] + " -- " + v + " (Peso: " + w[pai[v]][v] + ")");
                pesoTotal += w[pai[v]][v];
            }
        }

        System.out.println("Peso Total: " + pesoTotal);
    }
}

class HeapQ2 {
    final int[] posicao;
    final int[] vertices;
    final int[] chaves;
    int tamanho;

    public HeapQ2(int capacidade) {
        vertices = new int[capacidade];
        chaves = new int[capacidade];
        posicao = new int[capacidade];

        for (int i = 0; i < capacidade; i++) {
            posicao[i] = -1;
        }
        tamanho = 0;
    }

    void inserirListaPrioridade(int v, int chave) {
        vertices[tamanho] = v;
        chaves[tamanho] = chave;
        posicao[v] = tamanho;
        diminuirChave(tamanho++);
    }

    void atualizarChave(int v, int novaChave) {
        chaves[posicao[v]] = novaChave;
        diminuirChave(posicao[v]);
    }

    int extrairMinimo() {
        int raiz = vertices[0];
        trocar(0, --tamanho);
        minHeapify(0);
        posicao[raiz] = -1;
        return raiz;
    }

    boolean estaVazio() {
        return tamanho == 0;
    }

    void diminuirChave(int i) {
        while (i > 0 && (chaves[i] < chaves[(i - 1) / 2] ||
                (chaves[i] == chaves[(i - 1) / 2] && vertices[i] < vertices[(i - 1) / 2]))) {
            trocar(i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    void minHeapify(int i) {
        int menor = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < tamanho && (chaves[l] < chaves[menor] ||
                (chaves[l] == chaves[menor] && vertices[l] < vertices[menor]))) {
            menor = l;
        }
        if (r < tamanho && (chaves[r] < chaves[menor] ||
                (chaves[r] == chaves[menor] && vertices[r] < vertices[menor]))) {
            menor = r;
        }

        if (menor != i) {
            trocar(i, menor);
            minHeapify(menor);
        }
    }

    void trocar(int i, int j) {
        int tempVertice = vertices[i];
        int tempChave = chaves[i];

        vertices[i] = vertices[j];
        chaves[i] = chaves[j];

        vertices[j] = tempVertice;
        chaves[j] = tempChave;

        posicao[vertices[i]] = i;
        posicao[vertices[j]] = j;
    }
}

