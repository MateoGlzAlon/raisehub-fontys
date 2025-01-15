---
sidebar_position: 4
sidebar_label: Connect containers
---

# Connecting the container to the network

Execute the command:

```bash
docker network inspect <network_name>
```

You should get an output similar to this (I have hidden the sensitive information):

```json
[
    {
        "Name": "<network_name>",
        "Id": "HIDDEN",
        "Created": "HIDDEN",
        "Scope": "local",
        "Driver": "bridge",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": {},
            "Config": [
                {
                    "Subnet": "HIDDEN",
                    "Gateway": "HIDDEN"
                }
            ]
        },
        "Internal": false,
        "Attachable": false,
        "Ingress": false,
        "ConfigFrom": {
            "Network": ""
        },
        "ConfigOnly": false,
        "Containers": {
            "HIDDEN": {
                "Name": "raisehub_backend_container",
                "EndpointID": "HIDDEN",
                "MacAddress": "HIDDEN",
                "IPv4Address": "HIDDEN",
                "IPv6Address": ""
            },
            "HIDDEN": {
                "Name": "raisehub_database",
                "EndpointID": "HIDDEN",
                "MacAddress": "HIDDEN",
                "IPv4Address": "HIDDEN",
                "IPv6Address": ""
            }
        },
        "Options": {},
        "Labels": {}
    }
]

```

If the container section is empty, add the container to the network by using: 

```bash
docker network connect <network_name> <container_name>
```

:::warning
Make sure to connect the backend while it is running, if it is stopped it will not get connected, The backend needs the database to be running, so execute the connect command when the database is running and you just started the backend container.
:::

Now the containers can "see each other" as if they were on the same host