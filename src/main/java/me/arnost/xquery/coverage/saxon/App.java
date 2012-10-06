package me.arnost.xquery.coverage.saxon;

import net.sf.saxon.Configuration;
import net.sf.saxon.lib.ModuleURIResolver;
import net.sf.saxon.query.StaticQueryContext;
import net.sf.saxon.s9api.*;
import net.sf.saxon.trans.XPathException;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SaxonApiException, IOException, URISyntaxException {
        final String xQueryProgram = "/coverage/sample1.xq";


        Configuration config  = Configuration.newConfiguration();
        //config.setTraceListener(new XQueryTraceListener());
        config.setTraceListener(new CoverageTraceListener());
        final Processor processor = new Processor(config);

        final XQueryCompiler queryCompiler = processor.newXQueryCompiler();
        queryCompiler.setBaseURI(new URI("my:"+ xQueryProgram));

        final InputStream query = App.class.getResourceAsStream(xQueryProgram);
        assert query != null;


        //final XQueryExecutable executable = queryCompiler.compile("/coverage/sample1.xq");
        final StaticQueryContext underlyingStaticContext = queryCompiler.getUnderlyingStaticContext();
        underlyingStaticContext.setModuleURIResolver(new ModuleURIResolver() {
            @Override
            public StreamSource[] resolve(String s, String s1, String[] strings) throws XPathException {
                System.out.println(s + " <- " + s1 + " : " + Arrays.asList(strings));
                if (s!=null && s.startsWith("my:")) {
                    final InputStream query = App.class.getResourceAsStream(s.substring(3));
                    final StreamSource source = new StreamSource(query, s);
                    return new StreamSource[]{source};

                } else {
                    return null;
                }
            }
        });
        final XQueryExecutable executable = queryCompiler.compile(query);

        final XQueryEvaluator evaluator = executable.load();
        final Iterator<XdmItem> iterator = evaluator.iterator();
        while(iterator.hasNext()) {
            final XdmItem item = iterator.next();
            System.out.println(item);
        }
    }
}
