import time
import random
import csv

# ------------------------------
# Implementação da BST
# ------------------------------
class Node:
    def __init__(self, valor):
        self.valor = valor
        self.esquerda = None
        self.direita = None

class BinaryTree:
    def __init__(self):
        self.root = None

    def insert(self, valor):
        self.root = self._insert(self.root, valor)

    def _insert(self, node, valor):
        if node is None:
            return Node(valor)
        if valor < node.valor:
            node.esquerda = self._insert(node.esquerda, valor)
        else:
            node.direita = self._insert(node.direita, valor)
        return node

    def search(self, valor):
        return self._search(self.root, valor)

    def _search(self, node, valor):
        if node is None or node.valor == valor:
            return node
        if valor < node.valor:
            return self._search(node.esquerda, valor)
        else:
            return self._search(node.direita, valor)

    def remove(self, valor):
        self.root = self._remove(self.root, valor)

    def _remove(self, node, valor):
        if node is None:
            return None
        if valor < node.valor:
            node.esquerda = self._remove(node.esquerda, valor)
        elif valor > node.valor:
            node.direita = self._remove(node.direita, valor)
        else:
            if node.esquerda is None:
                return node.direita
            elif node.direita is None:
                return node.esquerda
            temp = self._minValueNode(node.direita)
            node.valor = temp.valor
            node.direita = self._remove(node.direita, temp.valor)
        return node

    def _minValueNode(self, node):
        atual = node
        while atual.esquerda is not None:
            atual = atual.esquerda
        return atual

# ------------------------------
# Geradores de dados
# ------------------------------
def gerar_dados_aleatorios(tamanho):
    return [random.randint(1, tamanho * 10) for _ in range(tamanho)]

def gerar_dados_ordenados(tamanho):
    return list(range(1, tamanho + 1))

# ------------------------------
# Teste de desempenho da BST
# ------------------------------
def testar_bst(valores):
    arvore = BinaryTree()

    inicio = time.time()
    for valor in valores:
        arvore.insert(valor)
    tempo_insercao = time.time() - inicio

    inicio = time.time()
    for valor in valores:
        arvore.search(valor)
    tempo_busca = time.time() - inicio

    # Remoção em ordem inversa
    inicio = time.time()
    for valor in reversed(valores):
        arvore.remove(valor)
    tempo_remocao = time.time() - inicio

    return tempo_insercao, tempo_busca, tempo_remocao

# ------------------------------
# Função principal
# ------------------------------
def executar_teste():
    escolha = input("Escolha o tipo de dados para o teste:\n1 - Aleatórios\n2 - Ordenados\n> ")

    if escolha == "1":
        tamanhos = [10000, 50000, 100000, 250000, 500000, 1000000]
        tipo_dados = "aleatorio"
    else:
        tamanhos = [1000, 5000, 10000, 25000, 50000, 100000]
        tipo_dados = "ordenado"

    nome_arquivo = f"dados_{tipo_dados}_binaria_python.csv"
    with open(nome_arquivo, mode='w', newline='') as arquivo:
        writer = csv.writer(arquivo)
        writer.writerow(["Tamanho", "Arvore", "Operacao", "Tempo"])

        for tamanho in tamanhos:
            dados = gerar_dados_aleatorios(tamanho) if escolha == "1" else gerar_dados_ordenados(tamanho)
            tempos = testar_bst(dados)

            writer.writerow([tamanho, "Binaria", "Insercao", f"{tempos[0]:.6f}"])
            writer.writerow([tamanho, "Binaria", "Busca", f"{tempos[1]:.6f}"])
            writer.writerow([tamanho, "Binaria", "Remocao", f"{tempos[2]:.6f}"])

            print(f"\nTamanho: {tamanho}")
            print(f"Árvore Binária - Inserção: {tempos[0]:.6f}s, Busca: {tempos[1]:.6f}s, Remoção (reversa): {tempos[2]:.6f}s")
            input("Pressione Enter para continuar com o próximo tamanho...")

    print(f"\nDados salvos em '{nome_arquivo}'.")

# ------------------------------
# Execução
# ------------------------------
if __name__ == "__main__":
    executar_teste()
