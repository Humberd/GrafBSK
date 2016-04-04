package bsk.file.extensions.BIN;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BinaryFileReader {

    public BinaryFileReader() {

    }

    public static String read(String path) {
        BufferedInputStream stream = null;
        try {
            stream = new BufferedInputStream(new FileInputStream(new File(path)));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BinaryFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        int Byte;
        int[] result = null;
        try {
            result = new int[stream.available() * 8];
        } catch (IOException ex) {
            Logger.getLogger(BinaryFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        int bytesCounter = 0;

        try {
            while ((Byte = stream.read()) != -1) {
                int tempValue = bytesCounter * 8;
                for (int i = 0; i < 8; i++) {
                    result[tempValue + (7 - i)] = (Byte >> i) & 1;
                }
                bytesCounter++;
            }
        } catch (IOException ex) {
            Logger.getLogger(BinaryFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            stream.close();
        } catch (IOException ex) {
            Logger.getLogger(BinaryFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        StringBuilder resultBuilder = new StringBuilder(result.length);
        for (int res : result) {
            resultBuilder.append(res);
        }
        return resultBuilder.toString();
    }

    public static void save(String path, String content) {
        if (content == null || content.equals("")) {
            return;
        }
        BufferedOutputStream stream = null;

        try {
            stream = new BufferedOutputStream(new FileOutputStream(new File(path)));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BinaryFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        int Byte = 0;
        for (int i = 0; i < content.length(); i++) {
            int Bit = 0;
            if (content.charAt(i) == '0') {
                Bit = 0;
            } else if (content.charAt(i) == '1') {
                Bit = 1;
            } else {
                continue;
            }
            Byte <<= 1;
            Byte += Bit;
            if (i % 8 == 7) {
                try {
                    stream.write(Byte);
                } catch (IOException ex) {
                    Logger.getLogger(BinaryFileReader.class.getName()).log(Level.SEVERE, null, ex);
                }
                Byte = 0;
            }
        }
        try {
            stream.close();
        } catch (IOException ex) {
            Logger.getLogger(BinaryFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean compareFiles(String[] paths) {
        File[] files = new File[paths.length];
        for (int i = 0; i < paths.length; i++) {
            files[i] = new File(paths[i]);
        }
        return compareFiles(files);
    }

    public static boolean compareFiles(File[] files) {
        if (files.length == 1) {
            return true;
        }
        long foo = files[0].length();
        for (File file : files) {
            if (file.length() != foo) {
                return false;
            }
        }
        BufferedInputStream[] streams = new BufferedInputStream[files.length];
        for (int i = 0; i < files.length; i++) {
            try {
                streams[i] = new BufferedInputStream(new FileInputStream(files[i]));
            } catch (FileNotFoundException ex) {
                return false;
            }
        }

        int value = 0;
        try {
            while ((value = streams[0].read()) != -1) {
                for (int i=1; i< streams.length; i++) {
                    if (streams[i].read() != value) {
                        return false;
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(BinaryFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (BufferedInputStream stream : streams) {
            try {
                stream.close();
            } catch (IOException ex) {
                return true;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        int test = 10;
        int[] foo = new int[8];
        for (int i = 0; i < 8; i++) {
            foo[7 - i] = (test >> i) & 1;
        }
        StringBuilder builder = new StringBuilder(foo.length);
        for (int i : foo) {
            builder.append(i);
        }

        System.out.println(builder);
    }
}
