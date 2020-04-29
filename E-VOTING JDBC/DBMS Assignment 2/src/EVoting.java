import java.awt.*; 
import java.awt.event.*; 

class EVoting extends Frame implements ActionListener
{ 
	  String msg = ""; 
	  Label ll;
	  InsertUser inu;
	  UpdateUser upu;
	  DeleteUser delu;
	  InsertVoter inv;
	  UpdateVoter upv;
	  DeleteVoter delv;
	  InsertCandidate inc;
	  UpdateCandidate upc;
	  DeleteCandidate delc;
	  
	  
	  EVoting() 
	  { 
			ll = new Label();
			ll.setAlignment(Label.CENTER);  
			ll.setBounds(100,350,350,200); 			
			ll.setText("Welcome to E-VOTING System");
			add(ll);
		 
			// create menu bar and add it to frame 
			MenuBar mbar = new MenuBar(); 
			setMenuBar(mbar); 
		 
			// create the menu items and add it to Menu
			Menu user = new Menu("Users"); 
			MenuItem item1, item2, item3; 
			user.add(item1 = new MenuItem("Insert User")); 
			user.add(item2 = new MenuItem("Update User")); 
			user.add(item3 = new MenuItem("Delete User")); 
			mbar.add(user); 
			
			
		 
			Menu voter = new Menu("Voters"); 
			MenuItem item4, item5, item6; 
			voter.add(item4 = new MenuItem("Insert Voter")); 
			voter.add(item5 = new MenuItem("Update Voter")); 
			voter.add(item6 = new MenuItem("Delete Voter"));  
			mbar.add(voter); 
			
			Menu candidate = new Menu("Candidates"); 
			MenuItem item7, item8, item9; 
			candidate.add(item7 = new MenuItem("Insert Candidate")); 
			candidate.add(item8 = new MenuItem("Update Candidate")); 
			candidate.add(item9 = new MenuItem("Delete Candidate")); 
			mbar.add(candidate);
			Menu login = new Menu("logins"); 
			MenuItem item10; 
			login.add(item10 = new MenuItem("Register")); 
			
			mbar.add(login); 
			
			
			
			// register listeners
			item1.addActionListener(this); 
			item2.addActionListener(this); 
			item3.addActionListener(this); 
			item4.addActionListener(this); 
			item5.addActionListener(this); 
			item6.addActionListener(this); 
			item7.addActionListener(this); 
			item8.addActionListener(this); 
			item9.addActionListener(this); 
			item10.addActionListener(this); 
			
						
					
			 // Anonymous inner class which extends WindowAdaptor to handle the Window event: windowClosing  
			addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent we) 
				{ 
					System.exit(0);	
				} 
			}); 
			
			//Frame properties
			setTitle("E-Voting System");  
			setFont(new Font("White" ,Font.BOLD, 14)); 
			setLayout(null);
			setSize(800, 900); 
			setVisible(true);	
			
	  }   
	  
	  public void actionPerformed(ActionEvent ae) 
	  { 

		  String arg = ae.getActionCommand(); 
		  if(arg.equals("Insert User"))
		  {
			inu = new InsertUser();

			inu.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) 
			{
				inu.dispose();
			}
			});		
			inu.buildGUI();	
          }			
		 
		 else if(arg.equals("Update Users")) 
		 {
			upu = new UpdateUser();
			upu.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) 
		    {
				upu.dispose();
			}
			});		
			upu.buildGUI();		 
		 }
		 
		 else if(arg.equals("Delete User")) 
		 {
			delu = new DeleteUser();

			delu.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) 
			{
				delu.dispose();
			}
			});		
			delu.buildGUI();		 
		 }
		 
		 else if(arg.equals("Insert Voter")) 
		 {
			inv = new InsertVoter();
			inv.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) 
		    {
				inv.dispose();
			}
			});		
			inv.buildGUI();		 
		 }
		 else if(arg.equals("Update Voter")) 
		 {
			upv = new UpdateVoter();
			setVisible(false); 
			upv.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) 
			{
				upv.dispose();
				setVisible(true);
			}
			});		
			upv.buildGUI();		 
		 }
		 else if(arg.equals("Delete Voter")) 
		 {
			delv = new DeleteVoter();
			setVisible(false); 
			delv.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) 
			{
				delv.dispose();
				setVisible(true);
			}
			});		
			delv.buildGUI();		 
		 }
		 else if(arg.equals("Insert Candidate")) 
		 {
			inc = new InsertCandidate();
			setVisible(false); 
			inc.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) 
			{
				inc.dispose();
				setVisible(true);
			}
			});		
			inc.buildGUI();		 
		 }
		 else if(arg.equals("Delete Candidate")) 
		 {
			delc = new DeleteCandidate();
			setVisible(false); 
			delc.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) 
			{
				delc.dispose();
				setVisible(true);
			}
			});		
			delc.buildGUI();		 
		 }
		 else if(arg.equals("Update Candidate")) 
		 {
			upc = new UpdateCandidate();
			setVisible(false); 
			upc.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) 
			{
				upc.dispose();
				setVisible(true);
			}
			});		
			upc.buildGUI();		 
		 }			 
	  }
	  public static void main(String...args)
	  {
			new EVoting();	  
	  }
} 
 

 