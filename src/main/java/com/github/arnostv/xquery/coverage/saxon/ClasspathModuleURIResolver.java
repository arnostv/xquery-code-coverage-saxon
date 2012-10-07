package com.github.arnostv.xquery.coverage.saxon;

import net.sf.saxon.lib.ModuleURIResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.util.Arrays;

public class ClasspathModuleURIResolver implements ModuleURIResolver {

    private static final Logger LOG = LoggerFactory.getLogger(ClasspathModuleURIResolver.class);


    private final String uriScheme;

    public ClasspathModuleURIResolver(String uriScheme) {
        this.uriScheme = uriScheme;

    }

    @Override
    public StreamSource[] resolve(String s, String s1, String[] strings) {
        LOG.debug(s + " <- " + s1 + " : " + Arrays.asList(strings));
        if (s != null && s.startsWith(uriScheme)) {
            final InputStream query = App.class.getResourceAsStream(s.substring(3));
            final StreamSource source = new StreamSource(query, s);
            return new StreamSource[]{source};

        } else {
            return null;
        }
    }
}
