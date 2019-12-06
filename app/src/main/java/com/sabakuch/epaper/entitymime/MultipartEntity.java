
package com.sabakuch.epaper.entitymime;


import com.sabakuch.epaper.entitymimecontent.ContentBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Random;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

public class MultipartEntity implements HttpEntity {

    private final static char[] MULTIPART_CHARS =
        "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
            .toCharArray();

    private final HttpMultipart multipart;
    private final Header contentType;

    // @GuardedBy("dirty") // we always read dirty before accessing length
    private long length;
    private volatile boolean dirty; // used to decide whether to recalculate length

    public MultipartEntity(
            HttpMultipartMode mode,
            String boundary,
            Charset charset) {
        super();
        if (boundary == null) {
            boundary = generateBoundary();
        }
        if (mode == null) {
            mode = HttpMultipartMode.STRICT;
        }
        this.multipart = new HttpMultipart("form-data", charset, boundary, mode);
        this.contentType = new BasicHeader(
                HTTP.CONTENT_TYPE,
                generateContentType(boundary, charset));
        this.dirty = true;
    }

    public MultipartEntity(final HttpMultipartMode mode) {
        this(mode, null, null);
    }

    public MultipartEntity() {
        this(HttpMultipartMode.STRICT, null, null);
    }

    protected String generateContentType(
            final String boundary,
            final Charset charset) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("multipart/form-data; boundary=");
        buffer.append(boundary);
        if (charset != null) {
            buffer.append("; charset=");
            buffer.append(charset.name());
        }
        return buffer.toString();
    }

    protected String generateBoundary() {
        StringBuilder buffer = new StringBuilder();
        Random rand = new Random();
        int count = rand.nextInt(11) + 30; // a random size from 30 to 40
        for (int i = 0; i < count; i++) {
            buffer.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
        }
        return buffer.toString();
    }

    public void addPart(final FormBodyPart bodyPart) {
        this.multipart.addBodyPart(bodyPart);
        this.dirty = true;
    }

    public void addPart(final String name, final ContentBody contentBody) {
        addPart(new FormBodyPart(name, contentBody));
    }

    public boolean isRepeatable() {
        for (FormBodyPart part: this.multipart.getBodyParts()) {
            ContentBody body = part.getBody();
            if (body.getContentLength() < 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isChunked() {
        return !isRepeatable();
    }

    public boolean isStreaming() {
        return !isRepeatable();
    }

    public long getContentLength() {
        if (this.dirty) {
            this.length = this.multipart.getTotalLength();
            this.dirty = false;
        }
        return this.length;
    }

    public Header getContentType() {
        return this.contentType;
    }

    public Header getContentEncoding() {
        return null;
    }

    public void consumeContent()
        throws IOException, UnsupportedOperationException{
        if (isStreaming()) {
            throw new UnsupportedOperationException(
                    "Streaming entity does not implement #consumeContent()");
        }
    }

    public InputStream getContent() throws IOException, UnsupportedOperationException {
        throw new UnsupportedOperationException(
                    "Multipart form entity does not implement #getContent()");
    }

    public void writeTo(final OutputStream outstream) throws IOException {
        this.multipart.writeTo(outstream);
    }

}
