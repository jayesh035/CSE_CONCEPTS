package main

import (
	"fmt"
	"sync"
)

//func worker(id int, tasks <-chan int, results chan<- int, wg *sync.WaitGroup) {
//	defer wg.Done()
//	for task := range tasks {
//		result := task * task
//		fmt.Printf("Worker %d processed %d, result: %d\n", id, task, result)
//		results <- result
//	}
//}

func main() {
	tasks := make(chan int, 10)
	results := make(chan int, 10)
	var wg sync.WaitGroup

	// Start 3 workers
	numWorkers := 3
	for i := 1; i <= numWorkers; i++ {
		wg.Add(1)
		go worker(i, tasks, results, &wg)
	}

	// Send tasks
	numbers := []int{1, 2, 3, 4, 5}
	for _, num := range numbers {
		tasks <- num
	}
	close(tasks)

	// Wait for workers to finish
	wg.Wait()
	close(results)

	// Collect results
	for result := range results {
		fmt.Println("Result:", result)
	}
}
