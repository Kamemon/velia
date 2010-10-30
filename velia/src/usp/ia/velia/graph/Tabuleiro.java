package usp.ia.velia.graph;

import processing.core.PApplet;
import usp.ia.velia.JogadaIlegal;
import usp.ia.velia.Jogador;
import usp.ia.velia.Jogo;
import usp.ia.velia.Jogador.Insignia;

public class Tabuleiro {
        
    private final int N = 3;
    
    private PApplet sketch;
    private Jogo jogo;
    private Cursor cursor;
    
    
    public Tabuleiro(PApplet sketch, Jogo jogo) {
        
        this.jogo = jogo;
        this.sketch = sketch;
        this.cursor = new Cursor(sketch);
    }
    
    public void enableCursor() {
        this.cursor.setEnabled(true);
    }

    public void disableCursor() {
        this.cursor.setEnabled(false);
    }
    
    /**
     * Desenha o tabuleiro no Processing
     */
    public void draw() {
    
        Jogador[][][] tab = this.jogo.viewTabuleiro(); 
        
        int e = 10; // espaçamento
        
        // desenha divisões (linhas)
        
        this.sketch.stroke(0, 255, 0);

        this.sketch.line(-10, 5, 5, 30, 5, 5);
        this.sketch.line(-10, 5, 15, 30, 5, 15);

        this.sketch.line(-10, 15, 5, 30, 15, 5);
        this.sketch.line(-10, 15, 15, 30, 15, 15);

        this.sketch.line(5, -10, 5, 5, 30, 5);
        this.sketch.line(5, -10, 15, 5, 30, 15);

        this.sketch.line(15, -10, 5, 15, 30, 5);
        this.sketch.line(15, -10, 15, 15, 30, 15);

        this.sketch.line(15, 15, -10, 15, 15, 30);
        this.sketch.line(5, 15, -10, 5, 15, 30);
        
        this.sketch.line(5, 5, -10, 5, 5, 30);
        this.sketch.line(15, 5, -10, 15, 5, 30);

        // desenha insígnias
        this.sketch.stroke(0, 0, 0);
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                for (int k=0; k<N; k++) {
                    
                    this.sketch.translate(i*e, j*e, k*e); // posicionamento da insígnia
                    
                    if (tab[i][j][k] != null) { // posição não vazia
                        Jogador jog = tab[i][j][k]; 
                        if (jog.getInsignia() == Insignia.X)
                            this.sketch.box(3);
                        if (jog.getInsignia() == Insignia.O)
                            this.sketch.sphere(2);
                    } 
                    
                    this.sketch.translate(-i*e, -j*e, -k*e);
                }
            }
        }
        
        // desenha o cursor
        this.cursor.draw();
    }
    
    /**
     * Efetua jogada para o jogador onde está marcada a insígnia
     * @param jogador
     */
    public void marcar(Jogador jogador) {
        
        int x = this.cursor.getCoord()[0];
        int y = this.cursor.getCoord()[1];
        int z = this.cursor.getCoord()[2];
        try {
            this.jogo.jogar(jogador, x, y, z);
        } catch (JogadaIlegal e) {
            e.printStackTrace();
        }
    }

    /**
     * Move o cursor pelo tabuleiro de acordo com o comando do usuário
     * @param key teclas afetam a movimentação: a, s, d, q, w, e
     */
    public void moveCursor(int key) {
        
        // posições a serem jogadas
        int x = cursor.getCoord()[0];
        int y = cursor.getCoord()[1];
        int z = cursor.getCoord()[2];
        
        switch(key) {
            case 'a': x--; break;
            case 'd': x++; break;
            case 's': z++; break;
            case 'w': z--; break;
            case 'e': y++; break;
            case 'q': y--; break;
        }
        
        // condições para movimentação válida
        if (x>=0 && x<3 && y>=0 && y<3 && z>=0 && z<3) {
            Jogador[][][] logicTab = this.jogo.viewTabuleiro();
            if (logicTab[x][y][z] == null) {
                this.cursor.setCoord(x, y, z);
            }
        }
        
    }
    
    /**
     * Move o cursor para a primeira posição livre que for encontrada
     */
    public void backCursor(){
        
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                for (int k=0; k<N; k++) {
                    if (this.jogo.viewTabuleiro()[i][j][k] == null) {
                        this.cursor.setCoord(i, j, k);
                        return;
                    }
                }
            }
        }
    }
}
