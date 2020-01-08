/* Author: Hunter Riley
 * 
 * Date: 5/21/2017
 * 
 * Program: Credit Card Validation
 */

import java.util.Scanner;

public class CreditCard {

	
	public static void main(String[] args) {
	//Create scanner object	
	Scanner input = new Scanner(System.in);
	System.out.println("Enter a credit card number as a long integer:");
	long cardNumber;
	//Display whether card number is valid or invalid
	if(isValid(cardNumber = input.nextLong()))
		System.out.println(cardNumber  + " is valid!");
	else
		System.out.println(cardNumber + " is invalid!");
	
	
	}
	
	//If credit card number is valid, return true
	public static Boolean isValid(long number){
		boolean result = false;
		if (getSize(number) >= 13 && getSize(number) <= 16)
			if (prefixMatched(number, 4) || prefixMatched(number, 5) || prefixMatched(number, 37) || prefixMatched(number, 6) ){
				result = ((sumOfOddPlaces(number) + sumOfEvenPlaces(number)) % 10 == 0 );
			}
		
		return result;
		
	}
	
	//Get size of credit card number
	public static int getSize(long d){
		//Turn credit card size into a string
		return Long.toString(d).length();
		
	}
	
	//Return true if the prefix of credit card matches 4, 5, 6, 37
	public static Boolean prefixMatched(long number, int d){
		int size = Integer.toString(d).length();
		String numberToString = Long.toString(number);
		String dToString = Integer.toString(d);
		
		for (int i=0; i<size; i++)
			if (numberToString.charAt(i) != dToString.charAt(i))
				return false;
		return true;
		
	}
	
	//Return this number if it is a single digit. Otherwise return the sum of the two digits
	public static int getDigit(int number) {
		if (number <= 9) {
			return number;
		} else if (number > 9)
			return (number % 10 + number / 10);
		
		return number;
	}
	
	//Get the sum of the even places *
	public static int sumOfEvenPlaces(long number) {
		int sum = 0;
		number = number / 10;
		while (number != 0) {
			sum += getDigit((int)((number % 10) * 2));
			number = number / 100;
		}
		return sum;
	}
	
	
	//Get the sum of the odd places in the credit card number
	public static int sumOfOddPlaces(long number) {
		int sum = 0;
		while (number != 0) {
			sum += (int)(number % 10);
			number = number / 100;
		}
		return sum;
	}
	
	//Return the first number of k digits.
	public static long getPrefix(long number, int k) {
		long result = number;
		for (int i = 0; i < getSize(number) - (k - 1); i++)
			result /= 10;
		return result;
		
	}
	
}
