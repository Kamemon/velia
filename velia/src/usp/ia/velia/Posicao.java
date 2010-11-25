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

    static public Posicao moddif(Posicao a,Posicao b){
    	//System.out.println(a.coord[0]);
    	//System.out.println(b.coord[0]);
    	
    	return new Posicao( Math.abs(a.coord[0]-b.coord[0]),Math.abs(a.coord[1]-b.coord[1]),Math.abs(a.coord[2]-b.coord[2]));
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
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(coord);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Posicao other = (Posicao) obj;
		if (!Arrays.equals(coord, other.coord))
			return false;
		return true;
	}

//	public boolean equals(Object to){
//    	if(coord[0]==to.coord[0] && coord[1]==to.coord[1] && coord[2]==to.coord[2])return true;
//    	return false;
//    }
    
    
    
}
