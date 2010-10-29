package usp.ia.velia.graph;

import processing.core.PApplet;
import usp.ia.velia.Jogador;
import usp.ia.velia.Jogo;
import usp.ia.velia.Jogador.Insignia;

public class Tabuleiro {
    
    private final int N = 3;
    
    private Jogo jogo;
    private PApplet sketch;
    
    
    public Tabuleiro(PApplet processing, Jogo jogo) {
        this.jogo = jogo;
        this.sketch = processing;
    }

    
    /**
     * Desenha o tabuleiro no Processing
     */
    public void draw() {
    
        Jogador[][][] tab = this.jogo.viewTabuleiro(); 
        
        int e = 10; // espaçamento
        
        // desenha divisões (linhas)
        this.sketch.stroke(255, 0, 0);
        this.sketch.line(-10, 5, 5, 30, 5, 5);
        this.sketch.line(-10, 5, 15, 30, 5, 15);
        this.sketch.line(-10, 5, 25, 30, 5, 25);

        this.sketch.line(-10, 15, 5, 30, 15, 5);
        this.sketch.line(-10, 15, 15, 30, 15, 15);
        this.sketch.line(-10, 15, 25, 30, 15, 25);

        this.sketch.line(5, -10, 5, 5, 30, 5);
        this.sketch.line(5, -10, 15, 5, 30, 15);
        this.sketch.line(5, -10, 25, 5, 30, 25);

        this.sketch.line(15, -10, 5, 15, 30, 5);
        this.sketch.line(15, -10, 15, 15, 30, 15);
        this.sketch.line(15, -10, 25, 15, 30, 25);

        

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

    }
}
