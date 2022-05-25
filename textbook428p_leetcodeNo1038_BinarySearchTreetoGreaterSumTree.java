/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
//실행시간은 0밀리초가 나왔지만, 제출된 거의 모든 풀이들이 0밀리초 구간에 집중돼 있어서 큰 의미는 없었습니다.
class Solution {

    //각 노드들의 값을 조건에 맞게 누적시킬 때 사용할 변수를 솔루션 클래스의 프로퍼티 위치에 정의합니다. 이렇게 함으로써 dfs 재귀호출 메서드가 자유롭게 sum 변수에 접근할 수 있게 됩니다. 따라서 sum을 재귀호출의 인자로 넘겨줄 필요가 없어서 코드를 더 짧게 작성할 수 있게 됩니다.
    public int sum = 0;

    public TreeNode bstToGst(TreeNode root) {
        //return type is TreeNode


        dfs(root);

        return root;

    }//main

    public void dfs ( TreeNode node ){

        //현재 노드가 null이 아닐 때만 다음 dfs 호출을 진행할 수 있게 설정합니다.
        if(node != null){
            //여기서 바로 현재 노드이 오른쪽 자식 노드를 재귀호출의 인자로 넘기면서 재귀호출하는 것이 중요합니다.
            //이진 탐색트리의 특징을 이용하기 위해서 오른쪽 자식 노드들을 먼저 재귀호출의 인자로 넘겨주고 있습니다.
            //이진 탐색트리의 특성상, 오른쪽 자식 노드는 현재 노드보다 크거나 같은 값을 가집니다. 현재 노드의 값보다 큰 값을 먼저 전부 찾아보기 위해서 오른쪽 자식 노드들을 먼저 전부 호출해주는 것입니다.
            //이러한 호출은 트리가 아래와 같은 구조를 가질 때, 7노드의 오른쪽 자식노드(==null)에 도달할 때까지 계속됩니다.
            //결국, 트리의 가장 오른쪽에 위치한 노드에서부터 본격적인 노드값 갱신이 시작되는 것입니다.
            /*
            *              4
            *        1          6
            *     0    2     5     7
            * */
            dfs(node.right);

            //dfs(node.right) 호출을 끝장까지 반복한 끝에 마침내 가장 오른쪽에 위치한 노드에 도착했습니다.
            //그후 가장 먼저 할 일은 sum에 현재의 노드 값을 누적시키고, 누적된 값을 이용해서 자신의 노드 값을 갱신하는 것입니다. 이진 탐색트리에서 가장 오른쪽에 존재하는 노드보다 큰 값을 갖는 노드는 없으므로, 결국 가장 오른쪽의 노드는 자기 자신과 동일한 값으로 갱신됩니다.
            sum += node.val;
            node.val = sum;

            //이제 왼쪽 자식노드를 처리해야 하는데, 왼쪽 자식노드를 재귀호출의 인자로 넘기면서 재귀메서드를 여기에서 호출하는 것도 이유가 있습니다.
            //앞의 7노드의 부모 노드는 6노드 입니다. 6노드의 오른쪽 자식노드가 7이었으므로, 여기의 node.left 노드는 당연히 5노드일 수밖에 없습니다.
            //5노드가 재귀호출의 인자로 넘어가면서 재귀호출이 일어날 때, 그 직전에 sum은 이미 6과 7이 더해진 13으로 초기화 돼 있습니다. 왜냐하면, dfs(node.right); 가 작업을 끝낸 후에 곧바로 sum+= ~~ 부분과 node.val = ~~ 부분이 작업을 끝내고나서야 dfs(node.left)가 호출되기 때문입니다.
            //이진 탐색트리의 특성상 왼쪽 자식 노드의 부모노드는 왼쪽 자식 노드보다 큰 값을 가집니다. 따라서 5노드에서 호출한 재귀메서드는 이미 13의 값을 갖는 sum에 5노드 자신의 값인 5를 누적시킨으로써 5노드는 5+6+7 = 18로 초기화 됩니다.
            dfs(node.left);
        }
    }//func

}//main class
/*입력되는 이진 탐색 트리의 구조가 아래와 같을 때, 재귀 호출이 진행되는 과정을 추적해 보면 다음과 같습니다. null인 노드는 제외했습니다.
 *              4
 *        1          6
 *     0    2     5     7
 *
 *
 * [1호출 : 4노드]
 *     |       \___________[2호출 : 6노드]
 *     |                          | \________________[3호출 : 7노드]
 *     |                          |                               3호출의 썸(==sum)에 7 누적. 썸 = 7.
 *     |                          |2호출의 썸에 6누적. 썸 = 13.
 *     |                          |
 *     |                          |
 *     |                          \__________________[4호출 : 5노드]
 *     |                                                        4호출의 썸에 썸에 5누적. 썸 = 18
 *     |
 *     |1호출의 썸에  4누적. 썸 = 22
 *       ...
 *         ...
 *           ...
 * */
