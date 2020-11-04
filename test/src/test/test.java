package test;

import java.util.ArrayList;
import java.util.Scanner;

public class test {
	final static int coverType[][][] = new int[][][]{
			{{ 0, 0},{1, 0}, {0, 1}}, 
			{{ 0, 0},{0, 1}, {1, 1}},
			{{ 0, 0},{1, 0}, {1, 1}},
			{{ 0, 0},{1, 0}, {1, -1}},
	};
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		int x, y;
		y = scanner.nextInt();
		x = scanner.nextInt();
		
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0; i < y; i ++) {
			String str = scanner.next();
			list.add(str);
		}
		
//		for(int i = 0; i < y; i++) {
//			System.out.println(list.get(i));
//		}
		cover(list);
	}
	
	public static boolean set(ArrayList<String> list, int y, int x, int type, int delta) {
		boolean ok = true;
		int nx = 0;
		int ny = 0;
		
		System.out.println("type : "+type);	
		System.out.println("x : " + x + " y : " + y);
		//3칸짜리 블록의 위치를 type에 따라서 하나씩 찾아준다.
		for(int i = 0; i < 3; i++) {
			ny = y + coverType[type][i][0];
			nx = x + coverType[type][i][1]; 
			//{ 0, 0},{1, 0}, {1, -1}
			System.out.println("nx : " + nx + " ny : " + ny);
			//블록이 전체 사이즈를 넘어갈때 
			if(ny < 0 || ny >= list.size() || nx < 0 || nx >= list.get(0).length()) {
				System.out.println("size out");
				ok = false;
			}
			//벽이 있는지 아닌지를 체크한다.
			else if((((int)list.get(ny).charAt(nx)-'0') + delta) > 1) {
				System.out.println("ocupied black");
				ok = false;
			}
		}
		return ok;
	}
	
	public static int cover(ArrayList<String> list) {
		int y = -1;
		int x = -1;
		// i = y; j = x;
		// (0,0) 부터 시작하는 완전탐색을 
		//하면서 지도에서 빈칸이 존재하는 좌표 하나를 선택.
		for(int i = 0; i < list.size(); i ++) { // i : 0~2
			for(int j = 0; j < list.get(i).length(); j++) { // j : 0~6
				if(list.get(i).charAt(j) == '0') {
					System.out.println("빈칸의 좌표 : x : " + j + " y : " + i);
					y = i;
					x = j;
					break;
				}
				System.out.println("빈칸이 없을 때" + y + ", " + x);
			}
			//if문으로 들어가지를 못했을 때('0'이 지도에 존재하지 않을때, 빈칸이 없을때)
			if(y != -1) break;
		}
		//빈칸이 존재하지 않는다 -> 모든 칸을 3칸블록으로 채웠다. return 1;
		if(y == -1) {
			System.out.println("*************************************************");
			return 1;
		}
		
		int ret = 0;
		//4가지 타입을 모두 대입하여 블록을 하나씩 두면서
		// 블록이 잘 채워지면 재귀호출을 하여 다음 블록을 놓는다.
		// 마지막까지 블록을 놓는다면 1이 리턴되어 1가지 방법이 ret에 더해진다.
		for(int type = 0; type < 4; type++) {
			if(set(list, y, x, type, 1))
				ret += cover(list);
			set(list, y, x, type, -1);
		}
		return ret;
	}
}
