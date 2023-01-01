import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	static char[][]  map = null;
	static int[][] state = null;
	static Position start = null;
	static ArrayList<Position> cameras = new ArrayList<>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		loadMap(); // should return start position and cameras
		preprocessState();
		go();
		print();
		
	}

	private static void print() {
		// TODO Auto-generated method stub
		for (int i=0;i<map.length;i++) {
			for (int j=0;j<map[0].length;j++) {
				if (map[i][j] == '.') {
					if (state[i][j] >= 0)
						System.out.println(state[i][j]);
					else System.out.println(-1);
				}
			}
		}
	}

	private static void go() {
		// TODO Auto-generated method stub
		if (state[start.x][start.y] == -2) {
			return;
		}
		
		ArrayList<Position> current = new ArrayList<>();
		current.add(start);
		ArrayList<Position> next = new ArrayList<>();
		int step = 1;
		state[start.x][start.y] = 0;
		
		
		while (!current.isEmpty()) {
			for (Position pos:current) {
				
				// go up
				Position newPos = move(pos,-1,0, step);
				if (newPos!=null) {
					next.add(newPos);
				}
				// go down
				newPos = move(pos,1,0, step);
				if (newPos!=null) {
					next.add(newPos);
				}
				// go left
				newPos = move(pos,0,-1, step);
				if (newPos!=null) {
					next.add(newPos);
				}
				// go right
				newPos = move(pos,0,1, step);
				if (newPos!=null) {
					next.add(newPos);
				}
			}
			step++;
			current = next;
			next = new ArrayList<>();
		}
	}

	private static Position move(Position pos, int dx, int dy, int step) {
		// TODO Auto-generated method stub
		int x = pos.x+dx;
		int y = pos.y+dy;
		if (x<0 || x>=map.length || y<0 || y>=map[0].length) {
			return null;
		}
		if (map[x][y] == '.') {
			if (state[x][y] == -1) {
				state[x][y] = step;
				return new Position(x,y);
			} else {
				return null;
			}
		} else if (map[x][y] == 'U') {
			if (state[x][y] == -1) {
				state[x][y] = -2;
				return move(new Position(x,y), -1, 0, step);
			} else {
				return null;
			}
		} else if (map[x][y] == 'D') {
			if (state[x][y] == -1) {
				state[x][y] = -2;
				return move(new Position(x,y), 1, 0, step);
			} else {
				return null;
			}
		} else if (map[x][y] == 'L') {
			if (state[x][y] == -1) {
				state[x][y] = -2;
				return move(new Position(x,y), 0, -1, step);
			} else {
				return null;
			}
		} else if (map[x][y] == 'R') {
			if (state[x][y] == -1) {
				state[x][y] = -2;
				return move(new Position(x,y), 0, 1, step);
			} else {
				return null;
			}
		} 
		
		return null;
	}

	private static void preprocessState() {
		// TODO Auto-generated method stub
		for (Position camera:cameras) {
			state[camera.x][camera.y] = -2;
			//up
			for (int x = camera.x-1; x>=0; x--) {
				if (map[x][camera.y]=='.' || map[x][camera.y]=='S') {
					state[x][camera.y] = -2;
				} else if (map[x][camera.y] == 'W') {
					break;
				}
			}
			//down
			for (int x = camera.x+1; x<map.length; x++) {
				if (map[x][camera.y]=='.' || map[x][camera.y]=='S') {
					state[x][camera.y] = -2;
				} else if (map[x][camera.y] == 'W') {
					break;
				}
			}
			//left
			for (int y = camera.y-1; y>=0; y--) {
				if (map[camera.x][y]=='.' || map[camera.x][y]=='S') {
					state[camera.x][y] = -2;
				} else if (map[camera.x][y] == 'W') {
					break;
				}
			}
			//right
			for (int y = camera.y+1; y<map[0].length; y++) {
				if (map[camera.x][y]=='.' || map[camera.x][y]=='S') {
					state[camera.x][y] = -2;
				} else if (map[camera.x][y] == 'W') {
					break;
				}
			}
		}
	}

	private static void loadMap() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int height = sc.nextInt();
		int width = sc.nextInt();
		map = new char[height][width];
		state = new int[height][width];
		
		sc.nextLine();
		
		for (int i=0;i<height;i++) {
			String line = sc.nextLine();
			for (int j=0;j<width;j++) {
				map[i][j] = line.charAt(j);
				if (map[i][j] == 'S') {
					start = new Position(i,j);
					state[i][j] = -1;
				} else if (map[i][j] == 'C') {
					cameras.add(new Position(i,j));
					state[i][j] = -2;
				} else if (map[i][j] == 'W') {
					state[i][j] = -2;
				} else {
					state[i][j] = -1;
				}
			}
		}
	}

}

class Position {
	int x;
	int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
