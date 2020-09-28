# demo-bulkhead

## Getting Started

Just run the Application.java in your IDE.  
Application is running on http://localhost:9090.

### Example
I will be using the idea of a bank where the operations to get cash or deposit money can only happen one at a time.

Requirements for this example:

- If I have a balance, I subtract the amount request from the current balance
- Each operation can take up to 3 seconds to process
- The bank can process only one transaction at a time.

To do the BankService Bulkhead tests, run the two endpoints below in less than 3 seconds.

Use the endpoint http://localhost:9090/bank/cashOut/500 to simulate a get cash worth 500.
Use the endpoint http://localhost:9090/bank/deposit/1000 to simulate a deposit worth 1000.

When trying to process two transactions at the same time, one to get cash and another to make a deposit, we get an exception in the attempt to process the second request because the application received the second request before we finish the first one avoid us to make a mistake in the way to calculate the current balance of the user.
