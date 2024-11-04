package org.instagramapi.exceptions;

import org.instagramapi.modal.User;

public class UserException extends Exception {
   public UserException(String message) {
        super(message);
   }
}
