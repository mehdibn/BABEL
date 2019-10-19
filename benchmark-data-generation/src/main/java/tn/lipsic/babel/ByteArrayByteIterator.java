package tn.lipsic.babel;

/**
 * A ByteIterator that iterates through a byte array.
 */
public class ByteArrayByteIterator extends ByteIterator {
    private final int originalOffset;
    private final int len;
    private byte[] str;
    private int off;

    public ByteArrayByteIterator(byte[] s) {
        this.str = s;
        this.off = 0;
        this.len = s.length;
        originalOffset = 0;
    }

    public ByteArrayByteIterator(byte[] s, int off, int len) {
        this.str = s;
        this.off = off;
        this.len = off + len;
        originalOffset = off;
    }

    @Override
    public boolean hasNext() {
        return off < len;
    }

    @Override
    public byte nextByte() {
        byte ret = str[off];
        off++;
        return ret;
    }

    @Override
    public long bytesLeft() {
        return len - off;
    }

    @Override
    public void reset() {
        off = originalOffset;
    }

}
