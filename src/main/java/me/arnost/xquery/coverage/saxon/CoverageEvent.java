package me.arnost.xquery.coverage.saxon;

public class CoverageEvent {
    private final String systemId;
    private final String displayName;
    private final int lineNumber;

    public CoverageEvent(String systemId, String displayName, int lineNumber) {
        this.systemId = systemId;
        this.displayName = displayName;
        this.lineNumber = lineNumber;
    }

    public String getSystemId() {
        return systemId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public String toString() {
        return "CoverageEvent{" +
                "systemId='" + systemId + '\'' +
                ", displayName='" + displayName + '\'' +
                ", lineNumber=" + lineNumber +
                '}';
    }
}
