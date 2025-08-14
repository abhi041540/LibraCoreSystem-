package librarySystem;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

public class UserList extends JDialog implements Runnable
{
	String id,s1[][],s2[];Font fn;JButton b1,b2;
	Connection con;JFrame f; int count=0;JTable ta;
	
     public UserList(JFrame f)
     {
    	 super(f,"USERS LIST");
    	 s1= new String[36][3];
    	 s2= new String[3];
    	 s2[0]="USER-ID";
		 s2[1]="USER-NAME";
		 s2[2]="EMAIL-ID";
    	 this.f=f;
//    	 pdd=new JPanel();
    	 fn=new Font("Arial",Font.BOLD, 15);
    	 this.id=id;
    	 Thread th= new Thread(this);
    	 th.start();
    	 setIconImage(ImageBox.FRAME_LOGO);
    	 setSize(700,500);
    	 setResizable(false);
    	addWindowListener(new WindowAdapter() 
    	{
    		@Override
    		public void windowClosing(WindowEvent we)
    		{
    			dispose();
    		}
    		
    	});
    	  try
    	  {
			th.join();
		  }
    	  catch (InterruptedException e) 
    	  {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
    	  if(count==1)
   	      {
   		  JOptionPane.showMessageDialog(this,"NO DATA AVAILABLE!");
   		  dispose();
   	      }
    	  else
    	  {
    	  ta=new JTable(s1,s2);
    	  ta.setFont(fn);
    	  JScrollPane js= new JScrollPane(ta);
    	  js.setBackground(new Color(150,75,0));
    	  js.setForeground(new Color(150,75,0));
    	  add(js);
    	 setLocationRelativeTo(f);
    	 setModal(true);
    	 setVisible(true);
    	  }
   	  
    	
     }

	@Override
	public void run()
	{
		 Button bb1,bb2,bb3;
		JPanel pd;
		
	  try 
	  {
		con=DataBase.getConnection();
		PreparedStatement ps= con.prepareStatement("select * from userdata");
		ResultSet rs= ps.executeQuery();
		if(!rs.next())
		{
			count=1;
		}
		else
		{
			int i=0;
			do
			{
				
				 s1[i][0]=rs.getString("userid");
			     s1[i][1]=rs.getString("name");
			     s1[i][2]=rs.getString("emailid");
			      i++;			
			}
			while(rs.next());
			
		}
	  }
	    catch (Exception e) 
	  {
		e.printStackTrace();
	  }
		
	}

}
