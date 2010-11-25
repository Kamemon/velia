package usp.ia.velia.graph;

import processing.core.PApplet;
import usp.ia.velia.JogadaIlegal;
import usp.ia.velia.Jogador;
import usp.ia.velia.JogadorHumano;
import usp.ia.velia.JogadorMaquina;
import usp.ia.velia.Jogo;
import usp.ia.velia.Jogador.Insignia;

@SuppressWarnings("serial")
public class Sketch extends PApplet {

    // atributos do jogo
    private Jogo jogo;
    private Tabuleiro tab;
    private Jogador humano, maquina, jogadorDaVez;
    private String cursorText = "Cursor (0,0,0)";
    private String textVictory = "";

    // rotação do modelo controlada interativamente:
    private float rotX = 0;
    private float rotY = 0;
    private float rotZ = 0;

    public void setup() {
        
        // configurações do Processing
        size(600, 600, OPENGL);         
        perspective(PI/4, 1.0f*width/height, 0.1f, 200);
        camera(30,  -20, 80,                        //  posição da câmera
                10, 10, 0,                         //  centro de atenção da câmera
                0, 1, 0);                        //  vetor vertical da câmera
        frameRate(20);

        // configurações do jogo
        this.humano = new JogadorHumano("Humano", Insignia.X);
        this.maquina = new JogadorMaquina("Máquina", Insignia.O);
        this.jogo = new Jogo(humano, maquina);
        this.tab = new Tabuleiro(this, jogo);
        this.jogadorDaVez = humano;
        Thread thread = new Thread(new AgenteScheduler());
        thread.start();
    }
    
    public void draw() {
        
        lights();
        background(0);
        
        // textos
        this.drawMessages();
        
        // movimento do mouse e teclas de câmera (w, s, a, d, q, e)
        rotateX(rotX);
        rotateY(rotY);
        rotateZ(rotZ);

        // desenha tabuleiro de acordo com estado de this.jogo
        this.tab.draw();
    }
    
    private void drawMessages() {
        
        this.textSize(10); 
        this.fill(0, 102, 153);
        this.text("VELIA", -15, -13);

        this.textSize(5); 
        this.fill(100, 102, 0);
        this.text(cursorText, 0, 40);

        this.textSize(6); 
        this.fill(150, 10, 12);
        this.text(textVictory, -25, 50);
    }
    
    // respostas de comandos do usuário
    public void keyPressed() {
            
        // controle de câmera
        this.moveCamera(key);
        
        // controle do cursor e ativação da jogada
        if (key == PApplet.ENTER) {
            this.jogada();
        } else {
            if (jogadorDaVez == humano) {
                int[] pos = this.tab.moveCursor(keyCode);
                cursorText = "Cursor ("+pos[0]+","+pos[1]+","+pos[2]+")";
            }
        }
    }
    
    private void moveCamera(int key) {

        if (key == 'a')
            rotY += -0.1;
        if (key == 'd')
                rotY += 0.1;
        if (key == 's')
                rotX += -0.1;
        if (key == 'w')
                rotX += 0.1;
        if (key == 'q')
                rotZ += -0.1;
        if (key == 'e')
                rotZ += 0.1;        
    }
    
    private void jogada() {
        
        if (jogadorDaVez == humano) {
            
            // jogada humano
            int x = this.tab.getCursorCoord()[0];
            int y = this.tab.getCursorCoord()[1];
            int z = this.tab.getCursorCoord()[2];
            try {
                this.jogo.jogar(humano, x, y, z);
            } catch (JogadaIlegal e) {
                System.out.println("Jogada ilegal");
                return;
            }
            jogadorDaVez = maquina;
            this.tab.disableCursor();
            
            this.verificaTermino(humano);
        }

    }
    
    private void verificaTermino(Jogador jogador) {

        if (this.jogo.isFinished()) { // acabou
            
            this.tab.finish(jogo.getRisca());
            Jogador vencedor = this.jogo.getVencedor();
            textVictory = vencedor.getNome() + " venceu";
        }
    }
    
    private class AgenteScheduler implements Runnable {

        @Override
        public void run() {
            
            while(true) {
                
                try {
                    Thread.sleep(10); // loops infinitos contínuos não são bons
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                
                if (jogadorDaVez == maquina && !jogo.isFinished()) {
                    
                    // agora é vez da máquina jogar
                    int[] pos = maquina.escolheJogada(jogo);
                    try {
                        jogo.jogar(maquina, pos[0], pos[1], pos[2]);
                    } catch (JogadaIlegal e) {
                        e.printStackTrace();
                    }
        
                    verificaTermino(maquina);
        
                    jogadorDaVez = humano;
                    // volta o cursor
                    tab.backCursor();
                    tab.enableCursor();
                }        
            }
        }
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
