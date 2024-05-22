# BankApp Build Phase

## Initialization

This document describes the initialization of the BankApp project, highlights identified vulnerabilities, and provides recommendations for mitigation.

## Vulnerabilities

### 1. Session Hijacking

**Description**:
An attacker can hijack an active session and perform actions without re-authentication if the session cookie is intercepted and reused.

**Exploitation Steps**:
1. Log in and capture the session cookie using Postman:
    ```bash
    curl --location 'http://localhost:8080/login' \
    --header 'Cookie: JSESSIONID=3019EB05A5802654A6DD26A2C228ABBE' \
    --form 'username="gdmrw"' \
    --form 'password="123456"'
    ```
2. Use the captured session cookie to perform a withdrawal on a different machine:
    ```bash
    curl --location 'http://localhost:8080/withdraw' \
    --header 'Cookie: JSESSIONID=6EA1974293EE4784AC2450418D89C7D6' \
    --form 'amount="100"'
    ```
   > `localhost` can be the specific target IP if you use an external machine.

**Mitigation**:
- Use HTTPS to encrypt all communication.
- Implement session filters to validate session integrity.

### 2. CWE-256: Plaintext Storage of a Password

**Description**:
Passwords are stored in plain text in the database when using the `registerUser` method. This exposes users to potential data breaches.

**Exploitation Steps**:
Review the database entries and observe that passwords are stored as plain text.

**Mitigation**:
- Encode passwords using SHA-256 before storing them in the database.
- Utilize libraries such as BCrypt for stronger password hashing.

### 3. CWE-79: Improper Neutralization of Input During Web Page Generation (Cross-site Scripting - XSS)

**Description**:
The application is vulnerable to XSS attacks, allowing attackers to inject malicious scripts into web pages viewed by other users.

**Exploitation Steps**:
1. Open the app and log in through the website.
2. On the banking system page, right-click the withdraw button and select `Inspect`.
3. Modify the `button` tag in HTML to include an `onclick` function:
    ```html
    <button type="submit" class="ant-btn css-dev-only-do-not-override-1r287do ant-btn-primary" onclick="alert('XSS Attack!');"><span>Withdraw</span></button>
    ```
4. Click the withdraw button to trigger the alert box.

**Mitigation**:
- Sanitize all user inputs.
- Use libraries like DOMPurify to clean HTML inputs.
- Implement Content Security Policy (CSP) headers.

### 4. Sensitive Data Exposure Without Authentication

**Description**:
Sensitive application data can be accessed without authentication through the Actuator endpoint.

**Exploitation Steps**:
Access the following URL without authentication:
```bash
http://localhost:8080/actuator/env
```

**Mitigation**:
- Restrict access to sensitive Actuator endpoints.
- Require authentication for accessing Actuator endpoints.

### 5. Remote Shutdown

**Description**:
The application allows remote shutdown via the Actuator endpoint without proper authentication, enabling potential Denial of Service (DoS) attacks.

**Exploitation Steps**:
Send a POST request to the shutdown endpoint:
```bash
curl --location --request POST 'http://localhost:8080/actuator/shutdown'
```

**Mitigation**:
- Disable the shutdown endpoint in production environments.
- Require authentication for sensitive Actuator endpoints.

## Secure Coding and Design Principles Followed

### Input Validation

- **Principle**: Validate all inputs to ensure they conform to expected formats.
- **Implementation**: Used regular expressions to validate inputs such as usernames, passwords, and monetary amounts.

### Data Encryption

- **Principle**: Encrypt sensitive data to protect it from unauthorized access.
- **Implementation**: Plan to implement SHA-256 hashing for password storage.

### Session Management

- **Principle**: Securely manage user sessions to prevent hijacking.
- **Implementation**: Plan to enforce HTTPS and implement session filters.

### Access Control

- **Principle**: Restrict access to sensitive resources based on user roles and permissions.
- **Implementation**: Plan to secure Actuator endpoints and require authentication for sensitive operations.

## Conclusion

This document provides an overview of the vulnerabilities identified in the BankApp project during the build phase. It includes detailed steps for exploiting these vulnerabilities and recommendations for mitigating them. Following secure coding practices and implementing the suggested mitigations will enhance the security of the application.
