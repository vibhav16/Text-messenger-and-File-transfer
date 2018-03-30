package networking;

import java.awt.FlowLayout;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import networking.JFilePicker;
import networking.FileTypeFilter;


public class Server extends JFrame {
	public static ServerSocket ss,ss1;
	public static Socket soc,soc1;
	public static String user1;
	
	public static String filepath;
	
	public Server() {
        super("Test using JFilePicker");
         
        setLayout(new FlowLayout());
 
        // set up a file picker component
        JFilePicker filePicker = new JFilePicker("Pick a file", "Browse...", "Send File");
        filePicker.setMode(JFilePicker.MODE_SAVE);
        filePicker.addFileTypeFilter(".jpg", "JPEG Images");
        filePicker.addFileTypeFilter(".mp4", "MPEG-4 Videos");
         
        // access JFileChooser class directly
        JFileChooser fileChooser = filePicker.getFileChooser();
        fileChooser.setCurrentDirectory(new File("D:/"));
         
        // add the component to the frame
        add(filePicker);
         
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 100);
        setLocationRelativeTo(null);    // center on screen
        filepath=filePicker.getSelectedFilePath();
        
		
    }
	
	
	public static ArrayList<PrintWriter> al = new ArrayList<>();
    public static void main(String[] args) throws Exception  {
    	
    	System.out.println("Type 'F' to transfer file and Type 'C' to chat with other clients...");
    	Scanner sc=new Scanner(System.in);
    	user1=sc.nextLine();
    	if(user1.equals("F"))
    	{
    	  SwingUtilities.invokeLater(new Runnable() {
              @Override
              public void run() 
              {
            	  System.out.println("Server signing On");           	    
            	 new Server().setVisible(true);
                 
                               
              }
          });
    	  
    	}
    	
    	else if(user1.equals("C")){
    	  	  
            System.out.println("Server signing On");
            ss = new ServerSocket(9087);
            
            for (int i = 0; i < 10; i++) 
            {
            	
                soc = ss.accept();              
                Conversation c = new Conversation(soc);
                c.start();
            }
            System.out.println("Server signing Off");
    	}
    	else{
    		System.out.println("Invalid choice try again");
    	}
  
    }
    
        
}

class Conversation extends Thread {

    Socket soc;
public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    public Conversation(Socket soc) {
        this.soc = soc;
    }

    @Override
    public void run() {
        System.out.println("Conversation thread "+Thread.currentThread().getName() +"signing On");
        try {
            BufferedReader nis = new BufferedReader(
                new InputStreamReader(
                    soc.getInputStream()
                )
            );
            PrintWriter nos = new PrintWriter(
                new BufferedWriter(
                    new OutputStreamWriter(
                        soc.getOutputStream()
                    )
                ), true
            );
            Server.al.add(nos);
            String str = nis.readLine();
            while(!str.equals("End")){
                System.out.println("Server Recieved  "+str);
                for(PrintWriter o : Server.al){
                    o.println(str);
             }
                str = nis.readLine();
            }
            nos.println("End");            
        }
        catch(Exception e){
            System.out.println("Client Seems to have abruptly closed the connection");
        }
     System.out.println("Conversation thread  "+
                                     Thread.currentThread().getName() +
                                      "   signing Off");
    }
}

/*class TCPServer extends Thread
{
	private final static String fileToSend = "C:\\Users\\VIBHAV\\Desktop\\FileClient.java";
	Socket soc;
	public TCPServer(Socket soc)
	{
       this.soc=soc;
        while (true) {

            BufferedOutputStream outToClient = null;

            try 
			{
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
}*/