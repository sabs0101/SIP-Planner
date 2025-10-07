# SIP-Planner
SIP & Investment Planner

A Systematic Investment Plan (SIP) and Investment Planner that helps users calculate and compare potential returns from FD, PPF, Mutual Funds, and Stocks. The application provides a modern GUI frontend and integrates real-time financial data using APIs.


---

Features

Calculate SIP/maturity for FD and PPF across multiple banks.

Fetch Mutual Fund data and calculate potential returns using mfapi.

Fetch Stock data from Yahoo Finance (yfinance) for analysis.

Recommend the best investment option based on calculated returns.

Interactive charts displaying historical performance for stocks and mutual funds.

Modern and responsive frontend UI using HTML, CSS, and JavaScript.

Loader animation while calculations are being fetched.



---

Screenshots





---

Tech Stack

Backend: Java, Spring Boot

Frontend: HTML, CSS, JavaScript

APIs: mfapi for Mutual Funds, yfinance for Stocks

Charts: Chart.js (frontend)

Data Storage: In-memory (for FD/PPF bank rates)



---

Installation & Setup

1. Clone the repository



git clone https://github.com/username/sip-planner.git
cd sip-planner

2. Install Java & Maven

Java 17+ recommended

Maven for dependency management



3. Run the Spring Boot application



mvn spring-boot:run

4. Open your browser at http://localhost:8080




---

API Integration

Mutual Funds: https://api.mfapi.in/mf

Stocks: Yahoo Finance (yfinance in backend or Java equivalent)

FD/PPF: Static bank interest rates stored in backend arrays.


> Note: For Stocks and MF, make sure your API calls are live; check API limits.




---

Usage

1. Enter monthly investment amount and duration (in years).


2. Choose Investment Type: FD, PPF, MF, or Stocks.


3. Click Calculate.


4. View all options, recommended investment, and interactive charts for selected plans.




---

Future Improvements

Integrate more investment types: ETFs, Bonds, Gold, etc.

Add user login and save historical investments.

Add dynamic bank interest rate fetching from APIs.

Improve charts with candlestick and comparative performance.



---

License

This project is MIT Licensed. See LICENSE for details.


---

If you want, I can also write a version that’s copy-paste ready with all your project instructions, API keys section, and screenshots placeholders—perfect for GitHub hosting.
