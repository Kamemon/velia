package usp.ia.velia.graph;

import processing.core.PApplet;
import usp.ia.velia.JogadaIlegal;
import usp.ia.velia.Jogador;
import usp.ia.velia.Jogo;
import usp.ia.velia.Jogador.Insignia;

@SuppressWarnings("serial")
public class Sketch extends PApplet {

    // atributos do jogo
    private Jogo jogo;
    private Tabuleiro tab;
    private Jogador jog1, jog2;

    // rotação do modelo controlada interativamente:
    float rotX = 0;
    float rotY = 0;
    float rotZ = 0;

    public void setup() {
        
        // configurações do Processing
        size(600, 600, OPENGL);         
        perspective(PI/4, 1.0f*width/height, 0.1f, 200);
        camera(30,  -20, 70,                        //  posição da câmera
                10, 10, 0,                         //  centro de atenção da câmera
                0, 1, 0);                        //  vetor vertical da câmera
        frameRate(20);

        // configurações do jogo
        this.jog1 = new Jogador("Humano", Insignia.X);
        this.jog2 = new Jogador("PC", Insignia.O);
        this.jogo = new Jogo();
        this.tab = new Tabuleiro(this, jogo);
        
        // rotina pra teste
        try {
            jogo.jogar(jog1, 0, 0, 0);
            jogo.jogar(jog1, 0, 2, 0);
            jogo.jogar(jog1, 2, 0, 0);
            jogo.jogar(jog1, 2, 2, 0);
            jogo.jogar(jog2, 1, 0, 1);
            jogo.jogar(jog2, 1, 2, 1);
            jogo.jogar(jog2, 0, 1, 1);
            jogo.jogar(jog2, 2, 1, 1);
            jogo.jogar(jog1, 0, 0, 2);
            jogo.jogar(jog1, 0, 2, 2);
            jogo.jogar(jog1, 2, 0, 2);
            jogo.jogar(jog1, 2, 2, 2);
        } catch (JogadaIlegal e) {
            e.printStackTrace();
        }
    }
    
    public void draw() {
        
        lights();
        background(0);

        // movimento do mouse e teclas de câmera (w, s, a, d, q, e)
        rotateX(rotX);
        rotateY(rotY);
        rotateZ(rotZ);

        // desenha tabuleiro
        this.tab.draw();
    }
    
    // respostas de comandos do usuário
    public void keyPressed() {
    
        // controle de câmera
        if (key == 'a')
                rotY += -0.1;
        if (key == 'd')
                rotY += 0.1;
        if (key == 'w')
                rotX += -0.1;
        if (key == 's')
                rotX += 0.1;
        if (key == 'q')
                rotZ += -0.1;
        if (key == 'e')
                rotZ += 0.1;
    }
    
    /**
     * Usando o processing processing-0191 (versão não-estável)
     * Download em:
     * http://code.google.com/p/processing/downloads/detail?name=processing-0191.tgz&can=2&q=
     * 
     * Rodar com VM Arguments = -Djava.library.path=<PROCESSING_HOME>/libraries/opengl/library
     *  
     */
    public static void main(String[] args) {
        
        PApplet.main(new String[] { "usp.ia.velia.graph.Sketch" });
    }

}
