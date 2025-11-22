## 🖨️ Sistema de Impressão Fiscal – Projeto Java

**Integrantes do grupo:** Catarina Nascimento, Davi Evangelista, Giovana Gouvea, Heloisa Monteiro e Luiz Barbosa

**Professor:** Richard Spanhol

---

**Ferramentas Utilizadas**

- Linguagem: Java

- IDE: IntelliJ IDEA Community

- GitHub

- Biblioteca nativa: E1_Impressora01.dll

- Documentação Elgin

- Projeto base: Java-Aluno EM

**Pré-requisitos**

Sistema Operacional

- Windows 10 ou Windows 11 (32 ou 64 bits)

**Software**

- Biblioteca 

- IDE Java (Eclipse, IntelliJ, etc.) — opcional

**Arquivos Necessários**

Disponíveis na biblioteca fornecida:

- E1_Impressora01.dll

- XMLSAT.xml (exemplo de venda)

- CANC_SAT.xml (exemplo de cancelamento)

**Hardware**

- Impressora fiscal compatível com a DLL

- Cabo USB ou interface serial

[Espaço para imagem da impressora]

---

**Descrição do Sistema**

É um sistema de atendimento de caixa (PDV simplificado), que permite o usuário selecionar operações básicas por 
meio de um menu interativo. O sistema se comunica com a impressora utilizando as funções disponíveis 
na biblioteca oficial da Elgin.

**Gerenciador de Conexão**

- Configuração de parâmetros
- Suporte para USB e Serial
- Monitoramento da conexão
- Abertura e fechamento seguro

**Funcionalidades de Impressão**
- Impressão de Texto: Texto simples via impressora térmica.

- Impressão de QR Code: Geração e impressão de QR Codes.

- Código de Barras: Imprime códigos de barras padrão.

- XML SAT: Imprime XML fiscal SAT (venda).

- XML de Cancelamento: Imprime o XML de cancelamento do SAT.

**Controle de Periféricos**

- Gaveta Elgin: abertura automática

- Gaveta padrão: abertura via parâmetros

- Sinal sonoro: emissão de alertas (bip)

---

**Passo a Passo de Instalação**

1. Baixe os arquivos

**Biblioteca:** https://drive.google.com/file/d/1obPkDgUYDJWebLDJLcu6GJZh8NDRQ_5c/view

**Documentação Elgin:** https://elgindevelopercommunity.github.io/group___m1.html#ga928f0795631b062f8d5c8c20b9681d8d

2. Coloque a DLL no diretório:

C:/Bibliotecas/E1_Impressora/x64/E1_Impressora01.dll

3. Ajuste os caminhos no código

**Caminho da DLL (~linha 22):**

"C:/Bibliotecas/E1_Impressora/x64/E1_Impressora01.dll"

**XML SAT (~linha 225):**

"C:/Arquivos_XML/XMLSAT.xml"


**XML Cancelamento (~linha 241):**

"C:/Arquivos_XML/CANC_SAT.xml"

**Configuração**
Tipos de Conexão

|   Tipo  |   Código  | Descrição|
|---------|-----------|----------|
|   USB   |       1   | Conexão via USB  |

Parâmetros usados no projeto:

- Modelo: i9

- Porta: USB

- Parâmetro: 0 (padrão)

---

## Como Usar o Sistema
1. Configurar Conexão

Define tipo de conexão, modelo e porta.

2. Abrir Conexão

Estabelece comunicação com a impressora.

3. Usar o menu para imprimir ou controlar periféricos

```Menu Principal
**************** MENU IMPRESSORA *******************

1 - Configurar Conexao
2 - Abrir Conexao
3 - Impressao Texto
4 - Impressao QRCode
5 - Impressao Cod Barras
6 - Impressao XML SAT
7 - Impressao XML Canc SAT
8 - Abrir Gaveta Elgin
9 - Abrir Gaveta
10 - Sinal Sonoro
0 - Fechar Conexao e Sair
```
***Exemplo de Uso***

- Opção: 1

Tipo:      
1

Modelo:    
i9

Porta:     
USB

Parâmetro: 

0

**Estrutura do Código**
```Interface da DLL
public interface ImpressoraDLL extends Library {

    ImpressoraDLL INSTANCE = (ImpressoraDLL) Native.load(
        "C:\\...\\E1_Impressora01.dll",
        ImpressoraDLL.class
    );

    int AbreConexaoImpressora(int tipo, String modelo, String conexao, int param);
    int FechaConexaoImpressora();
    int ImpressaoTexto(String dados, int posicao, int estilo, int tamanho);
    int Corte(int avanco);
    int ImpressaoQRCode(String dados, int tamanho, int nivelCorrecao);
    int ImpressaoCodigoBarras(int tipo, String dados, int altura, int largura, int HRI);
    int AvancaPapel(int linhas);
    int StatusImpressora(int param);
    int AbreGavetaElgin();
    int AbreGaveta(int pino, int ti, int tf);
    int SinalSonoro(int qtd, int ti, int tf);
    int ModoPagina();
    int LimpaBufferModoPagina();
    int ImprimeModoPagina();
    int ModoPadrao();
    int PosicaoImpressaoHorizontal(int posicao);
    int PosicaoImpressaoVertical(int posicao);
    int ImprimeXMLSAT(String dados, int param);
    int ImprimeXMLCancelamentoSAT(String dados, String assQRCode, int param);
}
```
[Espaço para imagem do fluxo do código]

**Principais Métodos**
| Método               | Função                               |
|----------------------|---------------------------------------|
| configurarConexao()  | Define parâmetros da impressora       |
| abrirConexao()       | Conecta à impressora                  |
| fecharConexao()      | Encerra a comunicação                 |
| ImpressaoTexto()     | Imprime texto                         |
| ImpressaoQRCode()    | Gera e imprime QR Code                |
| ImprimeXMLSAT()      | Imprime documentos fiscais            |
