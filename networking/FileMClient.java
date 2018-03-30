package networking;

import java.io.*;
import java.io.ByteArrayOutputStream;
import java.net.*;

public class FileMClient {

    private final static String serverIP = "172.16.96.209";
    private final static int serverPort = 9081;
	static String s=new File(".").getAbsolutePath();
	private final static String fileOutput = s+".."+"DSC.txt";
	

    public static void main(String args[]) throws InterruptedException {
		
        byte[] aByte = new byte[1];
        int bytesRead;

        Socket clientSocket = null;
        InputStream is = null;

        try {
            clientSocket = new Socket( serverIP , serverPort );
            is = clientSocket.getInputStream();
        } catch (IOException ex) {
            // Do exception handling
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        if (is != null) {

            FileOutputStream fos = null;
            BufferedOutputStream bos = null;
            try {
                fos = new FileOutputStream( fileOutput );
                bos = new BufferedOutputStream(fos);
                bytesRead = is.read(aByte, 0, aByte.length);

                do {
                        baos.write(aByte);
                        bytesRead = is.read(aByte);
                } while (bytesRead != -1);

                bos.write(baos.toByteArray());
                bos.flush();
                bos.close();
                clientSocket.close();
            } catch (IOException ex) {
                // Do exception handling
            }
        }
        Thread.sleep(1000);
        System.exit(0);   
    }
}