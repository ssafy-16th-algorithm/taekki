
// SWEA 보급로 문
import java.util.*;
import java.io.*;

/*
  더 작은 비용이 발견되면 그 해당 위치부터 다시 탐색
  	해당 위치 : nx, ny
  	비용 = 현재까지 비용 + 해당 위치의 비용(초기값은 정수 최대값)
  	비용 < 현재까지 비용
  		최솟값 갱신
  		해당위치를 다시 dq에 추가
  	다시 탐색

 */
class Solution {
	static int N;
	static int[][] map;
	static int[][] dist; // 칸 별 거리 계산
	
	// 상 하 좌 우
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(br.readLine()); // 지도의 크기
			
			map = new int[N][N];
			dist = new int[N][N];
			
			for(int i = 0; i < N; i++) {
				String line = br.readLine();
				for(int j = 0; j < N; j++) {
					map[i][j] = line.charAt(j) - '0';
					dist[i][j] = Integer.MAX_VALUE; // 칸 별 거리 합산을 최댓값으로 지정
				}
			}
			
			bfs();
			
			sb.append("#").append(tc).append(" ").append(dist[N-1][N-1]).append("\n");
		}

		System.out.print(sb);
	}

	static void bfs() {
		Deque<int[]> dq = new ArrayDeque<>();
		boolean[][] inDeque = new boolean[N][N];
		
		dist[0][0] = 0;
		dq.offerLast(new int[] {0, 0});
		inDeque[0][0] = true; // 탐색 처리
		
		while(!dq.isEmpty()) {
			int[] cur = dq.pollFirst();
			int x = cur[0];
			int y = cur[1];
			
			inDeque[x][y] = false; // 향후 재탐색을 위해 false 처리
			
			for(int d = 0; d < 4; d++) {
				int nx = x + dr[d];
				int ny = y + dc[d];
				
				// 범위를 벗어날 경우
				if(!isRange(nx, ny)) continue;
				
				int nextCost = dist[x][y] + map[nx][ny]; // (nx, ny)까지의 거리 합산
				
				if(nextCost < dist[nx][ny]) {
					dist[nx][ny] = nextCost; // 최솟값 갱
					
					// 탐색할 큐에 없을 경우
					if(!inDeque[nx][ny]) {
						dq.offerLast(new int[] {nx, ny}); // 다시 탐색하도록 추가
						inDeque[nx][ny] = true;
					}
				}
			}
		}
	}

	// 배열 범위
	static boolean isRange(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < N;
    }
}
