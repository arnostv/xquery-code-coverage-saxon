package com.github.arnostv.xquery.coverage.saxon;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.allOf;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.StringContains.containsString;

public class CoverageCollectorTest {

    @Test
    public void shouldPrintEventToStream() {
        CoverageEvent event = new CoverageEvent("Module1.xq","my:function",998877);

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
}
