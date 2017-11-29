package mclaudio76.astar.eightpuzzle;

public class Tile {
	private int x = 0;
	private int y = 0;
	private int v = 0;
	
	public Tile(int x, int y, int value) {
		this.x = x;
		this.y = y;
		this.v = value;
	}
	
	public Tile(int x, int y) {
		this(x,y,0);
	}
	
	public Tile(Tile a) {
		this.x = a.x;
		this.y = a.y;
		this.v = a.v;
	}

	
	public boolean isEmpty() {
		return v == 0;
	}
	
	public String toString() {
		//return " {["+x+","+y+"] : "+v+"}";
		return "  "+ (v < 10 ? "0"+v : v+"")+ " ";
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		Tile other = (Tile) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}





	public int getX() {
		return x;
	}

	
	public int getY() {
		return y;
	}

	
	public void setValue(int v) {
		this.v = v;
	}

	public int getValue() {
		return v;
	}

	
	
	
	
	
}
