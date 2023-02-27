package utils;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.*;

public class FixedObjectOutputStream extends ObjectOutputStream {

    private static final Logger log = LoggerFactory.getLogger(FixedObjectOutputStream.class);


    public FixedObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    public static void fixedWriteObject(Object o, String file) {
        try (ObjectOutputStream stream = getFixedObjectOutputStream(file)) {
            stream.writeObject(o);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

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
