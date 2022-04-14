package fr.vertours.poseidon.exception;

public class InvalidIDException extends RuntimeException {

    private final Integer id;

    public InvalidIDException(Integer id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "this Id : " + id + " is invalid";
    }
}
