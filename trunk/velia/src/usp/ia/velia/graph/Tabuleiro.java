package usp.ia.velia.graph;

import processing.core.PApplet;
import usp.ia.velia.Jogador;
import usp.ia.velia.Jogo;
import usp.ia.velia.Jogador.Insignia;

public class Tabuleiro {
    
    private final int N = 3;
    
    private Jogo jogo;
    private PApplet processing;
    
    
    public Tabuleiro(Jogo jogo, PApplet processing) {
        this.jogo = jogo;
        this.processing = processing;
    }

    
    /**
     * Desenha o tabuleiro no Processing
     */
    public void draw() {
    
        Jogador[][][] tab = this.jogo.viewTabuleiro(); 
        
        int e = 10; // espaçamento
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                for (int k=0; k<N; k++) {
                    
                    this.processing.translate(i*e, j*e, k*e);
                    
                    if (tab[i][j][k] != null) { // posição não vazia
                        Jogador jog = tab[i][j][k]; 
                        if (jog.getInsignia() == Insignia.X)
                            this.processing.box(3);
                        if (jog.getInsignia() == Insignia.O)
                            this.processing.sphere(3);
                    }
                }
            }
        }

    }
}
