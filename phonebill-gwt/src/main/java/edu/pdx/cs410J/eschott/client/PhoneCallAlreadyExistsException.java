package edu.pdx.cs410J.eschott.client;

/**
 * Created by Evan on 8/13/2015.
 */
public class PhoneCallAlreadyExistsException extends RuntimeException {
  public PhoneCallAlreadyExistsException(String message) {
    super(message);
  }

  public PhoneCallAlreadyExistsException() {
  }
}
