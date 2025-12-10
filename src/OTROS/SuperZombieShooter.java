package OTROS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * @author Moder principal: Ignacio MR
 */

public class SuperZombieShooter extends JFrame {

    public SuperZombieShooter() {
        this.add(new GamePanel());
        this.setTitle("Super Zombie Survival - Java Swing");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        // Acelerar renderizado en algunos sistemas
        System.setProperty("sun.java2d.opengl", "true");
        new SuperZombieShooter();
    }
}

class GamePanel extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener {

    // --- CONSTANTES ---
    final int W = 800, H = 600;
    // --- ESTADO ---
    boolean running = true;
    Timer timer;
    Random rand = new Random();
    
    // --- JUGADOR ---
    Player player = new Player(W/2, H/2);
    ArrayList<Weapon> weapons = new ArrayList<>();
    int currentWeaponIndex = 1;

    // --- ENTIDADES ---
    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<Misil> misils = new ArrayList<>();
    ArrayList<Mine> mines = new ArrayList<>();
    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<Particle> particles = new ArrayList<>();
    ArrayList<ItemDrop> items = new ArrayList<>();

    // --- INPUT ---
    boolean w, a, s, d, shooting, plantMina, up, down, left, right, mouseOutBounds=true;
    int mouseX, mouseY;

    // --- LOGICA JUEGO ---
    int score = 0;
    int wave = 1;
    int spawnTimer = 0;

    public GamePanel() {
        this.setPreferredSize(new Dimension(W, H));
        this.setBackground(new Color(20, 20, 25));
        this.setFocusable(true);
        
        // Listeners
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        // Inicializar Armas
        
        weapons.add(new Weapon("Mina", 35, 5, 1, 0, 10)); // Mina
        weapons.add(new Weapon("Pistola 9mm", 15, 20, 1, 0, 999)); // Infinita
        weapons.add(new Weapon("Rifle Asalto", 5, 8, 1, 0.1, 120)); // Rápida
        weapons.add(new Weapon("Escopeta", 25, 21, 5, 0.3, 160));   // Dispersión
        weapons.add(new Weapon("Rail Gun", 40, 50, 1, 0, 10)); // RailGun
        weapons.add(new Weapon("Lanza Cohetes", 40, 100, 1, 0, 10)); // Lanza Cohetes

        timer = new Timer(16, this); // ~60 FPS
        timer.start();
    }

    private void spawnWave() {
        spawnTimer++;
        int rate = Math.max(20, 60 - (wave * 2)); // Spawns más rápidos cada ronda
        if (spawnTimer > rate) {
            spawnTimer = 0;
            // Spawn en bordes
            double ex, ey;
            if (rand.nextBoolean()) {
                ex = rand.nextBoolean() ? -30 : W + 30;
                ey = rand.nextInt(H);
            } else {
                ex = rand.nextInt(W);
                ey = rand.nextBoolean() ? -30 : H + 30;
            }
            enemies.add(new Enemy(ex, ey, 1.5 + (wave * 0.1), 3 + wave));
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            // Logica Jugador
            player.move(w, s, a, d, W, H);
            // Apuntar y disparar con teclas de flecha
            if ((up || down || left || right) && mouseOutBounds) {//TODO UbicaciónFlechas1
                if (up && left) {player.angle = -3 * Math.PI / 4;
                } else if (up && right) {player.angle = -Math.PI / 4;
                } else if (up) {player.angle = -Math.PI / 2;
                } else if (down && left) {player.angle = 3 * Math.PI / 4;
                } else if (down && right) {player.angle = Math.PI / 4;
                } else if (down) {player.angle = Math.PI / 2;
                } else if (left) {player.angle = Math.PI;
                } else if (right) {player.angle = 0;}
            } else if (!mouseOutBounds) player.lookAt(mouseX, mouseY);
            Weapon cw = weapons.get(currentWeaponIndex);
            cw.cooldown--;
            if (shooting && plantMina && cw.cooldown <= 0 && cw.ammo > 0) {
                plantMine(cw);
            } else if (shooting && cw.cooldown <= 0 && cw.ammo > 0) {
                fireWeapon(cw);
            }

            // Actualizar Balas
            Iterator<Bullet> bit = bullets.iterator();
            while(bit.hasNext()){
                Bullet b = bit.next();
                b.update();
                if(b.outOfBounds(W, H)) bit.remove();
            }
            
            // Actualizar Minas
            Iterator<Mine> mit = mines.iterator();
            while(mit.hasNext()){
                Mine m = mit.next();
                if(m.outOfBounds(W, H)) mit.remove();
            }

            // Actualizar Enemigos
            Iterator<Enemy> eit = enemies.iterator();
            while(eit.hasNext()){
                Enemy en = eit.next();
                en.chase(player);
                
                // Colisión con Jugador
                if(en.getBounds().intersects(player.getBounds())){
                    player.hp -= 1;
                    createBlood(player.x, player.y, Color.RED, 2);
                    if(player.hp <= 0) running = false;
                }

                // Colisión Minas vs Enemigos
                Iterator<Mine> mineIt = mines.iterator();
                while(mineIt.hasNext()){
                    Mine mine = mineIt.next();
                    if(en.getBounds().intersects(mine.getBounds())){//TODO Al colisionar con una Mina lanzar 6 balas
                        en.hp -= mine.damage;
                        createBlood(en.x, en.y, Color.GREEN, 5); // Sangre zombie verde
                        mineIt.remove();
                        bullets.add(new Bullet(mine.x, mine.y, 0, 6));//Derecha
                        bullets.add(new Bullet(mine.x, mine.y, -Math.PI / 4, 6));//Arriba derecha
                        bullets.add(new Bullet(mine.x, mine.y, -3 * Math.PI / 4, 6));//Arriba izquierda
                        bullets.add(new Bullet(mine.x, mine.y, Math.PI, 6));//Izquierda
                        bullets.add(new Bullet(mine.x, mine.y, 3 * Math.PI / 4, 6));//Abajo izquierda
                        bullets.add(new Bullet(mine.x, mine.y, Math.PI / 4, 6));//Abajo derecha
                        if(en.hp <= 0){//Si el enemigo muere
                            // Drop Item chance 20%
                            if(rand.nextInt(100) < 20) {
                                int type = rand.nextBoolean() ? 0 : 1; // 0 HP, 1 Ammo
                                items.add(new ItemDrop(en.x, en.y, type));
                            }
                            score++;
                            if(score % 15 == 0) wave++;
                            eit.remove();
                            break;
                        }
                    }
                }

                // Colisión Balas vs Enemigos
                Iterator<Bullet> bulIt = bullets.iterator();
                while(bulIt.hasNext()){
                    Bullet b = bulIt.next();
                    if(en.getBounds().intersects(b.getBounds())){//TODO IDEA: la colisión es con un rectangulo en lugar de con un punto
                        en.hp -= b.damage;
                        createBlood(en.x, en.y, Color.GREEN, 5); // Sangre zombie verde
                        
                        // Si es el arma de misiles, generar explosión en el punto de impacto
                        if (currentWeaponIndex == 5){
                            // Crear misil estático que daña enemigos cercanos
                            Misil explosion = new Misil(b.x, b.y, 0); // No necesita dirección, explota en lugar
                            misils.add(explosion);
                            bulIt.remove();
                        } else if (currentWeaponIndex != 4) {
                            bulIt.remove();
                        }
                        
                        if(en.hp <= 0){
                            // Drop Item chance 20%
                            if(rand.nextInt(100) < 20) {
                                int type = rand.nextBoolean() ? 0 : 1; // 0 HP, 1 Ammo
                                items.add(new ItemDrop(en.x, en.y, type));
                            }
                            score++;
                            if(score % 15 == 0) wave++;
                            eit.remove();
                            break;
                        }
                    }
                }
            }

            // Colisión Misiles vs Enemigos (procesado DESPUÉS de todas las balas)
            if(!misils.isEmpty()) {
                Iterator<Enemy> eit2 = enemies.iterator();
                while(eit2.hasNext()){
                    Enemy en = eit2.next();
                    // Iterar sobre copia para evitar problemas
                    for(Misil m : new ArrayList<>(misils)){
                        m.update();
                        if(en.getBounds().intersects(m.getBounds())){
                            en.hp -= m.damage;
                            createBlood(en.x, en.y, Color.GREEN, 5); // Sangre zombie verde
                            if(en.hp <= 0){
                                // Drop Item chance 20%
                                if(rand.nextInt(100) < 20) {
                                    int type = rand.nextBoolean() ? 0 : 1; // 0 HP, 1 Ammo
                                    items.add(new ItemDrop(en.x, en.y, type));
                                }
                                score++;
                                if(score % 15 == 0) wave++;
                                eit2.remove();
                                break;
                            }
                        }
                    }
                }
                // Limpiar misiles cuya vida se ha agotado
                Iterator<Misil> misilCleanup = misils.iterator();
                while(misilCleanup.hasNext()){
                    Misil m = misilCleanup.next();
                    if(m.lifetime <= 0){
                        misilCleanup.remove();
                    }
                }
            }

            // Actualizar Items
            Iterator<ItemDrop> iit = items.iterator();
            while(iit.hasNext()){
                ItemDrop item = iit.next();
                if(player.getBounds().intersects(item.getBounds())){
                    if(item.type == 0) player.hp = Math.min(100, player.hp + 25);
                    else {
                        // Dar municion a todas las armas menos pistola
                        weapons.get(0).ammo += 2;
                        weapons.get(2).ammo += 60;
                        weapons.get(3).ammo += 15;
                        weapons.get(4).ammo += 5;
                        weapons.get(5).ammo += 2;
                    }
                    iit.remove();
                }
            }

            // Particulas
            Iterator<Particle> pit = particles.iterator();
            while(pit.hasNext()){
                Particle p = pit.next();
                p.update();
                if(p.life <= 0) pit.remove();
            }

            spawnWave();
        }
        repaint();
    }

    private void fireWeapon(Weapon w) {
        w.cooldown = w.fireRate;
        if(w.maxAmmo != 999) w.ammo--;
        /* Retroceso sin considerar dircción bullet
        if(player.x > 0 && player.x < W) player.x -= Math.cos(player.angle) * 2;
        if(player.y > 0 && player.y < H) player.y -= Math.sin(player.angle) * 2;
        */
        for(int i=0; i<w.pellets; i++) {
            double spread = (rand.nextDouble() - 0.5) * w.spread;

            // Retroceso por dirección bullet
            if(player.x > 0 && player.x < W) player.x -= Math.cos(player.angle + spread) * 2;
            if(player.y > 0 && player.y < H) player.y -= Math.sin(player.angle + spread) * 2;
            
            // Calcular dispersión y dirección
            bullets.add(new Bullet(player.x, player.y, player.angle + spread, currentWeaponIndex == 4 ? 25 : currentWeaponIndex == 5 ? 6 : 12));
        }
    }

    private void plantMine(Weapon w) {
        w.cooldown = w.fireRate;
        if(w.maxAmmo != 999) w.ammo--;
        for(int i=0; i<w.pellets; i++) {
            mines.add(new Mine(player.x, player.y));
        }
    }

    private void createBlood(double x, double y, Color c, int amount) {
        for(int i=0; i<amount; i++) {
            particles.add(new Particle(x, y, c));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Cámara: seguir al jugador suavemente (simple offset)
        // Para simplificar, mantenemos camara fija pero dibujamos escenario
        
        // Dibujar Suelo (Grid simple)
        g2.setColor(new Color(30, 30, 35));
        for(int i=0; i<W; i+=50) g2.drawLine(i, 0, i, H);
        for(int i=0; i<H; i+=50) g2.drawLine(0, i, W, i);

        // Items
        for(ItemDrop i : items) i.draw(g2);

        // Particulas (Sangre)
        for(Particle p : particles) p.draw(g2);

        // Jugador
        AffineTransform old = g2.getTransform();
        g2.translate(player.x, player.y);
        g2.rotate(player.angle);
        
        // Cuerpo
        g2.setColor(Color.CYAN);
        g2.fillOval(-12, -12, 24, 24);

        ////TODO implementar una imagen del ordenador como cuerpo del jugador
            

        // Arma
        g2.setColor(Color.GRAY);
        g2.fillRect(5, -4, 20, 8);
        
        g2.setTransform(old);

        // Enemigos
        for(Enemy e : enemies) e.draw(g2);

        // Balas
        g2.setColor(new Color(255, 200, 0));
        for(Bullet b : bullets) g2.fillOval((int)b.x-3, (int)b.y-3, 6, 6);

        //Misiles
        g2.setColor(new Color(255, 200, 0));
        for(Misil mis : misils) g2.fillOval((int)mis.x-18, (int)mis.y-18, 36, 36);
        
        // Minas
        g2.setColor(new Color(200, 200, 200));
        for(Mine m : mines) g2.fillOval((int)m.x-6, (int)m.y-6, 12, 12);
        g2.setColor(new Color(255, 0, 0));
        for(Mine m : mines) g2.fillRect((int)m.x-2, (int)m.y-2, 4, 4);
        
        // Efecto de Oscuridad (Linterna)
        drawLighting(g2);

        // UI (HUD)
        drawHUD(g2);

        if(!running) drawGameOver(g2);
    }

    private void drawLighting(Graphics2D g2) {
        // Crea un degradado radial desde el jugador hacia afuera
       /* Point2D center = new Point2D.Float((float)player.x, (float)player.y);
        float radius = 250;
        float[] dist = {0.0f, 1.0f};
        Color[] colors = {new Color(0,0,0,0), new Color(0,0,0, 220)}; // Transparente a Negro
        
        RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);
        g2.setPaint(p);
        g2.fillRect(0, 0, W, H);*/
    }

    private void drawHUD(Graphics2D g2) {
        // Fondo Panel
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(10, H-60, 300, 50);
        g2.setColor(Color.WHITE);
        g2.drawRect(10, H-60, 300, 50);

        // Arma
        g2.setFont(new Font("Consolas", Font.BOLD, 16));
        Weapon w = weapons.get(currentWeaponIndex);
        g2.drawString("Arma: " + w.name, 20, H-40);
        String ammoTxt = (w.maxAmmo >= 999) ? "INF" : w.ammo + "";
        g2.drawString("Munición: " + ammoTxt, 20, H-20);

        // Vida
        g2.setColor(Color.RED);
        g2.fillRect(195, H-40, 100, 10);
        g2.setColor(Color.GREEN);
        int hpBar = (int)(100 * ((double)player.hp / 100));
        g2.fillRect(195, H-40, Math.max(0, hpBar), 10);
        g2.setColor(Color.WHITE);
        g2.drawRect(195, H-40, 100, 10);
        
        // Score & Wave
        g2.drawString("Kills: " + score, 20, 30);
        g2.drawString("Horda: " + wave, 20, 50);
        g2.setColor(Color.YELLOW);
        g2.drawString("[1] Pistola  [2] Rifle  [3] Escopeta  [4] Rail Gun", W-475, H-45);
        g2.drawString("[5] Lanza Cohetes  [M1] Disparar  [M2+M1] Mina", W-475, H-15);
    }

    private void drawGameOver(Graphics2D g2) {
        g2.setColor(new Color(0,0,0,200));
        g2.fillRect(0, 0, W, H);
        g2.setColor(Color.RED);
        g2.setFont(new Font("Arial", Font.BOLD, 60));
        String t = "MUERTO";
        int tw = g2.getFontMetrics().stringWidth(t);
        g2.drawString(t, W/2 - tw/2, H/2);
        
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        g2.drawString("Puntuación Final: " + score, W/2 - 80, H/2 + 50);
    }

    // --- INPUT HANDLING ---
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        if(k==KeyEvent.VK_W) w=true;
        if(k==KeyEvent.VK_S) s=true;
        if(k==KeyEvent.VK_A) a=true;
        if(k==KeyEvent.VK_D) d=true;
        if(k==KeyEvent.VK_1) currentWeaponIndex = 1;
        if(k==KeyEvent.VK_2) currentWeaponIndex = 2;
        if(k==KeyEvent.VK_3) currentWeaponIndex = 3;
        if(k==KeyEvent.VK_4) currentWeaponIndex = 4;
        if(k==KeyEvent.VK_5) currentWeaponIndex = 5;//El index 0 es la Mina con M2
        if(k==KeyEvent.VK_UP) {if (mouseOutBounds) shooting = true; up=true;}
        if(k==KeyEvent.VK_DOWN) {if (mouseOutBounds) shooting = true; down=true;}
        if(k==KeyEvent.VK_LEFT) {if (mouseOutBounds) shooting = true; left=true;}
        if(k==KeyEvent.VK_RIGHT) {if (mouseOutBounds) shooting = true; right=true;}
        //TODO Ubicacion Keys
    }
    public void keyReleased(KeyEvent e) {
        int k = e.getKeyCode();
        if(k==KeyEvent.VK_W) w=false;
        if(k==KeyEvent.VK_S) s=false;
        if(k==KeyEvent.VK_A) a=false;
        if(k==KeyEvent.VK_D) d=false;
        if(k==KeyEvent.VK_UP) {if(!down && !left && !right) shooting = false; up=false;}
        if(k==KeyEvent.VK_DOWN) {if(!up && !left && !right) shooting = false; down=false;}
        if(k==KeyEvent.VK_LEFT) {if(!down && !up && !right) shooting = false; left=false;}
        if(k==KeyEvent.VK_RIGHT) {if(!down && !left && !up) shooting = false; right=false;}
    }
    int prevWeaponIndex;
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) shooting = true;
        if(e.getButton() == MouseEvent.BUTTON3) {plantMina = true; prevWeaponIndex = currentWeaponIndex; currentWeaponIndex = 0;}

    }
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) shooting = false;
        if(e.getButton() == MouseEvent.BUTTON3) {plantMina = false; currentWeaponIndex = prevWeaponIndex;}
    }
    public void mouseMoved(MouseEvent e) { mouseX=e.getX(); mouseY=e.getY(); }
    public void mouseDragged(MouseEvent e) { mouseX=e.getX(); mouseY=e.getY(); }
    public void keyTyped(KeyEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {mouseOutBounds = false;}
    public void mouseExited(MouseEvent e) {mouseOutBounds = true;}
}

// --- CLASES DE ENTIDADES ---


class Player {
    double x, y, angle;
    int hp = 100;
    Player(int x, int y) { this.x = x; this.y = y; }
    
    void move(boolean w, boolean s, boolean a, boolean d, int width, int height) {
        double speed = 3.5;
        if(w && y > 0) y -= speed;
        if(s && y < height) y += speed;
        if(a && x > 0) x -= speed;
        if(d && x < width) x += speed;
    }
    void lookAt(int mx, int my) {
        angle = Math.atan2(my - y, mx - x);
    }
    Rectangle getBounds() { return new Rectangle((int)x-12, (int)y-12, 24, 24); }
}

class Weapon {
    String name;
    int cooldown, fireRate, damage, pellets, ammo, maxAmmo;
    double spread;
    Weapon(String n, int fr, int dmg, int p, double s, int ma) {
        name = n; fireRate = fr; damage = dmg; pellets = p; spread = s; maxAmmo = ma; ammo = ma;
    }
}

class Enemy {
    double x, y, speed;
    int hp, maxHp;
    Enemy(double x, double y, double s, int hp) {
        this.x = x; this.y = y; this.speed = s; this.hp = hp; maxHp = hp;
    }
    void chase(Player p) {
        double angle = Math.atan2(p.y - y, p.x - x);
        x += Math.cos(angle) * speed;
        y += Math.sin(angle) * speed;
    }
    void draw(Graphics2D g) {
        g.setColor(new Color(50, 150, 50)); // Verde Zombie
        AffineTransform old = g.getTransform();
        g.translate(x, y);
        g.rotate(Math.atan2(0, 0)); // Si quisieras rotarlos
        g.fillRect(-12, -12, 24, 24);
        
        // Barra vida
        g.setColor(Color.RED);
        g.fillRect(-12, -20, 24, 4);
        g.setColor(Color.GREEN);
        g.fillRect(-12, -20, (int)(24 * ((double)hp/maxHp)), 4);
        
        g.setTransform(old);
    }
    Rectangle getBounds() { return new Rectangle((int)x-12, (int)y-12, 24, 24); }
}

class Bullet {
    double x, y, dx, dy;
    int damage;
    Bullet(double x, double y, double angle, double speed) {
        this.x = x; this.y = y;
        this.dx = Math.cos(angle) * speed;
        this.dy = Math.sin(angle) * speed;
        this.damage = 10; // Base damage
    }
    void update() { x += dx; y += dy; }
    boolean outOfBounds(int w, int h) { return x<0 || x>w || y<0 || y>h; }
    Rectangle getBounds() { return new Rectangle((int)x-3, (int)y-3, 6, 6); }
}
class Misil {
    double x, y;
    int damage;
    int lifetime = 60; // Durará 30 frames (0.5 segundos a 60 FPS)
    Misil(double x, double y, double angle) {
        this.x = x; this.y = y;
        this.damage = 50; // Base damage
    }
    void update() {
        lifetime--;
    }
    Rectangle getBounds() { return new Rectangle((int)x-18, (int)y-18, 36, 36); }
}

class Mine{
    double x, y;
    int damage;
    Mine(double x, double y) {
        this.x = x; this.y = y;
        this.damage = 5; // Base damage
    }
    boolean outOfBounds(int w, int h) { return x<0 || x>w || y<0 || y>h; }
    Rectangle getBounds() { return new Rectangle((int)x-6, (int)y-6, 12, 12); }
}

class Particle {
    double x, y, dx, dy;
    float life = 1.0f;
    Color color;
    Particle(double x, double y, Color c) {
        this.x = x; this.y = y; this.color = c;
        Random r = new Random();
        double angle = r.nextDouble() * Math.PI * 2;
        double speed = r.nextDouble() * 3;
        this.dx = Math.cos(angle) * speed;
        this.dy = Math.sin(angle) * speed;
    }
    void update() {
        x += dx; y += dy;
        life -= 0.05f;
    }
    void draw(Graphics2D g) {
        if(life > 0) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, life));
            g.setColor(color);
            g.fillRect((int)x, (int)y, 4, 4);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        }
    }
}

class ItemDrop {
    double x, y;
    int type; // 0 = Health, 1 = Ammo
    ItemDrop(double x, double y, int t) { this.x = x; this.y = y; type = t; }
    void draw(Graphics2D g) {
        g.setColor(type == 0 ? Color.PINK : Color.ORANGE);
        g.fillRect((int)x-8, (int)y-8, 16, 16);
        g.setColor(Color.WHITE);
        g.drawString(type == 0 ? "+" : "A", (int)x-3, (int)y+5);
    }
    Rectangle getBounds() { return new Rectangle((int)x-8, (int)y-8, 16, 16); }
}