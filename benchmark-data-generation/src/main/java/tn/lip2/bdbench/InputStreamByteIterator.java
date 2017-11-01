package tn.lip2.bdbench;

import java.io.IOException;
import java.io.InputStream;

/**
 *  A ByteIterator that iterates through an inputstream of bytes.
 */
public class InputStreamByteIterator extends ByteIterator {
  private long len;
  private InputStream ins;
  private long off;
  private final boolean resetable;

  public InputStreamByteIterator(InputStream ins, long len) {
    this.len = len;
    this.ins = ins;
    off = 0;
    resetable = ins.markSupported();
    if (resetable) {
      ins.mark((int) len);
    }
  }

  @Override
  public boolean hasNext() {
    return off < len;
  }

  @Override
  public byte nextByte() {
    int ret;
    try {
      ret = ins.read();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
    if (ret == -1) {
      throw new IllegalStateException("Past EOF!");
    }
    off++;
    return (byte) ret;
  }

  @Override
  public long bytesLeft() {
    return len - off;
  }

  @Override
  public void reset() {
    if (resetable) {
      try {
        ins.reset();
        ins.mark((int) len);
      } catch (IOException e) {
        throw new IllegalStateException("Failed to reset the input stream", e);
      }
    }
    throw new UnsupportedOperationException();
  }
  
}
