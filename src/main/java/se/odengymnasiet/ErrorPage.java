package se.odengymnasiet;

/**
 * Handling error pages. Currently only HTTP 404 (not found) and HTTP 500
 * (internal server error) are supported.
 */
public class ErrorPage {

    public static final String VIEW_NAME = "error";

    public static final String MESSAGE_NOT_FOUND = "Sidan hittades inte!";
    public static final String MESSAGE_INTERNAL_ERROR = "Internt serverfel!";

    private final Application app;

    private String code;
    private String message;

    public ErrorPage(Application app) {
        this.app = app;
    }

    public ErrorPage(Application app, String code, String message) {
        this(app);

        this.code = code;
        this.message = message;
    }

    public ErrorPage(Application app, int code, String message) {
        this(app, Integer.toString(code), message);
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getTitle() {
        return this.getCode() + " - " + this.getMessage();
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCode(int code) {
        this.setCode(Integer.toString(code));
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String render() {
        return this.app.renderView(VIEW_NAME, Attributes.create("error", this));
    }
}
