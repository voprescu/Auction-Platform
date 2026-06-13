# Auction Platform 

A desktop auction application built with Java, featuring a Swing GUI and MySQL database.
Users can register, create auctions, and place bids in real time.

## Screenshots
<img width="300" height="250" alt="app1" src="https://github.com/user-attachments/assets/5f5c6963-da24-4628-b120-8f137a1b4419" />
<img width="300" height="250" alt="app2" src="https://github.com/user-attachments/assets/de862a94-f3a2-4c45-a866-07a1337c2103" />
<img width="300" height="250" alt="app3" src="https://github.com/user-attachments/assets/98019029-1a8b-43c0-b60d-47256accfb7c" />

## Project Structure
```
src/main/java/
|- model/      - Data classes (User, Auction, Bid)
|- dao/        - Database access layer
|- service/    - Business logic
|- ui/         - Swing windows
|- util/       - Database connection & utilities
```

## Tech Stack
- Java 23
- MySQL
- Swing + Flatlaf (UI)
- Maven
- BCrypt (password hashing)

## Features
- User registration and authentication
- Create auctions with title, description, starting price and expiration date
- Browse active auctions
- Place bids with automatic validation
- Bid history for each auction
- Modern UI

## Installing

### Requirements
- Java 23
- MySQL Server

### Steps
1. Clone the repository
```bash
   git clone https://github.com/voprescu/Auction-Platform.git
```

2. Open the project in IntelliJ IDEA

3. Import the database
```bash
   mysql -u root -p < database/auctionDB.sql
```
   This will create the `licitatii` database and all tables.

4. Update your MySQL credentials in `src/main/java/util/DatabaseConnection.java`
```java
   private static final String USER = "root";
   private static final String PASS = "your_password";
```

5. Run `Main.java`

   

