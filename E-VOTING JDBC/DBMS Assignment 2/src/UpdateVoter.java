import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class UpdateVoter extends Frame 
{
	Button updateVoterButton;
	List voterIDList;
	TextField useridText, aadharnoText;
	TextArea errorText;
	Connection connection;
	Statement statement;
	ResultSet rs;
	
	public UpdateVoter() 
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
	
	private void loadVoters() 
	{	   
		try 
		{
		  rs = statement.executeQuery("SELECT user_id FROM voters");
		  while (rs.next()) 
		  {
			voterIDList.add(rs.getString("user_id"));
		  }
		} 
		catch (SQLException e) 
		{ 
		  displaySQLErrors(e);
		}
	}
	
	public void buildGUI() 
	{		
	    voterIDList = new List(10);
		loadVoters();
		add(voterIDList);
		
		voterIDList.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e) 
			{
				try 
				{
					rs = statement.executeQuery("SELECT * FROM voters where user_id ="+voterIDList.getSelectedItem());
					rs.next();
					useridText.setText(rs.getString("user_id"));
					aadharnoText.setText(rs.getString("aadhar_no"));
					
				} 
				catch (SQLException selectException) 
				{
					displaySQLErrors(selectException);
				}
			}
		});		
		
	    
		updateVoterButton = new Button("Update Voter");
		updateVoterButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					Statement statement = connection.createStatement();
					int i = statement.executeUpdate("UPDATE voters "
					+ "SET aadhar_no=" + aadharnoText.getText() + " WHERE user_id = "
					+ voterIDList.getSelectedItem());
					errorText.append("\nUpdated " + i + " rows successfully");
					voterIDList.removeAll();
					loadVoters();
				} 
				catch (SQLException insertException) 
				{
					displaySQLErrors(insertException);
				}
			}
		});
		
		useridText = new TextField(15);
		useridText.setEditable(false);
		aadharnoText = new TextField(15);
		
		errorText = new TextArea(10, 40);
		errorText.setEditable(false);

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
		first.add(new Label("User ID:"));
		first.add(useridText);
		first.add(new Label("Aadhar No.:"));
		first.add(aadharnoText);
		
		Panel second = new Panel(new GridLayout(4, 1));
		second.add(updateVoterButton);
		
		Panel third = new Panel();
		third.add(errorText);
		
		add(first);
		add(second);
		add(third);
	    
		setTitle("Update Voter");
		setSize(500, 600);
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
		UpdateVoter upv = new UpdateVoter();

		upv.addWindowListener(new WindowAdapter(){
		  public void windowClosing(WindowEvent e) 
		  {
			System.exit(0);
		  }
		});
		
		upv.buildGUI();
	}
}