import java.util.*;

class PG_매뉴리뉴얼 {
    
    static Map<String, Integer> menuCombo = new HashMap<>(); // 모든 주문의 조합 빈도수

    // temp: 현재 조합, order: idx에 대응하는 주문
    static void dfs(int idx, String tmp, String order) {
        if (tmp.length() > order.length())
            return;

        menuCombo.put(tmp, menuCombo.getOrDefault(tmp, 0) + 1); // 해당 메뉴의 빈도수 증가

        for (int i = idx; i < order.length(); i++) {
            dfs(i + 1, tmp + order.charAt(i), order); // 조합 확장 ex.A -> AB -> ABC
        }
    }

    public String[] solution(String[] orders, int[] course) {
        List<String> answer = new ArrayList<>();

        // 주문별 정렬한 후 조합 생성
        for (String order : orders) {
            char[] chars = order.toCharArray(); // 문자로 변경 후 정렬해야 함(AB와 BA를 동일시로 만들어야 함)
            Arrays.sort(chars); // 주문별 정렬
            order = new String(chars);

            dfs(0, "", order); // 조합 가능한 메뉴들을 m에 추가
        }

        // 해당 단품메뉴의 개수에서 많이 선택된 메뉴 찾기
        for (int setSize : course) {

            // 많이 선택된 메뉴 횟수 저장
            int maxOrder = 0;

            for (Map.Entry<String, Integer> menu : menuCombo.entrySet()) {
                if (menu.getKey().length() == setSize) { // 원하는 단품 메뉴 개수가 나오면
                    maxOrder = Math.max(maxOrder, menu.getValue()); // 가장 많이 선택된 코스 저장
                }
            }

            if (maxOrder <= 1)
                continue; // 최소 2번 주문 이상이 되어야 함

            for (Map.Entry<String, Integer> menu : menuCombo.entrySet()) {
                // 해당 단품메뉴가 단품메뉴 개수와 동일하고 많이 주문된 메뉴라면
                if (menu.getKey().length() == setSize
                        && menu.getValue() == maxOrder) {

                    answer.add(menu.getKey());
                }
            }
        }

        Collections.sort(answer); // 오름차순

        return answer.toArray(new String[0]);
    }
}