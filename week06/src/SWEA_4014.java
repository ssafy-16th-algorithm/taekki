// SWEA 활주로 건설	
/*
1. 가로와 세로를 각각 한 줄씩 탐색
2. 한 줄에서 현재 칸과 다음 칸의 높이를 비교
3. 높이가 같으면 그대로 다음 칸으로 이동
4. 높이 차이가 1보다 크면
   → 경사로 설치가 불가능하므로 해당 줄은 실패
5. 현재 칸이 다음 칸보다 1 높은 경우 (내리막)
   → 다음 칸부터 앞으로 X칸 확인
   → 모든 높이가 다음 칸의 높이와 동일해야 함
   → 배열 범위를 벗어나면 안됨
   → 이미 경사로가 설치된 칸이 있으면 안됨
   → 조건을 만족하면 X칸을 visited 처리

6. 현재 칸이 다음 칸보다 1 낮은 경우 (오르막)
   → 현재 칸부터 뒤쪽으로 X칸 확인
   → 모든 높이가 현재 칸의 높이와 동일해야 한다.
   → 배열 범위를 벗어나면 안됨
   → 이미 경사로가 설치된 칸이 있으면 안됨
   → 조건을 만족하면 X칸을 visited 처리
   
7. 끝까지 조건을 만족하면 활주로 건설 가능
*/

import java.util.*;
import java.io.*;

public class SWEA_4014 {
	static int N, X; // N: 지도 한 변의 크기, X: 경사로의 길이
	static int ans; // 건설한 활주로 개수

	static int[][] map; // 활주로를 건설할 지도
	static boolean[][] visited; // 경사로 설치 유무

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			// 지도 한 변의 길이와 경사로 길이 입력
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			X = Integer.parseInt(st.nextToken());

			// 구간 높이 입력
			map = new int[N][N];
			for (int r = 0; r < N; r++) {
				st = new StringTokenizer(br.readLine());

				for (int c = 0; c < N; c++) {
					map[r][c] = Integer.parseInt(st.nextToken());
				}
			}

			// 가로 탐색
			for (int r = 0; r < N; r++) {
				for(int c = 0; c < N; c++) {
					constructLane(r, c);
				}
			}
			
			// 세로 탐색

		}
	}

	// 활주로 건설 함수
	static void constructLane(int x, int y) { // direction: 가로가 0, 세로가 1
		Deque<int[]> dq = new ArrayDeque<>();
		visited = new boolean[N][N];

		dq.offerFirst(new int[] { x, y });
		visited[x][y] = true;

		while (!dq.isEmpty()) {
			int[] cur = dq.pollFirst();
			int r = cur[0];
			int c = cur[1];

			// 다음 구간과의 높이 차이 구하기
			int diff = map[r][c] - map[r][c+1];

			// 높이 차이가 동일한 경우
			if (diff == 0) {
				dq.offerFirst(new int[] { r, c + 1 }); // 다음 구간을 dq에 삽입
				continue;
			}

			// 높이 차이가 1보다 큰 경우
			if(diff > 1) {
				break; // 경사로 높이는 1이기 때문에 건설 불가능
			}
			
			// 내리막인 경우
			if(diff == 1 || diff == -1) {
				int value = map[r][c+1];
				for(int i = 1; i <= X; i++) {
					// 범위를 벗어나면 경사로 설치 불가
					if(!isRange(r, c+i)) {
						break;
					}
					
					// 높이가 동일하지 않거나 경사로가 설치된 경우 경사로 설치 불가
					if(map[r][c+i] != value || visited[r][c+i]) {
						break;
					}
					
					visited[r][c+i] = true;
				}
			}
		}
	}

	static boolean isRange(int x, int y) {
		if (x >= 0 && x < N && y >= 0 && y < N) {
			return true;
		} else {
			return false;
		}
	}
}
