package networking;
import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import networking.Server;
import networking.JFilePicker;

public class client{

    public static void main(String[] args) throws Exception  {
        Socket soc;
        Socket soc1;
        
        try
        {soc1=new Socket("172.16.96.26",9082);
        
        TCPClient c=new TCPClient(soc1);
        	
        	}
        	catch (Exception e) {
        		System.out.println("fail");
				// TODO: handle exception
			}
        		
        try{
        	soc=new Socket("172.16.96.26",9087);
        	Scanner sc = new Scanner(System.in);
            BufferedReader nis = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            PrintWriter nos = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())),true);
             
             
             JFrame frame = new JFrame("Get Username");
             String uname = new String();
             uname = JOptionPane.showInputDialog(frame, "Enter your Username :");
             if (uname == null) {
                 uname = "Unknown";
             }
             JFrame f1 = new JFrame(uname);
             JButton b1 = new JButton("Send");
             JTextArea ta = new JTextArea();
             ta.setEditable(false);
             JTextField tf = new JTextField(20);
             JPanel p1 = new JPanel();
             p1.add(tf);
             p1.add(b1);
             f1.add(ta);
             f1.add(BorderLayout.SOUTH,p1);
            ChatListener l1 = new ChatListener(tf,nos,uname);
            b1.addActionListener(l1);
            tf.addActionListener(l1);
             f1.setSize(400,400);
             f1.setVisible(true);
             f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           String str = nis.readLine();
           while(!str.equals("End")){
               ta.append(str + "\n" );
                str = nis.readLine();
            }
             ta.append("Client Signing Off");
             Thread.sleep(1000);
             System.exit(0);
             
        	}
        catch (Exception e) {
			// TODO: handle exception
        	System.out.println("fail2");
		}
        }
        
       
    }
    

class ChatListener implements ActionListener{
   JTextField tf ;
   PrintWriter nos;
   String uname;
    public ChatListener(JTextField tf,PrintWriter nos,String uname){
        this.tf = tf;
        this.nos = nos;
        this.uname = uname;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String str  = tf.getText();
        nos.println(uname+" : "+str);
        tf.setText("");
    }    

}

class TCPClient {

	static String s=new File(".").getAbsolutePath();
	private final static String fileOutput = s+".."+"file.java";
	Socket soc;
	public TCPClient(Socket soc)
	{
		this.soc=soc;
        byte[] aByte = new byte[1];
        int bytesRead;

        Socket clientSocket = null;
        InputStream is = null;

        try {
            //clientSocket = new Socket( serverIP , serverPort );
            is = soc.getInputStream();
        } catch (IOException ex) {
          
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        if (is != null) {

            FileOutputStream fos = null;
            BufferedOutputStream bos = null;
            try {
                fos = new FileOutputStream(fileOutput);
                bos = new BufferedOutputStream(fos);
                bytesRead = is.read(aByte, 0, aByte.length);

                do {
                        baos.write(aByte);
                        bytesRead = is.read(aByte);
                } while (bytesRead != -1);

                bos.write(baos.toByteArray());
               bos.flush();
                bos.close();
                soc.close();
            } catch (IOException ex) {
            	System.out.println("fail3");
                // Do exception handling
            }
        }
    }
}