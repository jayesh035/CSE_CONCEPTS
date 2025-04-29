package main

import (
	"fmt"
	"sync"
	"time"
)

func worker(id int, jobs <-chan int, results chan<- string, wg *sync.WaitGroup) {
	defer wg.Done()
	for job := range jobs {
		fmt.Println("Worker ", id, " started job ", job)
		time.Sleep(500 * time.Millisecond) // Simulate work
		results <- fmt.Sprintf("Worker %d finished job %d", id, job)
	}
}

func main() {
	const numWorkers = 3
	const numJobs = 5

	jobs := make(chan int, numJobs)
	results := make(chan string, numJobs)

	var wg sync.WaitGroup

	// Start workers
	for i := 1; i <= numWorkers; i++ {
		wg.Add(1)
		go worker(i, jobs, results, &wg)
	}

	// Send jobs
	for j := 1; j <= numJobs; j++ {
		jobs <- j
		time.Sleep(1)
	}
	close(jobs)

	// Wait for workers
	wg.Wait()
	close(results)

	// Print results
	for res := range results {
		fmt.Println(res)
	}
}
