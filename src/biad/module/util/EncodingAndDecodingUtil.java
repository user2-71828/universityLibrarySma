package biad.module.util;

import java.io.*;
import java.util.Base64;
/**
 * I had to encode and decode manually because i kept getting this error from jade platform
 * 	===== E R R O R !!! =======
 *
 * Missing support for Base64 conversions
 * Please refer to the documentation for details.
 * =============================================
 * */
public class EncodingAndDecodingUtil {

    public static byte [] encode(Object obj){
        byte[] bytes = objectToByteArray(obj);
        byte[] encodedBytes = Base64.getEncoder().encode(bytes);
        return encodedBytes;
    }
    public static Object decode (byte[] bytes){
        byte[] decodedBytes = Base64.getDecoder().decode(bytes);
        Object obj = byteArrayToObject(decodedBytes);
        return obj;
    }

    public static byte[] objectToByteArray(Object obj){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            out.flush();
            byte[] bytes = bos.toByteArray();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }
    }

    public static Object byteArrayToObject(byte[] bytes){
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            Object o = in.readObject();
            return o;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
    }
}
