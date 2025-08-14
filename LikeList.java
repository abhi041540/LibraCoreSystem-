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
import javax.swing.SwingWorker;

public class LikeList extends JDialog implements Runnable,ActionListener
{
	String id;Font fn;JPanel pdd;JButton b1,b2;
	Connection con;JFrame f; int count=0;ImageIcon im1=null,im2=null;
     public LikeList (String id,JFrame f)
     {
    	 super(f,"FAVORITE");
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
   		  JOptionPane.showMessageDialog(this,"NO FAVORITE BOOK'S AVAILABLE!");
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
    	 
//   	     SwingWorker<String , String> w=new SwingWorker<>() 
//   	     {
//   	    	@Override
//   	    	public String doInBackground()
//   	    	{
//   	    		return("yes");
//   	    	}
//		 };
//    	w.execute();
     }

	@Override
	public void run()
	{
		 Button bb1;
		JPanel pd;
		
	  try 
	  {
		con=DataBase.getConnection();
		PreparedStatement ps= con.prepareStatement("select * from inusebooks where userid=? and usetype=?");
		ps.setString(1,id);
		ps.setInt(2,2);
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
				pd= new JPanel();
				
				b2= new JButton(rs.getString("bookid").substring(2),im2);
				b2.setPreferredSize(new Dimension(64,64));
				b2.setBackground(Color.white);
				b2.setForeground(new Color(0,0,0,0));
				b2.setHorizontalTextPosition(SwingConstants.CENTER);
				b1= new JButton(rs.getString("bookid").substring(2),im1);
				b1.setPreferredSize(new Dimension(60,40));
				b1.setBackground(Color.white);
				b1.setForeground(new Color(0,0,0,0));
				b1.setHorizontalTextPosition(SwingConstants.CENTER);
				b1.addActionListener(this);
				b2.addActionListener(this);
				pd.add(bb1);pd.add(b1);pd.add(b2);
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
		else if(b.getIcon()==im2)
		{
			try
			{
			ps1 = con.prepareStatement("delete from inusebooks where userid= ? and bookid=? and usetype=?");
			
			ps1.setString(1, id);
			ps1.setString(2,"B-"+b.getText());
			ps1.setInt(3,2);
			ps1.executeUpdate();
			ps1.close();
			JOptionPane.showMessageDialog(this, "REMOVED SUCCESSFULLY!");
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

