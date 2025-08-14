package librarySystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;

public class StaffSection extends JFrame implements ActionListener
{
	JPanel p1,p2,data;JLabel l1,l2,l3,l4;
   String staffname;
	TextField t1,t2,t3; JPasswordField pf;
	JButton b1,b2; Font fn;JDialog dj; JFrame f;
   public StaffSection(String str,String stf)
   {
	   super(str);
	   staffname=stf;
	   setIconImage(ImageBox.FRAME_LOGO);
	   f=this;
	   fn=new Font("Arial",Font.BOLD, 20);
	   addWindowListener(new WindowAdapter()
  	 {
  		 @Override
  		 public void windowClosing(WindowEvent we)
  		 {
  			 dispose();
  			 new LibraryInterface("My Library");
  			 
  		 }
  	 });
	   setSize(Toolkit.getDefaultToolkit().getScreenSize());
	   p1= new JPanel() 
	   {
		   @Override
		   public void paintComponent(Graphics g)
		   {
			   g.drawImage(ImageBox.STAFF_WINDOW, 0, 0,getWidth(), getHeight(), this);
		   }
	   };
	   setContentPane(p1);
	   Thread th= new Thread(new Runnable()
			   {
		         @Override
		         public void run()
		         {
		        			         }
			   });
	   
	       th.start();
		  setLayout(null);
		  data=new JPanel();
		  data.setOpaque(false);
		  data.setBackground(new Color(0,0,0,0));
		  l1= new JLabel("             Name: ");
		  l4= new JLabel("             Email Address:");
		  l2= new JLabel("             New LoginID: ");
		  l3= new JLabel("             New Password: ");
		  l1.setForeground(Color.white);
		  l4.setForeground(Color.white);

		  l2.setForeground(Color.white);
		  l3.setForeground(Color.white);
		  l1.setFont(fn);
		  l4.setFont(fn);
		  l2.setFont(fn);
		  l3.setFont(fn);
		  t1= new TextField(30);
		  t2= new TextField(30);
		  t3= new TextField(30);
          t3.setFont(fn);
		  pf=new JPasswordField(30);
		  b1= new JButton("SAVE");
		  b2= new JButton("GO TO DESHBORD");
		  t1.setFont(fn);
		  t2.setFont(fn);
		  pf.setFont(fn);
		  b1.setFont(fn);
		  b2.setFont(fn);
		  data.add(l1); data.add(t1); data.add(l4);data.add(t3);data.add(l2); data.add(t2); data.add(l3); data.add(pf);
		  
//		  addMouseListener(new MouseAdapter()
//				  {
//			  @Override
//			  public void mouseClicked(MouseEvent me)
//			  {
//				  	System.out.println(me.getX()+","+me.getY());
//			  }
//				 });
		  
		  
		  data.setLayout(new GridLayout(4,2,10,20));
		  data.setBounds(45, 254, 521, 170);
		  b1.setBounds(105, 444, 472, 30);
		  b1.setBackground(Color.white);
          b2.setBounds(105, 484, 472, 30);  
		  b2.setBackground(Color.white);
		  add(data);add(b1);add(b2);
		  b1.addActionListener(this);
		  b2.addActionListener(this);
		 
    	 
	   setVisible(true);
   }
//   public static void main(String str[])
//   {
//	   new StaffSection("Staff Section");
//   }
@Override
public void actionPerformed(ActionEvent e) 
{
       if(e.getSource()==b1)
       {
    	   if(t1.getText()==null || t1.getText().isEmpty() || t1.getText().equalsIgnoreCase("") || t3.getText()==null || t3.getText().isEmpty() || t3.getText().equalsIgnoreCase("")  || t2.getText()==null || t2.getText().isEmpty() || t2.getText().equalsIgnoreCase("") || pf.getText()==null || pf.getText().isEmpty() || pf.getText().equalsIgnoreCase(""))	
    	    {
    	    	JOptionPane.showMessageDialog(this, "PLEASE ENTER ALL REQURED DATA TO CONTINUE");
    	    	t1.setText(null);
    	    	t3.setText(null);
    	    	t2.setText(null);
    	    	pf.setText(null);
    	    }
    	    else
    	    {
    	    	String name=t1.getText();
    	    	String id=t2.getText();
    	    	String email=t3.getText();
    	    	String pass=pf.getText();
    	    	
    	    	try 
    	    	{
    				Connection con= DataBase.getConnection();
    				Statement s= con.createStatement();
    			 
    				ResultSet rs=s.executeQuery("select * from userdata where userid='"+id+"' or Emailid='"+email+"'");
    				if(rs.next()==false)
    				{
    				   rs=s.executeQuery("select * from libraryCapacity");
    				   rs.next();
    				   long l= rs.getLong("usercount");
    				  PreparedStatement ps= con.prepareStatement("insert into userdata(name,userid,password,emailid) values(?,?,?,?)");
    				  ps.setString(1, name);
    				  ps.setString(2, id);
    				  ps.setString(3, pass);
    				  ps.setString(4, email);
    				  ps.executeUpdate();
    				
    				  ps= con.prepareStatement("update librarycapacity set usercount=?");
    				 
    	              l+=1;
    				  ps.setLong(1,l);
    				  ps.executeUpdate();
    				  JOptionPane.showMessageDialog(data,"YOUR USERID IS: "+ id);
    				    t1.setText(null);
    	    	    	t3.setText(null);
    	    	    	t2.setText(null);
    	    	    	pf.setText(null);
    				}
    				else
    				{
    					if(id.equalsIgnoreCase(rs.getString("userid")))
    					{
    					JOptionPane.showMessageDialog(this, "LoginId Not Available (Already Taken)");
    					t1.setText(null);
    	    	    	t3.setText(null);
    	    	    	t2.setText(null);
    	    	    	pf.setText(null);
    					}
    					else
    					{
    						JOptionPane.showMessageDialog(this, "THIS EMAIL IS ALREADY CONNECTED WITH AN EXISTING ACCOUNT PLEASE USE ANOTHER EMAIL ADDRESS");
        					t1.setText(null);
        	    	    	t3.setText(null);
        	    	    	t2.setText(null);
        	    	    	pf.setText(null);	
    					}
    				}
    			} 
    	    	catch (SQLException e1) 
    	    	{
    				e1.printStackTrace();
    			}
    	    }
       }
       else if(e.getSource()==b2)
       {
    	  new StaffDeshbord("Staff Deshbord",this,staffname);
    	  setVisible(false);
       }
}
}
