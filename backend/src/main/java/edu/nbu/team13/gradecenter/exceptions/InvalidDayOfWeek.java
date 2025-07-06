package edu.nbu.team13.gradecenter.exceptions;

public class InvalidDayOfWeek extends RuntimeException {
    public InvalidDayOfWeek(byte id) {
        super("Invalid day of week:" + id);
    }
}
