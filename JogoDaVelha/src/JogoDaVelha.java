import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

public class JogoDaVelha {

    private char[] tabuleiro;
    private String[] simbolos;
    private LinkedHashMap<Integer, String> historico;
    private int qtdJogadas;
    private int nivelMaquina;
    private Random random;

    public JogoDaVelha(String simbolo1, String simbolo2) {
        this.tabuleiro = new char[9];
        this.simbolos = new String[2];
        this.simbolos[0] = simbolo1;
        this.simbolos[1] = simbolo2;
        this.historico = new LinkedHashMap<>();  // Armazena as jogadas na ordem cronológica do jogo
        this.qtdJogadas = 0;                     // Contador de jogadas
        this.random = new Random();              // Auxiliar do método JogaMaquina para escolher as posições aleatórias
        
        inicializarTabuleiro();
    }

    public JogoDaVelha(String nomeJogador1, int nivel) {
        this.tabuleiro = new char[9];
        this.simbolos = new String[2];
        this.simbolos[0] = nomeJogador1;
        this.simbolos[1] = "m"; // Símbolo da máquina
        this.historico = new LinkedHashMap<>();
        this.qtdJogadas = 0;
        this.nivelMaquina = nivel;
        this.random = new Random();
        inicializarTabuleiro();
    }

    private void inicializarTabuleiro() {
        for (int i = 0; i < 9; i++) {
            tabuleiro[i] = ' '; // Método auxiliar que preenche o tabuleiro com as celulas vazias no inicio do jogo.
        }
    }

    public void jogaJogador(int numJogador, int posicao) {
      
        if (numJogador < 1 || numJogador > 2) {
            throw new IllegalArgumentException("Número de Jogador inválido: " + numJogador);
        }

        if (posicao < 0 || posicao > 8) {
            throw new IllegalArgumentException("Posição inválida: " + posicao);
        }

        if (tabuleiro[posicao] != ' ') {
            throw new IllegalArgumentException("Posição já ocupada: " + posicao + ",Tente novamente!");
        }

        String simbolo = getSimbolo(numJogador); 

        tabuleiro[posicao] = simbolo.charAt(0); // retorna um char // Executa a jogada

        historico.put(posicao, simbolo); // Atualiza o histórico.

        qtdJogadas++; // Incrementa o contador de jogadas.
    }

    public int jogaMaquina() { 
        if (terminou()) {
            return -1; // Retorna -1 se o jogo já terminou e a máquina não pode jogar
        }

        String simboloMaquina = getSimbolo(2);
        String simboloOponente = getSimbolo(1);
        ArrayList<Integer> posicoesLivres = getPosicoesDisponiveis(); 
        int posicaoEscolhida = -1;

        if (posicoesLivres.isEmpty()) {
            return -1; // Retorna -1 se não houver posições livres
        }

        if (nivelMaquina == 1) {
            int indiceAleatorio = random.nextInt(posicoesLivres.size());
            posicaoEscolhida = posicoesLivres.get(indiceAleatorio);

        } else if (nivelMaquina == 2) {
            posicaoEscolhida = encontrarJogada(simboloMaquina.charAt(0));

            
            if (posicaoEscolhida == -1) {
                posicaoEscolhida = encontrarJogada(simboloOponente.charAt(0));
            }

           
            if (posicaoEscolhida == -1) {
                if (tabuleiro[4] == ' ') {
                    posicaoEscolhida = 4;
                }
            }

            ArrayList<Integer> cantosDisponiveis = new ArrayList<>();
            if (posicaoEscolhida == -1) { // Só procura nos cantos se ainda não achou jogada
                int[] cantos = {0, 2, 6, 8};
                for (int celulas : cantos) {
                    if (tabuleiro[celulas] == ' ')
                        cantosDisponiveis.add(celulas);
                }
            }

            if (!cantosDisponiveis.isEmpty() && posicaoEscolhida == -1) { // Verifica se ainda não achou jogada
                posicaoEscolhida = cantosDisponiveis.get(random.nextInt(cantosDisponiveis.size()));
            }

            ArrayList<Integer> lateraisDisponiveis = new ArrayList<>();
            if (posicaoEscolhida == -1) { // Só procura nas laterais se ainda não achou jogada
                int[] laterais = {1, 3, 5, 7};
                for (int celulas : laterais) {
                    if (tabuleiro[celulas] == ' ') {
                        lateraisDisponiveis.add(celulas);
                    }
                }

                if (!lateraisDisponiveis.isEmpty()) {
                    posicaoEscolhida = lateraisDisponiveis.get(random.nextInt(lateraisDisponiveis.size()));
                }
            }

            // Se ainda não achou jogada, escolhe aleatoriamente entre as restantes
            if (posicaoEscolhida == -1) {
                int indiceAleatorio = random.nextInt(posicoesLivres.size());
                posicaoEscolhida = posicoesLivres.get(indiceAleatorio);
            }

        } else { // Caso nivelMaquina seja inválido (nem 1 nem 2)
            System.err.println("Nível de esperteza da máquina inválido: " + nivelMaquina);
            int indiceAleatorio = random.nextInt(posicoesLivres.size());
            posicaoEscolhida = posicoesLivres.get(indiceAleatorio);
        }

        // Esta parte executa a jogada na posição escolhida
        if (posicaoEscolhida != -1) {
            tabuleiro[posicaoEscolhida] = simboloMaquina.charAt(0);
            historico.put(posicaoEscolhida, simboloMaquina);
            qtdJogadas++;
        }
        return posicaoEscolhida; // RETORNA A POSIÇÃO JOGADA
    }

    private int encontrarJogada(char simboloParaVerificar) {    
        ArrayList<int[]> combinacoesVencedoras = new ArrayList<>();

        combinacoesVencedoras.add(new int[]{0, 1, 2});
        combinacoesVencedoras.add(new int[]{3, 4, 5}); // Linhas
        combinacoesVencedoras.add(new int[]{6, 7, 8});
        combinacoesVencedoras.add(new int[]{0, 3, 6});
        combinacoesVencedoras.add(new int[]{1, 4, 7}); // Colunas
        combinacoesVencedoras.add(new int[]{2, 5, 8});
        combinacoesVencedoras.add(new int[]{0, 4, 8});
        combinacoesVencedoras.add(new int[]{2, 4, 6}); // Diagonais

        for (int[] combinacao : combinacoesVencedoras) {
            int contagemSimbolo = 0;
            int posicaoLivre = -1;

            for (int pos : combinacao) {
                if (tabuleiro[pos] == simboloParaVerificar) {
                    contagemSimbolo++;
                } else if (tabuleiro[pos] == ' ') {
                    posicaoLivre = pos;
                }
            }

            if (contagemSimbolo == 2 && posicaoLivre != -1) {
                return posicaoLivre;
            }
        }
        return -1; 
    }


    public boolean terminou() {
        if (getSimboloVencedor() != ' ') {
            return true;
        }

        if (qtdJogadas == 9) {
            return true;
        }

        return false;
    }

    public int getResultado() {
        if (!terminou()) {
            return -1;
        }

        char simboloVencedor = getSimboloVencedor();

        if (simboloVencedor != ' ') { // Verifica se existe um simbolo vencedor
            if (simboloVencedor == simbolos[0].charAt(0)) {
                return 1;
            } else {
                return 2;
            }
        }

        return 0;
    }

    public String getSimbolo(int numJogador) {
        if (numJogador < 1 || numJogador > 2) {
            throw new IllegalArgumentException("Número de Jogador inválido: " + numJogador);
        }
        return simbolos[numJogador - 1];
    }

    public String getFoto() {   // a tela obtem o simbolo a partir deste método 
        StringBuilder fotoTabuleiro = new StringBuilder();  

        for (int i = 0; i < 9; i++) {
            fotoTabuleiro.append(tabuleiro[i]); // Adiciona o simbolo da célula

            if ((i + 1) % 3 == 0) { // Fim da linha
                fotoTabuleiro.append("\n");
                if (i < 8) { // Se não for a última linha do tabuleiro
                    fotoTabuleiro.append("---+---+---\n");
                }
            } else { // Meio da linha
                fotoTabuleiro.append(" | ");
            }
        }
        return fotoTabuleiro.toString();  // Converte StringBuilder em uma String imutável 
    }

    private char getSimboloVencedor() {
        ArrayList<int[]> combinacoes = new ArrayList<>(); // Armazena as combinações vencedoras no Array

        combinacoes.add(new int[]{0, 1, 2});
        combinacoes.add(new int[]{3, 4, 5}); // Linhas
        combinacoes.add(new int[]{6, 7, 8});

        combinacoes.add(new int[]{0, 3, 6});
        combinacoes.add(new int[]{1, 4, 7}); // Colunas
        combinacoes.add(new int[]{2, 5, 8});

        combinacoes.add(new int[]{0, 4, 8});
        combinacoes.add(new int[]{2, 4, 6}); // Diagonais

        for (int[] i : combinacoes) {
            char primeiroSimbolo = tabuleiro[i[0]];

            if (primeiroSimbolo != ' ' && primeiroSimbolo == tabuleiro[i[1]] && primeiroSimbolo == tabuleiro[i[2]]) {
                return primeiroSimbolo; // Retorna o símbolo do vencedor
            }
        }
        return ' ';
    }

    public ArrayList<Integer> getPosicoesDisponiveis() { 
        ArrayList<Integer> livres = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (tabuleiro[i] == ' ') {
                livres.add(i);
            }
        }
        return livres;
    }

    public LinkedHashMap<Integer, String> getHistorico() {
        return this.historico;
    }

    private char getCelula(int pos) {  // auxilia a interface na consulta do símbolo do tabuleiro sem precisar acessar diretamente o array do tabuleiro
        if (pos < 0 || pos >= tabuleiro.length) {
            throw new IllegalArgumentException("Posição de célula inválida: " + pos);
        }
        return tabuleiro[pos];
    }

    public int getTotalJogadas() {  
        return qtdJogadas;
    }
}