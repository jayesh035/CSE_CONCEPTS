package Collection;

import java.util.Stack;

public class stack {


    public static void main(String[] args) {


        Stack<Integer> stack=new Stack<>();


        stack.push(6);
        stack.push(7);

        System.out.println(stack.peek());

//      Integer topElement=  stack.pop();

        System.out.println(stack.isEmpty());
      stack.push(9);
        System.out.println(stack);
    }
}
