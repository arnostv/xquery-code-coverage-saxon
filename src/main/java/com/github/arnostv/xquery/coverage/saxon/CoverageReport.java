package com.github.arnostv.xquery.coverage.saxon;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CoverageReport {

    private final List<String> lines;
    private final String systemId;
    private final Set<Integer> coveredLines;

    public CoverageReport(String systemId, List<String> lines, Set<Integer> coveredLines) {
        this.systemId = systemId;
        this.lines = lines;
        this.coveredLines = coveredLines;
    }

    public int numberOfLines() {
        return lines.size();
    }

    public Set<Integer> coveredLines() {
        return coveredLines;
    }

    public Set<Integer> unusedLines() {
        Set<Integer> unusedLines = new HashSet<Integer>();
        for (int i = 1, l = lines.size(); i <= l; i++) {
            if (!coveredLines.contains(i)) {
                unusedLines.add(i);
            }
        }
        return Collections.unmodifiableSet(unusedLines);
    }

    public String lineText(int i) {
        return lines.get(i - 1);
    }
}
