/*
이 문제의 핵심
- 행과 열을 장애물이나 경계까지 미끄러진 후의 위치를 다음 위치로 갱신하여 최소 이동 횟수를 반환

1. board에서 R의 위치를 찾는다.
2. R을 큐에 넣고 방문 처리한다.
3. 큐에서 현재 위치를 꺼낸다.
4. 상하좌우 각각 끝까지 미끄러진다.
5. 멈춘 위치가 방문하지 않은 곳이면 큐에 넣는다.
6. 멈춘 위치가 G라면 이동 횟수를 반환한다.
7. 큐가 빌 때까지 G에 도달하지 못하면 -1을 반환한다.
*/

import java.util.*;

public class PGS_리코쳇로봇 {
	public int solution(String[] board) {
		int answer = 0;
		int rows = board.length;
		int cols = board[0].length();

		boolean[][] visited = new boolean[rows][cols];
		Queue<int[]> queue = new LinkedList<>();

		// 상, 하, 좌, 우
		int[] dr = { -1, 1, 0, 0 };
		int[] dc = { 0, 0, -1, 1 };

		// R 위치 찾기
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				if (board[r].charAt(c) == 'R') {
					// 이 문제의 핵심을 해결하기 위해 행, 열, 이동횟수를 사용
					queue.offer(new int[] { r, c, 0 }); // {행, 열, 이동 횟수}
					visited[r][c] = true;
				}
			}
		}

		// BFS
		while (!queue.isEmpty()) {
			// 현재 위치 꺼내기
			int[] current = queue.poll();

			int r = current[0];
			int c = current[1];
			int moveCnt = current[2];

			// 현재 위치가 목표 지점이라면 이동 횟수 반환
			if (board[r].charAt(c) == 'G') {
				return moveCnt;
			}

			// 상하좌우 끝까지 이동하기
			for (int dir = 0; dir < 4; dir++) {
				int nr = r;
				int nc = c;

				while (true) {
					// 이동
					int mr = nr + dr[dir];
					int mc = nc + dc[dir];

					// 보드 밖이거나 장애물 지점이면 현재 위치에서 정지
					if (mr < 0 || mr >= rows || mc < 0 || mc >= cols || board[mr].charAt(mc) == 'D') {
						break;
					}

					// 현재 지점을 이동한 위치로 갱신
					nr = mr;
					nc = mc;
				}

				// 도착한 지점이 방문한 지점이 아니라면 큐에 추가
				if (!visited[nr][nc]) {
					visited[nr][nc] = true; // 방문 표시
					queue.offer(new int[] { nr, nc, moveCnt + 1 });
				}
			}
		}

		// G에 도달할 수 없을 경우 -1 반환
		return -1;
	}
}
