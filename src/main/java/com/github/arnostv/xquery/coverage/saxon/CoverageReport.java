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

    public Set<Integer> ignoredLines() {
        Set<Integer> ignoredLines = new HashSet<Integer>();

        for (int i = 1, l = lines.size(); i <= l; i++) {
            if (!coveredLines.contains(i) && isIgnoredLine(lines.get(i-1))) {
                ignoredLines.add(i);
            }
        }

        return Collections.unmodifiableSet(ignoredLines);

    }

    private boolean isIgnoredLine(final String line) {
      if (line.isEmpty()) {
          return true;
      } else if (line.startsWith("import module")) {
          return true;
      } else if (line.startsWith("module namespace")) {
          return true;
      } else if (line.startsWith("import module")) {
          return true;
      } else if (line.startsWith("declare default")) {
          return true;
      } else if (line.trim().equals("};")) {
          return true;
      } else {
          return false;
      }
    };

    public String lineText(int i) {
        return lines.get(i - 1);
    }
}
