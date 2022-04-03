//입력으로 제시된 인트 타입 배열에서 합해서 0이 될 수 있는 3개의 정수의 조합을 전부 찾아내는 문제입니다.
//실행시간은 26밀리초였고, 이는 상위 20%, 100명중 20등에 해당합니다.
//브루트 포스로 풀 경우 O(n^3)의 복잡도를 보이는 문제인데, 투포인터를 이용한 풀이를 통해 O(n^2)으로 최적화한 풀이입니다.
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
      //return type is List< List<Integer> > .
        
        List<List<Integer>> answer = new ArrayList<>();
        
      //입력값이 처음부터 잘못된 경우를 처리해 줍니다.
        if(nums.length<=2){return answer;}
        
        Arrays.sort(nums);//입력 배열을 오름차순으로 정렬해주면 훨씬 쉽게(그리고 빠르게) 답을 구할 수 있습니다.
        
        for(int i=0; i<=nums.length-3; i++){
            //우선 입력배열의 첫 부분에서 중복된 요소들을 스킵처리 해줍니다.
            //스킵처리를 해줄 때, if문의 조건들을 다음과 같이 정의해준 이유가 있습니다.
            //i>0 이라는 조건이 없다면 루프가 시작하자마자 i=0인 상태에서 i-1=-1인 인덱스에 접근하려고 하면서 Range Error가 발생합니다.
            //또한 if(nums[i]==nums[i+1])과 같이 만들 경우에도 index range error가 발생합니다.
            //(i>0 && nums[i]==nums[i-1])과 같이 설정해줌으로써, 인덱스 범위 오류를 방지함과 동시에, 현재의 요소가 직전 인덱스의 요소와 동일한지 정확하게 판단해줄 수 있습니다.
            if(i>0 && nums[i]==nums[i-1]){ continue; }

            //스킵 처리가 완료되면, 확정된 i 인덱스를 바탕으로 레프트(left)와 라이트(right) 인덱스를 정해줍니다.
            int left = i+1;//레프트는 i 인덱스의 바로 다음 인덱스이고,
            int right = nums.length-1;//라이트는 입력배열의 마지막 요소의 인덱스입니다.
            while(left<right){//i, left, right 인덱스를 바탕으로 탐색을 시작합니다.
                //레프트와 라이트 인덱스를 바탕으로 합이 0이 되는지 먼저 판단합니다.
                //레프트와 라이트도 동일한 요소들이 나열될 수 있기에 스킵처리를 해주는 것이 필요합니다.
                //하지만 중요한 것은 스킵처리보다도 "합이 0이 되는지" 검사하는 것이 먼저여야 정확한 답을 구할 수 있습니다.
                //합이 0이 되는지 검사하지 않고 레프트와 라이트 인덱스에 대한 스킵처리를 먼저 할 경우, 입력배열이 {0,0,0,0}같이 동일한 숫자의 나열로 입력됐을 때 정답을 도출하지 못하고 while 루프를 탈출하는 오류를 범하게 됩니다.
                int sum = nums[i] + nums[left] + nums[right];

                if(sum<0){ left++; }//현재 판단을 위해서 사용하고 있는 입력배열을 오름차순으로 정렬된 배열이라는 것을 기억해야 합니다. 그렇기 때문에, 합이 0보다 작을 경우 레프트 인덱스를 +1 해줌으로써 합의 크기를 키우는 것이 필요합니다. 이 경우, 맨 밑의 else if 및 else 파트는 실행하지 않고 다시 while 루프의 처음으로 돌아갑니다.
                else if(sum > 0){ right--; }//마찬가지로 합이 0보다 클 경우 라이트 인덱스를 -1 해줌으로써 합의 크기를 낮추는 것이 필요합니다. 이 경우에도, 맨 밑이 else if 및 else 파트는 실행하기 않고 다시 while 루프의 처음으로 돌아갑니다.
                else{//합이 0인 경우를 찾았을 때입니다. 일단 합이 0이기 때문에 리스트에 추가한 후, answer 리스트에 추가해줍니다.
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(nums[i]);
                    list.add(nums[left]);
                    list.add(nums[right]);
                    answer.add(list);
                    while(left<right && nums[left]==nums[left+1]){
                        left++;//정답을 추가한 이후 레프트 인덱스에 스킵처리를 진행합니다. 여기서 스킵처리를 해줘야 정답을 중복하여 answer 리스트에 추가하지 않고, 다음 정답이 있는지 확인하는 작업을 이어갈 수 있습니다.
                        //여기서 스킵처리를 함으로써, 레프트 인덱스를 예를 들어서 입력배열이 [1,1,1,3...] 이렇게 있을 때 첫 번째 1에서 마지막 1로 이동시킬 수 있습니다.
                    }
                    while(left<right && nums[right]==nums[right-1]){
                        right--;//정답을 추가한 이후 레프트 인덱스에 스킵처리를 진행합니다. 여기서 스킵처리를 해줘야 정답을 중복하여 answer 리스트에 추가하지 않고, 다음 정답이 있는지 확인하는 작업을 이어갈 수 있습니다.
                        //여기서 스킵처리를 함으로써, 레프트 인덱스를 예를 들어서 입력배열이 [...,3,3,3,3] 이렇게 있을 때 마지막 요소에 위치해 있는 3에서 마지막 인덱스로부터 세 칸만큼 작은 인덱스로 위치시킬 수 있습니다.
                    }
                    left++;//레프트에 대힌 스킵처리가 완료된 후, 다음 정답을 탐색하기 위해 레프트를 1칸 이동시킵니다.
                    right--;//라이트에 대힌 스킵처리가 완료된 후, 다음 정답을 탐색하기 위해 라이트를 1칸 이동시킵니다.
                }//else
                
                //이러한 과정으로 탐색을 지속하다보면, 결국 left는 점점 커지고 right는 점점 작아지면서 결국 while 루프를 탈출하게 됩니다.
            }//while
        }//1st for
        
        return answer; 
        
    }//main
}//main class
