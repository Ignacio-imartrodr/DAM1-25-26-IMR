package Extras;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;

public class RecorridoRobotGUI extends JFrame {


/**
 * Adaptación gráfica de RecorridoRobot
 */

    public RecorridoRobotGUI() {
        this.setTitle("Robot Puzzle GUI");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        
        // Agregamos el panel del juego
        PanelDeJuego panel = new PanelDeJuego();
        this.add(panel);
        
        this.pack(); // Ajusta la ventana al tamaño del panel
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        // Suavizado de bordes para gráficos
        System.setProperty("sun.java2d.opengl", "true"); 
        SwingUtilities.invokeLater(() -> new RecorridoRobotGUI());
    }
}

class GamePanel extends JPanel implements ActionListener {

    // --- CONFIGURACIÓN ---
    final int TILE_SIZE = 64; // Tamaño de cada cuadro
    final int COLS = 16;
    final int ROWS = 8;
    final int W = COLS * TILE_SIZE;
    final int H = ROWS * TILE_SIZE;

    // --- ESTADO DEL JUEGO ---
    Timer timer;
    boolean executing = false;
    boolean gameOver = false;
    boolean win = false;
    String message = ""; // Mensaje de estado (Ganaste/Perdiste)
    
    // --- DATOS DEL NIVEL ---
    char[][] map; // Usamos char[][] en lugar de String[] para modificar placas fácilmente
    int level = 0;
    int lvlMax = 1; // Número máximo de niveles (ajustable)

    // --- ROBOT ---
    int robotRow, robotCol;
    int robotDir; // 0: Arriba, 1: Derecha, 2: Abajo, 3: Izquierda
    
    // --- LÓGICA DE INSTRUCCIONES ---
    String instructionQueue = "";
    int currentInstructionIndex = 0;
    int tickCounter = 0;
    final int SPEED = 30; // Velocidad de ejecución (menor es más rápido)

    // --- INTERFAZ UI (Input) ---
    JTextField inputField;
    JButton runButton;

    public GamePanel() {
        this.setPreferredSize(new Dimension(W, H + 50)); // +50 para la barra de abajo
        this.setBackground(new Color(30, 30, 35));
        this.setFocusable(true);
        this.setLayout(null); // Layout nulo para posicionar manual los controles (simple)

        // Configurar UI
        setupUI();

        // Cargar Nivel Inicial
        loadLevel(0);

        // Iniciar Loop de Renderizado y Lógica
        timer = new Timer(16, this); // ~60 FPS
        timer.start();
    }

    private void setupUI() {
        // Campo de Texto
        inputField = new JTextField();
        inputField.setBounds(10, H + 10, 300, 30);
        inputField.setBackground(Color.WHITE);
        inputField.setForeground(Color.BLACK);
        inputField.setFont(new Font("Consolas", Font.BOLD, 14));
        inputField.setToolTipText("Escribe instrucciones: A, L, R");
        this.add(inputField);

        // Botón Ejecutar
        runButton = new JButton("EJECUTAR");
        runButton.setBounds(320, H + 10, 100, 30);
        runButton.setBackground(new Color(0, 150, 0));
        runButton.setForeground(Color.WHITE);
        runButton.addActionListener(e -> startExecution());
        this.add(runButton);

        // Botón Reset
        JButton resetButton = new JButton("RESET");
        resetButton.setBounds(430, H + 10, 80, 30);
        resetButton.setBackground(new Color(150, 50, 50));
        resetButton.setForeground(Color.WHITE);
        resetButton.addActionListener(e -> loadLevel(level));
        this.add(resetButton);
    }

    private void loadLevel(int lvl) {
        this.level = lvl;
        String[] rawMap = mapaBase(lvl);
        
        // Convertir String[] a char[][] y buscar al robot
        map = new char[rawMap.length][rawMap[0].length()];
        
        for (int r = 0; r < rawMap.length; r++) {
            for (int c = 0; c < rawMap[r].length(); c++) {
                char cell = rawMap[r].charAt(c);
                if (cell == 'A') { // 'A' es el inicio (Start)
                    robotRow = r;
                    robotCol = c;
                    map[r][c] = ' '; // Borramos la 'A' del mapa, el robot se dibuja aparte
                } else {
                    map[r][c] = cell;
                }
            }
        }
        
        robotDir = 0; // Empieza mirando arriba
        executing = false;
        gameOver = false;
        win = false;
        message = "Nivel " + (lvl + 1);
        currentInstructionIndex = 0;
        inputField.setEnabled(true);
        runButton.setEnabled(true);
        repaint();
    }

    private void startExecution() {
        loadLevel(level);
        String text = inputField.getText().toUpperCase().replace(" ", "");
        if (text.isEmpty()) return;
        
        instructionQueue = text;
        currentInstructionIndex = 0;
        executing = true;
        gameOver = false;
        win = false;
        message = "Ejecutando...";
        inputField.setEnabled(false);
        runButton.setEnabled(false);
        this.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Esta función se ejecuta 60 veces por segundo
        if (executing && !gameOver && !win) {
            tickCounter++;
            if (tickCounter >= SPEED) { // Controlamos la velocidad de pasos
                tickCounter = 0;
                executeNextStep();
            }
        }
        repaint(); // Siempre redibujamos
    }

    private void executeNextStep() {
        if (currentInstructionIndex >= instructionQueue.length()) {
            checkWinCondition();
            executing = false;
            // Reactivamos los botones si terminó y no ganó
            if (!win) {
                inputField.setEnabled(true);
                runButton.setEnabled(true);
            }
            return;
        }

        char cmd = instructionQueue.charAt(currentInstructionIndex);
        currentInstructionIndex++;

        switch (cmd) {
            case 'A': 
                moveRobot();
                checkTileEvents(); // <--- CORRECCIÓN: Solo comprobamos placas al MOVERSE
                break;
            case 'L': 
                robotDir = (robotDir == 0) ? 3 : robotDir - 1; 
                break;
            case 'R': 
                robotDir = (robotDir == 3) ? 0 : robotDir + 1; 
                break;
        }
        
        // Eliminamos checkTileEvents() de aquí abajo
    }

    private void moveRobot() {
        int nextR = robotRow;
        int nextC = robotCol;

        // Calcular siguiente posición basado en dirección
        // 0: Arriba, 1: Derecha, 2: Abajo, 3: Izquierda
        if (robotDir == 0) nextR--;
        if (robotDir == 1) nextC++;
        if (robotDir == 2) nextR++;
        if (robotDir == 3) nextC--;

        // Verificar límites
        if (nextR < 0 || nextR >= map.length || nextC < 0 || nextC >= map[0].length) {
            gameOver = true;
            message = "¡Te caíste del mapa!";
            return;
        }

        robotRow = nextR;
        robotCol = nextC;
    }

    private void checkTileEvents() {
        char tile = map[robotRow][robotCol];

        if (tile == '*') {
            gameOver = true;
            message = "¡BOOM! Mina activada.";
        } else if (tile == '-') {
            map[robotRow][robotCol] = '+'; // Encender placa
        } else if (tile == '+') {
            map[robotRow][robotCol] = '-'; // Apagar placa
        }
    }

    private void checkWinCondition() {
        // Verificar si está en la meta (Z)
        if (map[robotRow][robotCol] != 'Z') {
            message = "Instrucciones terminadas. No estás en la meta.";
            executing = false;
            return;
        }

        // Verificar si todas las placas están ON (+)
        boolean allSwitchesOn = true;
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[r].length; c++) {
                if (map[r][c] == '-') {
                    allSwitchesOn = false;
                    break;
                }
            }
        }

        if (allSwitchesOn) {
            win = true;
            message = "¡NIVEL " + (level + 1) + " COMPLETADO!";
            
            // --- CORRECCIÓN: Lógica para pasar al siguiente nivel ---
            Timer nextLevelTimer = new Timer(2000, e -> {
                // Verificar si existen más niveles
                // Una forma simple de saber si se acabaron los niveles es mirar el tamaño
                // o controlar un MAX_LEVEL fijo. En tu código original lvlMax era 1.
                if (level < lvlMax) { // Asumiendo que hay nivel 0 y nivel 1
                    loadLevel(level + 1);
                } else {
                    message = "¡JUEGO TERMINADO! Eres un crack.";
                    win = true; // Mantener estado de victoria
                    executing = false;
                }
            });
            
            nextLevelTimer.setRepeats(false); // Que se ejecute solo una vez
            nextLevelTimer.start();
            
        } else {
            message = "Llegaste, pero faltan placas por activar.";
            executing = false;
            inputField.setEnabled(true);
            runButton.setEnabled(true);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Limpiar pantalla
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. Dibujar Mapa
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[r].length; c++) {
                int x = c * TILE_SIZE;
                int y = r * TILE_SIZE;
                char tile = map[r][c];

                // Dibujar Suelo (Cuadrícula)
                g2.setColor(new Color(50, 50, 55));
                g2.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                g2.setColor(new Color(70, 70, 75));
                g2.drawRect(x, y, TILE_SIZE, TILE_SIZE);

                // Dibujar Elementos
                if (tile == '*') { // Mina
                    g2.setColor(new Color(200, 50, 50));
                    g2.fillOval(x + 10, y + 10, TILE_SIZE - 20, TILE_SIZE - 20);
                    g2.setColor(Color.WHITE);
                    g2.drawString("X", x + 28, y + 38);
                } else if (tile == '-') { // Placa OFF
                    g2.setColor(Color.GRAY);
                    g2.fillRect(x + 10, y + 10, TILE_SIZE - 20, TILE_SIZE - 20);
                } else if (tile == '+') { // Placa ON
                    g2.setColor(Color.GREEN);
                    g2.fillRect(x + 10, y + 10, TILE_SIZE - 20, TILE_SIZE - 20);
                    // Efecto de brillo
                    g2.setColor(new Color(100, 255, 100, 100));
                    g2.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                } else if (tile == 'Z') { // Meta
                    g2.setColor(new Color(255, 215, 0)); // Dorado
                    g2.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                    g2.setColor(Color.BLACK);
                    g2.drawString("META", x + 15, y + 35);
                }
            }
        }

        // 2. Dibujar Robot
        drawRobot(g2);

        // 3. Dibujar UI Overlay (Mensajes)
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString(message, 20, 30);
    }

    private void drawRobot(Graphics2D g2) {
        int x = robotCol * TILE_SIZE + TILE_SIZE / 2;
        int y = robotRow * TILE_SIZE + TILE_SIZE / 2;

        AffineTransform old = g2.getTransform();
        g2.translate(x, y); // Mover origen al centro del tile del robot
        
        // Rotar según dirección (0=Up, 1=Right, 2=Down, 3=Left)
        // Math.PI / 2 es 90 grados
        g2.rotate(robotDir * (Math.PI / 2)); 

        // Cuerpo del Robot (Triángulo simple)
        g2.setColor(gameOver ? Color.GRAY : Color.CYAN); // Gris si muere, Cyan si vive
        if (win) g2.setColor(Color.MAGENTA); // Magenta si gana

        int[] xPoints = {0, 15, -15};
        int[] yPoints = {-20, 20, 20}; // Apunta hacia arriba (relativo)
        g2.fillPolygon(xPoints, yPoints, 3);
        
        // Detalle (Cabina)
        g2.setColor(Color.BLACK);
        g2.fillOval(-5, 0, 10, 10);

        g2.setTransform(old); // Restaurar rotación
    }

    // --- DATOS ORIGINALES (Adaptados para devolver String limpios) ---
    static String[] mapaBase(int lvl) {
        switch (lvl) {
            case 0:
                return new String[] {
                    "  Z -     ",
                    " * ",
                    "  * * ",
                    "      -   ",
                    " A        "
                };
            case 1:
                return new String[] {
                    "     -    Z   ",
                    "  * * ",
                    "      * ",
                    "  -       * ",
                    "       -      ",
                    " * ",
                    "A             "
                };
            default:
                return new String[] {
                    "  Z       ",
                    "          ",
                    "          ",
                    " A        "
                };
        }
    }
}
