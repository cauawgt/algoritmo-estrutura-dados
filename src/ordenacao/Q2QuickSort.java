package ordenacao;

import java.util.Scanner;

public class Q2QuickSort {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int[] vetor;
        int M = 0;

        boolean parar = false;
        while (!parar) {
            boolean verificacaoTamanho = false;
            while (!verificacaoTamanho) {
                System.out.print("TAMANHO DO VETOR: ");
                M = input.nextInt();
                if (M > 0) {
                    verificacaoTamanho = true;
                }
            }

            vetor = new int[M];

            System.out.println();

            for (int i = 0; i < M; i++) {
                System.out.print("ELEMENTO (" + i + ")\n >> ");
                vetor[i] = input.nextInt();
            }

            System.out.println();

            System.out.println("(1) - ORDENAR || (2) - REFAZER: ");
            int decisao = input.nextInt();

            if (decisao == 1) {
                System.out.println("\n--- IMPRIMINDO VETOR ORIGINAL ---");
                for (int i = 0; i < M; i++) {
                    System.out.print(vetor[i] + " ");
                }
                System.out.println("\n--- ORDENANDO VETOR ---");

                OrdenacaoQuicksort ordenacao = new OrdenacaoQuicksort();
                ordenacao.contTrocas = 0;

                System.out.println("Quicksort (Vetor, " + 0 + ", " + (M - 1) + ")");
                vetor = ordenacao.quickSort(vetor, 0, M - 1, M);

                System.out.println("\n--- VETOR ORDENADO ---");
                for (int i = 0; i < M; i++) {
                    System.out.print(vetor[i] + " ");
                }
                System.out.println("\nTrocar foi utilizado: " + ordenacao.contTrocas);

                System.out.println("\n\n(1) - FINALIZAR || (2) - REFAZER: ");
                decisao = input.nextInt();
                if (decisao == 1) {
                    parar = true;
                }
            }

        }


    }
}

class OrdenacaoQuicksort {
    int contTrocas;

    int[] quickSort(int[] nums, int p, int r, int tamanho) {

        if (p < r) {
            int q = particao(nums, p, r, tamanho);

            System.out.println("Quicksort (Vetor, " + p + ", " + (q - 1) + ")");
            quickSort(nums, p, q - 1, tamanho);
            System.out.println("\nQuicksort (Vetor, " + (q + 1) + ", " + r + ")");
            quickSort(nums, q + 1, r, tamanho);

        }

        return nums;
    }

    int particao(int[] nums, int p, int r, int tamanho) {
        int i = p - 1;
        int pivo = nums[r];

        for (int j = p; j < r; j++) {
            if (nums[j] <= pivo) {
                i++;
                trocar(nums, i, j);
            }
        }

        trocar(nums, r, i + 1);

        System.out.print("pós particionamento: ");
        for (int n = 0; n < tamanho; n++) {
            System.out.print(nums[n] + " ");
        }
        System.out.println("\n");

        return i + 1;
    }

    void trocar(int[] nums, int x, int y) {
        contTrocas++;
        int aux = nums[x];
        nums[x] = nums[y];
        nums[y] = aux;
    }
}

