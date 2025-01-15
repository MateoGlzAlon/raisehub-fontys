---
sidebar_position: 4
sidebar_label: Environment variables
---

# Environment Variables

The following environment variables are used in the application:

## JWT Configuration

| Variable       | Description                                |
|----------------|--------------------------------------------|
| `JWT_SECRET`   | Secret key used to sign and verify JWT tokens. Keep this secure. | 

## MySQL Database Configuration

| Variable          | Description                                  |
|-------------------|----------------------------------------------|
| `MYSQL_USERNAME`  | Username for MySQL database authentication.  |
| `MYSQL_PASSWORD`  | Password for MySQL database authentication.  |

---

### Notes:
1. **Security**:  
   - These environment variables should be kept secure.
   - Use password managers to store them in case you need to see them again.
   - Store them in GitHub secrets to be able to use them in GitHub Actions for CI/CD.

2. **Database**:  
   - Ensure MySQL is properly configured with the provided credentials.  
   - Use strong passwords for production environments.

3. **JWT**:  
   - Change `JWT_SECRET` to a secure, random value in production.  
   - Never hardcode this into your code, use tools such as .env files or GitHub secrets.
