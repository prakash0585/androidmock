package com.sabakuch.epaper.entitymimecontent;


import com.sabakuch.epaper.entitymime.MIME;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;


public class StringBody extends AbstractContentBody {

    private final byte[] content;
    private final Charset charset;

    public static StringBody create(
            final String text,
            final String mimeType,
            final Charset charset) throws IllegalArgumentException {
        try {
            return new StringBody(text, mimeType, charset);
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalArgumentException("Charset " + charset + " is not supported", ex);
        }
    }

   
    public static StringBody create(final String text, final Charset charset) throws IllegalArgumentException {
        return create(text, null, charset);
    }

    public static StringBody create(final String text) throws IllegalArgumentException {
        return create(text, null, null);
    }

    public StringBody(
            final String text,
            final String mimeType,
            Charset charset) throws UnsupportedEncodingException {
        super(mimeType);
        if (text == null) {
            throw new IllegalArgumentException("Text may not be null");
        }
        if (charset == null) {
            charset = Charset.forName("US-ASCII");
        }
        this.content = text.getBytes(charset.name());
        this.charset = charset;
    }

    
    public StringBody(final String text, final Charset charset) throws UnsupportedEncodingException {
        this(text, "text/plain", charset);
    }

    public StringBody(final String text) throws UnsupportedEncodingException {
        this(text, "text/plain", null);
    }

    public Reader getReader() {
        return new InputStreamReader(
                new ByteArrayInputStream(this.content),
                this.charset);
    }

    @Deprecated
    public void writeTo(final OutputStream out, int mode) throws IOException {
        writeTo(out);
    }

    public void writeTo(final OutputStream out) throws IOException {
        if (out == null) {
            throw new IllegalArgumentException("Output stream may not be null");
        }
        InputStream in = new ByteArrayInputStream(this.content);
        byte[] tmp = new byte[4096];
        int l;
        while ((l = in.read(tmp)) != -1) {
            out.write(tmp, 0, l);
        }
        out.flush();
    }

    @Override
	public String getTransferEncoding() {
        return MIME.ENC_8BIT;
    }

    @Override
	public String getCharset() {
        return this.charset.name();
    }

    @Override
	public long getContentLength() {
        return this.content.length;
    }

    @Override
	public String getFilename() {
        return null;
    }

}
