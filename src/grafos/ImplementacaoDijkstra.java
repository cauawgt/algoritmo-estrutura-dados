package grafos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ImplementacaoDijkstra {
    public static void main(String[] args) {
        File fileIn = null;
        GrafoQ3 grafo = null;
        Scanner input = new Scanner(System.in);
        System.out.println("--- ALGORITMO DE DIJKSTRA ---");
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
                grafo = new GrafoQ3(numVertices);
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

        // Executa Dijkstra a partir do vértice informado
        System.out.print("Informe o vértice que deseja iniciar: ");
        int verticeEntrada = input.nextInt();
        BuscaPorMenorCaminho busca = new BuscaPorMenorCaminho();
        busca.dijkstra(grafo, new Vertice(verticeEntrada));
    }
}

class Vertice {
    int indice;
    int distancia;
    int pai;

    public Vertice(int indice) {
        this.indice = indice;
        this.distancia = 0;
        this.pai = -1;
    }
}

class GrafoQ3 {
    int[][] matrizAdjacencia;
    Vertice[] vertices;
    int quantidadeVertices;

    public GrafoQ3(int quantidadeVertices) {
        this.quantidadeVertices = quantidadeVertices;
        this.matrizAdjacencia = new int[quantidadeVertices][quantidadeVertices];
        this.vertices = new Vertice[quantidadeVertices];

        for (int i = 0; i < quantidadeVertices; i++) {
            vertices[i] = new Vertice(i);
            for (int j = 0; j < quantidadeVertices; j++) {
                matrizAdjacencia[i][j] = Integer.MAX_VALUE; // Sem aresta (infinito)
            }
        }
    }

    void adicionarAresta(int origem, int destino, int peso) {
        matrizAdjacencia[origem][destino] = peso;
    }

    void exibirGrafo() {
        System.out.println("Matriz de Adjacência (pesos):");
        for (int i = 0; i < quantidadeVertices; i++) {
            for (int j = 0; j < quantidadeVertices; j++) {
                if (matrizAdjacencia[i][j] == Integer.MAX_VALUE) {
                    System.out.print(" -- ");
                } else {
                    System.out.printf("%3d ", matrizAdjacencia[i][j]);
                }
            }
            System.out.println();
        }
    }
}

class BuscaPorMenorCaminho {
    static final int Inf = 2147483647;

    void dijkstra(GrafoQ3 grafo, Vertice s) {
        int n = grafo.quantidadeVertices;
        int[][] w = grafo.matrizAdjacencia;
        boolean[] noHeap = new boolean[n];
        Heap filaPrioridade = new Heap(n);

        for (int u = 0; u < n; u++) {
            grafo.vertices[u].distancia = Inf;
            grafo.vertices[u].pai = -1;
            noHeap[u] = true;
            filaPrioridade.inserir(grafo.vertices[u]);
        }

        grafo.vertices[s.indice].distancia = 0;
        filaPrioridade.atualizarValorChave(s.indice);

        while (!filaPrioridade.estaVazio()) {
            Vertice u = filaPrioridade.extrairMinimo();
            if (u != null) {
                noHeap[u.indice] = false;

                for (int v = 0; v < n; v++) {
                    if (w[u.indice][v] != 0 && noHeap[v] && w[u.indice][v] < grafo.vertices[v].distancia) {
                        relaxar(u, grafo.vertices[v], w[u.indice][v]);
                        filaPrioridade.atualizarValorChave(v);
                    }
                }
            }
        }

        exibirResultado(grafo, s);
    }

    void relaxar(Vertice u, Vertice v, int peso) {
        if (v.distancia > u.distancia + peso) {
            v.distancia = u.distancia + peso;
            v.pai = u.indice;
        }
    }

    void exibirResultado(GrafoQ3 grafo, Vertice s) {
        System.out.println("\nMenor caminho a partir do vértice " + s.indice + ":");
        for (Vertice v : grafo.vertices) {
            System.out.printf("Vértice %d - Distância: %s - Pai: %d\n",
                    v.indice,
                    v.distancia == Integer.MAX_VALUE ? "∞" : v.distancia,
                    v.pai
            );
        }
    }
}

class Heap {
    Vertice[] vertices;
    int tamanho;
    int ultimo;

    public Heap(int tamanho) {
        this.tamanho = tamanho;
        this.vertices = new Vertice[tamanho];
        this.ultimo = -1;
    }

    boolean estaVazio() {
        return ultimo == -1;
    }

    void inserir(Vertice v) {
        if (ultimo < tamanho - 1) {
            ultimo++;
            vertices[ultimo] = v;
            diminuirChave(ultimo);
        } else {
            System.out.println("Overflow");
        }
    }

    void minHeapify(int i) {
        int menor = i;
        int l = 2 * i + 1; // esq
        int r = 2 * i + 2; //  dir

        if (l <= ultimo && (vertices[l].distancia < vertices[menor].distancia ||
                (vertices[l].distancia == vertices[menor].distancia && vertices[l].indice < vertices[menor].indice))) {
            menor = l;
        }
        if (r <= ultimo && (vertices[r].distancia < vertices[menor].distancia ||
                (vertices[r].distancia == vertices[menor].distancia && vertices[r].indice < vertices[menor].indice))) {
            menor = r;
        }

        if (menor != i) {
            trocar(i, menor);
            minHeapify(menor);
        }
    }

    void diminuirChave(int i) {
        while (i > 0 && (vertices[i].distancia < vertices[(i - 1) / 2].distancia ||
                (vertices[i].distancia == vertices[(i - 1) / 2].distancia && vertices[i].indice < vertices[(i - 1) / 2].indice))) {
            trocar(i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    void atualizarValorChave(int indiceVertice) {
        for (int i = 0; i <= ultimo; i++) {
            if (vertices[i].indice == indiceVertice) {
                diminuirChave(i);
                return;
            }
        }
    }

    void trocar(int i, int j) {
        Vertice temp = vertices[i];
        vertices[i] = vertices[j];
        vertices[j] = temp;
    }

    Vertice extrairMinimo() {
        if (ultimo == -1) return null;
        Vertice minVertice = vertices[0];
        trocar(0, ultimo);
        ultimo--;
        minHeapify(0);
        return minVertice;
    }
}
