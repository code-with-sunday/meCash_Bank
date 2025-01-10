<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
    <li>
      <a href="#about-the-project">Built With</a>
    <li>
      <a href="#about-the-project">Getting Started</a>
    <li>
      <a href="#about-the-project">Features/Usage</a>
    <li>
      <a href="#about-the-project">Sample Test</a>
    <li>
      <a href="#about-the-project">Sample Data Persisting DB</a>
    <li>
      <a href="#about-the-project">Contact</a>
  </ol>
</details>
# meCash - Multicurrency Wallet Application

meCash is a financial application that enables businesses and individuals to send and receive money across different currencies. At meCash, we are committed to building robust and scalable financial solutions.

---

## About The Project

The **meCash API** is designed for a multicurrency wallet application where users can manage their funds in different currencies. This RESTful API supports essential financial operations such as account creation, login, deposits, withdrawals, transfers, and transaction history viewing.

As a Backend Engineer, this project aims to assess your technical expertise, problem-solving skills, and ability to design scalable systems.

---

## Technology Stack

- **Programming Language**: Java
- **Framework**: Spring Boot
- **ORM**: JPA/Hibernate
- **Database**: Relational Database (PostgreSQL)
- **Testing**: JUnit, Mockito
- **Version Control**: Git
- **Authentication**: JWT (JSON Web Token)
- **API Documentation**: Swagger UI

---

## Features

- **User Management**: Register, log in, and authenticate users.
- **Wallet Management**: Deposit, withdraw, and transfer money.
- **Transaction History**: Users can view past transactions.
- **Multicurrency Support**: Manage and transfer funds in different currencies.
- **Security**: Secure user authentication using JWT.

---

## API Endpoints

Below are the key endpoints for interacting with the **meCash** API:

### Authentication
- **POST /auth/signup** - Register a new user.
- **POST /auth/admin/signup** - Register a new admin user.
- **POST /auth/login** - Log in and receive a JWT token.

### Bank Operations
- **GET /bank/transactions/{accountNumber}/history** - View transaction history for a specific account.
- **GET /bank/accounts/{accountNumber}/balance** - View the current balance of an account.
- **POST /bank/accounts/{accountNumber}/deposit** - Deposit money into an account.
- **POST /bank/accounts/{accountNumber}/withdraw** - Withdraw money from an account.
- **POST /bank/accounts/{fromAccountNumber}/transfer** - Transfer money from one account to another.
- **GET /bank/accounts/{accountNumber}/transactions** - View all transactions for a specific account.

# meCash - Database Schema

This document outlines the database schema for the **meCash** financial application, which allows businesses and individuals to send and receive money across different currencies.

## Database Tables

### **1. Users Table**

The `users` table stores information about each user, including their account details, authentication data, and roles.

| Field         | Type      | Description                                         |
|---------------|-----------|-----------------------------------------------------|
| id            | UUID      | Primary key, automatically generated.               |
| create_date   | TIMESTAMP | Timestamp when the user record was created.         |
| update_date   | TIMESTAMP | Timestamp when the user record was last updated.    |
| username      | VARCHAR   | Unique username, not null.                          |
| password      | VARCHAR   | User's password, not null.                          |
| email         | VARCHAR   | Unique email address, not null.                     |
| first_name    | VARCHAR   | User's first name, not null.                        |
| last_name     | VARCHAR   | User's last name, not null.                         |
| role          | VARCHAR   | User's role (e.g., `USER`, `ADMIN`).                 |

### **2. Accounts Table**

The `accounts` table contains information about each account associated with a user.

| Field            | Type      | Description                                        |
|------------------|-----------|----------------------------------------------------|
| id               | UUID      | Primary key, automatically generated.              |
| create_date      | TIMESTAMP | Timestamp when the account record was created.     |
| update_date      | TIMESTAMP | Timestamp when the account record was last updated.|
| account_number   | VARCHAR   | Unique account number, not null.                   |
| currency         | VARCHAR   | Currency type, not null (e.g., USD, EUR).          |
| account_status   | VARCHAR   | Account status (e.g., `ACTIVE`, `INACTIVE`).        |
| balance          | DECIMAL   | Account balance, default is `0`, not null.         |
| user_id          | UUID      | Foreign key referencing `users(id)`, not null.     |

### **3. Transactions Table**

The `transactions` table stores all transaction records related to user accounts, including deposits, withdrawals, and transfers.

| Field               | Type      | Description                                        |
|---------------------|-----------|----------------------------------------------------|
| id                  | UUID      | Primary key, automatically generated.              |
| create_date         | TIMESTAMP | Timestamp when the transaction record was created. |
| update_date         | TIMESTAMP | Timestamp when the transaction record was last updated. |
| transaction_id      | UUID      | Unique transaction ID, automatically generated.    |
| transaction_type    | VARCHAR   | Type of transaction (e.g., `DEPOSIT`, `WITHDRAWAL`, `TRANSFER`), not null. |
| amount              | DECIMAL   | Transaction amount, not null.                      |
| transaction_status  | VARCHAR   | Status of the transaction (e.g., `COMPLETED`, `PENDING`), not null. |
| currency            | VARCHAR   | Currency used for the transaction (e.g., USD, EUR), not null. |
| transaction_date    | TIMESTAMP | Timestamp of when the transaction occurred.        |
| account_id          | UUID      | Foreign key referencing `accounts(id)`, not null.  |
| target_account_id   | UUID      | Foreign key referencing `accounts(id)` (nullable), used for transfers. |
| note                | VARCHAR   | Optional note or description for the transaction.  |

## Relationships

- **User and Account**:
    - A **user** can have many **accounts** (one-to-many relationship).
    - The `accounts` table has a foreign key `user_id` referencing `users(id)`.

- **Account and Transaction**:
    - An **account** can have many **transactions** (one-to-many relationship).
    - The `transactions` table has a foreign key `account_id` referencing `accounts(id)`.

- **Transaction and Target Account**:
    - A **transaction** may optionally have a **target account** (used for transfers).
    - The `transactions` table has a foreign key `target_account_id` referencing `accounts(id)`.

## Table Creation SQL Scripts

Here are the SQL scripts to create the tables:

```sql
CREATE TABLE users (
    id UUID PRIMARY KEY,
    create_date TIMESTAMP NOT NULL,
    update_date TIMESTAMP NOT NULL,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    role VARCHAR(50)
);

CREATE TABLE accounts (
    id UUID PRIMARY KEY,
    create_date TIMESTAMP NOT NULL,
    update_date TIMESTAMP NOT NULL,
    account_number VARCHAR(255) UNIQUE NOT NULL,
    currency VARCHAR(50) NOT NULL,
    account_status VARCHAR(50) NOT NULL,
    balance DECIMAL DEFAULT 0 NOT NULL,
    user_id UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE transactions (
    id UUID PRIMARY KEY,
    create_date TIMESTAMP NOT NULL,
    update_date TIMESTAMP NOT NULL,
    transaction_id UUID UNIQUE NOT NULL,
    transaction_type VARCHAR(50) NOT NULL,
    amount DECIMAL NOT NULL,
    transaction_status VARCHAR(50) NOT NULL,
    currency VARCHAR(50) NOT NULL,
    transaction_date TIMESTAMP NOT NULL,
    account_id UUID NOT NULL,
    target_account_id UUID,
    note VARCHAR(255),
    FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE,
    FOREIGN KEY (target_account_id) REFERENCES accounts(id) ON DELETE CASCADE
);



Contact
Sunday Peter

LinkedIn: linkedin.com/in/sundaypeter1
Email: sundaypetersp12@gmail.com
WhatsApp: +234 8186707807
Phone: +234 8169036052