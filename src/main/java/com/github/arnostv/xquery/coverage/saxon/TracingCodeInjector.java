package com.github.arnostv.xquery.coverage.saxon;

import net.sf.saxon.expr.Container;
import net.sf.saxon.expr.Expression;
import net.sf.saxon.expr.Literal;
import net.sf.saxon.expr.StaticContext;
import net.sf.saxon.expr.flwor.Clause;
import net.sf.saxon.expr.flwor.TraceClause;
import net.sf.saxon.expr.instruct.TraceExpression;
import net.sf.saxon.expr.parser.CodeInjector;
import net.sf.saxon.om.StructuredQName;

public class TracingCodeInjector implements CodeInjector {

    final boolean traceLiteral;

    public TracingCodeInjector() {
        this(true);
    }

    public TracingCodeInjector(boolean traceLiteral) {
        this.traceLiteral = traceLiteral;
    }

    /**
     * If tracing, wrap an expression in a trace instruction
     *
     * @param exp         the expression to be wrapped
     * @param env         the static context
     * @param construct   integer constant identifying the kind of construct
     * @param qName       the name of the construct (if applicable)
     * @return the expression that does the tracing
     */

    public Expression inject(Expression exp, /*@NotNull*/ StaticContext env, int construct, StructuredQName qName) {
        if (exp instanceof Literal && !traceLiteral) {
            return exp;
        }
        TraceExpression trace = new TraceExpression(exp);
        trace.setNamespaceResolver(env.getNamespaceResolver());
        trace.setConstructType(construct);
        trace.setObjectName(qName);
        return trace;
    }


    /**
     * If tracing, add a clause to a FLWOR expression that can be used to monitor requests for
     * tuples to be processed
     *
     * @param target the clause whose evaluation is to be traced (or otherwise monitored)
     * @param env
     *@param container the container of the containing FLWORExpression  @return the new clause to do the tracing; or null if no tracing is required at this point
     */

    public Clause injectClause(Clause target, StaticContext env, Container container) {
        return new TraceClause(target, env.getNamespaceResolver(), container);
    }

}

//
// The contents of this file are subject to the Mozilla Public License Version 1.0 (the "License");
// you may not use this file except in compliance with the License. You may obtain a copy of the
// License at http://www.mozilla.org/MPL/
//
// Software distributed under the License is distributed on an "AS IS" basis,
// WITHOUT WARRANTY OF ANY KIND, either express or implied.
// See the License for the specific language governing rights and limitations under the License.
//
// The Original Code is: all this file
//
// The Initial Developer of the Original Code is Saxonica Limited.
// Portions created by ___ are Copyright (C) ___. All rights reserved.
//
// Contributor(s):
//
