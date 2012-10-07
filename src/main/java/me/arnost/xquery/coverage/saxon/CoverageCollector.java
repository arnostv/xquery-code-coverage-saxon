package me.arnost.xquery.coverage.saxon;

import java.io.PrintStream;
import java.util.ArrayList;

public class CoverageCollector {
    ArrayList<CoverageEvent> events = new ArrayList<CoverageEvent>();

    public void register(CoverageEvent event) {
        events.add(event);
    }

    public void printAll(final PrintStream out) {
        for (CoverageEvent event : events) {
            out.println(event);
        }
    }
}
