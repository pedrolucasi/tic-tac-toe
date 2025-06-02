import java.util.ArrayList;
import java.util.LinkedHashMap;

public class JogoDaVelha {
	
	private char[] tabuleiro;
	private String[] simbolos;
	private LinkedHashMap<Integer, String> historico;
	private int qtdJogadas;
	private int nivelMaquina;
	
	public JogoDaVelha(String simbolo1, String simbolo2) {
		
		this.tabuleiro = new char[9];
		this.simbolos = new String[2];
		this.simbolos[0] = simbolo1;
		this.simbolos[1] = simbolo2;
		this.historico = new LinkedHashMap<>();
		this.qtdJogadas = 0;
		
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
		
		inicializarTabuleiro();
		
	}
	
	private void inicializarTabuleiro() {
		
		for (int i = 0; i < 9; i ++) {
			tabuleiro[i] = ' ';		    // Método auxiliar que preenche o tabuleiro com as celulas vazias no inicio do jogo.
			
		}	
	}
	
	public void jogaJogador(int numJogador, int posicao) {
		
		if (numJogador > 1	||	numJogador>2) {
			
			throw new IllegalArgumentException("Número de Jogador inválido: " + numJogador);
		}
		
		if (posicao < 0 || posicao > 8) {
			
			throw new IllegalArgumentException("Posição inválida: " + posicao);
			
		}
		
		
		
		
		
	}

}
