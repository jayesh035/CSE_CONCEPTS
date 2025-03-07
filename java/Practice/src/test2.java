//You are given an integer array nums and two integers, k and m.
//
//Return the maximum sum of k non-overlapping
// subarrays of nums, where each subarray has a length of at least m.
//
//
//
//        Example 1:
//
//Input: nums = [1,2,-1,3,3,4], k = 2, m = 2
//
//Output: 13
//
//Explanation:
//
//The optimal choice is:
//
//Subarray nums[3..5] with sum 3 + 3 + 4 = 10 (length is 3 >= m).
//Subarray nums[0..1] with sum 1 + 2 = 3 (length is 2 >= m).
//The total sum is 10 + 3 = 13.
//
//Example 2:
//
//Input: nums = [-10,3,-1,-2], k = 4, m = 1
//
//Output: -10
//
//Explanation:
//
//The optimal choice is choosing each element as a subarray. The output is (-10) + 3 + (-1) + (-2) = -10.
//
//
//
//Constraints:
//
//        1 <= nums.length <= 2000
//        -104 <= nums[i] <= 104
//        1 <= k <= floor(nums.length / m)
//1 <= m <= 3
//Seen this question in a real interview before?
//        1/5
//Yes



public class test2 {


    int find(int indx,int isTaken,int k,int [][]prefix,int m)
    {

        if(indx>= prefix.length && k==0)
        {
            return  0;
        }

        if(indx>= prefix.length && k!=0)
        {
           return Integer.MIN_VALUE;
        }

        if(indx< prefix.length && k==0)
        {
            return Integer.MIN_VALUE;
        }


        long take=Integer.MIN_VALUE;
        if(isTaken==0)
        {
            if( (prefix.length-indx) >=m)
            {
                take=prefix[indx][indx+m-1]+find(indx+m,1,k-1,prefix,m);


            }

        }
        else
        {
            take=prefix[indx][indx]+find(indx+1,1,k,prefix,m);
        }
        long not_take=find(indx+1,0,isTaken==1?k-1:k,prefix,m);

        return (int)Math.max(take,not_take);

    }
    public int maxSum(int[] nums, int k, int m) {



        int [][]prefix=new int[nums.length][nums.length];


        for(int i=0;i< nums.length;i++)
        {
            int sum=0;
            for(int j=i;j< nums.length;j++)
            {
                sum+=nums[j];
                prefix[i][j]=sum;
            }
        }



        return find(0,0,k,prefix,m);
    }
}
