package tn.lipsic.babel;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * babel-specific buffer class.  ByteIterators are designed to support
 * efficient field generation, and to allow backend drivers that can stream
 * fields (instead of materializing them in RAM) to do so.
 * <p>
 * babel originially used String objects to represent field values.  This led to
 * two performance issues.
 * </p><p>
 * First, it leads to unnecessary conversions between UTF-16 and UTF-8, both
 * during field generation, and when passing data to byte-based backend
 * drivers.
 * </p><p>
 * Second, Java strings are represented internally using UTF-16, and are
 * built by appending to a growable array type (StringBuilder or
 * StringBuffer), then calling a toString() method.  This leads to a 4x memory
 * overhead as field values are being built, which prevented babel from
 * driving large object stores.
 * </p>
 * The StringByteIterator class contains a number of convenience methods for
 * backend drivers that convert between Map&lt;String,String&gt; and
 * Map&lt;String,ByteBuffer&gt;.
 */
public abstract class ByteIterator implements Iterator<Byte> {

    @Override
    public abstract boolean hasNext();

    @Override
    public Byte next() {
        throw new UnsupportedOperationException();
    }

    public abstract byte nextByte();

    /**
     * @return byte offset immediately after the last valid byte
     */
    public int nextBuf(byte[] buf, int bufOff) {
        int sz = bufOff;
        while (sz < buf.length && hasNext()) {
            buf[sz] = nextByte();
            sz++;
        }
        return sz;
    }

    public abstract long bytesLeft();

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    /**
     * Resets the iterator so that it can be consumed again. Not all
     * implementations support this call.
     *
     * @throws UnsupportedOperationException if the implementation hasn't implemented
     *                                       the method.
     */
    public void reset() {
        throw new UnsupportedOperationException();
    }

    /**
     * Consumes remaining contents of this object, and returns them as a string.
     */
    public String toString() {
        Charset cset = Charset.forName("UTF-8");
        CharBuffer cb = cset.decode(ByteBuffer.wrap(this.toArray()));
        return cb.toString();
    }

    /**
     * Consumes remaining contents of this object, and returns them as a byte array.
     */
    public byte[] toArray() {
        long left = bytesLeft();
        if (left != (int) left) {
            throw new ArrayIndexOutOfBoundsException("Too much data to fit in one array!");
        }
        byte[] ret = new byte[(int) left];
        int off = 0;
        while (off < ret.length) {
            off = nextBuf(ret, off);
        }
        return ret;
    }

}
