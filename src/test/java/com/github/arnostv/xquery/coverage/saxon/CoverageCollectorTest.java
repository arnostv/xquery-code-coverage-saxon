package com.github.arnostv.xquery.coverage.saxon;

import net.sf.saxon.lib.ModuleURIResolver;
import org.junit.Test;

import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.collect.ImmutableSet.of;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.StringContains.containsString;

public class CoverageCollectorTest {

    private static final Pattern pattern = Pattern.compile(".+_([0-9]+)\\.xq");
    ;


    ModuleURIResolver moduleURIResolver = new ModuleURIResolver() {
        @Override
        public StreamSource[] resolve(String moduleURI, String baseURI, String[] locations) {
            final Matcher matcher = pattern.matcher(moduleURI);
            if (!matcher.matches()) {
                throw new RuntimeException("Expected URI patters is " + pattern.pattern() + " but URI is " + moduleURI);
            }
            ;
            final String group = matcher.group(1);
            int numberOfLines = Integer.parseInt(group);
            StringBuffer sb = new StringBuffer();
            for (int i = 1; i <= numberOfLines; i++) {
                sb.append("Line of code ").append(i).append('\n');
            }
            return new StreamSource[]{new StreamSource(new StringReader(sb.toString()))};
        }
    };


    @Test
    public void shouldPrintEventToStream() {
        CoverageEvent event = new CoverageEvent("Module1.xq", "my:function", 998877);

        CoverageCollector collector = new CoverageCollector();
        collector.register(event);
        final ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(arrayOutputStream);
        collector.printAll(out);
        out.flush();


        String output = new String(arrayOutputStream.toByteArray());

        assertThat(output, allOf(
                containsString("Module1.xq"),
                containsString("my:function"),
                containsString("998877"))
        );

    }

    @Test
    public void shouldFindAllInvolvedModules() {
        CoverageCollector collector = new CoverageCollector();
        collector.register(new CoverageEvent("Module1_20.xq", "my:function1", 11));
        collector.register(new CoverageEvent("Module2_10.xq", "my:function2", 2));
        collector.register(new CoverageEvent("Module3_15.xq", "my:function3", 13));
        collector.register(new CoverageEvent("Module3_15.xq", "my:function4", 14));
        collector.register(new CoverageEvent("Module1_20.xq", "my:function5", 12));


        Set<String> involvedModules = collector.involvedModules();

        assertThat(involvedModules, is((Set) of("Module1_20.xq", "Module2_10.xq", "Module3_15.xq")));
    }

    @Test
    public void shouldFindProperNumberOfLines() {
        CoverageCollector collector = new CoverageCollector();
        CoverageReport report = collector.generateCoverageReport("Module_20.xq", moduleURIResolver);
        assertThat(report.numberOfLines(), is(20));
    }

    @Test
    public void shouldFindCoveredLinesForModuleCoverage() {
        CoverageCollector collector = new CoverageCollector();

        collector.register(new CoverageEvent("Module1_20.xq", "my:function1", 11));
        collector.register(new CoverageEvent("Module1_20.xq", "my:function1", 12));
        collector.register(new CoverageEvent("Module1_20.xq", "my:function1", 13));

        collector.register(new CoverageEvent("Module1_20.xq", "my:function2", 3));
        collector.register(new CoverageEvent("Module1_20.xq", "my:function2", 4));

        CoverageReport report = collector.generateCoverageReport("Module1_20.xq", moduleURIResolver);

        Set<Integer> covered = of(11, 12, 13, 3, 4);
        Set<Integer> uncovered = of(1, 2, 5, 6, 7, 8, 9, 10, 14, 15, 16, 17, 18, 19, 20);

        assertThat(report.coveredLines(), is(covered));
        assertThat(report.unusedLines(), is(uncovered));
    }
}
