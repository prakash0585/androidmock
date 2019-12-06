
package com.sabakuch.epaper.entitymime;

import java.nio.charset.Charset;

public final class MIME {

    public static final String CONTENT_TYPE          = "Content-Type";
    public static final String CONTENT_TRANSFER_ENC  = "Content-Transfer-Encoding";
    public static final String CONTENT_DISPOSITION   = "Content-Disposition";

    public static final String ENC_8BIT              = "8bit";
    public static final String ENC_BINARY            = "binary";

    /** The default character set to be used, i.e. "US-ASCII" */
    public static final Charset DEFAULT_CHARSET      = Charset.forName("US-ASCII");

}
