package librarySystem;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class BookList extends JDialog implements Runnable
{
	String id;Font fn;
	Connection con;JFrame f; int count=0;
	JTable ta; String s1[][],s2[];
     public BookList (JFrame f)
     {
    	 super(f,"BOOKS-LIST");
    
    	 this.f=f;
    	 s1= new String[36][4];
    	 s2= new String[4];
    	 
    		 s2[0]="BOOK-ID";
    		 s2[1]="BOOK-NAME";
    		 s2[2]="AUTHOR-NAME";
    		 s2[3]="BOOK-COUNT";
    	 
    	
    	 fn=new Font("Arial",Font.BOLD, 15);
    	 this.id=id;
    	 Thread th= new Thread(this);
    	 th.start();
    	 setIconImage(ImageBox.FRAME_LOGO);
    	 setSize(Toolkit.getDefaultToolkit().getScreenSize());
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
//    		  ta.setPreferredSize(new Dimension(1100,700));
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
		 Button bb1,bb2,bb3,bb5;
		JPanel pd;
		
	  try 
	  {
		con=DataBase.getConnection();
		PreparedStatement ps= con.prepareStatement("select * from booklist");
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
				s1[i][0]=rs.getString("bookid");
				s1[i][1]=rs.getString("bookname");
				s1[i][2]=rs.getString("aurthorname");
				s1[i][3]=String.valueOf(rs.getInt("bookcount"));
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
