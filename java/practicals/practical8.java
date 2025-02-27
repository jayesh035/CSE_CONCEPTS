
interface Shape{


    double calculateArea();
}

class  Triangle implements  Shape{
    double height;
    double base;

    Triangle(double height, double base)
    {
        this.height=height;
        this.base=base;
    }
    @Override
    public double calculateArea() {
        return (height*base)/2;
    }
}

class  Rectangle implements  Shape{

    double length;
    double width;

    Rectangle(double length, double width)
    {
        this.length=length;
        this.width=width;
    }
    @Override
    public double calculateArea() {
        return length*width;
    }
}

class Circle implements  Shape{

    double radious;
    Circle(double radious)
    {
        this.radious=radious;

    }
    @Override
    public double calculateArea() {
        return Math.PI * radious* radious;
    }


}


public  class practical8
{

    public static void main(String[] args) {

        Shape circle = new Circle(5);

        Shape rectangle = new Rectangle(5,4);

        Shape triangle = new Triangle(5,2);

        System.out.println("circle area:"+circle.calculateArea());

        System.out.println("rectangle area:"+rectangle.calculateArea());

        System.out.println("triangle area:"+ triangle.calculateArea());

    }
}