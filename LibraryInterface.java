package librarySystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.SwingConstants;

public class LibraryInterface extends JFrame implements ActionListener
{
	JPanel pj,pb;JButton b1,b2;Font fn;
	JLabel dl1,dl2;TextField dt1;JPasswordField dpf;
	JButton db; JDialog dj; JFrame f; JPanel p2; String staffname;
    public LibraryInterface(String str)
    {
    	super(str);
    	db=new JButton("Login");
    	f=this;
    	fn= new Font("Arial", Font.BOLD,20);
    	setSize(Toolkit.getDefaultToolkit().getScreenSize());
    	setIconImage(ImageBox.FRAME_LOGO);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	pj= new JPanel()
    	{
    		@Override
    		public void paintComponent(Graphics g)
    		{
    			g.drawImage(ImageBox.LIBRARY,0,0,getWidth(),getHeight(),this);
    		}
    	};
    	setContentPane(pj);
    	b1=new JButton(new ImageIcon(ImageBox.STAFF.getScaledInstance(200, 200,Image.SCALE_SMOOTH)));
    	b1.setText("Staff Login");
    	b1.setFont(fn);
    	b1.setForeground(Color.white);
    	b1.setOpaque(false);
    	b1.setBackground(new Color(0,0,0,0));
    	b1.setBorderPainted(false);
    	b1.setHorizontalTextPosition(SwingConstants.CENTER);
    	b1.setVerticalTextPosition(SwingConstants.BOTTOM);
    	b2=new JButton(new ImageIcon(ImageBox.USERPORT.getScaledInstance(200, 200,Image.SCALE_SMOOTH)));
    	b2.setText("Reader Login");
    	b2.setFont(fn);
    	b2.setForeground(Color.white);
    	b2.setOpaque(false);
    	b2.setBackground(new Color(0,0,0,0));
    	b2.setBorderPainted(false);
    	b2.setHorizontalTextPosition(SwingConstants.CENTER);
    	b2.setVerticalTextPosition(SwingConstants.BOTTOM);
    	
    	pb= new JPanel();
    	pb.setOpaque(false);
    	pb.setBackground(new Color(0,0,0,0));
    	pb.setLayout(new GridLayout(1,2,20,20));
    	pb.add(b1);pb.add(b2);
    	pb.setBounds(250, 120, 839,455);
    	add(pb);
    	addMouseListener(new MouseAdapter()
    	{
    		@Override
    		public void mouseClicked(MouseEvent me)
    		{
    			System.out.println(me.getX()+","+me.getY());
    		}
		});
    	b1.addActionListener(this);
    	b2.addActionListener(this);
    	 db.addActionListener(this);
    	setLayout(null);
    	setVisible(true);
    	
    	
    }
    public static void main(String args[])
    {
    	new LibraryInterface("My Library");
    }
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==b1)
		{
			 dj= new JDialog(f,"Staff Login");
        	 dj.setSize(760,500);
        	 dj.setModal(true);
        	 dj.addWindowListener(new WindowAdapter()
        	 {
        		 @Override
        		 public void windowClosing(WindowEvent we)
        		 {
        			 int i=JOptionPane.showConfirmDialog(dj,"WANT TO EXIT?","Confirmation",JOptionPane.OK_CANCEL_OPTION);
        		     if(i==JOptionPane.OK_OPTION)
        		     {
        		    	 dj.dispose();
        		     }
        		     else if(i==JOptionPane.CANCEL_OPTION)
        		     {
        		    	 dj.setDefaultCloseOperation(dj.DO_NOTHING_ON_CLOSE);
        		     }
        		 }
        	 });
        	 p2= new JPanel() 
      	   {
      		   @Override
      		   public void paintComponent(Graphics g)
      		   {
      			   g.drawImage(ImageBox.STAFF_LOGIN, 0, 0,getWidth(), getHeight(), this);
      		   }
      	   };
// 		  dj.addMouseListener(new MouseAdapter()
//		  {
//	  @Override
//	  public void mouseClicked(MouseEvent me)
//	  {
//		  	System.out.println(me.getX()+","+me.getY());
//	  }
//		 });
      	   dj.setContentPane(p2);
      	   
      	   JPanel dp= new JPanel();
      	   dp.setOpaque(false);
      	   dp.setBackground(new Color(0,0,0,0));
      	   dl1=new JLabel("      ENTER STAFFID/LOGINID:- ");
      	   dl2= new JLabel("       ENTER PASSWORD:- ");
      	   dt1=new TextField(30);
      	   dpf= new JPasswordField(30);
      	   dl1.setFont(fn);
      	   dj. setIconImage(ImageBox.FRAME_LOGO);
      	  dl2.setFont(fn); 
      	  dt1.setFont(fn);  
      	  dpf.setFont(fn);
      	 
      	  db.setBackground(Color.DARK_GRAY);
      	  db.setForeground(Color.white);
      	  db.setFont(fn);
      	  dl1.setForeground(Color.white);
      	  dl2.setForeground(Color.white);
           dp.setLayout(new GridLayout(4,1,10,10));
           dp.add(dl1);
           dp.add(dt1);
           dp.add(dl2);
           dp.add(dpf);
           dp.setBounds(16, 131,300,150);
           db.setBounds(35, 303, 260, 28);
           dj.add(dp);dj.add(db);
      	   dj.setLocationRelativeTo(f);
      	   dj.setLayout(null);
        	 dj.setVisible(true);
        	
        	
		}
		else if(e.getSource()==db)
		{
			 if(dt1.getText()==null || dt1.getText().isEmpty() || dt1.getText().equalsIgnoreCase("") || dpf.getText()==null || dpf.getText().isEmpty() || dpf.getText().equalsIgnoreCase(""))	
	    	    {
	    	    	JOptionPane.showMessageDialog(f, "PLEASE ENTER ALL REQURED DATA TO CONTINUE");
	    	    	dt1.setText(null);
	    	    	dpf.setText(null);
	    	    }
	    	   else 
	    	   {
	    	    	String id=dt1.getText();
	    	    	String pass=dpf.getText();
	    	    	try 
	    	    	{
	    				Connection con= DataBase.getConnection();
	    				Statement s= con.createStatement();
	    			 
	    				ResultSet rs=s.executeQuery("select * from staffdata where loginid='"+id+"' or staffid='"+id+"'");
	    				if(rs.next()==false)
	    				{
	    					JOptionPane.showMessageDialog(f, "INCORRECT DATA PLEASE TRY AGAIN");
	    			    	dt1.setText(null);
	    			    	dpf.setText(null);
	    				}
	    				else
	    				{
	    					if(pass.equalsIgnoreCase(rs.getString("password")))
	    					{
	    						staffname=rs.getString("name");
	    					  dj.dispose();
	    					   new StaffSection("Staff Section",staffname);
	    					     dispose();
	    					  dt1.setText(null);
	      			    	  dpf.setText(null);
	    					}
	    					else
	    					{
	    						JOptionPane.showMessageDialog(f, "INCORRECT DATA PLEASE TRY AGAIN");
	        			    	dt1.setText(null);
	        			    	dpf.setText(null);
	    					}
	    				}
	    			} 
	    	    	catch (SQLException e1) 
	    	    	{
	    				e1.printStackTrace();
	    			}
	    	   }
			
		}  
		else
		{
			new UserLogin("User Login");
			dispose();
		}
		
	}
}
