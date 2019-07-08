package pl.britenet.exception;

public class FormatNotSupportedExcepion extends RuntimeException {

    public FormatNotSupportedExcepion() {
        super("Given file format not supported");
    }
}
