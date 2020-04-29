
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class DeleteUser extends Frame 
{
	Button deleteUserButton;
	List userIDList;
	TextField useridText, nameText, dateofbirthText, genderText,aadharnoText,voteridText,addressText;
	TextArea errorText;
	Connection connection;
	Statement statement;
	ResultSet rs;
	
	public DeleteUser() 
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
		  rs = statement.executeQuery("SELECT * FROM users");
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
					rs = statement.executeQuery("SELECT * FROM users");
					while (rs.next()) 
					{
						if (rs.getString("user_id").equals(userIDList.getSelectedItem()))
						break;
					}
					if (!rs.isAfterLast()) 
					{
						useridText.setText(rs.getString("user_id"));
						nameText.setText(rs.getString("name"));
						dateofbirthText.setText(rs.getString("dob"));
						genderText.setText(rs.getString("gender"));
						aadharnoText.setText(rs.getString("aadhar_no"));
						voteridText.setText(rs.getString("voter_id"));
						addressText.setText(rs.getString("Address"));
					}
				} 
				catch (SQLException selectException) 
				{
					displaySQLErrors(selectException);
				}
			}
		});		
		
	    
		deleteUserButton = new Button("Delete User");
		deleteUserButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					Statement statement = connection.createStatement();
					int i = statement.executeUpdate("DELETE FROM users WHERE user_id = "
							+ userIDList.getSelectedItem());
					errorText.append("\nDeleted " + i + " rows successfully");
					useridText.setText(null);
					nameText.setText(null);
					dateofbirthText.setText(null);
					genderText.setText(null);
					aadharnoText.setText(null);
					voteridText.setText(null);
					addressText.setText(null);
					userIDList.removeAll();
					loadUsers();
				} 
				catch (SQLException insertException) 
				{
					displaySQLErrors(insertException);
				}
			}
		});
		
		useridText = new TextField(20);
		nameText = new TextField(20);
		dateofbirthText = new TextField(20);
		genderText = new TextField(20);
		aadharnoText = new TextField(20);
		voteridText = new TextField(20);
		addressText = new TextField(20);
		errorText = new TextArea(10, 40);
		errorText.setEditable(false);

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
		first.add(new Label("User ID:"));
		first.add(useridText);
		first.add(new Label("Name:"));
		first.add(nameText);
		first.add(new Label("Date of Birth:"));
		first.add(dateofbirthText);
		first.add(new Label("Gender:"));
		first.add(genderText);
		first.add(new Label("Aadhar No:"));
		first.add(aadharnoText);
		first.add(new Label("Voter Id:"));
		first.add(voteridText);
		first.add(new Label("Address:"));
		first.add(addressText);
		
		Panel second = new Panel(new GridLayout(4, 1));
		second.add(deleteUserButton);
		
		Panel third = new Panel();
		third.add(errorText);
		
		add(first);
		add(second);
		add(third);
	    
		setTitle("Remove User");
		setSize(750, 800);
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
		DeleteUser delu = new DeleteUser();

		delu.addWindowListener(new WindowAdapter(){
		  public void windowClosing(WindowEvent e) 
		  {
			System.exit(0);
		  }
		});
		
		delu.buildGUI();
	}
}