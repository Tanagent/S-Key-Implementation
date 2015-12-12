package cs460;

/**
 * @author Brian Tan
 * CS 460
 * Final Project
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;


public class Authenticate {
	// determines if the program should continue
	public static boolean success = true;

	public static void main(String[] args) throws Exception {
		// This is the database file
		File file = new File("account.txt");

		// Starts a new file every time the program starts
		if (file.exists())
			file.delete();

		// The program continues until the user is able to log in.
		do {
			menu(file);
		} while (success);
	}

	/**
	 * MD5 algorithm to encrypt a given text.
	 * This method converts a plain text to an encrypted text.
	 * 
	 * @param text - the plain text
	 * @return - the encrypted text
	 */
	public static String MD5(String text) {
		String password = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(text.getBytes(), 0, text.length());
			password = new BigInteger(1, md.digest()).toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return password;
	}

	/**
	 * This method prompts the user to either register an account
	 * or prompts the user to log in.
	 * 
	 * @param file
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings("resource")
	public static void menu(File file) throws FileNotFoundException {
		
		// Enters loop and prompts the user again if user has selected
		// to log in when there are no accounts in the database
		boolean check = false;
		do {
			check = false;
			Scanner scanner = new Scanner(System.in);

			System.out.println("What would you like to do?");
			System.out.println("1.) Register");
			System.out.println("2.) Log in");
			System.out.print("Select 1 or 2: ");
			char input = scanner.next().charAt(0);

			// User selects 1 to register an account
			// User selects 2 to log into an account
			if (input == '1')
				register(file);
			else if (input == '2') {
				if (file.length() == 0) {
					System.out.println("There are no accounts registered."
							+ "\nPlease register an account\n");
					check = true;
				} else
					login(file);
			}
			else
				System.out.println("Not a valid option");
		} while (check);
	}

	/**
	 * This method prompts the user to register an account by
	 * providing a user name and password. It then gets saved in
	 * a text file as a database. The password is encrypted with
	 * the MD5 method and is saved in the text file.
	 * 
	 * @param file
	 */
	@SuppressWarnings("resource")
	public static void register(File file) {
		try {
			FileWriter fw = new FileWriter(file, true);
			Scanner scanner = new Scanner(System.in);
			Scanner txtscan = new Scanner(file);

			String username = null;

			// Enters a do-while loop for the case if an existing username has already been registered
			// If there already exists that username, 'same' will change to 'true' and 
			// the loop will repeat and 'same' will change back to 'false' at the start of each loop
			// until the user enters a username that has not already been registered into the database.
			//
			// The loop also checks if there are any spaces in the username.
			// If there is, the program will notify the user and the loop will restart and they will
			// be prompted to enter another username without any spaces.
			boolean same = false;
			do {
				// set same to false at the start of each loop cycle.
				same = false;
				// User enters username here
				System.out.print("Enter username (no space): ");
				username = scanner.nextLine();

				// Checks if the username provided has any space
				// If it does, change 'same' to 'true' and repeat the loop to prompt user for another username
				if (username.contains(" ")) {
					System.out.println("The username cannot contain any space."
							+ "\nPlease enter another username\n");
					same = true;
				}

				// This scans each word in the text file to see if the username has already been taken
				// If the username has already been taken, then it will notify the user and the program
				// will repeat the function and prompt the user to enter another username.
				while (txtscan.hasNext()) {
					String str = txtscan.next();
					if (str.indexOf(username) != -1) {
						System.out.println("Username already exists. "
								+ "\nPlease choose another username\n");

						// change 'same' to 'true' to repeat the loop.
						same = true;
					}
				}
			} while (same);

			// User enters password here
			System.out.print("Enter password: ");
			String password = scanner.nextLine();

			// Program writes the username and the hashed password into the file
			// then creates a new line and closes the file here.
			fw.write(username + " ");
			fw.write(MD5(password));
			fw.write("\r\n");
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println();
	}

	/**
	 * This method prompts the user to login using their user name and
	 * password. If the user name matches with the password that they
	 * provided, the program will print "SUCCESS!" and it will exit out
	 * of the program. If the user provided the wrong password with the
	 * user name, then it will print "WRONG PASSWORD"
	 * 
	 * @param file
	 * @throws FileNotFoundException
	 */
	public static void login(File file) throws FileNotFoundException {
		// scanner to scan the user input.
		Scanner scanner = new Scanner(System.in);

		// txtscan to scan the text file.
		Scanner txtscan = new Scanner(file);

		// User enters their username here.
		System.out.print("Username: ");
		String username = scanner.nextLine();

		// User enters their password here.
		System.out.print("Password: ");
		String password = scanner.nextLine();

		// Program will search through the text file and find the matching username
		// if the username matches, it will hash the given password with the MD5 function
		// and compare it to the hash next to the username in the text file.
		// If the hash matches, the system will print out "SUCCESS!" and exits out of the program.
		// If the hash doesn't match, the system will print "WRONG PASSWORD" and returns to the main menu.
		while (txtscan.hasNextLine()) {
			String str = txtscan.next();
			if (str.indexOf(username) != -1) {
				if (MD5(password).equals(txtscan.next())) {
					System.out.println("SUCCESS!");
					success = false;
				}
				else {
					System.out.println("WRONG PASSWORD\n");
					menu(file);
				}
				break;
			}
		}

		scanner.close();
		txtscan.close();
	}
}
