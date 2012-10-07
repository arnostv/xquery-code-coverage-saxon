package com.github.arnostv.xquery.coverage.saxon;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CoverageSuiteReportWriterTest {

    @Test
    public void shouldGenerateExpectedReport() {
        DummyModuleURIResolver uriResolver = new DummyModuleURIResolver();

        CoverageCollector collector = new CoverageCollector();
        collector.register(new CoverageEvent("ModuleA_5.xq", "foo", 2));
        collector.register(new CoverageEvent("ModuleA_5.xq", "foo", 3));
        collector.register(new CoverageEvent("ModuleB_4.xq", "foo", 1));
        collector.register(new CoverageEvent("ModuleB_4.xq", "foo", 2));

        final ByteArrayOutputStream outArray = new ByteArrayOutputStream();

        new CoverageSuiteReportWriter(new PrintStream(outArray), collector, uriResolver).writeReport();

        final String expected =
                "*** ModuleB_4.xq ***\n" +
                "\n" +
                "   1 + Line of code 1\n" +
                "   2 + Line of code 2\n" +
                "   3   Line of code 3\n" +
                "   4   Line of code 4\n" +
                "\n" +
                "*** ModuleA_5.xq ***\n" +
                "\n" +
                "   1   Line of code 1\n" +
                "   2 + Line of code 2\n" +
                "   3 + Line of code 3\n" +
                "   4   Line of code 4\n" +
                "   5   Line of code 5\n" +
                "\n";

        final String generated = outArray.toString();

        assertThat(generated, is(expected));
    }
}
