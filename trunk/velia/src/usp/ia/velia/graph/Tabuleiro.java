package usp.ia.velia.graph;

import processing.core.PApplet;
import usp.ia.velia.Jogador;
import usp.ia.velia.Jogo;
import usp.ia.velia.Jogador.Insignia;

public class Tabuleiro {
        
    private final int N = 3;
    private final int e = 10; // espaçamento
    
    private PApplet sketch;
    private Jogo jogo;
    private Cursor cursor;
    private int[][] risca; // preenchida caso alguém tenha ganho
    
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
                
        // desenha divisões (linhas)
        
        this.sketch.stroke(0, 255, 0);

        this.sketch.line(-10, 5, 5, 30, 5, 5);
        this.sketch.line(-10, 5, 15, 30, 5, 15);

        this.sketch.line(-10, 15, 5, 30, 15, 5);
        this.sketch.line(-10, 15, 15, 30, 15, 15);

        this.sketch.stroke(0, 0, 255);

        this.sketch.line(5, -10, 5, 5, 30, 5);
        this.sketch.line(5, -10, 15, 5, 30, 15);

        this.sketch.line(15, -10, 5, 15, 30, 5);
        this.sketch.line(15, -10, 15, 15, 30, 15);
        
        this.sketch.stroke(0, 255, 0);

        this.sketch.line(15, 15, -10, 15, 15, 30);
        this.sketch.line(5, 15, -10, 5, 15, 30);
        
        this.sketch.line(5, 5, -10, 5, 5, 30);
        this.sketch.line(15, 5, -10, 15, 5, 30);

        // desenha insígnias
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                for (int k=0; k<N; k++) {
                    
                    this.sketch.stroke(0, 0, 0);
                    this.sketch.fill(255,255,255);
                    this.sketch.translate(i*e, j*e, k*e); // posicionamento da insígnia
                    
                    if (tab[i][j][k] != null) { // posição não vazia
                        
                        Jogador jog = tab[i][j][k];
                        
                        if (this.isInRisca(i, j, k)) { // destaca risca da vitória
                            this.sketch.stroke(255, 0, 0);
                            this.sketch.fill(255, 0, 0);
                        }
                        if (this.isInCursor(i, j, k)) { // evita que usuário perca o cursor
                            this.sketch.stroke(255, 255, 0);                            
                            this.sketch.fill(255, 255, 0);
                        }
                        
                        if (jog.getInsignia() == Insignia.X) 
                            this.sketch.box(3);
                        if (jog.getInsignia() == Insignia.O) 
                            this.sketch.sphere(2);
                    } 
                                        
                    this.sketch.translate(-i*e, -j*e, -k*e);
                }
            }
        }
        
        // desenha a risca
        if (risca != null)
            this.drawRisca();
        
        // desenha o cursor
        this.cursor.draw();
    }
    
    private boolean isInCursor(int x, int y, int z) {

        if (cursor.getCoord()[0] == x && cursor.getCoord()[1] == y && cursor.getCoord()[2] == z)
            return true;
        else
            return false;
    }

    /**
     * Desenha uma linha passando pelas marcas da risca
     */
    private void drawRisca() {
       
        this.sketch.stroke(255, 0, 0);
        int x1 = risca[0][0];
        int y1 = risca[0][1];
        int z1 = risca[0][2];
        int x2 = risca[2][0];
        int y2 = risca[2][1];
        int z2 = risca[2][2];
        this.sketch.line(x1*e, y1*e, z1*e, x2*e, y2*e, z2*e);
    }

    /**
     * Move o cursor pelo tabuleiro de acordo com o comando do usuário
     * Não deixa cursor ir "além tabuleiro"
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
            this.cursor.setCoord(x, y, z);
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

    /**
     * Faz a risca nas marcas que determinaram a vitória
     * @param risca coordenadas das marcas (insígnias)
     */
    public void finish(int[][] risca) {

        this.risca = risca;
    }
    
    private boolean isInRisca(int x, int y, int z) {
        
        if (risca != null) {
            
            for (int m=0; m<3; m++) {
                int[] marca = risca[m];
                if (marca[0]==x && marca[1]==y && marca[2]==z)
                    return true;
            }
        }
        return false;
    }
    
    public int[] getCursorCoord() {
        
        return this.cursor.getCoord();
    }
}
