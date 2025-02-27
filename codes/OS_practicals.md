# Linux System Monitoring and Information Commands

## 1. Get the Number of Context Switches
```sh
vmstat | awk 'NR==3 {print "context switches:" $12}'
```

## 2. Get the Number of Running Processes
```sh
ps aux | awk '$8 ~ /R+|R/  {count++} END {print "Total Running Process:" count}'
ps -elf | awk '$2 ~ /R/ {count++} END {print count}'
```

## 3. Get the CPU Used and Idle Percent
```sh
mpstat | awk  ' NR ==4 {print "Idle:" $14 "\nUsed:"  100-$14}'
```

## 4. Get Inet Address and MAC Address
```sh
ifconfig | awk '{ if( NR==19) print "inet:"$2; if(NR==21) print"MAC:"$2 }'
```

## 5. Get Context Switches per Second
```sh
vmstat 1
```

## 6. Get Load Average for 1min, 5min, and 15min
```sh
uptime | awk '{print "Load average:\n1min:"$8"\n5min:"$9"\n15min:"$10}'
```

## 7. Watch Any File for Continuous Modification
```sh
watch -n 1
```

## 8. Get Used and Free Memory
```sh
free -h | awk 'NR==2 {print "Free:" $4 "\nUsed:" $3 "\nTotal:"$2 }'
```

## 9. Get Used CPU and Context Switches per Process
```sh
pidstat -uw
```

## 10. Get Disk Read/Write Stats
```sh
iostat -x | awk '$1 == "nvme0n1" {print "Disk: " $1 "\nRead/s: " $2*1000 "\nWrite/s: " $8*1000 "\nReadBytes/s: " $3*1000 "\nWriteBytes/s: " $9*1000 "\nUtilization%: " $21}'
```

## 11. TCP and UDP Connections
```sh
netstat -s | awk '{if (NR>=31 && NR <=49) print}'
```

## 12. Running Threads
```sh
pidstat -t
ps -eLf
```

## 13. Running Processes
```sh
ps aux
pidstat
```

## 14. List All Details of File Permission, Owner, Group
```sh
ll
ls -al
```

## 15. List File Stats (File Type, Block Size, Last Modification Time, Last Status Change Time)
```sh
stat --format="Type: %F/Block Size: %o/ Modify: %Y/ Change: %Z" test.java
```

## 16. Maximum Number of Threads Supported by OS Kernel
```sh
cat /proc/sys/kernel/threads-max
```

## 17. List Names of Zombie Processes & Interrupted Processes
```sh
ps -elf | awk '{ if($2 =="I" )print "Interrupted process: " $15; if($2=="Z") print "Zombie Process: "$15}'
```

## 18. List All Device Drives Available in System & Exclude Loop Devices
```sh
iostat | awk 'NR>6 &&  $1 !~ /loop/ {print $1 }'
```

## 19. List All Partitions & Print Sector-Wise Details
```sh
sudo fdisk -l
```

## 20. List the Size Occupied by Each Directory & Files Inside That Directory
```sh
du -sh */
du -sh */*
