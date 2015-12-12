# S-Key-Implementation
Implementation of an authentication system using the one time password mechanism S/Key.

How to use the program

When you first run the program, the program will prompt the user with either to register an account by entering ‘1’ or log into an account by entering ‘2’. You would need to register an account before logging in so, the user should select ‘1’ before selecting ‘2’. If the user were to select ‘2’ when there are no accounts registered, then the program will notify the user and they will be prompted with the same menu. Selecting ‘1’ will send you to the registration section where you are prompted to enter your username and password. If the username is already registered into the database, then the program will prompt the user to enter another username. After doing so, it will send you back to the main menu where you are asked to either register an account or log in again. You can register as many accounts as you want. After you finished registering all your desired accounts into the database, you can try logging into one of them by selecting ‘2’ from the main menu, in which it will send you to the login section. The login section will prompt you to enter a username and password. If the username and password match, then the program will print “SUCCESS!” and will the program will end, and if they do not match, then the program will print “WRONG PASSWORD” and will return the user back to the main menu.

Introduction of database file

The program uses a standard text file to store the username and password under the name of accounts.txt. It is created within the program and then saved and located in the same folder as where the program is implemented. When the program is writing to the text file, it writes the name of the username first along with the encrypted password next to the name. If another account is registered into the database, then it will be registered onto the next line. The password is encrypted to protect the user in case the database gets hacked by a third party.

Explanation of cryptographic function

The program uses the MD5 algorithm to encrypt the password. When the user registers their password, the program will immediately encrypt their password before writing it into the text file database. This is to ensure the protection of the user. If the database is hacked by a third party, then they would not see the actual password because it will be encrypted. MD5 was used to encrypt the password because it is a one-way hash function – meaning that it cannot be decrypted; it can only be encrypted. Therefore, even if they had the encrypted password, there would be no way of them figuring out the actual password from that.

Security Analysis on the current system

One possible attack from using this security system is through collision attacks. This happens when two inputs produces the same hash value. So, if a hacker obtains the database files, all he would need to do is provide a password input that has the same hash value as the original password and they would have access to the victim’s account – they would not necessarily need to enter the original password to log in. One way to make the password more secure is to use a random data called a salt in which it is an additional input to a one-way hash function. It would mean that the program would also save this salt into the database. It is a random string which is more or less long and is unique for each user.
