package edu.pdx.cs410J.eschott.client;

/**
 * Creates exception handler for errors related to customer name.
 * Created by Evan on 8/13/2015.
 */
public class InvalidCustomerNameException extends RuntimeException {
  public InvalidCustomerNameException(String message) {
    super(message);
  }

  public InvalidCustomerNameException() {
  }
}
