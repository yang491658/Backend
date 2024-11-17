package project.blobus.Backend.member.security.util;

public class CustomJWTException extends RuntimeException {
    public CustomJWTException(String msg) {
        super(msg);
    }
}
