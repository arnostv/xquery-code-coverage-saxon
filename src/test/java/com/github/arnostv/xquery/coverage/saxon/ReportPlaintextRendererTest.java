package com.github.arnostv.xquery.coverage.saxon;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ReportPlaintextRendererTest {

    @Test
    public void shouldRenderReportAsPlaintext() {
        final ImmutableSet<Integer> coveredLines = ImmutableSet.of(2, 3, 5, 6);

        List<String> lines = new ArrayList<String>();
        StringBuilder expected = new StringBuilder();
        for (int i = 1; i <= 10; i++) {
            expected.append(String.format("%4d ", i));
            if (coveredLines.contains(i)) {
                expected.append("+ Module line " + i + "\n");

            }  else {
                expected.append("  Module line " + i + "\n");
            }
            lines.add("Module line " + i);
        }
        CoverageReport report = new CoverageReport("module1.xq", lines, coveredLines);


        ReportPlaintextRenderer renderer = new ReportPlaintextRenderer();
        String reportText = renderer.renderToPlaintext(report);

        assertThat(reportText, is(expected.toString()));

    }


}
