#include <iostream>
#include <fstream>
#include <vector>
#include <chrono>
#include <random>
#include <string>
#include <limits>
#include <algorithm> // Necessário para std::shuffle
#include "AVLTree.cc" // Supondo que a AVL está em um arquivo separado

using namespace std;
using namespace chrono;

/*
    Aluno: Jessé Oliveira das Chagas
    Email: jesse.oliveira.chagas@ccc.ufcg.edu.br
    Matrícula: 123210473
    Nome de usuário do GitHub: jessechagas
*/

// Gera dados aleatórios para a árvore AVL
vector<int> gerarDadosAleatorios(int tamanho) {
    vector<int> dados(tamanho);
    iota(dados.begin(), dados.end(), 1); // Preenche com 1 até tamanho
    random_device rd;
    mt19937 gen(rd());
    shuffle(dados.begin(), dados.end(), gen); // Embaralha
    return dados;
}


// Gera dados ordenados para a árvore AVL
vector<int> gerarDadosOrdenados(int tamanho) {
    vector<int> dados(tamanho);
    for (int i = 0; i < tamanho; ++i) dados[i] = i + 1;
    return dados;
}

// Testa a árvore AVL com inserção, busca e remoção
tuple<double, double, double> testarAVL(const vector<int>& valores) {
    AVLTree arvore;

    auto inicio = high_resolution_clock::now();
    for (int v : valores) arvore.root = arvore.insert(v);
    double tempoInsercao = duration<double>(high_resolution_clock::now() - inicio).count();

    inicio = high_resolution_clock::now();
    for (int v : valores) arvore.search(v);
    double tempoBusca = duration<double>(high_resolution_clock::now() - inicio).count();

    inicio = high_resolution_clock::now();
    for (int v : valores) arvore.root = arvore.remove(v);
    double tempoRemocao = duration<double>(high_resolution_clock::now() - inicio).count();

    return {tempoInsercao, tempoBusca, tempoRemocao};
}

// Executa o teste interativo em partes
void executarTesteEmPartesSeparadas(const vector<int>& entradas) {
    int escolha;
    cout << "Escolha o tipo de dados para o teste:\n1 - Aleatórios\n2 - Ordenados\n> ";
    cin >> escolha;
    cin.ignore(numeric_limits<streamsize>::max(), '\n');

    string tipoDados = (escolha == 1) ? "aleatorio" : "ordenado";
    string fileName = "dados_" + tipoDados + "_avl_c.csv";
    ofstream out(fileName);

    if (!out) {
        cerr << "Erro ao abrir arquivo para escrita: " << fileName << endl;
        return;
    }

    out << "Tamanho,Arvore,Operacao,Tempo\n";

    for (int tamanho : entradas) {
        vector<int> dados = (escolha == 1) ? gerarDadosAleatorios(tamanho) : gerarDadosOrdenados(tamanho);

        auto [insercao, busca, remocao] = testarAVL(dados);

        out << tamanho << ",AVL,Insercao," << insercao << "\n"
            << tamanho << ",AVL,Busca," << busca << "\n"
            << tamanho << ",AVL,Remocao," << remocao << "\n";

        cout << "Tamanho: " << tamanho << endl;
        printf("AVL - Insercao: %.6fs, Busca: %.6fs, Remocao: %.6fs\n", insercao, busca, remocao);

        cout << "Pressione Enter para continuar...";
        cin.get();
    }

    out.close();
    cout << "Dados salvos em '" << fileName << "'." << endl;
}

int main() {
    vector<int> entradas = {10000, 50000, 100000, 250000, 500000, 1000000};
    executarTesteEmPartesSeparadas(entradas);
    return 0;
}
