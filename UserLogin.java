package librarySystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class UserLogin extends JFrame implements ActionListener
{
	JPanel jp,dp;
	JLabel l1,l2;TextField t1;JPasswordField pf;
	JButton b; Font fn;
    public UserLogin(String str)
    {
    	super(str);
 	   setIconImage(ImageBox.FRAME_LOGO);
 	   setSize(Toolkit.getDefaultToolkit().getScreenSize());
 	  addWindowListener(new WindowAdapter()
   	 {
   		 @Override
   		 public void windowClosing(WindowEvent we)
   		 {
   			 dispose();
   			 new LibraryInterface("My Library");
   			 
   		 }
   	 });
 	   fn=new Font("Arial",Font.BOLD, 20);
 	   jp= new JPanel()
 		{
 		   @Override
 		   public void paintComponent(Graphics g)
 		   {
 			   g.drawImage(ImageBox.USER_LOGIN, 0, 0, getWidth(), getHeight(), this);
 		   }
 	    };
 	    setContentPane(jp);
 	    dp=new JPanel();
 	   dp.setOpaque(false);
  	   dp.setBackground(new Color(0,0,0,0));
  	   l1=new JLabel("       ENTER USERID:- ");
  	   l2= new JLabel("       ENTER PASSWORD:- ");
  	   t1=new TextField(30);
  	   pf= new JPasswordField(30);
  	   l1.setFont(fn);
  	  l2.setFont(fn); 
  	  t1.setFont(fn);  
  	  pf.setFont(fn);
  	  b=new JButton("Login");
  	  b.setBackground(Color.DARK_GRAY);
  	  b.setForeground(Color.white);
  	  b.setFont(fn);
  	  l1.setForeground(Color.white);
  	  l2.setForeground(Color.white);
//	  addMouseListener(new MouseAdapter()
//	  {
//        @Override
//       public void mouseClicked(MouseEvent me)
//        {
//	  	System.out.println(me.getX()+","+me.getY());
//        }
//	  });

       dp.setLayout(new GridLayout(2,2,10,30));
       dp.add(l1);
       dp.add(t1);
       dp.add(l2);
       dp.add(pf);
       dp.setBounds(20, 300,540,100);
        b.setBounds( 101, 423, 380, 28);
 	    setLayout(null);
 	    add(dp);add(b);
 	    b.addActionListener(this);
 	    setIconImage(ImageBox.FRAME_LOGO);
 	   setVisible(true);
    }
//    public static void main(String args[])
//    {
//    	new UserLogin("User Login");
//    }
	@Override
	public void actionPerformed(ActionEvent e)
	{
		 if(t1.getText()==null || t1.getText().isEmpty() || t1.getText().equalsIgnoreCase("") || pf.getText()==null || pf.getText().isEmpty() || pf.getText().equalsIgnoreCase(""))	
 	    {
 	    	JOptionPane.showMessageDialog(this, "PLEASE ENTER ALL REQURED DATA TO CONTINUE");
 	    	t1.setText(null);
 	    	pf.setText(null);
 	    }
		 else
		 {
			 String id,pass;
			 id=t1.getText();
			 pass=pf.getText();
			  try 
			  {
				Connection con= DataBase.getConnection();
				PreparedStatement ps= con.prepareStatement("select * from userdata where userid=?");
				ps.setString(1, id);
				ResultSet rs= ps.executeQuery();
				if(rs.next()==false)
				{
		 	    	JOptionPane.showMessageDialog(this, "USER NOT FOUND PLEASE CONTECT TO THE STAFF FOR REGISTER");
		 	    	 t1.setText(null);
			 	     pf.setText(null);
				}
				else
				{
					if(rs.getString("password").equalsIgnoreCase(pass))
					{
						 new UserPortal("USER SECTION",id);
						 t1.setText(null);
				 	     pf.setText(null);
				 	     dispose();
					}
					else
					{
			 	     JOptionPane.showMessageDialog(this, "INCORRECT PASSWORD PLEASE TRY AGAIN");
			 	       t1.setText(null);
			 	       pf.setText(null);
					}
				}
				con.close();
				rs.close();
				ps.close();
			  } 
			  catch (SQLException e1)
			  {
				e1.printStackTrace();
			  }
		 }
		
	}
}
