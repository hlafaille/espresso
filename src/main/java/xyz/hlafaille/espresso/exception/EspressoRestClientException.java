package xyz.hlafaille.espresso.exception;

public class EspressoRestClientException extends Exception{
    public EspressoRestClientException(String message, Integer statusCode) {
        super("Status %s \n %s".formatted(statusCode, message));

    }
}
