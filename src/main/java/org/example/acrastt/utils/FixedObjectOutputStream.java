package org.example.acrastt.utils;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.*;

/**
 * A fixed object writer than does not throw {@link java.io.StreamCorruptedException}
 * when writing multiple Objects.
 *
 * @author Bohan Du
 * @version 1.0
 * @since 1.0
 * @see java.io.ObjectOutputStream
 * @see java.io.StreamCorruptedException
 */
public class FixedObjectOutputStream extends ObjectOutputStream {

    private static final Logger log = LoggerFactory.getLogger(FixedObjectOutputStream.class);


    /**
     * Creates an ObjectOutPutStream that doesn't add headers.
     *
     * @param out the OutputStream to write to
     * @throws IOException the exception that can be thrown when creating the stream
     */
    public FixedObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    /**
     * Write Object in a fixed format. If the file is empty, header will be written.
     * Otherwise, no header will be written.
     *
     * @param o    the object to write
     * @param file the name of the file to write to
     */
    public static void fixedWriteObject(Object o, String file) {
        try (ObjectOutputStream stream = getFixedObjectOutputStream(file)) {
            stream.writeObject(o);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Get a fixed object output stream. If the file is empty,
     * it will create an ObjectOutputStream that writes headers.
     * Otherwise, it will create an ObjectOutputStream that doesn't write headers.
     * Only writing one object per ObjectOutputStream will be recommended.
     *
     * @param file the name of the file to write to
     * @return the fixed object output stream
     */
    public static ObjectOutputStream getFixedObjectOutputStream(String file) {
        ObjectOutputStream output = null;
        try (FileOutputStream surveys = new FileOutputStream(file, true); InputStream input = new FileInputStream(file)) {
            if (input.available() == 0) {
                output = new ObjectOutputStream(surveys);
            } else {
                output = new FixedObjectOutputStream(surveys);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return output;
    }

    @Override
    protected void writeStreamHeader() {
        //Do nothing because it will cause exception for more than 1 Objects
    }
}
