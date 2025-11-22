import com.sun.jna.Library;
import com.sun.jna.Native;
import java.util.Scanner;
import javax.naming.ldap.spi.LdapDnsProvider;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.io.FileInputStream;




import static javax.swing.text.html.HTML.Tag.EM;

public class Main {

    // Interface que representa a DLL, usando JNA
    public interface ImpressoraDLL extends Library {
        // Caminho completo para a DLL que cria uma ponte entre Java e a biblioteca
        ImpressoraDLL INSTANCE = (ImpressoraDLL) Native.load(
                "C:\\Users\\giovana_gouvea\\Documents\\Java-Aluno EM\\Java-Aluno EM\\E1_Impressora01.dll",
                ImpressoraDLL.class
        );

        //variaveis das funcoes com seus parametros
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
        int SinalSonoro(int qtd, int tempoInicio, int tempoFim);
        int ModoPagina();
        int LimpaBufferModoPagina();
        int ImprimeModoPagina();
        int ModoPadrao();
        int PosicaoImpressaoHorizontal(int posicao);
        int PosicaoImpressaoVertical(int posicao);
        int ImprimeXMLSAT(String dados, int param);
        int ImprimeXMLCancelamentoSAT(String dados, String assQRCode, int param);
    }
    //definindo essas variáveis globais para guardar os dados da impressora:
    private static boolean conexaoAberta = false;
    private static int tipo;
    private static String modelo;
    private static String conexao;
    private static int parametro;
    private static final Scanner scanner = new Scanner(System.in);

    private static String capturarEntrada(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    // Solicita e armazena os dados de configuração da conexão com a impressora e envia para a função abrirConexao
    public static void configurarConexao() {
        if (!conexaoAberta) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Digite o tipo de conexão (ex: 1 para USB, 2 para serial, etc.): ");
            tipo = scanner.nextInt();
            System.out.println("Digite o modelo da impressora: ");
            modelo = scanner.nextLine();
            scanner.nextLine();
            System.out.println("Digite a porta de conexão (ex: USB): ");
            conexao = scanner.nextLine();
            System.out.println("Digite o parâmetro adicional (ex: 0 para padrão): ");
            parametro = scanner.nextInt();
        }
    }

    //abre conexão com a impressora para que as outras funções funcione
    public static void abrirConexao () {
        if (!conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.AbreConexaoImpressora(tipo, modelo, conexao, parametro);
            if (retorno == 0) {
                conexaoAberta = true;
                System.out.println("Conexão aberta com sucesso.");
            } else {
                System.out.println("Erro ao abrir conexão. Código de erro: " + retorno);
            }
        } else {
            System.out.println("Conexão já está aberta.");
        }
    }

    //impressao do texto "Teste de impressao" e avaço de 5 linhas
    public static void ImpressaoTexto () {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.ImpressaoTexto("Teste de impressao", 1, 4, 0);
            if (retorno == 0) {
                ImpressoraDLL.INSTANCE.AvancaPapel(5);
                System.out.println("Impressão realizada com sucesso!");
            } else {
                System.out.println("Erro ao abrir conexao. Codigo de erro: " + retorno);
            }
        } else {
            System.out.println("Abra a conexão com a impressora");
        }
    }

    // Realiza o corte da folha avançando 5 linhas
    public static void Corte () {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.Corte(5);
            if (retorno == 0) {
                System.out.println("Corte realizado com sucesso!");
            } else {
                System.out.println("Erro ao cortar. Código: " + retorno);
            }
        } else {
            System.out.println("Abra a conexão com a impressora primeiro.");
        }

    }

    //Realiza a impressão do QRCode e avança 5 linhas
    public static void ImpressaoQRCode() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.ImpressaoQRCode("Teste de QRCode", 6, 4);
            if (retorno == 0) {
                ImpressoraDLL.INSTANCE.AvancaPapel(5);
                System.out.println("QR Code impresso com sucesso!");
            } else {
                System.out.println("Erro ao imprimir QR Code. Código: " + retorno);
            }
        } else {
            System.out.println("Abra a conexão com a impressora primeiro.");
        }
    }

    //Realiza a impressão do Código de Barras e avança 5 linhas
    public static void ImpressaoCodigoBarras() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.ImpressaoCodigoBarras(8, "{A012345678912", 100, 2, 3);
            if (retorno == 0) {
                ImpressoraDLL.INSTANCE.AvancaPapel(5);
                System.out.println("Código de barras impresso com sucesso!");
            } else {
                System.out.println("Erro ao imprimir código de barras. Código: " + retorno);
            }
        } else {
            System.out.println("Abra a conexão com a impressora primeiro.");
        }
    }

    //Avança o papel por DUAS linhas
    public static void AvancaPapel() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.AvancaPapel(2);
            if (retorno == 0) {
                System.out.println("Papel avançado com sucesso!");
            } else {
                System.out.println("Erro ao avançar papel. Código: " + retorno);
            }
        } else {
            System.out.println("Abra a conexão com a impressora primeiro.");
        }
    }

    // Abre a gaveta usando o comando específico da Elgin
    public static void AbreGavetaElgin() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.AbreGavetaElgin();
            if (retorno == 0) {
                System.out.println("Gaveta Elgin aberta com sucesso!");
            } else {
                System.out.println("Erro ao abrir gaveta Elgin. Código: " + retorno);
            }
        } else {
            System.out.println("Abra a conexão com a impressora primeiro.");
        }
    }

    // Abre a gaveta usando parâmetros genéricos
    public static void AbreGaveta() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.AbreGaveta(1, 50, 50);
            if (retorno == 0) {
                System.out.println("Gaveta aberta com sucesso!");
            } else {
                System.out.println("Erro ao abrir gaveta. Código: " + retorno);
            }
        } else {
            System.out.println("Abra a conexão com a impressora primeiro.");
        }
    }

    //Emite um Sinal Sonoro
    public static void SinalSonoro() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.SinalSonoro(4, 5, 5);
            if (retorno == 0) {
                System.out.println("Sinal sonoro emitido com sucesso!");
            } else {
                System.out.println("Erro ao emitir sinal sonoro. Código: " + retorno);
            }
        } else {
            System.out.println("Abra a conexão com a impressora primeiro.");
        }
    }

    //Fecha a conexão da impressora
    public static void fecharConexao () {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.FechaConexaoImpressora();
            if (retorno == 0) {
                conexaoAberta = false;
                System.out.println("Conexão encerrada com sucesso.");
            } else {
                System.out.println("Erro ao fechar conexão. Código: " + retorno);
            }
        } else {
            System.out.println("Nenhuma conexão está aberta.");
        }
    }

    //Imprime a nota fiscal
    public static void ImprimeXMLSAT() {
        if (conexaoAberta) {
            String dados = "path=C:\\Users\\giovana_gouvea\\Documents\\Java-Aluno EM\\Java-Aluno EM\\XMLSAT.xml";
            int retorno = ImpressoraDLL.INSTANCE.ImprimeXMLSAT(dados, 0);
            if (retorno == 0) {
                System.out.println("Impressao de xml com sucesso.");
            } else {
                System.out.println("Erro ao imprimir xml. Código: " + retorno);
            }
        } else {
            System.out.println("Nenhuma conexão está aberta.");
        }
    }

    //Imprime uma nota fiscal de cancelamento
    public static void ImprimeXMLCancelamentoSAT() {
        if (conexaoAberta) {
            String assQRCode = "Q5DLkpdRijIRGY6YSSNsTWK1TztHL1vD0V1Jc4spo/CEUqICEb9SFy82ym8EhBRZjbh3btsZhF+sjHqEMR159i4agru9x6KsepK/q0E2e5xlU5cv3m1woYfgHyOkWDNcSdMsS6bBh2Bpq6s89yJ9Q6qh/J8YHi306ce9Tqb/drKvN2XdE5noRSS32TAWuaQEVd7u+TrvXlOQsE3fHR1D5f1saUwQLPSdIv01NF6Ny7jZwjCwv1uNDgGZONJdlTJ6p0ccqnZvuE70aHOI09elpjEO6Cd+orI7XHHrFCwhFhAcbalc+ZfO5b/+vkyAHS6CYVFCDtYR9Hi5qgdk31v23w==";
            String dados = "path=C:\\Users\\giovana_gouvea\\Documents\\Java-Aluno EM\\Java-Aluno EM\\CANC_SAT.xml";
            int retorno = ImpressoraDLL.INSTANCE.ImprimeXMLCancelamentoSAT(dados, assQRCode, 0);
            if (retorno == 0) {
                System.out.println("Impressao de cancelamento do xml com sucesso.");
            } else {
                System.out.println("Erro ao imprimir o cancelamento do xml. Código: " + retorno);
            }
        } else {
            System.out.println("Nenhuma conexão está aberta.");
        }

    }

    //Função principal do código
    public static void main (String[]args){
        while (true) {
            System.out.println("\n*************************************************");
            System.out.println("**************** MENU IMPRESSORA *******************");
            System.out.println("*************************************************\n");

            //Opcoes do menu para o usuario escolher
            System.out.println("1 - Configurar Conexao");
            System.out.println("2 - Abrir Conexao");
            System.out.println("3 - Impressao Texto");
            System.out.println("4 - Impressao QRCode");
            System.out.println("5 - Impressao Cod Barras");
            System.out.println("6 - Impressao XML SAT");
            System.out.println("7 - Impressao XML Canc SAT");
            System.out.println("8 - Abrir Gaveta Elgin");
            System.out.println("9 - Abrir Gaveta");
            System.out.println("10 - Sinal Sonoro");
            System.out.println("0 - Fechar Conexao e Sair");

            String escolha = capturarEntrada("\nDigite a opção desejada: ");

            if (escolha.equals("0")) {
                fecharConexao();
                System.out.println("Programa encerrado.");
                break;
            }

            //chamando as funções aqui
            switch (escolha) {
                case "1":
                    configurarConexao();
                    break;
                case "2":
                    abrirConexao();
                    break;
                case "3":
                    ImpressaoTexto();
                    Corte();
                    break;
                case "4":
                    ImpressaoQRCode();
                    Corte();
                    break;
                case "5":
                    ImpressaoCodigoBarras();
                    Corte();
                    break;
                case "6":
                    ImprimeXMLSAT();
                    Corte();
                    break;
                case "7":
                    ImprimeXMLCancelamentoSAT();
                    Corte();
                    break;
                case "8":
                    AbreGavetaElgin();
                    break;
                case "9":
                    AbreGaveta();
                    break;
                case "10":
                    SinalSonoro();
                    break;
                default:
                    System.out.println("OPÇÃO INVÁLIDA");
            }
        }
        scanner.close();//Fecha o Scanner
    }

    //
    private static String lerArquivoComoString (String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        byte[] data = fis.readAllBytes();
        fis.close();
        return new String(data, StandardCharsets.UTF_8);
    }
}











