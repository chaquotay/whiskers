package org.notatoaster.whiskers;

public class ProbeResult {

    private String message;
    private ProbeResultType type;

    public ProbeResult(ProbeResultType type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return type==ProbeResultType.Error;
    }

    public boolean isSuccess() {
        return type==ProbeResultType.Success;
    }

    public static ProbeResult error(String message) {
        return new ProbeResult(ProbeResultType.Error, message);
    }

    public static ProbeResult success() {
        return success("");
    }

    public static ProbeResult success(String message) {
        return new ProbeResult(ProbeResultType.Success, "");
    }

    private enum ProbeResultType {
        Success, Error
    }
}
