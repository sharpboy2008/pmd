/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.java.ast;

import net.sourceforge.pmd.lang.java.multifile.signature.JavaOperationSignature;
import net.sourceforge.pmd.lang.java.symbols.JExecutableSymbol;


abstract class AbstractMethodOrConstructorDeclaration<T extends JExecutableSymbol>
    extends AbstractJavaNode
    implements ASTMethodOrConstructorDeclaration,
               LeftRecursiveNode {

    private T symbol;
    private JavaOperationSignature signature;

    AbstractMethodOrConstructorDeclaration(int i) {
        super(i);
    }


    @Override
    public JavaOperationSignature getSignature() {
        if (signature == null) {
            signature = JavaOperationSignature.buildFor(this);
        }

        return signature;
    }


    void setSymbol(T symbol) {
        AbstractTypedSymbolDeclarator.assertSymbolNull(this.symbol, this);
        this.symbol = symbol;
    }

    @Override
    public T getSymbol() {
        AbstractTypedSymbolDeclarator.assertSymbolNotNull(symbol, this);
        return symbol;
    }
}
