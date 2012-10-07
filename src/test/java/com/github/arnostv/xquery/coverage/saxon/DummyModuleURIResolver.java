package com.github.arnostv.xquery.coverage.saxon;

import net.sf.saxon.lib.ModuleURIResolver;

import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DummyModuleURIResolver implements ModuleURIResolver {
    private static final Pattern pattern = Pattern.compile(".+_([0-9]+)\\.xq");

    @Override
    public StreamSource[] resolve(String moduleURI, String baseURI, String[] locations) {
        final Matcher matcher = pattern.matcher(moduleURI);
        if (!matcher.matches()) {
            throw new RuntimeException("Expected URI patters is " + pattern.pattern() + " but URI is " + moduleURI);
        }

        final String group = matcher.group(1);
        int numberOfLines = Integer.parseInt(group);
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= numberOfLines; i++) {
            sb.append("Line of code ").append(i).append('\n');
        }
        return new StreamSource[]{new StreamSource(new StringReader(sb.toString()))};
    }
}