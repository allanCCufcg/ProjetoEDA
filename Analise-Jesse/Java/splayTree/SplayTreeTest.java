package splayTree;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/*
    Aluno: Emerson Henrique Sulpino de Araújo
    Email: emerson.henrique.sulpino.araujo@ccc.ufcg.edu.br
    Matrícula: 123210141
    User do GitHub: Emerson349
*/

public class SplayTreeTest {

    // Gera dados aleatórios
    private int[] gerarDadosAleatorios(int tamanho) {
        Random random = new Random();
        int[] dados = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            dados[i] = random.nextInt(tamanho * 10) + 1;
        }
        return dados;
    }

    // Gera dados ordenados
    private int[] gerarDadosOrdenados(int tamanho) {
        int[] dados = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            dados[i] = i + 1;
        }
        return dados;
    }

    // Testa a SplayTree com inserção, busca e remoção
    private double[] testSplayTree(int[] valores) {
        SplayTree arvore = new SplayTree();
        long inicio;

        // Inserção
        inicio = System.nanoTime();
        for (int valor : valores) {
            arvore.insert(valor);
        }
        double tempoInsercao = (System.nanoTime() - inicio) / 1e9;

        // Busca
        inicio = System.nanoTime();
        for (int valor : valores) {
            arvore.search(valor);
        }
        double tempoBusca = (System.nanoTime() - inicio) / 1e9;

        // Remoção
        inicio = System.nanoTime();
        for (int valor : valores) {
            arvore.remove(valor);
        }
        double tempoRemocao = (System.nanoTime() - inicio) / 1e9;

        return new double[]{tempoInsercao, tempoBusca, tempoRemocao};
    }

    // Execução dos testes com entrada do usuário
    private void executarTeste(int[] tamanhos) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Escolha o tipo de dados para o teste:");
        System.out.println("1 - Aleatórios");
        System.out.println("2 - Ordenados");
        int escolha = scanner.nextInt();

        String tipo = (escolha == 1) ? "aleatorio" : "ordenado";
        String nomeArquivo = "dados_" + tipo + "_splay_java.csv";
        StringBuilder csvData = new StringBuilder("Tamanho,Arvore,Operacao,Tempo\n");

        for (int tamanho : tamanhos) {
            int[] dados = (escolha == 1) ? gerarDadosAleatorios(tamanho) : gerarDadosOrdenados(tamanho);

            double[] tempos = testSplayTree(dados);
            csvData.append(tamanho).append(",Splay,Inserção,").append(tempos[0]).append("\n");
            csvData.append(tamanho).append(",Splay,Busca,").append(tempos[1]).append("\n");
            csvData.append(tamanho).append(",Splay,Remoção,").append(tempos[2]).append("\n");

            System.out.println("Tamanho: " + tamanho);
            System.out.printf("Splay Tree - Inserção: %.6fs, Busca: %.6fs, Remoção: %.6fs%n",
                    tempos[0], tempos[1], tempos[2]);

            System.out.println("Pressione Enter para continuar...");
            scanner.nextLine(); // espera Enter
            scanner.nextLine(); // limpa buffer
        }

        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            writer.write(csvData.toString());
            System.out.println("Dados salvos em '" + nomeArquivo + "'.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar o CSV: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SplayTreeTest teste = new SplayTreeTest();
        int[] entradas = {10000, 50000, 100000, 250000, 500000, 1000000};
        teste.executarTeste(entradas);
    }
}
