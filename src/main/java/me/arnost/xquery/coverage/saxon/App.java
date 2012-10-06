package me.arnost.xquery.coverage.saxon;

import net.sf.saxon.s9api.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SaxonApiException, IOException {
        final Processor processor = new Processor(true);
        final XQueryCompiler queryCompiler = processor.newXQueryCompiler();

        final InputStream query = App.class.getResourceAsStream("/coverage/sample1.xqx");
        assert query != null;


        final XQueryExecutable executable = queryCompiler.compile(query);

        final XQueryEvaluator evaluator = executable.load();
        final Iterator<XdmItem> iterator = evaluator.iterator();
        while(iterator.hasNext()) {
            final XdmItem item = iterator.next();
            System.out.println(item);
        }
    }
}
