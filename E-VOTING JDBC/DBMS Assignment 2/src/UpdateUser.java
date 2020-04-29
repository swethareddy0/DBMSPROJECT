import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class UpdateUser extends Frame 
{
	Button updateUserButton;
	List userIDList;
	TextField useridText, nameText,dateofbirthText, genderText,aadharnoText,voteridText,addressText;
	TextArea errorText;
	Connection connection;
	Statement statement;
	ResultSet rs;
	
	public UpdateUser() 
	{
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} 
		catch (Exception e) 
		{
			System.err.println("Unable to find and load driver");
			System.exit(1);
		}
		connectToDB();
	}

	public void connectToDB() 
    {
		try 
		{
		  connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","swetha","vasavi");
		  statement = connection.createStatement();

		} 
		catch (SQLException connectException) 
		{
		  System.out.println(connectException.getMessage());
		  System.out.println(connectException.getSQLState());
		  System.out.println(connectException.getErrorCode());
		  System.exit(1);
		}
    }
	
	private void loadUsers() 
	{	   
		try 
		{
		  rs = statement.executeQuery("SELECT user_id FROM users");
		  while (rs.next()) 
		  {
			userIDList.add(rs.getString("user_id"));
		  }
		} 
		catch (SQLException e) 
		{ 
		  displaySQLErrors(e);
		}
	}
	
	public void buildGUI() 
	{		
	    userIDList = new List(10);
		loadUsers();
		add(userIDList);
	
		userIDList.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e) 
			{
				try 
				{
					rs = statement.executeQuery("SELECT * FROM users where user_id ="+userIDList.getSelectedItem());
					rs.next();
					useridText.setText(rs.getString("user_id"));
					nameText.setText(rs.getString("name"));
					dateofbirthText.setText(rs.getString("dob"));
					genderText.setText(rs.getString("gender"));
					aadharnoText.setText(rs.getString("aadhar_no"));
					voteridText.setText(rs.getString("voter_id"));
					addressText.setText(rs.getString("address"));
					
				} 
				catch (SQLException selectException) 
				{
					displaySQLErrors(selectException);
				}
			}
		});		
		
	    
		updateUserButton = new Button("Update User");
		updateUserButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					Statement statement = connection.createStatement();
					int i = statement.executeUpdate("UPDATE users "
							+ "SET name='" + nameText.getText() + "', "
							+ "dob='" + dateofbirthText.getText() + "', "
							+ "gender='"+ genderText.getText() + "'," + "aadhar_no = "+aadharnoText.getText()
							 + "," +"voter_id = "+voteridText.getText() + ","
							 + "address='" + addressText.getText() + "' WHERE user_id = "
							+ userIDList.getSelectedItem());
					errorText.append("\nUpdated " + i + " rows successfully");
					userIDList.removeAll();
					loadUsers();
				} 
				catch (SQLException insertException) 
				{
					displaySQLErrors(insertException);
				}
			}
		});
		
		useridText = new TextField(15);
		useridText.setEditable(false);
		nameText = new TextField(15);
		dateofbirthText = new TextField(15);
		genderText = new TextField(15);
		aadharnoText = new TextField(15);
		voteridText = new TextField(15);
		addressText = new TextField(15);
		
		errorText = new TextArea(10, 40);
		errorText.setEditable(false);

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
		first.add(new Label("User ID:"));
		first.add(useridText);
		first.add(new Label("Name"));
		first.add(nameText);
		first.add(new Label("Date of Birth"));
		first.add(dateofbirthText);
		first.add(new Label("Gender"));
		first.add(genderText);
		first.add(new Label("Aadhar No.:"));
		first.add(aadharnoText);
		first.add(new Label("Voter ID:"));
		first.add(voteridText);
		first.add(new Label("Address"));
		first.add(addressText);
		
		Panel second = new Panel(new GridLayout(4, 1));
		second.add(updateUserButton);
		
		Panel third = new Panel();
		third.add(errorText);
		
		add(first);
		add(second);
		add(third);
	    
		setTitle("Update User");
		setSize(600, 800);
		setLayout(new FlowLayout());
		setVisible(true);
		
	}

	private void displaySQLErrors(SQLException e) 
	{
		errorText.append("\nSQLException: " + e.getMessage() + "\n");
		errorText.append("SQLState:     " + e.getSQLState() + "\n");
		errorText.append("VendorError:  " + e.getErrorCode() + "\n");
	}

	public static void main(String[] args) 
	{
		UpdateUser upu = new UpdateUser();

		upu.addWindowListener(new WindowAdapter(){
		  public void windowClosing(WindowEvent e) 
		  {
			System.exit(0);
		  }
		});
		
		upu.buildGUI();
	}
}