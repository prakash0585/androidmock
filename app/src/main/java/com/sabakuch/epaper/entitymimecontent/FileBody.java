package com.sabakuch.epaper.entitymimecontent;


import com.sabakuch.epaper.entitymime.MIME;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileBody extends AbstractContentBody {

    private final File file;
    private final String filename;
    private final String charset;

    public FileBody(final File file,
                    final String filename,
                    final String mimeType,
                    final String charset) {
        super(mimeType);
        if (file == null) {
            throw new IllegalArgumentException("File may not be null");
        }
        this.file = file;
        if (filename != null)
            this.filename = filename;
        else
            this.filename = file.getName();
        this.charset = charset;
    }

    public FileBody(final File file,
                    final String mimeType,
                    final String charset) {
        this(file, null, mimeType, charset);
    }

    public FileBody(final File file, final String mimeType) {
        this(file, mimeType, null);
    }

    public FileBody(final File file) {
        this(file, "application/octet-stream");
    }

    public InputStream getInputStream() throws IOException

    {



        return new FileInputStream(this.file);


    }

    /**
     * @deprecated use {@link #writeTo(OutputStream)}
     */
    @Deprecated
    public void writeTo(final OutputStream out, int mode) throws IOException {
        writeTo(out);
    }

    public void writeTo(final OutputStream out) throws IOException {
        if (out == null) {
            throw new IllegalArgumentException("Output stream may not be null");
        }
        InputStream in = new FileInputStream(this.file);
        try {
            byte[] tmp = new byte[4096];
            int l;
            while ((l = in.read(tmp)) != -1) {
                out.write(tmp, 0, l);
            }
            out.flush();
        } finally {
            in.close();
        }
    }

    public String getTransferEncoding() {
        return MIME.ENC_BINARY;
    }

    public String getCharset() {
        return charset;
    }

    public long getContentLength() {
        return this.file.length();
    }

    public String getFilename() {
        return filename;
    }

    public File getFile() {
        return this.file;
    }

}
