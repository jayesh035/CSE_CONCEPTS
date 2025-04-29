package main

func sum(start, end int, ch chan int) {
	total := 0
	for i := start; i <= end; i++ {
		total += i
	}
	ch <- total
}

//func main() {

//}
