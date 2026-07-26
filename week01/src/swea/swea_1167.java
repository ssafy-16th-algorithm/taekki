package swea;

import java.util.*;
import java.io.*;

// [SW Test 샘플문제] 프로세서 연결하기
public class swea_1167 {
	static int N, max, min;
	static int processor[][];
	static int[] dr = { -1, 1, 0, 0 };
	static int[] dc = { 0, 0, -1, 1 };

	static ArrayList<Node> cores; // 가장자리를 제외한 코어

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws NumberFormatException, IOException {
	    int T = Integer.parseInt(br.readLine());

	    for (int tc = 1; tc <= T; tc++) {
	        max = 0;
	        min = Integer.MAX_VALUE; // 최소 길
	        cores = new ArrayList<>(); // 가장자리 제외 코어들

	        N = Integer.parseInt(br.readLine());

	        processor = new int[N][N];

	        for (int r = 0; r < N; r++) {
	            StringTokenizer st = new StringTokenizer(br.readLine());

	            for (int c = 0; c < N; c++) {
	                processor[r][c] = Integer.parseInt(st.nextToken());

	                if (processor[r][c] == 1) {
	                    if (r != 0 && r != N - 1 && c != 0 && c != N - 1) {
	                        cores.add(new Node(r, c));
	                    }
	                }
	            }
	        }

	        dfs(0, 0);

	        sb.append("#")
	          .append(tc)
	          .append(" ")
	          .append(min)
	          .append("\n"); // 테스트 케이스별 줄바꿈
	    }

	    System.out.print(sb);
	}

	private static void dfs(int index, int coreCnt) {
		if (index == cores.size()) {
			int line = countLine(); // 전선 길이
			if (max < coreCnt) {
				max = coreCnt;
				min = line;
			} else if (max == coreCnt) { // 코어 개수가 같으면 전선 최소 길이로 결정
				if (min > line)
					min = line;
			}

			return;
		}

		// 현재 코어의 행과 열 좌표 가져오기
		Node cur = cores.get(index);
		int x = cur.x;
		int y = cur.y;

		for (int d = 0; d < 4; d++) {

			if (isAvailable(x, y, dr[d], dc[d])) {
				// 전선 놓기
				setLine(x, y, dr[d], dc[d], 2);

				// 다음 코어로 이동
				dfs(index + 1, coreCnt + 1);

				// 놓았던 전선 되돌려 놓기
				setLine(x, y, dr[d], dc[d], 0);
			}

		}

		// 코어 비선택
		dfs(index + 1, coreCnt);
	}

	// 전선 길이 구하는 함수
	private static int countLine() {
		int cnt = 0;

		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				if (processor[r][c] == 2)
					cnt++;
			}
		}

		return cnt;
	}

	// 전선 놓는 함수
	private static void setLine(int x, int y, int dr, int dc, int s) {
		int copy_x = x, copy_y = y;

		while (true) {
			copy_x += dr;
			copy_y += dc;

			if (copy_x < 0 || copy_x > N - 1 || copy_y < 0 || copy_y > N - 1)
				break;

			processor[copy_x][copy_y] = s;
		}
	}

	// 전선을 놓을 수 있는지 여부 확인 함수
	private static boolean isAvailable(int x, int y, int dr, int dc) {
		int copy_x = x, copy_y = y;

		while (true) {
			copy_x += dr;
			copy_y += dc;

			if (copy_x < 0 || copy_x > N - 1 || copy_y < 0 || copy_y > N - 1)
				break;

			// 중간에 장애물이 있다면 false 반환
			if (processor[copy_x][copy_y] != 0)
				return false;
		}

		return true;
	}

	private static class Node {
		int x;
		int y;

		Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
