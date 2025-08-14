package librarySystem;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class DueSoon extends JDialog implements Runnable,ActionListener
{
	String id;Font fn;JPanel pdd;JButton b1,b2;
	Connection con;JFrame f; int count=0;ImageIcon im1,im2;
     public DueSoon(String id,JFrame f)
     {
    	 super(f,"Due Soon");
    	 im2=new ImageIcon(ImageBox.RETURN_BOOK.getScaledInstance(60, 60,Image.SCALE_SMOOTH));
		 im1=new ImageIcon(ImageBox.SEARCHLOGO.getScaledInstance(60, 40,Image.SCALE_SMOOTH));
    	 this.f=f;
    	 pdd=new JPanel();
    	 fn=new Font("Arial",Font.BOLD, 20);
    	 this.id=id;
    	 Thread th= new Thread(this);
    	 th.start();
    	 setIconImage(ImageBox.FRAME_LOGO);
    	 setSize(900,700);
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
   		  JOptionPane.showMessageDialog(this,"NO BOOKS AVAILABLE WITH RETURN DATE WITH IN 2 DAYS");
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
		 Button bb1,bb2,bb3;
		JPanel pd;
		
	  try 
	  {
		con=DataBase.getConnection();
		PreparedStatement ps= con.prepareStatement("select * from inusebooks where userid=? and usetype=? and borrowcomp between curdate() and date_add(curdate(),interval 2 day)");
		ps.setString(1,id);
		ps.setInt(2,1);
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
				b1= new JButton(rs.getString("bookid").substring(2),im1);
				b1.setPreferredSize(new Dimension(60,50));
				b1.setBackground(Color.white);
				b1.setForeground(new Color(0,0,0,0));
				b1.setHorizontalTextPosition(SwingConstants.CENTER);
				b2= new JButton(rs.getString("bookid").substring(2),im2);
				b2.setPreferredSize(new Dimension(64,64));
				b2.setBackground(Color.white);
				b2.setForeground(new Color(0,0,0,0));
				b2.setHorizontalTextPosition(SwingConstants.CENTER);
				b1.addActionListener(this);
				b2.addActionListener(this);
				pd= new JPanel();
				pd.add(bb1);pd.add(bb2);pd.add(bb3);pd.add(b1);pd.add(b2);
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

	@Override
	public void actionPerformed(ActionEvent e)
	{
		JButton b=(JButton)e.getSource();
		PreparedStatement ps1;
		if(b.getIcon()==im1)
		{
			
			try
			{
			ps1 = con.prepareStatement("select bookimage from booklist where bookid=?");
			ps1.setString(1,"B-"+b.getText());
			ResultSet rs1=ps1.executeQuery();
			rs1.next();
//			String book,String id,JFrame f,ImageIcon bi
			URL ibp=new URL(rs1.getString("bookimage"));
			new SelectionDialog(b.getText(),id, f,new ImageIcon(ibp));
			} 
			catch (Exception e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else
		{
			try
			{
			ps1 = con.prepareStatement("update inusebooks set usetype=?,borrowcomp=curdate() where userid= ? and bookid=? and usetype <> ?");
			ps1.setInt(1, 0);
			ps1.setString(2, id);
			ps1.setString(3,"B-"+b.getText());
			ps1.setInt(4,2);
			ps1.executeUpdate();
			ps1.close();
			ps1 = con.prepareStatement("select bookcount from booklist where bookid=?");
			ps1.setString(1, "B-"+b.getText());
			ResultSet rs=ps1.executeQuery();
			rs.next();
			int cou=rs.getInt("bookcount");
			ps1.close();
			ps1 = con.prepareStatement("update booklist set bookcount=? where bookid=?");
			ps1.setInt(1, cou+1);
			ps1.setString(2, "B-"+b.getText());
			ps1.executeUpdate();
			ps1.close();
			JOptionPane.showMessageDialog(this, "RETURNED SUCCESSFULLY");
			revalidate();
			} 
			catch (Exception e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
}
