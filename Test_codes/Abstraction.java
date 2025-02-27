// // Demonstrating Abstraction in Java
// abstract class Geeks {
//     abstract void turnOn();
//     abstract void turnOff();
// }

// // Concrete class implementing the abstract methods
// class TVRemote extends Geeks {
//     @Override
//     void turnOn() {
//         System.out.println("TV is turned ON.");
//     }

//     @Override
//     void turnOff() {
//         System.out.println("TV is turned OFF.");
//     }
// }

// // Main class to demonstrate abstraction
// public class Abstraction {
//     public static void main(String[] args) {
//         Geeks remote = new TVRemote();
//         remote.turnOn();   
//         remote.turnOff();  
//     }
// }


// Java program to illustrate the
// concept of Abstraction
// abstract class Shape {
//     String color;

//     // these are abstract methods
//     abstract double area();
//     public abstract String toString();

//     // abstract class can have the constructor
//     public Shape(String color)
//     {
//         System.out.println("Shape constructor called");
//         this.color = color;
//     }

//     // this is a concrete method
//     public String getColor() { return color; }
// }
// class Circle extends Shape {
//     double radius;

//     public Circle(String color, double radius)
//     {

//         // calling Shape constructor
//         super(color);
//         // this.color=color;
//         System.out.println("Circle constructor called");
//         this.radius = radius;
//     }

//     @Override double area()
//     {
//         return Math.PI * Math.pow(radius, 2);
//     }

//     @Override public String toString()
//     {
//         return "Circle color is " + color
//             + "and area is : " + area();
//     }
// }
// class Rectangle extends Shape {

//     double length;
//     double width;
//     String color;
//     public Rectangle(String color, double length,
//                      double width)
//     {
//         // calling Shape constructor
//         super(color);
//        this.color="blue";
//         System.out.println("Rectangle constructor called");
//         this.length = length;
//         this.width = width;
//     }

//     @Override double area() { return length * width; }

//     @Override public String toString()
//     {
//         return "Rectangle color is " +color+super.getColor()
//             + "and area is : " + area();
//     }
// }




abstract class Animal
{

    String name;

    Animal(String name)
    {
        this.name=name;
    }
    abstract void sound();
    String getName()
    {
        return name;
    }

}
class Dog extends Animal
{

    Dog(String name)
    {
        super(name);
    }
    
    @Override
    void sound() {
        // TODO Auto-generated method stub
        System.out.println("barks");
    }
}

public class Abstraction {
    public static void main(String[] args)
    {
        // Shape s1 = new Circle("Red", 2.2);
        // Shape s2 = new Rectangle("Yellow", 2, 4);

        // System.out.println(s1.toString());
        // System.out.println(s2.toString());
    }
}
