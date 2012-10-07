package com.github.arnostv.xquery.coverage.saxon;

import net.sf.saxon.lib.ModuleURIResolver;
import net.sf.saxon.trans.XPathException;
import org.apache.commons.io.IOUtils;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

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

    public Set<String> involvedModules() {
        Set<String> modules = new HashSet<String>();
        for (CoverageEvent event : events) {
            modules.add(event.getSystemId());
        }
        return Collections.unmodifiableSet(modules);
    }

    public CoverageReport generateCoverageReport(String systemId, ModuleURIResolver moduleURIResolver) {
        final StreamSource[] streamSources;
        try {
            streamSources = moduleURIResolver.resolve(systemId, null, null);
        } catch (XPathException e) {
            throw new RuntimeException(e);
        }

        if (streamSources == null) {
            throw new RuntimeException("Could not find source for " + systemId);
        }

        if (streamSources.length > 1) {
            throw new RuntimeException("Multiple (" + streamSources.length + ") found for " + systemId);
        }

        final StreamSource streamSource = streamSources[0];
        final List<String> lines;
        try {
            if (streamSource.getReader() != null) {
                lines = Collections.unmodifiableList(IOUtils.readLines(streamSource.getReader()));
            } else {
                lines = Collections.unmodifiableList(IOUtils.readLines(streamSource.getInputStream()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Set<Integer> coveredLines = findCoveredLines(systemId);

        return new CoverageReport(systemId, lines, coveredLines);
    }

    private Set<Integer> findCoveredLines(String systemId) {
        Set<Integer> coveredLines = new HashSet<Integer>();

        for (CoverageEvent event : events) {
            final String displayName = event.getSystemId();
            if (systemId.equals(displayName)) {
                coveredLines.add(event.getLineNumber());
            }
        }

        return Collections.unmodifiableSet(coveredLines);
    }


}
