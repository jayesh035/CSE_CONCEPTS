# Basic Networking Practical

## 1. Get Ping Statistics
```sh
fping -c 3 10.20.40.28 | awk '{if(NR>4) print }'
```

## 2. Resolve IP Address with Detailed Summary of Multiple Hosts
```sh
fping -s -c 3 10.20.40.28 10.20.41.113 google.com | awk 'END {print}'
```

## 3. Check for Packets Received from a Specific Host and Port
```sh
sudo tcpdump host <host ip> and port <port number>
```

## 4. Capture Network Traffic on a Specific Interface
```sh
sudo tcpdump -i <interface name>
```

## 5. Check Whether a Port is Reachable
```sh
telnet 10.20.41.113 22
```

## 6. View & Manage Network Interfaces
### View Interfaces
```sh
ip link show
```
### Bring Interface Up
```sh
sudo ip link set <interface> up
```
### Bring Interface Down
```sh
sudo ip link set <interface> down
```

## 7. Find the Port Being Used by a Process
```sh
netstat -tulpn
```

## 8. Add & Delete Entry in Address Routing Table
### Add Route
```sh
sudo ip route add <destination_network> via <gateway_ip> dev <interface>
```
### Delete Route
```sh
sudo ip route del <destination_network>
```

## 9. View Routes Used by Current Network Interface
```sh
ip show route
```

## 10. Check Reachability of Port of Multiple IPs/IP Ranges
```sh
nmap -p <port number> <ip addresses>
```

## 11. Check the IP Address, Subnet Mask, and Default Gateway
### IP Address and Subnet Mask
```sh
ifconfig <interface>
```
### Default Gateway
```sh
ip route show
```

## 12. Configure a Static IP Address on a Network Interface
```sh
iface <interface name> inet static
address <ip>
gateway <gateway>
subnetmask <mask>
dns-nameserver <server>
```

## 13. Flush the DNS Cache
```sh
sudo systemd-resolve --flush-caches
```

## 14. Trace the Route Packets Take to a Destination
```sh
traceroute <host name>
```

## 15. Add a Static ARP Entry to the ARP Table
```sh
sudo arp -s <ip> <MAC>
```

## 16. Check Network Statistics (Packets Sent/Received, Errors)
```sh
netstat -i
ifconfig <interface name>
```

## 17. Check Listening Ports and Established Connections
```sh
netstat -al
```

## 18. Find the Process ID (PID) Associated with a Specific Port
```sh
netstat -tunp | grep <port>
```

## 19. Check the MTU (Maximum Transmission Unit) Size of a Network Interface
```sh
ifconfig wlp0s20f3 | awk 'NR==1 {print "MTU:" $4}'
```

## 20. Configure a Network Interface to Use DHCP
```sh
cd /etc/netplan/01-network-manager-all.yaml
network:
  version: 2
  renderer: networkd
  ethernets:
    eth0:
      dhcp4: true
sudo netplan apply
```

## 21. Allow or Block Traffic on a Specific Port or from a Specific Host
```sh
sudo ufw deny/allow proto <protocol name> from <host ip> to <interface name> port <port number>
```

## 22. Check the System's Network Interface DNS Servers
```sh
systemd-resolve --status
```

## 23. Check the System's Network Interface RX/TX Packets
```sh
ifconfig <interface name> | grep -e RX -e TX 
netstat -i | grep <interface name>
```

## 24. Query an SNMP Agent for System Information
```sh
snmpget -v2c -c public 10.20.41.113 .1.3.6.1.2.1.1.1.0
```

## 25. Perform a Walk Operation to Retrieve All OIDs from an SNMP Agent
```sh
snmpwalk -v2c -c public 10.20.41.113
```

## 26. Configure SNMPv3 with Authentication and Encryption
```sh
sudo apt install snmpd snmp libsnmp-dev -y
sudo net-snmp-create-v3-user -ro -A "jayesh12345" -a SHA -X "motadata12345" -x AES jayesh1-snmp
```

## 27. Query an SNMPv3 Agent Using snmpget and snmpwalk
```sh
snmpwalk -v3 -u jayesh1-snmp -l authPriv -A "jayesh12345" -a SHA -X "motadata12345" -x AES localhost
snmpget -v3 -u jayesh1-snmp -l authPriv -A "jayesh12345" -a SHA -X "motadata12345" -x AES localhost
```

## 28. Monitor CPU/Memory/Disk Usage Using SNMP
### Create `monitor.sh`
```sh
#!/bin/bash

# SNMP Configuration
SNMP_USER="jayesh1-snmp"
SNMP_AUTH_PASS="jayesh12345"
SNMP_PRIV_PASS="motadata12345"
SNMP_HOST="localhost"
SNMP_VERSION="3"
AUTH_PROTO="SHA"
PRIV_PROTO="AES"

# Function to Retrieve SNMP Data
snmp_get() {
  snmpwalk -v$SNMP_VERSION -u $SNMP_USER -l authPriv -A "$SNMP_AUTH_PASS" -a $AUTH_PROTO -X "$SNMP_PRIV_PASS" -x $PRIV_PROTO $SNMP_HOST $1 | awk '{print $NF}'
}

# CPU Usage
echo "🖥 CPU Load (1 min): $(snmp_get .1.3.6.1.4.1.2021.10.1.3.1)%"
echo "🖥 CPU Load (5 min): $(snmp_get .1.3.6.1.4.1.2021.10.1.3.2)%"
echo "🖥 CPU Load (15 min): $(snmp_get .1.3.6.1.4.1.2021.10.1.3.3)%"

# Memory Usage
TOTAL_RAM=$(snmp_get .1.3.6.1.4.1.2021.4.5.0)
USED_RAM=$(snmp_get .1.3.6.1.4.1.2021.4.6.0)
FREE_RAM=$(snmp_get .1.3.6.1.4.1.2021.4.11.0)
echo "🗄 Total RAM: $TOTAL_RAM KB"
echo "🗄 Used RAM: $USED_RAM KB"
echo "🗄 Free RAM: $FREE_RAM KB"

# Disk Usage
DISK_PATH=$(snmp_get .1.3.6.1.4.1.2021.9.1.2.1)
TOTAL_DISK=$(snmp_get .1.3.6.1.4.1.2021.9.1.6.1)
echo "💾 Disk Path: $DISK_PATH"
echo "💾 Total Disk: $TOTAL_DISK KB"
```
