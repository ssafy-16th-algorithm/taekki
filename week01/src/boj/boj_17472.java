package boj;

import java.io.*;
import java.util.*;

// 다리 건설
public class boj_17472 {

	// 섬 번호 저장 로직
	static void save_num(int r, int c, int row, int col, int num, int[][] map, int[][] island) {
		Queue<int[]> q = new LinkedList<>();
		q.offer(new int[] { r, c }); // 섬 좌표 삽입
		island[r][c] = num;

		int[] dr = {-1, 1, 0, 0};
		int[] dc = {0, 0, -1, 1};
		
		while (!q.isEmpty()) {
			int [] cur = q.poll();
			
			for(int d = 0; d < 4; d++) {
				int new_row = cur[0] + dr[d];
				int new_col = cur[1] + dc[d];
				
				if(new_row < 0 || new_row >= row 
						|| new_col < 0 || new_col >= col) continue;
			
				if(map[new_row][new_col] == 0) continue;
				
				if(island[new_row][new_col] != 0) continue;
				
				island[new_row][new_col] = num; // 섬 번호 저장
				
				q.offer(new int[] {new_row, new_col}); // 다음 작업을 위한 큐 삽입
			}
		}
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // 입력
		StringBuilder sb = new StringBuilder();

		// 지도 세로 가로 크기 입력
		int col = Integer.parseInt(br.readLine());
		int row = Integer.parseInt(br.readLine());

		// 지도 정보 입력
		int[][] map = new int[row][col];
		for (int r = 0; r < row; r++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			for (int c = 0; c < col; c++) {
				map[r][c] = st.nextToken().charAt(0);
			}
		}

		// 섬 번호 저장
		int[][] island = new int[row][col]; // 섬 번호를 저장할 배열
		int islandNum = 0;
		for (int r = 0; r < row; r++) {
			for (int c = 0; c < col; c++) {
				if (map[r][c] == 1 && island[r][c] == 0) {
					save_num(r, c, row, col, islandNum, map, island);
					islandNum++;
				}

			}
		}
	}

}
