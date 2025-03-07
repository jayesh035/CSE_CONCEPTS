//Your car starts at position 0 and speed +1
// on an infinite number line.
// Your car can go into negative positions.
// Your car drives automatically according to a sequence
// of instructions 'A' (accelerate) and 'R' (reverse):
//
//When you get an instruction 'A', your car does the following:
//position += speed
//speed *= 2
//When you get an instruction 'R', your car does the following:
//If your speed is positive then speed = -1
//otherwise speed = 1
//Your position stays the same.
//        For example, after commands "AAR",
//        your car goes to positions 0 --> 1 --> 3 --> 3,
//        and your speed goes to 1 --> 2 --> 4 --> -1.
//
//Given a target position target, return the length
// of the shortest sequence of instructions to get there.
//
//
//
//        Example 1:
//
//Input: target = 3
//Output: 2
//Explanation:
//The shortest instruction sequence is "AA".
//Your position goes from 0 --> 1 --> 3.
//Example 2:
//
//Input: target = 6
//Output: 5
//Explanation:
//The shortest instruction sequence is "AAARA".
//Your position goes from 0 --> 1 --> 3 --> 7 --> 7 --> 6.
//
//
//Constraints:
//
//        1 <= target <= 104
//
//Seen this question in a real interview before?
//        1/5
//Yes
//        No
//Accepted
//95.1K
//        Submissions
//216.4K
//Acceptance Rate
//44.0%


import java.util.*;
import java.util.concurrent.CyclicBarrier;

public class Test {



    int findBits(int target)
    {
        int count=0;
        int mul=1;

        while(mul<target)
        {
            mul*=2;
            count++;
        }

        return count;
    }
    class PairX{

        int first;
        int second;
        int third;

        public PairX(int first, int second, int third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }
    }

    class  Pair{
        int first,second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }
    public int racecar(int target) {


      HashSet<String>vis=new HashSet<>();

      Queue<Pair>q=new ArrayDeque<>();
      HashMap<Pair,Integer>dp=new HashMap<>();

      vis.add(0+"/"+1);
      q.add(new Pair(0,1));
int ans=0;
      while(!q.isEmpty())
      {
//         PairX p= q.poll();
//         dp.put(new Pair(p.first,p.second),p.third)


          int n=q.size();


          for(int i=0;i<n;i++)
          {


              Pair front=q.poll();
              String accelarate=(front.first+ front.second)+"/"+(2* front.second);
              String reverse=(front.first)+"/"+( front.second>0?-1:1);
              if(front.first==target)
              {
                  return ans;
              }
              if((front.first+ front.second-target)<target && !vis.contains(accelarate))
              {
                  q.add(new Pair(front.first+ front.second,2* front.second));
                  vis.add(accelarate);
              }
              if((front.first-target)<target && !vis.contains(reverse))
              {
                  q.add(new Pair(front.first,front.second>0?-1:1));
                  vis.add(reverse);
              }


          }
          ans++;






      }


      return  -1;








    }


    public static void main(String[] args) {

        CyclicBarrier br=new CyclicBarrier(8);



//        Set<Integer>st=new TreeSet<>();
//        SortedSet<Integer>st2=new TreeSet<>();
//        Set<Integer>queue=new PriorityQueue<>();

//        Queue<Integer>pq=new
    }
}
