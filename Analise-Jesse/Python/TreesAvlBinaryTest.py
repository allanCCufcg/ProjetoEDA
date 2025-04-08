import time
import csv
import os
from BinaryTree import BinaryTree
from BinaryTreeAVL import AVLTree
import random
import sys


    #Aluno: Jessé Oliveira das Chagas
    #Email: jesse.oliveira.chagas@ccc.ufcg.edu.br
    #Matrícula: 123210473
    #Nome de usuário do GitHub: jessechagas


# Defina o limite com base no maior número de elementos esperado
sys.setrecursionlimit(1000000) 

def gerar_dados_aleatorios(tamanho):         
    return random.sample(range(1, tamanho * 10), tamanho)

def gerar_dados_ordenados_binaria(tamanho):
    return list(range(1, tamanho + 1)) 

def gerar_dados_ordenados_avl(tamanho):
    return list(range(1, tamanho + 1))

def test_binary_tree(valores):
    arvore = BinaryTree()  # Sem balanceamento, árvore binária simples
    inicio = time.time()

    for valor in valores:
        arvore.insert(valor)  # Inserção na árvore binária simples (desbalanceada)

    tempo_insercao = time.time() - inicio

    inicio = time.time()
    for valor in valores:
        arvore.search(valor)  # Busca na árvore binária
    tempo_busca = time.time() - inicio

    inicio = time.time()
    for valor in valores:
        arvore.remove(valor)  # Remoção na árvore binária
    tempo_remocao = time.time() - inicio

    return tempo_insercao, tempo_busca, tempo_remocao

def test_avl_tree(valores):
    arvore = AVLTree()  # Árvore AVL balanceada
    inicio = time.time()

    for valor in valores:
        arvore.insert(valor)  # Inserção na árvore AVL
    tempo_insercao = time.time() - inicio

    inicio = time.time()
    for valor in valores:
        arvore.search(valor)  # Busca na árvore AVL
    tempo_busca = time.time() - inicio

    inicio = time.time()
    for valor in valores:
        arvore.remove(valor)  # Remoção na árvore AVL
    tempo_remocao = time.time() - inicio

    return tempo_insercao, tempo_busca, tempo_remocao

def salvar_csv(nome_arquivo, dados):
    caminho_pasta = os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'csv dados')
    os.makedirs(caminho_pasta, exist_ok=True)
    caminho_arquivo = os.path.join(caminho_pasta, nome_arquivo)
    
    with open(caminho_arquivo, mode='w', newline='', encoding='utf-8') as arquivo_csv:
        escritor = csv.writer(arquivo_csv)
        escritor.writerow(["Tamanho", "Arvore", "Operacao", "Tempo"])
        escritor.writerows(dados)

def executar_teste():
    entradas_binaria = [10000, 50000, 100000, 250000, 500000, 1000000]  # Entradas ordenadas para a árvore binária
    entradas_avl = [10000, 50000, 100000, 250000, 500000, 1000000]  # Entradas ordenadas para a árvore AVL
    
    escolha = input("Escolha o tipo de entrada (1 - Aleatória, 2 - Ordenada): ")
    if escolha == "1":
        tipo_dados = "aleatorio"
        gerar_dados = gerar_dados_aleatorios
    elif escolha == "2":
        tipo_dados = "ordenado"
        gerar_dados = gerar_dados_ordenados_binaria  # Para dados ordenados binários inicialmente
    else:
        print("Opção inválida!")
        return
    
    dados_binaria = []
    dados_avl = []
    
    for tamanho in entradas_binaria:
        print(f"Iniciando teste para {tamanho} elementos (Árvore Binária)...")
        valores = gerar_dados(tamanho)

        # Teste da árvore binária
        tempo_ins_bin, tempo_bus_bin, tempo_rem_bin = test_binary_tree(valores)
        dados_binaria.append([tamanho, "Binaria", "Inserção", tempo_ins_bin])
        dados_binaria.append([tamanho, "Binaria", "Busca", tempo_bus_bin])
        dados_binaria.append([tamanho, "Binaria", "Remoção", tempo_rem_bin])

        print(f"Tamanho: {tamanho}")
        print(f"Árvore Binária - Inserção: {tempo_ins_bin:.6f}s, Busca: {tempo_bus_bin:.6f}s, Remoção: {tempo_rem_bin:.6f}s")
        print()
    
    # Alterar para AVL com entradas específicas para AVL
    gerar_dados = gerar_dados_ordenados_avl  # Para dados ordenados AVL
    for tamanho in entradas_avl:
        print(f"Iniciando teste para {tamanho} elementos (Árvore AVL)...")
        valores = gerar_dados(tamanho)

        # Teste da árvore AVL
        tempo_ins_avl, tempo_bus_avl, tempo_rem_avl = test_avl_tree(valores)
        dados_avl.append([tamanho, "AVL", "Inserção", tempo_ins_avl])
        dados_avl.append([tamanho, "AVL", "Busca", tempo_bus_avl])
        dados_avl.append([tamanho, "AVL", "Remoção", tempo_rem_avl])

        print(f"Tamanho: {tamanho}")
        print(f"Árvore AVL - Inserção: {tempo_ins_avl:.6f}s, Busca: {tempo_bus_avl:.6f}s, Remoção: {tempo_rem_avl:.6f}s")
        print()

    salvar_csv(f"dados_{tipo_dados}_binaria_python.csv", dados_binaria)
    salvar_csv(f"dados_{tipo_dados}_avl_python.csv", dados_avl)
    
if __name__ == "__main__":
    executar_teste()
