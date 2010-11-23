package usp.ia.velia;

import java.util.Arrays;

public class Posicao {
	
    private int N = 3;
    private int[] coord = new int[N];
    
    public Posicao(int x, int y, int z) {
        
        this.coord = new int[N];
        this.coord[0] = x;
        this.coord[1] = y;
        this.coord[2] = z;
    }

    public Posicao() {

    }

    public int[] getCoord() {
        return Arrays.copyOf(coord,N);
    }

    @Override
    public String toString() {
        int x = coord[0];
        int y = coord[1];
        int z = coord[2];
        return "("+x+","+y+","+z+")";
    }
    
    
    
}
