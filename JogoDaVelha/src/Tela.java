import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.LineBorder; 
import java.util.ArrayList; 
import java.util.LinkedHashMap; 
import java.util.stream.Collectors; 

public class Tela extends JFrame { 

    private JogoDaVelha jogo; 

    private JFrame frame; 
    private JLabel lblNewLabel; 
    private JLabel lblNewLabel_1; 
    private JCheckBox chckbxNewCheckBox; 
    private JCheckBox chckbxNewCheckBox_1; // Jogador vs Máquina
    private JLabel lblNewLabel_2; 
    private JLabel lblNewLabel_3; 
    private JLabel lblNewLabel_4; 
    private JTextField textFieldSimbolo1; 
    private JTextField textFieldSimbolo2; 
    private JLabel lblNewLabel_5; // Nível da Máquina:
    private JCheckBox chckbxNewCheckBox_2; // 1 Básico
    private JCheckBox chckbxNewCheckBox_3; // 2 Avançado
    private JButton btnNewButton; // Botão Jogar

    private JLabel[][] grid; 
    private final int TABULEIRO_DIM = 3; 
    
    private JLabel labelInfoJogo;    
    private JLabel labelHistorico;   
    private int turnoAtual; 
    private JLabel lblNewLabel_6;
    private JLabel lblNewLabel_7;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Tela window = new Tela();
                    window.frame.setVisible(true); 
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Tela() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(0, 128, 192));
        frame.setBounds(100, 100, 671, 600); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null); 

        lblNewLabel = new JLabel("Jogo Da Velha ");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 16));
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setBounds(267, 0, 131, 46);
        frame.getContentPane().add(lblNewLabel);

        lblNewLabel_1 = new JLabel("Modo do Jogo:");
        lblNewLabel_1.setForeground(new Color(255, 255, 255));
        lblNewLabel_1.setBounds(10, 42, 95, 13);
        frame.getContentPane().add(lblNewLabel_1);

        chckbxNewCheckBox = new JCheckBox("Jogador vs Jogador");
        chckbxNewCheckBox.setForeground(new Color(255, 255, 255));
        chckbxNewCheckBox.setBackground(new Color(0, 128, 192));
        chckbxNewCheckBox.setBounds(6, 57, 169, 21);
        frame.getContentPane().add(chckbxNewCheckBox);

        chckbxNewCheckBox_1 = new JCheckBox("Jogador vs Máquina");
        chckbxNewCheckBox_1.setForeground(new Color(255, 255, 255));
        chckbxNewCheckBox_1.setBackground(new Color(0, 128, 192));
        chckbxNewCheckBox_1.setBounds(6, 76, 151, 21);
        frame.getContentPane().add(chckbxNewCheckBox_1);

        // CheckBoxes do modo de jogo
        ButtonGroup grupoModoJogo = new ButtonGroup();
        grupoModoJogo.add(chckbxNewCheckBox);
        grupoModoJogo.add(chckbxNewCheckBox_1);
        // Definir um modo padrão do Jogo
        chckbxNewCheckBox.setSelected(true); // Padrão: Jogador vs Jogador

        lblNewLabel_2 = new JLabel("Simbolo:");
        lblNewLabel_2.setForeground(new Color(255, 255, 255));
        lblNewLabel_2.setBounds(176, 42, 131, 13);
        frame.getContentPane().add(lblNewLabel_2);

        lblNewLabel_3 = new JLabel("Jogador 1");
        lblNewLabel_3.setForeground(new Color(255, 255, 255));
        lblNewLabel_3.setBounds(176, 56, 65, 21);
        frame.getContentPane().add(lblNewLabel_3);

        lblNewLabel_4 = new JLabel("Jogador 2");
        lblNewLabel_4.setForeground(new Color(255, 255, 255));
        lblNewLabel_4.setBounds(176, 80, 65, 13);
        frame.getContentPane().add(lblNewLabel_4);

        textFieldSimbolo1 = new JTextField("X"); 
        textFieldSimbolo1.setBounds(251, 56, 96, 19);
        frame.getContentPane().add(textFieldSimbolo1);
        textFieldSimbolo1.setColumns(10);

        textFieldSimbolo2 = new JTextField("O"); 
        textFieldSimbolo2.setBounds(251, 77, 96, 19);
        frame.getContentPane().add(textFieldSimbolo2);
        textFieldSimbolo2.setColumns(10);

        lblNewLabel_5 = new JLabel("Nível da Máquina:");
        lblNewLabel_5.setForeground(new Color(255, 255, 255));
        lblNewLabel_5.setBounds(360, 42, 131, 13);
        frame.getContentPane().add(lblNewLabel_5);

        chckbxNewCheckBox_2 = new JCheckBox("1 Básico");
        chckbxNewCheckBox_2.setForeground(new Color(255, 255, 255));
        chckbxNewCheckBox_2.setBackground(new Color(0, 128, 192));
        chckbxNewCheckBox_2.setBounds(360, 57, 93, 21);
        frame.getContentPane().add(chckbxNewCheckBox_2);

        chckbxNewCheckBox_3 = new JCheckBox("2 Avançado"); 
        chckbxNewCheckBox_3.setForeground(new Color(255, 255, 255));
        chckbxNewCheckBox_3.setBackground(new Color(0, 128, 192));
        chckbxNewCheckBox_3.setBounds(360, 76, 93, 21);
        frame.getContentPane().add(chckbxNewCheckBox_3);

        // CheckBoxes do nível da máquina
        ButtonGroup grupoNivelMaquina = new ButtonGroup();
        grupoNivelMaquina.add(chckbxNewCheckBox_2);
        grupoNivelMaquina.add(chckbxNewCheckBox_3);
        chckbxNewCheckBox_2.setSelected(true); // Padrão: Básico

        chckbxNewCheckBox.addActionListener(e -> { // Jogador vs Jogador
            textFieldSimbolo2.setEditable(true);
            chckbxNewCheckBox_2.setEnabled(false);
            chckbxNewCheckBox_3.setEnabled(false);
            textFieldSimbolo2.setText("O"); 
        });
        chckbxNewCheckBox_1.addActionListener(e -> { // Jogador vs Máquina
            textFieldSimbolo2.setEditable(false); // Máquina usa "m"
            chckbxNewCheckBox_2.setEnabled(true);
            chckbxNewCheckBox_3.setEnabled(true);
            textFieldSimbolo2.setText("m"); // Símbolo da máquina
        });
        
        chckbxNewCheckBox_2.setEnabled(false);
        chckbxNewCheckBox_3.setEnabled(false);


        btnNewButton = new JButton("Iniciar");
        btnNewButton.setToolTipText("Iniciar / Reiniciar Jogo");
        btnNewButton.setForeground(new Color(0, 128, 192));
        btnNewButton.setBackground(new Color(255, 255, 255));
        btnNewButton.setBounds(494, 57, 100, 21);
        frame.getContentPane().add(btnNewButton);

        labelInfoJogo = new JLabel("Configurar e Iniciar Jogo", SwingConstants.CENTER);
        labelInfoJogo.setFont(new Font("Arial", Font.BOLD, 16));
        labelInfoJogo.setForeground(Color.WHITE);
        labelInfoJogo.setBounds(10, 120, frame.getWidth() - 30, 20); 
        frame.getContentPane().add(labelInfoJogo);

        createGameBoard();

        labelHistorico = new JLabel("<html>Histórico:<br></html>", SwingConstants.LEFT);
        labelHistorico.setFont(new Font("Monospaced", Font.PLAIN, 12));
        labelHistorico.setForeground(new Color(0, 0, 64));
        
        JScrollPane scrollHistorico = new JScrollPane(labelHistorico);
        scrollHistorico.setBounds(10, 480, frame.getWidth() - 30, 80); 
        frame.getContentPane().add(scrollHistorico);
        
        lblNewLabel_6 = new JLabel("New label");
        lblNewLabel_6.setIcon(new ImageIcon(Tela.class.getResource("/images/icons8-person-old-female-skin-type-4-80.png")));
        lblNewLabel_6.setBounds(106, 194, 85, 80);
        frame.getContentPane().add(lblNewLabel_6);
        
        lblNewLabel_7 = new JLabel("New label");
        lblNewLabel_7.setIcon(new ImageIcon(Tela.class.getResource("/images/icons8-person-old-female-skin-type-6-80.png")));
        lblNewLabel_7.setBounds(489, 194, 85, 80);
        frame.getContentPane().add(lblNewLabel_7);


        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarNovoJogo();
            }
        });
    }

   
    private void createGameBoard() {
        int cellSize = 80; 
        int boardWidth = TABULEIRO_DIM * cellSize + (TABULEIRO_DIM - 1) * 5; 
        int boardHeight = TABULEIRO_DIM * cellSize + (TABULEIRO_DIM - 1) * 5; 

        int d1 = (frame.getWidth() - boardWidth) / 2; 
        int d2 = 150; // 

        grid = new JLabel[TABULEIRO_DIM][TABULEIRO_DIM];

        for (int y = 0; y < TABULEIRO_DIM; y++) { 
            for (int x = 0; x < TABULEIRO_DIM; x++) { 
                final int r = y;
                final int c = x;
                final int posicaoLinear = r * TABULEIRO_DIM + c; 

                grid[r][c] = new JLabel(""); 
                grid[r][c].setFont(new Font("Arial", Font.BOLD, 60));
                grid[r][c].setHorizontalAlignment(SwingConstants.CENTER);
                grid[r][c].setBorder(new LineBorder(Color.BLACK, 2, true)); 
                grid[r][c].setOpaque(true); 
                grid[r][c].setBackground(Color.LIGHT_GRAY); 
                grid[r][c].setForeground(Color.BLACK); 
                grid[r][c].setEnabled(false); // Desabilitado até o jogo iniciar

                grid[r][c].setBounds(d1 + x * (cellSize + 5), d2 + y * (cellSize + 5), cellSize, cellSize); 

                grid[r][c].addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (grid[r][c].isEnabled()) { // 
                            jogadaHumana(posicaoLinear); 
                        }
                    }
                    
                    public void mouseEntered(MouseEvent e) {
                        if (grid[r][c].isEnabled() && grid[r][c].getText().isEmpty()) {
                            grid[r][c].setBackground(new Color(200, 200, 255)); 
                        }
                    }
                   
                    public void mouseExited(MouseEvent e) {
                        if (grid[r][c].isEnabled() && grid[r][c].getText().isEmpty()) {
                            grid[r][c].setBackground(Color.LIGHT_GRAY); 
                        }
                    }
                });
                frame.getContentPane().add(grid[r][c]); // 
            }
        }
    }


    private void iniciarNovoJogo() {
        String simbolo1 = textFieldSimbolo1.getText().trim();
        String simbolo2 = textFieldSimbolo2.getText().trim();
        int nivelMaquina = 0;

        if (chckbxNewCheckBox_1.isSelected()) {
            if (chckbxNewCheckBox_2.isSelected()) {
                nivelMaquina = 1; // Básico
            } else if (chckbxNewCheckBox_3.isSelected()) {
                nivelMaquina = 2; // Avançado
            } else {
                JOptionPane.showMessageDialog(frame, "Por favor, selecione o nível da máquina.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        if (chckbxNewCheckBox.isSelected()) { // Jogador vs Jogador
            if (simbolo1.isEmpty() || simbolo2.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Por favor, insira os símbolos para ambos os jogadores.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (simbolo1.equals(simbolo2)) {
                 JOptionPane.showMessageDialog(frame, "Os símbolos dos jogadores não podem ser iguais.", "Erro", JOptionPane.ERROR_MESSAGE);
                 return;
            }
            jogo = new JogoDaVelha(simbolo1, simbolo2);
            turnoAtual = 1; 
            labelInfoJogo.setText("Jogador 1 (" + jogo.getSimbolo(1) + ") começa!");
        } else if (chckbxNewCheckBox_1.isSelected()) { // Jogador vs Máquina
            if (simbolo1.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Por favor, insira o seu símbolo/nome.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            jogo = new JogoDaVelha(simbolo1, nivelMaquina);
            turnoAtual = 1; 
            labelInfoJogo.setText("Seu turno (" + jogo.getSimbolo(1) + ")!");
        } else { 
             JOptionPane.showMessageDialog(frame, "Por favor, selecione um modo de jogo.", "Erro", JOptionPane.ERROR_MESSAGE);
             return;
        }

        for (int r = 0; r < TABULEIRO_DIM; r++) {
            for (int c = 0; c < TABULEIRO_DIM; c++) {
                grid[r][c].setText("");
                grid[r][c].setEnabled(true); 
                grid[r][c].setBackground(Color.LIGHT_GRAY); 
                grid[r][c].setForeground(Color.BLACK); 
            }
        }
        atualizarTabuleiroUI(); 
        atualizarHistoricoUI();
    }

    private void jogadaHumana(int posicaoLinear) {
        if (jogo == null) {
            JOptionPane.showMessageDialog(frame, "Por favor, inicie um novo jogo primeiro.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (jogo.terminou()) {
            JOptionPane.showMessageDialog(frame, "O jogo já terminou! Inicie um novo jogo.", "Informação", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            jogo.jogaJogador(turnoAtual, posicaoLinear); 
            
            int linha = posicaoLinear / TABULEIRO_DIM;
            int coluna = posicaoLinear % TABULEIRO_DIM;
            grid[linha][coluna].setBackground(Color.WHITE); 
            grid[linha][coluna].setEnabled(false); // Desabilita célula após jogada

            atualizarTabuleiroUI();
            atualizarHistoricoUI();

            if (jogo.terminou()) {
                finalizarJogo();
            } else {
                if (chckbxNewCheckBox.isSelected()) { // Modo Jogador vs Jogador
                    turnoAtual = (turnoAtual == 1) ? 2 : 1; 
                    labelInfoJogo.setText("Jogador " + turnoAtual + " (" + jogo.getSimbolo(turnoAtual) + ") é a sua vez!");
                } else { 
                    labelInfoJogo.setText("Máquina pensando...");
                    Timer timer = new Timer(500, new ActionListener() {
                       
                        public void actionPerformed(ActionEvent e) {
                            ((Timer)e.getSource()).stop();
                            maquinaJoga();
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Erro na Jogada", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void maquinaJoga() {
        if (jogo.terminou()) {
            finalizarJogo();
            return;
        }

        jogo.jogaMaquina(); 
        
        int ultimaPosicaoJogada = -1;
        if (!jogo.getHistorico().isEmpty()) {
        	
            ultimaPosicaoJogada = jogo.getHistorico().keySet().stream()
                                    .reduce((first, second) -> second)
                                    .orElse(-1);
        }

        if (ultimaPosicaoJogada != -1) {
            int linha = ultimaPosicaoJogada / TABULEIRO_DIM;
            int coluna = ultimaPosicaoJogada % TABULEIRO_DIM;
            grid[linha][coluna].setBackground(new Color(255, 220, 220)); 
            grid[linha][coluna].setEnabled(false); 
        }

        atualizarTabuleiroUI();
        atualizarHistoricoUI();

        if (jogo.terminou()) {
            finalizarJogo();
        } else {
            labelInfoJogo.setText("Sua vez (" + jogo.getSimbolo(1) + ")!");
        }
    }

    private void atualizarTabuleiroUI() {
        String rawFoto = jogo.getFoto();
        
      
        String cleanedFoto = rawFoto.replace(" | ", "").replace("---+---+---\n", "").replace("\n", "");

        for (int linha = 0; linha < TABULEIRO_DIM; linha++) {
            for (int coluna = 0; coluna < TABULEIRO_DIM; coluna++) {
                int posicaoLinear = linha * TABULEIRO_DIM + coluna;
                
                char simboloNaCelula = ' '; 
                if (posicaoLinear < cleanedFoto.length()) { 
                     simboloNaCelula = cleanedFoto.charAt(posicaoLinear); 
                }

                grid[linha][coluna].setText(String.valueOf(simboloNaCelula));

                if (simboloNaCelula == 'X') {
                    grid[linha][coluna].setForeground(Color.RED);
                } else if (simboloNaCelula == 'O') {
                    grid[linha][coluna].setForeground(Color.BLUE);
                } else if (simboloNaCelula == 'm') {
                    grid[linha][coluna].setForeground(Color.MAGENTA.darker());
                } else {
                    grid[linha][coluna].setForeground(Color.BLACK); 
                }
    
                if (simboloNaCelula != ' ') {
                    grid[linha][coluna].setEnabled(false);
                } else {
                    grid[linha][coluna].setText("");
                    grid[linha][coluna].setEnabled(true);
                    grid[linha][coluna].setBackground(Color.LIGHT_GRAY);
                }
            }
        }
    }

    private void atualizarHistoricoUI() {
        LinkedHashMap<Integer, String> historicoJogo = jogo.getHistorico();
        StringBuilder sb = new StringBuilder("<html>Histórico:<br>");
        int jogadaNum = 1;
        for (java.util.Map.Entry<Integer, String> entry : historicoJogo.entrySet()) {
            sb.append("J").append(jogadaNum++).append(": Pos ").append(entry.getKey())
              .append(" (").append(entry.getValue()).append(")<br>");
        }
        sb.append("</html>");
        labelHistorico.setText(sb.toString());
    }

    private void finalizarJogo() {
        int resultado = jogo.getResultado();
        String mensagem = "";

        if (resultado == 0) {
            mensagem = "Jogo terminou em empate!";
        } else if (resultado == 1) {
            mensagem = "Vitória do Jogador 1 (" + jogo.getSimbolo(1) + ")!";
        } else if (resultado == 2) {
            mensagem = "Vitória do Jogador 2 (" + jogo.getSimbolo(2) + ")!";
        }

        labelInfoJogo.setText(mensagem + " Jogo Finalizado.");
        JOptionPane.showMessageDialog(frame, mensagem, "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);

        for (int r = 0; r < TABULEIRO_DIM; r++) {
            for (int c = 0; c < TABULEIRO_DIM; c++) {
                grid[r][c].setEnabled(false);
                grid[r][c].setBackground(Color.LIGHT_GRAY); 
                grid[r][c].setForeground(Color.BLACK);      
            }
        }
    }
}