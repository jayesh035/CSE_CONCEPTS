package main

import (
	"fmt"
	"sync"
)

type person struct {
	name string
	age  int
}

func display(str string, wg *sync.WaitGroup) {

	for i := 0; i < 3; i++ {
		fmt.Println(str)
	}
	if wg != nil {
		defer wg.Done()
	}

}
func IsBirthDay(person2 *person) {

	person2.age++
}

func (p *person) getName() string {

	return p.name
}

//func main() {
//	//fmt.Println("Hello World")
//	//
//	////Go()
//	//
//	//p := person{age: 20, name: "jayesh"}
//	////fmt.Println(p)
//	//
//	//fmt.Println(p.getName())
//	////IsBirthDay(&p)
//	//
//	////fmt.Println(p)
//	////
//	//
//	////a := 5 / 0
//	//
//	////fmt.Println(a)
//	//
//	//ch := make(chan int)
//	//go sum(1, 5, ch)
//	//go sum(6, 10, ch)
//	//total1 := <-ch
//	//total2 := <-ch
//	//fmt.Println("Sum from 1 to 5:", total1)
//	//fmt.Println("Sum from 6 to 10:", total2)
//	//fmt.Println("Total sum from 1 to 10:", total1+total2)
//
//	var wg sync.WaitGroup
//	wg.Add(1)
//	go display("Hello, Goroutine!", &wg) // Start a goroutine
//	display("Hello, Main!", nil)
//
//	wg.Wait()
//
//}

func Go() {
	fmt.Println("this is go")
	tiny()
}

func tiny() {

	fmt.Println("tiny")
}
