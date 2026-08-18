
// SWEA 블록 제거 게임
/* 
 * 블록 하나가 사라지면 빈 자리를 뒤에서 채워줘야함 -> 블록들을 동적 할당 배열인 ArrayList에 집어넣는다
 * 
 * 전략 1.
 * 블록을 하나씩 깨서 최대 점수를 구해야 함
 * 		1부터 하나씩 출발해서 DFS (백트래킹)로 최대 점수를 구해야 할 거 같음
 * 		1-(2-3-4), (2-4-3), (3-2-4), (3-4-2), ...
 *		2-(1-3-4), (1-4-3), (3-1-4), (3-4-1), ...
 *		-> 이런 식으로 0번째 선택하고 나머지 수도 0,1,2번째 고르는 식으로 모든 경우의 수를 만들어 진행 
 *		-> DFS 알고리즘을 사용해야 할 듯
 *
 * 그냥 배열에 블록들이 사라질 때까지 while문을 돌려서 작업을 수행
 * 		블록 하나 remove 함수 사용
 * 		왼쪽 이웃, 오른쪽 이웃 두개로 나누어서 점수를 획득
 * 
 * block 사이즈에 따라 점수를 계산
 * 		1일 경우 남은 블록으로 점수 계산
 * 		양쪽 끝일 경우 이웃한 하나의 블록만 계산
 * 		양쪽에 이웃이 있을 경우 
 * 			왼쪽에 없으면 i+1로 가져와 계산
 * 			오른쪽에 없으면 i-1로 가져와 계산
 *
 * 해당 블록 제거 후 자기 자신의 함수 호출
 * 재귀 작업이 다 끝나면 복구를 위해 blocks에 추가
 * 
 * 
 * ----------------------------------------------------------------------
 *
 * 전략 2.
 * for문을 돌려서 왼쪽 이웃과 오른쪽 이웃이 있는지 확인한 후
 * 		있으면 큰 값의 점수를 가진 블록을 제거
 * 		없으면 왼쪽 혹은 오른쪽 이웃이 큰 점수를 가진 블록을 제거
 * 한 개밖에 없으면 나머지 점수 획득 처리
 * 
 * 
 * */

import java.util.*;
import java.io.*;

class Solution {
	static int N; // 블록의 개수 및 타격 기회
	static int ans; // 모든 블록을 깨서 얻을 수 있는 최대 점수

	static List<Integer> blocks;

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int test_case = 1; test_case <= T; test_case++) {
			N = Integer.parseInt(br.readLine());
			ans = 0;

			StringTokenizer st = new StringTokenizer(br.readLine());
			blocks = new ArrayList<>();
			for (int i = 0; i < N; i++) {
				blocks.add(Integer.parseInt(st.nextToken()));
			}

			removeBlock(0);

			sb.append("#").append(test_case).append(" ").append(ans).append("\n");
		}

		System.out.print(sb);
	}

	// 블록 부수기
	static void removeBlock(int result) {
		
		if (blocks.isEmpty()) {
			ans = Math.max(ans, result);

			return;
		}
		
		for (int i = 0; i < blocks.size(); i++) {

			int score = 0; // 하나의 블록을 제거했을 때의 점수

			if (blocks.size() == 1) {
				score = blocks.get(0);
			}

			// 왼쪽 블록이 없을 경우
			else if (i == 0) {
				score = blocks.get(i + 1); // 유일하게 이웃하는 오른쪽 블록으로 점수 계산
			}

			// 오른쪽 블록이 없을 경우
			else if (i == blocks.size() - 1) {
				score = blocks.get(i - 1); // 유일하게 이웃하는 왼쪽 블록으로 점수 계산
			}

			// 양쪽에 이웃이 있을 경우
			else {
				score = blocks.get(i - 1) * blocks.get(i + 1);
			}

			int removed = blocks.remove(i); // 블록 제거 후 값 반환

			removeBlock(result + score); // 다음 블록 탐색

			blocks.add(i, removed); // 재귀 작업이 끝나면 복구
		}
	}
}