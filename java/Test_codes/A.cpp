#include<bits/stdc++.h>
using namespace std;


class A
{


protected : void getId()
{
cout<<"A";
}
};

class B : public A
{


       public : void getId()
        {
        A::getId();
        }
};

int main()
{

    B b;
    b.getId();

}