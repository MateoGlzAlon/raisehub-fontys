---
sidebar_position: 6
sidebar_label: DockerHub Repositories
---

# DockerHub Repositories

This guide provides the details for pulling the necessary Docker images from DockerHub to set up the RaiseHub application.

---

## Frontend Image

To pull the frontend image, use the following command:

```bash
docker pull mateogonzalezz/raisehub_frontend:latest
```

Once you have pulled the image, follow this guide to set up the backend container:
 - [Frontend container setup](fe_container.md#creating-and-running-the-docker-container)

You can also visit the [frontend repository on DockerHub](https://hub.docker.com/r/mateogonzalezz/raisehub_frontend).

---

## Backend Image

To pull the backend image, run the following command:

```bash
docker pull mateogonzalezz/raisehub_backend:latest
```

Once you have pulled the image, follow this guide to set up the backend container:
 - [Backend container setup](be_container.md#creating-and-running-the-docker-container)

You can also visit the [backend repository on DockerHub](https://hub.docker.com/r/mateogonzalezz/raisehub_backend).

---

## Database Image

To pull the database image, execute the command below:

```bash
docker pull mateogonzalezz/raisehub_database:latest
```

More information can be found in the [database repository on DockerHub](https://hub.docker.com/r/mateogonzalezz/raisehub_database).

:::note
The database image includes preloaded demo data. Use the following credentials to access the demo environment:
- **Username**: `root`  
- **Password**: `25DX0MR2CG`
:::

:::warning
Dont forget to [create a docker network](brigde_config.md) and then [connect the database and backend containers to the same network](connect_to_network.md).
:::
---

Use these images to set up the backend, frontend, and database for the RaiseHub application efficiently.