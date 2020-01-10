/*
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.ast.impl.javacc;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import net.sourceforge.pmd.lang.ast.CharStream;
import net.sourceforge.pmd.lang.ast.impl.TokenDocument;

/**
 * Token document for Javacc implementations. This is a helper object
 * for generated token managers.
 */
public class JavaccTokenDocument extends TokenDocument<JavaccToken> {

    private JavaccToken first;

    public JavaccTokenDocument(String fullText) {
        super(fullText);
    }

    /**
     * Open the document. This is only meant to be used by
     * Javacc-generated file.
     *
     * @throws IllegalStateException If the document has already been opened
     */
    public JavaccToken open() {
        synchronized (this) {
            if (first != null) {
                throw new RuntimeException("Document is already opened");
            }
            first = JavaccToken.newImplicit(0, this);
        }
        return first;
    }

    /**
     * Returns the first token of the token chain.
     */
    // technically the first non-implicit, though this is stupid
    public JavaccToken getFirstToken() {
        if (first == null || first.next == null) {
            throw new IllegalStateException("Document has not been opened");
        }
        return first.next;
    }

    /**
     * Returns a string that describes the token kind.
     *
     * @param kind Kind of token
     *
     * @return A descriptive string
     */
    public final @NonNull String describeKind(int kind) {
        if (kind == JavaccToken.IMPLICIT_TOKEN) {
            return "implicit token";
        }
        String impl = describeKindImpl(kind);
        if (impl != null) {
            return impl;
        }
        return "token of kind " + kind;
    }


    /**
     * Describe the given kind. If this returns a non-null value, then
     * that's what {@link #describeKind(int)} will use. Otherwise a default
     * implementation is used.
     *
     * <p>An implementation typically uses the JavaCC-generated array
     * named {@code <parser name>Constants.tokenImage}. Remember to
     * check the bounds of the array.
     *
     * @param kind Kind of token
     *
     * @return A descriptive string, or null to use default
     */
    protected @Nullable String describeKindImpl(int kind) {
        return null;
    }

    /**
     * Creates a new token with the given kind. This is called back to
     * by JavaCC-generated token managers (jjFillToken).
     *
     * @param kind  Kind of the token
     * @param cs    Char stream of the file. This can be used to get text
     *              coordinates and the image
     * @param image Shared instance of the image token. If this is non-null,
     *              then no call to {@link CharStream#GetImage()} should be
     *              issued.
     *
     * @return A new token
     */
    public JavaccToken createToken(int kind, CharStream cs, @Nullable String image) {
        return new JavaccToken(
            kind,
            image == null ? cs.GetImage() : image,
            cs.getStartOffset(),
            cs.getEndOffset(),
            this
        );
    }
}
