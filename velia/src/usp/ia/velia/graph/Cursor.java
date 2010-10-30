package usp.ia.velia.graph;

import processing.core.PApplet;

public class Cursor {
    
    private final int N = 3;
    
    private PApplet sketch;
    private boolean enabled = true;
    
    public Cursor(PApplet sketch) {
        
        this.sketch = sketch;
        this.coord = new int[N]; // por defualt começa em (0,0,0)
    }

    private int[] coord;
    
    public int[] getCoord() {
        return coord;
    }

    public void setCoord(int[] coord) {
        this.coord = coord;
    }
    
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setCoord(int x, int y, int z) {
        this.coord = new int[N];
        this.coord[0] = x;
        this.coord[1] = y;
        this.coord[2] = z;
    }

    public void draw() {
        
        if (enabled) {
            int x = coord[0], y = coord[1], z = coord[2];
            int e = 10; // espaçamento
            this.sketch.stroke(255, 0, 0);
            this.sketch.translate(x*e, y*e, z*e); // posicionamento do cursor
            this.sketch.sphere(1);
            this.sketch.translate(-x*e, -y*e, -z*e);
        }
    }
}
