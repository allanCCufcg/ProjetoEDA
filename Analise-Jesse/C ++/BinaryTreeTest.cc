#include <iostream>
#include <fstream>
#include <vector>
#include <chrono>
#include <random>
#include <algorithm>
#include <limits>
#include <string>
#include "BinaryTree.cc"

using namespace std;
using namespace std::chrono;

/*
    Aluno: Jessé Oliveira das Chagas
    Email: jesse.oliveira.chagas@ccc.ufcg.edu.br
    Matrícula: 123210473
    Nome de usuário do GitHub: jessechagas
*/

// Função para gerar dados aleatórios
vector<int> gerarDadosAleatorios(int tamanho) {
    vector<int> dados(tamanho);
    for (int i = 0; i < tamanho; i++) {
        dados[i] = rand() % (tamanho * 10) + 1;
    }
    return dados;
}

// Função para gerar dados ordenados
vector<int> gerarDadosOrdenados(int tamanho) {
    vector<int> dados(tamanho);
    for (int i = 0; i < tamanho; i++) {
        dados[i] = i + 1;
    }
    return dados;
}

// Testa a árvore binária e retorna os tempos
vector<double> testBinaryTree(vector<int>& valores) {
    BinaryTree arvore;

    auto inicio = high_resolution_clock::now();
    for (int valor : valores) {
        arvore.root = arvore.insert(valor);
    }
    auto fim = high_resolution_clock::now();
    double tempoInsercao = duration<double>(fim - inicio).count();

    inicio = high_resolution_clock::now();
    for (int valor : valores) {
        arvore.search(valor);
    }
    fim = high_resolution_clock::now();
    double tempoBusca = duration<double>(fim - inicio).count();

    // Remoção em ordem inversa (do último valor para o primeiro)
    inicio = high_resolution_clock::now();
    for (auto it = valores.rbegin(); it != valores.rend(); ++it) {
        arvore.root = arvore.remove(*it);
    }
    fim = high_resolution_clock::now();
    double tempoRemocao = duration<double>(fim - inicio).count();

    return {tempoInsercao, tempoBusca, tempoRemocao};
}

void executarTeste() {
    srand(time(0));
    int escolha;
    cout << "Escolha o tipo de dados para o teste:\n";
    cout << "1 - Aleatórios\n";
    cout << "2 - Ordenados\n";
    cin >> escolha;

    vector<int> entradas;
    string tipoDados, fileName;

    if (escolha == 1) {
        entradas = {10000, 50000, 100000, 250000, 500000, 1000000};
        tipoDados = "aleatorio";
    } else {
        entradas = {1000, 5000, 10000, 25000, 50000, 100000};
        tipoDados = "ordenado";
    }

    fileName = "dados_" + tipoDados + "_binaria_cpp.csv";
    ofstream arquivo(fileName);
    if (!arquivo.is_open()) {
        cerr << "Erro ao abrir o arquivo para escrita.\n";
        return;
    }

    arquivo << "Tamanho,Arvore,Operacao,Tempo\n";

    cin.ignore(); // Limpa o buffer para o getline funcionar corretamente
    for (int tamanho : entradas) {
        vector<int> dados = (escolha == 1) ? gerarDadosAleatorios(tamanho) : gerarDadosOrdenados(tamanho);
        vector<double> tempos = testBinaryTree(dados);

        arquivo << tamanho << ",Binaria,Insercao," << tempos[0] << "\n";
        arquivo << tamanho << ",Binaria,Busca," << tempos[1] << "\n";
        arquivo << tamanho << ",Binaria,Remocao," << tempos[2] << "\n";

        cout << "Tamanho: " << tamanho << "\n";
        printf("Árvore Binária - Inserção: %.6fs, Busca: %.6fs, Remoção: %.6fs\n", tempos[0], tempos[1], tempos[2]);

        cout << "Pressione Enter para continuar com o próximo tamanho...\n";
        cin.ignore();
    }

    arquivo.close();
    cout << "Dados da Árvore Binária salvos em '" << fileName << "'.\n";
}

int main() {
    executarTeste();
    return 0;
}
