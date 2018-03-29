package networking;
 
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import networking.TCPServer;
import networking.Server;
 
public class JFilePicker extends JPanel {
	public static String test;
	
    private String textFieldLabel;
    private String buttonLabel,sendlabel;
    
     
    private JLabel label;
    private JTextField textField;
    private static JButton button;

	static JButton send;
     
    private JFileChooser fileChooser;
     
    private int mode;
    public static final int MODE_OPEN = 1;
    public static final int MODE_SAVE = 2;
    
     
 

	public JFilePicker() {
		super();
		this.textFieldLabel = textFieldLabel;
		this.buttonLabel = buttonLabel;
		this.sendlabel=sendlabel;
		this.label = label;
		this.textField = textField;
		this.button = button;
		this.send=send;
		this.fileChooser = fileChooser;
		this.mode = mode;
	}

	public JFilePicker(String textFieldLabel, String buttonLabel,String sendlabel) {
        this.textFieldLabel = textFieldLabel;
        this.buttonLabel = buttonLabel;
        this.sendlabel=sendlabel;
         
        fileChooser = new JFileChooser();
         
        setLayout(new FlowLayout(FlowLayout.CENTER, 4, 4));

        label = new JLabel(textFieldLabel);
         
        textField = new JTextField(20);
        button = new JButton(buttonLabel);
        send=new JButton(sendlabel);
        
         
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                buttonActionPerformed(evt);            
            }
        });
        send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 try {
					 System.out.println("Sending File");
					 
						System.out.println(getSelectedFilePath());
						Server.ss1 = new ServerSocket(9082);
						Server.soc1=Server.ss1.accept();
						TCPServer server=new TCPServer(Server.soc1);
						System.out.println("File Sent");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
			}
		});
         
        add(label);
        add(textField);
        add(button);
        add(send);
         
    }
     
    private void buttonActionPerformed(ActionEvent evt) {
        if (mode == MODE_OPEN) {
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        } else if (mode == MODE_SAVE) {
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        }
    }
 
    public void addFileTypeFilter(String extension, String description) {
        FileTypeFilter filter = new FileTypeFilter(extension, description);
        fileChooser.addChoosableFileFilter(filter);
    }
     
    public void setMode(int mode) {
        this.mode = mode;
    }
     
    public String getSelectedFilePath() {
        return textField.getText();
    }
     
    public JFileChooser getFileChooser() {
        return this.fileChooser;
    }
}