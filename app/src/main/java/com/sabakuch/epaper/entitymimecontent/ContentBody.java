package com.sabakuch.epaper.entitymimecontent;

import java.io.IOException;
import java.io.OutputStream;

public interface ContentBody extends ContentDescriptor {

    String getFilename();

    void writeTo(OutputStream out) throws IOException;

}
