/* (C)2023 */
package com.springbootbooks.api.exceptions;

// The unchecked exceptions are more preferred than checked exceptions
public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
