This is a springboot assessment project

It uses Java 17, and an in-memory H2 Database, the project contains a few unit tests as well.
On startup, it runs a script that insers a few dummy data, So you can test the makepayment endpoint for any scenario you want.

N.B : The project is setup in a way that for a shared student with 2 parents, if parent A does not have enough in the account, parent B will be deducted as well, and both balances will be updated accordingly
N.B : The students balances at the start of every semester is always in Negative.

Two login credentials are shared below as a basic authentication was used
username : admin
password : admin123

username : support
password : admin123

To run, 
Install JDK 17 and run.

The postman collection is shared in the resources folder.

