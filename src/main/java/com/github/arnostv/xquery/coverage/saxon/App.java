package com.github.arnostv.xquery.coverage.saxon;

import net.sf.saxon.Configuration;
import net.sf.saxon.lib.ModuleURIResolver;
import net.sf.saxon.query.StaticQueryContext;
import net.sf.saxon.s9api.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

/**
 * Hello world!
 */
public class App {
    private static final String URI_SCHEME = "my:";

    public static void main(String[] args) throws SaxonApiException, IOException, URISyntaxException {
        System.out.println("org.slf4j.simpleLogger.defaultLogLevel=" + System.getProperty("org.slf4j.simpleLogger.defaultLogLevel"));
        final String xQueryProgram = "/coverage/sample1.xq";


        Configuration config = Configuration.newConfiguration();
        //config.setTraceListener(new XQueryTraceListener());
        CoverageCollector collector = new CoverageCollector();
        config.setTraceListener(new CoverageTraceListener(collector));
        final Processor processor = new Processor(config);

        final XQueryCompiler queryCompiler = processor.newXQueryCompiler();
        queryCompiler.setBaseURI(new URI(URI_SCHEME + xQueryProgram));

        final InputStream query = App.class.getResourceAsStream(xQueryProgram);
        assert query != null;


        //final XQueryExecutable executable = queryCompiler.compile("/coverage/sample1.xq");
        final StaticQueryContext underlyingStaticContext = queryCompiler.getUnderlyingStaticContext();
        final ModuleURIResolver moduleURIResolver = new ClasspathModuleURIResolver(URI_SCHEME);
        underlyingStaticContext.setModuleURIResolver(moduleURIResolver);
        final XQueryExecutable executable = queryCompiler.compile(query);

        final XQueryEvaluator evaluator = executable.load();
        final Iterator<XdmItem> iterator = evaluator.iterator();
        while (iterator.hasNext()) {
            final XdmItem item = iterator.next();
            System.out.println(item);
        }

        collector.printAll(System.out);
    }
}
