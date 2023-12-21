/* (C)2023 */
package com.springbootbooks.api.exceptions;

public class IntegrityConstraintViolationException extends Exception {

    public IntegrityConstraintViolationException(String message) {

        super(message);
    }
}
