package networking;

import java.io.*;
import java.net.*;
import java.util.*;

class Conversation1 extends Thread 
{

    Socket soc;
public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    public Conversation1(Socket soc) {
        this.soc = soc;
    }

    @Override
    public void run() {

     final String fileToSend = "C:\\Users\\DEVRISHEE UPADHYAY\\Desktop\\java.txt";

         System.out.println("Conversation thread "+Thread.currentThread().getName() +"signing On");
        while (true) {

            BufferedOutputStream outToClient = null;

            try {
                //welcomeSocket = new ServerSocket(3248);
                //connectionSocket = welcomeSocket.accept();
                outToClient = new BufferedOutputStream(soc.getOutputStream());
            } catch (IOException ex) {
                // Do exception handling
            }

            if (outToClient != null) {
                File myFile = new File( fileToSend );
                byte[] mybytearray = new byte[(int) myFile.length()];

                FileInputStream fis = null;

                try {
                    fis = new FileInputStream(myFile);
                } catch (FileNotFoundException ex) {
                    // Do exception handling
                }
                BufferedInputStream bis = new BufferedInputStream(fis);

                try {
                    bis.read(mybytearray, 0, mybytearray.length);
                    outToClient.write(mybytearray, 0, mybytearray.length);
                    outToClient.flush();
                    outToClient.close();
                    soc.close();

                    // File sent, exit the main method
                    return;
                } catch (IOException ex) {
                    // Do exception handling
                }
            }
        }
    }
}

public class FileMServer {
	public static ArrayList<PrintWriter> al = new ArrayList<>();
    public static void main(String[] args) throws Exception  {
        System.out.println("Server signing On");
        ServerSocket ss = new ServerSocket(9081);
        for (int i = 0; i < 10; i++) {
            Socket connectionSocket = ss.accept();
            Conversation1 c = new Conversation1(connectionSocket);
            c.start();
        }
        System.out.println("Server signing Off");
    }
        
}
