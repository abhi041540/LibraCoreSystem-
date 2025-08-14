package librarySystem;

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
import javax.swing.SwingConstants;

public class ActivityStaffSec extends JDialog implements Runnable
{
	String id;Font fn;JPanel pdd;JButton b1,b2;
	Connection con;JFrame f; int count=0;ImageIcon im1;
	 public ActivityStaffSec (String id,JFrame f)
     {
    	 super(f,id+" User Data");
    	 this.f=f;
    	 pdd=new JPanel();
    	 fn=new Font("Arial",Font.BOLD, 20);
    	 this.id=id;
//		 im1=new ImageIcon(ImageBox.SEARCHLOGO.getScaledInstance(60, 40,Image.SCALE_SMOOTH));
    	 Thread th= new Thread(this);
    	 th.start();
    	 setIconImage(ImageBox.FRAME_LOGO);
    	 setSize(800,700);
    	 setResizable(false);
    	addWindowListener(new WindowAdapter() 
    	{
    		@Override
    		public void windowClosing(WindowEvent we)
    		{
    			dispose();
    		}
    		
    	});
    	  pdd.setBackground(new Color(150,75,0));
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
   		  JOptionPane.showMessageDialog(this,"NO BOOK TRANSACTION DATA AVAILABLE!");
   		  dispose();
   	      }
    	  else
    	  {
    		 
    	  pdd.setLayout(new BoxLayout(pdd, BoxLayout.Y_AXIS));
    	  JScrollPane js= new JScrollPane(pdd);
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
		 Button bb1,bb2,bb3,bb4,bb5;
		JPanel pd;
		
	  try 
	  {
		con=DataBase.getConnection();
		PreparedStatement ps= con.prepareStatement("select * from inusebooks where userid=? and (usetype=? or usetype=?)");
		ps.setString(1,id);
		ps.setInt(2,0);
		ps.setInt(3,1);
		ResultSet rs= ps.executeQuery();
		if(!rs.next())
		{
			count=1;
		}
		else
		{
			do
			{
				bb1=new Button("BOOK-ID: "+rs.getString("bookid"));
				bb1.setBackground(Color.white);
				bb1.setFont(fn);
				bb2=new Button("BORROW-DATE: "+rs.getString("borrowdate"));
				bb2.setBackground(Color.white);
				bb2.setFont(fn);
				bb3=new Button("RETURN-DATE: "+rs.getString("borrowcomp"));
				bb3.setBackground(Color.white);
				bb3.setFont(fn);
				bb5=new Button("USER-ID: "+rs.getString("userid"));
				bb5.setBackground(Color.white);
				bb5.setFont(fn);
				pd= new JPanel();
				
				if(rs.getInt("usetype")==1)
				{
					bb4=new Button("STATUS: ACTIVE");
					bb4.setBackground(Color.white);
					bb4.setForeground(Color.green);
					bb4.setFont(fn);
					pd.add(bb5);pd.add(bb1);pd.add(bb4);
				}
				else if(rs.getInt("usetype")==0)
				{
					bb4=new Button("STATUS: RETURNED");
					bb4.setBackground(Color.white);
					bb4.setForeground(Color.red);
					bb4.setFont(fn);
					
					pd.add(bb5);pd.add(bb1);pd.add(bb4);
					
				}
				
				pd.add(bb2);pd.add(bb3);
				pd.setBackground(new Color(150,75,0));
				
				pdd.add(pd);
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
