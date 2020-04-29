import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class DeleteVoter extends Frame 
{
	Button deleteVoterButton;
	List voterIDList;
	TextField useridText, aadharnoText;
	TextArea errorText;
	Connection connection;
	Statement statement;
	ResultSet rs;
	
	public DeleteVoter() 
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
		  rs = statement.executeQuery("SELECT * FROM voters");
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
					rs = statement.executeQuery("SELECT * FROM voters");
					while (rs.next()) 
					{
						if (rs.getString("user_id").equals(voterIDList.getSelectedItem()))
						break;
					}
					if (!rs.isAfterLast()) 
					{
						useridText.setText(rs.getString("user_id"));
						aadharnoText.setText(rs.getString("aadhar_no"));
					}
				} 
				catch (SQLException selectException) 
				{
					displaySQLErrors(selectException);
				}
			}
		});		
		
	    
		
		deleteVoterButton = new Button("Delete Voter");
		deleteVoterButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					Statement statement = connection.createStatement();
					int i = statement.executeUpdate("DELETE FROM voters WHERE user_id = "
							+ voterIDList.getSelectedItem());
					errorText.append("\nDeleted " + i + " rows successfully");
					useridText.setText(null);
					aadharnoText.setText(null);
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
		second.add(deleteVoterButton);
		
		Panel third = new Panel();
		third.add(errorText);
		
		add(first);
		add(second);
		add(third);
	    
		setTitle("Remove Voter");
		setSize(450, 600);
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
		DeleteVoter delv = new DeleteVoter();

		delv.addWindowListener(new WindowAdapter(){
		  public void windowClosing(WindowEvent e) 
		  {
			System.exit(0);
		  }
		});
		
		delv.buildGUI();
	}
}