package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

// Worker Pool Pattern
// Processes a stream of tasks using a fixed number of workers
func workerPoolExample(numWorkers, numTasks int) {
	fmt.Println("=== Worker Pool Example ===")

	// Create channels for tasks and results
	tasks := make(chan int, numTasks)
	results := make(chan string, numTasks)

	// Start worker pool
	var wg sync.WaitGroup
	for i := 1; i <= numWorkers; i++ {
		wg.Add(1)
		go func(id int) {
			defer wg.Done()
			for task := range tasks {
				// Simulate work
				time.Sleep(time.Duration(rand.Intn(100)) * time.Millisecond)
				results <- fmt.Sprintf("Worker %d processed task %d", id, task)
			}
		}(i)
	}

	// Send tasks
	for i := 1; i <= numTasks; i++ {
		tasks <- i
	}
	close(tasks)

	// Wait for all workers to complete
	wg.Wait()
	close(results)

	// Collect results
	for result := range results {
		fmt.Println(result)
	}
}

// Pipeline Pattern
// Processes data through a series of stages
func pipelineExample(input []int) {
	fmt.Println("\n=== Pipeline Example ===")

	// Stage 1: Generate numbers
	generator := func(nums []int) <-chan int {
		out := make(chan int)
		go func() {
			for _, n := range nums {
				out <- n
			}
			close(out)
		}()
		return out
	}

	// Stage 2: Square numbers
	square := func(in <-chan int) <-chan int {
		out := make(chan int)
		go func() {
			for n := range in {
				out <- n * n
			}
			close(out)
		}()
		return out
	}

	// Stage 3: Double numbers
	double := func(in <-chan int) <-chan int {
		out := make(chan int)
		go func() {
			for n := range in {
				out <- n * 2
			}
			close(out)
		}()
		return out
	}

	// Create pipeline
	out := double(square(generator(input)))

	// Collect results
	for result := range out {
		fmt.Printf("Result: %d\n", result)
	}
}

// Fan-out/Fan-in Pattern
// Distributes work to multiple workers and collects results
func fanOutFanInExample(numWorkers, numTasks int) {
	fmt.Println("\n=== Fan-out/Fan-in Example ===")

	// Fan-out: Create multiple worker channels
	worker := func(id int, tasks <-chan int, results chan<- string) {
		for task := range tasks {
			// Simulate work
			time.Sleep(time.Duration(rand.Intn(100)) * time.Millisecond)
			results <- fmt.Sprintf("Worker %d processed task %d", id, task)
		}
	}

	// Create task channel and result channel
	tasks := make(chan int, numTasks)
	results := make(chan string, numTasks)

	// Start workers (fan-out)
	for i := 1; i <= numWorkers; i++ {
		go worker(i, tasks, results)
	}

	// Send tasks
	for i := 1; i <= numTasks; i++ {
		tasks <- i
	}
	close(tasks)

	// Fan-in: Collect results using a separate goroutine
	var wg sync.WaitGroup
	wg.Add(1)
	go func() {
		defer wg.Done()
		for result := range results {
			fmt.Println(result)
		}
	}()

	// Wait for all results
	time.Sleep(time.Second) // Allow workers to complete
	close(results)
	wg.Wait()
}

func main() {
	rand.Seed(time.Now().UnixNano())

	// Run worker pool example
	workerPoolExample(3, 10)

	// Run pipeline example
	input := []int{1, 2, 3, 4, 5}
	pipelineExample(input)

	// Run fan-out/fan-in example
	fanOutFanInExample(3, 10)
}
