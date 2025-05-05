package main

import (
	"encoding/json"
	"fmt"
	"os"
	"sync"
	"time"

	"github.com/gosnmp/gosnmp"
)

// Input represents the JSON structure received from the Java application
type Input struct {
	IPs         []string     `json:"ips"`
	Credentials []Credential `json:"credentials"`
}

// Credential represents a credential object
type Credential struct {
	ID         int64  `json:"id"`
	SystemType string `json:"system_type"`
	Community  string `json:"community"`
}

// SNMPCheckResult represents the result of an SNMP check
type SNMPCheckResult struct {
	CredentialID int64  `json:"credential_id"`
	Success      bool   `json:"success"`
	ErrorMessage string `json:"error_message,omitempty"`
	ResponseTime int64  `json:"response_time_ms,omitempty"`
}

// IPResult represents the result for an IP, including all credential results
type IPResult struct {
	IP          string            `json:"ip"`
	Credentials []SNMPCheckResult `json:"credentials"`
}

// Result represents the final output JSON structure
type Result struct {
	Results []IPResult `json:"results"`
}

// Perform an SNMP check for a specific IP and credential
func performSNMPCheck(ip string, credential Credential) SNMPCheckResult {
	result := SNMPCheckResult{
		CredentialID: credential.ID,
		Success:      false,
	}

	// Only proceed with SNMP check if system_type is "snmp"
	if credential.SystemType != "SNMP_V2C" {
		result.ErrorMessage = fmt.Sprintf("Credential system type '%s' is not supported for SNMP check", credential.SystemType)
		return result
	}

	// Initialize SNMP connection
	snmp := &gosnmp.GoSNMP{
		Target:    ip,
		Port:      161,
		Community: credential.Community,
		Version:   gosnmp.Version2c,
		Timeout:   time.Duration(2) * time.Second, // Short timeout for quick check
		Retries:   1,                              // Single retry for speed
	}

	startTime := time.Now()

	// Try to connect to the SNMP service
	err := snmp.Connect()
	if err != nil {
		result.ErrorMessage = fmt.Sprintf("Connect error: %v", err)
		return result
	}
	defer snmp.Conn.Close()

	// Query for the sysDescr OID (1.3.6.1.2.1.1.1.0)
	oids := []string{"1.3.6.1.2.1.1.1.0"} // sysDescr OID

	pdu, err := snmp.Get(oids)
	if err != nil {
		result.ErrorMessage = fmt.Sprintf("Get error: %v", err)
		return result
	}

	// SNMP connection is successful, calculate response time
	endTime := time.Now()
	result.ResponseTime = endTime.Sub(startTime).Milliseconds()

	// Check if sysDescr OID is present
	if len(pdu.Variables) > 0 {
		// If sysDescr is present, consider SNMP as successful
		result.Success = true
	} else {
		result.ErrorMessage = "sysDescr OID not found"
	}

	return result
}

func main() {
	// Check command line arguments
	if len(os.Args) != 3 {
		fmt.Fprintf(os.Stderr, "Usage: %s <input_file> <output_file>\n", os.Args[0])
		os.Exit(1)
	}

	inputFile := os.Args[1]
	outputFile := os.Args[2]

	// Read input JSON
	inputData, err := os.ReadFile(inputFile)
	if err != nil {
		fmt.Fprintf(os.Stderr, "Error reading input file: %v\n", err)
		os.Exit(1)
	}

	var input Input
	if err := json.Unmarshal(inputData, &input); err != nil {
		fmt.Fprintf(os.Stderr, "Error parsing input JSON: %v\n", err)
		os.Exit(1)
	}

	// Perform SNMP checks for each IP and credential combination
	var wg sync.WaitGroup
	var mutex sync.Mutex
	var results []IPResult

	for _, ip := range input.IPs {
		var ipResult IPResult
		ipResult.IP = ip

		for _, credential := range input.Credentials {
			wg.Add(1)
			go func(ip string, cred Credential) {
				defer wg.Done()

				result := performSNMPCheck(ip, cred)

				// Lock and append the result to avoid race conditions
				mutex.Lock()
				ipResult.Credentials = append(ipResult.Credentials, result)
				mutex.Unlock()
			}(ip, credential)
		}

		// Append results after processing all credentials for this IP
		wg.Wait()
		results = append(results, ipResult)
	}

	// Create output structure
	output := Result{
		Results: results,
	}

	// Write output JSON
	outputData, err := json.MarshalIndent(output, "", "  ")
	if err != nil {
		fmt.Fprintf(os.Stderr, "Error creating output JSON: %v\n", err)
		os.Exit(1)
	}

	if err := os.WriteFile(outputFile, outputData, 0644); err != nil {
		fmt.Fprintf(os.Stderr, "Error writing output file: %v\n", err)
		os.Exit(1)
	}
}
