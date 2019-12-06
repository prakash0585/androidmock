package com.sabakuch.epaper.entitymimecontent;

/**
 * Represents common content properties.
 */
public interface ContentDescriptor {

   
    String getMimeType();

   
    String getMediaType();

    String getSubType();

    String getCharset();

   
    String getTransferEncoding();

    
    long getContentLength();

}
