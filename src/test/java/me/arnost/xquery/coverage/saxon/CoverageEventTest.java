package me.arnost.xquery.coverage.saxon;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CoverageEventTest {

    @Test
    public void verifyConstructorParameterOrder() {
        CoverageEvent event = new CoverageEvent("SystemId", "DisplayName", 123);

        assertThat(event.getSystemId(), is("SystemId"));
        assertThat(event.getDisplayName(), is("DisplayName"));
        assertThat(event.getLineNumber(), is(123));
    }
}
