import time
import matplotlib.pyplot as plt
import matplotlib.ticker as ticker
from BinaryTree import BinaryTree
from BinaryTreeAVL import AVLTree
import random

    # Aluno: Jessé Oliveira das Chagas
    # Email: jesse.oliveira.chagas@ccc.ufcg.edu.br
    # Matrícula: 123210473
    # User do GitHub: jessechagas


def gerar_dados_aleatorios(tamanho):
    return random.sample(range(1, tamanho * 10), tamanho)

def test_binary_tree(valores):
    arvore = BinaryTree()
    inicio = time.time()
    
    for valor in valores:
        arvore.insert(valor)
    tempo_insercao = time.time() - inicio

    inicio = time.time()
    for valor in valores:
        arvore.search(valor)
    tempo_busca = time.time() - inicio

    inicio = time.time()
    for valor in valores:
        arvore.remove(valor)
    tempo_remocao = time.time() - inicio

    return tempo_insercao, tempo_busca, tempo_remocao

def test_avl_tree(valores):
    arvore = AVLTree()
    inicio = time.time()
    
    for valor in valores:
        arvore.insert(valor)
    tempo_insercao = time.time() - inicio

    inicio = time.time()
    for valor in valores:
        arvore.search(valor)
    tempo_busca = time.time() - inicio

    inicio = time.time()
    for valor in valores:
        arvore.remove(valor)
    tempo_remocao = time.time() - inicio

    return tempo_insercao, tempo_busca, tempo_remocao

def executar_teste():
    entradas = [10000, 50000, 100000, 250000, 500000, 1000000]
    tempos_binaria = {"insercao": [], "busca": [], "remocao": []}
    tempos_avl = {"insercao": [], "busca": [], "remocao": []}

    for tamanho in entradas:
        dados = gerar_dados_aleatorios(tamanho)

        tempo_ins_bin, tempo_bus_bin, tempo_rem_bin = test_binary_tree(dados)
        tempos_binaria["insercao"].append(tempo_ins_bin)
        tempos_binaria["busca"].append(tempo_bus_bin)
        tempos_binaria["remocao"].append(tempo_rem_bin)

        tempo_ins_avl, tempo_bus_avl, tempo_rem_avl = test_avl_tree(dados)
        tempos_avl["insercao"].append(tempo_ins_avl)
        tempos_avl["busca"].append(tempo_bus_avl)
        tempos_avl["remocao"].append(tempo_rem_avl)

        print(f"Tamanho: {tamanho}")
        print(f"Árvore Binária - Inserção: {tempo_ins_bin:.6f}s, Busca: {tempo_bus_bin:.6f}s, Remoção: {tempo_rem_bin:.6f}s")
        print(f"Árvore AVL - Inserção: {tempo_ins_avl:.6f}s, Busca: {tempo_bus_avl:.6f}s, Remoção: {tempo_rem_avl:.6f}s")
        print()

    plt.figure(figsize=(14, 8))

    def formatar_valor(valor, _):
        if valor >= 1_000_000:
            return f"{int(valor / 1_000_000)}M"
        elif valor >= 1_000:
            return f"{int(valor / 1_000)}k"
        return str(int(valor))

    for i, (operacao, titulo) in enumerate(zip(["insercao", "busca", "remocao"], ["Tempo de Inserção", "Tempo de Busca", "Tempo de Remoção"]), 1):
        eixo = plt.subplot(3, 1, i)
        plt.plot(entradas, tempos_binaria[operacao], label="Árvore Binária", marker="o")
        plt.plot(entradas, tempos_avl[operacao], label="Árvore AVL", marker="o")
        plt.title(titulo)
        plt.xlabel("Tamanho da Entrada")
        plt.ylabel("Tempo (s)")
        plt.legend()
        plt.grid()
        eixo.xaxis.set_major_formatter(ticker.FuncFormatter(formatar_valor))
        eixo.set_xticks(entradas)  

    plt.tight_layout()
    plt.show()

if __name__ == "__main__":
    executar_teste()