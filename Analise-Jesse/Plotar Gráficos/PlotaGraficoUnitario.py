import pandas as pd
import matplotlib.pyplot as plt
import matplotlib.ticker as ticker

    #Aluno: Jessé Oliveira das Chagas
    #Email: jesse.oliveira.chagas@ccc.ufcg.edu.br
    #Matrícula: 123210473
    #Nome de usuário do GitHub: jessechagas

# Função para formatar valores no eixo Y (Tempo)
def formatar_valor(valor, _):
    if valor < 1:
        return f"{valor:.1f}"  
    return f"{int(valor)}"  

# Lê os arquivos CSV
dados_binaria = pd.read_csv("dados_aleatorio_binaria_python.csv")
dados_avl = pd.read_csv("dados_aleatorio_avl_python.csv")

# Converte a coluna "Tempo" para float, caso esteja como string
dados_binaria["Tempo"] = dados_binaria["Tempo"].astype(float)
dados_avl["Tempo"] = dados_avl["Tempo"].astype(float)

# Definição dos tamanhos de entrada fixos
tamanhos_entrada = [10_000, 50_000, 100_000, 250_000, 500_000, 1_000_000]
tamanhos_formatados = ["10k", "50k", "100k", "250k", "500k", "1M"]

# Lista de operações e títulos
operacoes = ["Inserção", "Busca", "Remoção"]
titulos = ["Tempo de Inserção", "Tempo de Busca", "Tempo de Remoção"]

# Cores para cada árvore
cores = {"binaria": "blue", "avl": "red"}

# Função para gerar os gráficos
def gerar_grafico(dados, nome_arvore, cor):
    fig, axs = plt.subplots(3, 1, figsize=(12, 10))

    for i, (operacao, titulo) in enumerate(zip(operacoes, titulos), 1):
        dados_operacao = dados[dados["Operacao"] == operacao]
        max_tempo = dados_operacao["Tempo"].max()

        axs[i-1].plot(dados_operacao["Tamanho"], dados_operacao["Tempo"], label=f"Árvore {nome_arvore}", 
                      marker="o", color=cor)
        axs[i-1].set_title(titulo, pad=15)  
        axs[i-1].set_xlabel("Tamanho da Entrada", labelpad=10) 
        axs[i-1].set_ylabel("Tempo (s)")
        axs[i-1].legend()
        axs[i-1].grid()

        axs[i-1].set_ylim(0, max_tempo * 1.1) 
        axs[i-1].set_xticks(tamanhos_entrada)
        axs[i-1].set_xticklabels(tamanhos_formatados)
        axs[i-1].yaxis.set_major_formatter(ticker.FuncFormatter(formatar_valor))

    plt.subplots_adjust(bottom=0.05, top=0.95, hspace=0.4)  
    plt.savefig(f"grafico_{nome_arvore}.png", bbox_inches="tight")  
    plt.close()  

# Gera os gráficos separados
gerar_grafico(dados_binaria, "Binaria", cores["binaria"])
gerar_grafico(dados_avl, "AVL", cores["avl"])
