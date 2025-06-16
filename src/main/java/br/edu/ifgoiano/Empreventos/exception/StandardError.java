package br.edu.ifgoiano.Empreventos.exception;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

public class StandardError implements Serializable {
    private static final long serialVersionUID = 1L;

    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
    private List<String> errors;

    public StandardError(Instant timestamp, Integer status, String error, String message, String path, List<String> validationErrors) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.errors = validationErrors;
    }

    public StandardError(Instant timestamp, Integer status, String error, String message, String path) {
        this(timestamp, status, error, message, path, Collections.emptyList());
    }


    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    public List<String> getErrors() { return errors; }
    public void setErrors(List<String> errors) { this.errors = errors; }
}