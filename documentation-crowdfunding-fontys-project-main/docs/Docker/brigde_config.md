---
sidebar_position: 1
sidebar_label: Bridge Network
---

# Creating the Bridge Network for connectiong the Backend and database

To set up the bridge network for your Docker containers, follow these steps:

## 1. Create a Bridge Network for Staging

Run the following command to create a new bridge network:

```bash
docker network create <network_name>
```

## 2. List All Docker Networks

To verify the networks available, list them using this command:

```bash
docker network ls
```

## 3. Verify the Network Exists

Check that your newly created network, `<network_name>`, is in the list of available networks.