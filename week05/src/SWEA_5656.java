import java.io.*;
import java.util.*;

/*
 * 첫 번째 열을 부신다
 * 명중한 벽돌의 상하좌우(벽돌에 적힌 숫자 - 1) 칸 만큼 제거한다
 * 제거된 칸 위에 벽돌이 있으면 중력에 의해 떨어뜨린다
 * 
 * N만큼 다 던지면(베이스 조건) 남은 벽돌 개수를 구하고 최솟값인지 아닌지 구한 후 return
 * */

public class SWEA_5656 {
	static int N, W, H; // H: 열, W: 행
	static int ans;

	static int[][] blocks;

	static int[] dr = { -1, 1, 0, 0 };
	static int[] dc = { 0, 0, -1, 1 };

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			N = Integer.parseInt(st.nextToken()); // 던진 횟수
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());

			// 벽돌에 적힌 값들
			blocks = new int[H][W];
			for (int r = 0; r < H; r++) {
				st = new StringTokenizer(br.readLine());
				for (int c = 0; c < W; c++) {
					blocks[r][c] = Integer.parseInt(st.nextToken());
				}
			}

			ans = Integer.MAX_VALUE;
			brokenBlocks(0);

			sb.append("#").append(tc).append(" ").append(ans).append("\n");
		}
		System.out.print(sb);
	}

	static void brokenBlocks(int count) {
		if (count == N) {
			int remainBlocks = countBlocks();
			ans = Math.min(ans, remainBlocks); // 최솟값

			return;
		}

		// 모든 열에 구슬을 떨어뜨리기
		for (int c = 0; c < W; c++) {
			int[][] copyBlocks = initBlocks(blocks);

			// 해당 열에서 떨어뜨릴 가장 위쪽 벽돌 찾기
			for (int r = 0; r < H; r++) {
				if (blocks[r][c] > 0) {
					destroy(r, c);

					break;
				}
			}

			// 연쇄 폭발 이후 블록들 떨어뜨리기
			fall();

			brokenBlocks(count + 1);

			blocks = copyBlocks;
		}
	}

	// 벽돌 깨는 로직
	static void destroy(int r, int c) {
		Deque<int[]> dq = new ArrayDeque<>();

		dq.offerFirst(new int[] { r, c, blocks[r][c] });
		blocks[r][c] = 0;
		
		while (!dq.isEmpty()) {
			int[] cur = dq.pollFirst();
			int x = cur[0];
			int y = cur[1];
			int n = cur[2];

			// 상하좌우 탐색
			for (int d = 0; d < 4; d++) {
				for(int i = 1; i < n; i++) {
					// 행 열 이동
					int nx = x + dr[d] * i;
					int ny = y + dc[d] * i;

					// 범위를 벗어나면 여기서 끝내야 함
					if (!isRange(nx, ny)) {
						break;
					}
					
					if(blocks[nx][ny] == 0) {
						continue;
					}

					int nextN = blocks[nx][ny];
					blocks[nx][ny] = 0; // 벽돌 깨기
					
					dq.offer(new int[] {nx, ny, nextN});
				}
			}
		}

	}

	// 벽돌 아래로 떨어뜨리기 (연쇄 폭발이 다 끝난 후 진행)
	static void fall() {
		for (int c = 0; c < W; c++) {
			for (int r = H - 1; r > 0; r--) {
				// 0이 발견되면
				if (blocks[r][c] == 0) {
					// 해당 블록 위에 있는 블록들 떨어뜨리기
					for (int a = r - 1; a >= 0; a--) {
						if (blocks[a][c] > 0) {
							blocks[r][c] = blocks[a][c];

							blocks[a][c] = 0;
							break;
						}
					}
				}
			}
		}
	}

	// 남은 벽돌들 카운팅
	static int countBlocks() {
		int cnt = 0;
		for (int r = 0; r < H; r++) {
			for (int c = 0; c < W; c++) {
				if (blocks[r][c] > 0)
					cnt++;
			}
		}

		return cnt;
	}

	// blocks 복사
	static int[][] initBlocks(int[][] b) {
		int[][] copy = new int[H][W];

		for (int r = 0; r < H; r++) {
			System.arraycopy(b[r], 0, copy[r], 0, W); // b[r]의 인덱스 0번부터 끝까지 copy[r]에 복사
		}

		return copy;
	}

	static boolean isRange(int nr, int nc) {
		// 범위 벗어난 경우
		return nr >= 0 && nr < H && nc >= 0 && nc < W;
	}
}
