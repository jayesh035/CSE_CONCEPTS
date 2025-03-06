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
        for(int i=0;i<31;i++)
        {
            if((target&(1<<i))!=0)
            {
                count++;
            }
        }

        return count;
    }

    public int racecar(int target) {


        int numBits=findBits(target)+1;

        int nextPos=(int)Math.pow(2,numBits)-1;
        if(nextPos == target)
        {
            return numBits;
        }


        int acsed=numBits+1+findBits(nextPos-target);





        return 0;




    }


    public static void main(String[] args) {

        CyclicBarrier br=new CyclicBarrier(8);



//        Set<Integer>st=new TreeSet<>();
//        SortedSet<Integer>st2=new TreeSet<>();
//        Set<Integer>queue=new PriorityQueue<>();

//        Queue<Integer>pq=new
    }
}
