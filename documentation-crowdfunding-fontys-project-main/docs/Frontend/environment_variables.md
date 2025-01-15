---
sidebar_position: 4
sidebar_label: Environment variables
---

# Environment Variables

The following environment variables are used in the application:

## AWS Configuration

| Variable                 | Description                                               |
|--------------------------|-----------------------------------------------------------|
| `AWS_BUCKET_NAME`        | Name of the S3 bucket used for storing application data.  |
| `AWS_BUCKET_REGION`      | AWS region where the S3 bucket is hosted.                 |
| `AWS_ACCESS_KEY`         | AWS access key for programmatic access.                   |
| `AWS_SECRET_ACCESS_KEY`  | AWS secret access key for programmatic access. Keep this secure. |

---

### Notes:
1. **Security**:  
   - These environment variables contain sensitive credentials and must be kept secure.
   - Use tools like AWS Secrets Manager or `.env` files to manage them securely.
   - Store these keys in GitHub Secrets for use in CI/CD pipelines.

2. **AWS Configuration**:  
   - Ensure the S3 bucket (`AWS_BUCKET_NAME`) and its region (`AWS_BUCKET_REGION`) are correctly configured in your AWS account.
   - Use IAM roles with least privilege for production environments instead of static access keys.

3. **Access Keys**:  
   - Rotate `AWS_ACCESS_KEY` and `AWS_SECRET_ACCESS_KEY` regularly to maintain security.
   - Avoid hardcoding these credentials directly in source code or exposing them in public repositories.
