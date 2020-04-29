
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class UpdateCandidate extends Frame 
{
	Button updateCandidateButton;
	List candidateIDList;
	TextField cidText, useridText, aadharnoText, symbolText;
	TextArea errorText;
	Connection connection;
	Statement statement;
	ResultSet rs;
	
	public UpdateCandidate() 
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
	
	private void loadCandidates() 
	{	   
		try 
		{
		  rs = statement.executeQuery("SELECT cid FROM candidates");
		  while (rs.next()) 
		  {
			candidateIDList.add(rs.getString("cid"));
		  }
		} 
		catch (SQLException e) 
		{ 
		  displaySQLErrors(e);
		}
	}
	
	public void buildGUI() 
	{		
	    candidateIDList = new List(10);
		loadCandidates();
		add(candidateIDList);
		
		candidateIDList.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e) 
			{
				try 
				{
					rs = statement.executeQuery("SELECT * FROM candidates where cid ="+candidateIDList.getSelectedItem());
					rs.next();
					cidText.setText(rs.getString("cid"));
					useridText.setText(rs.getString("user_id"));
					aadharnoText.setText(rs.getString("aadhar_no"));
					symbolText.setText(rs.getString("symbol"));
					
				} 
				catch (SQLException selectException) 
				{
					displaySQLErrors(selectException);
				}
			}
		});		
		
	    
		updateCandidateButton = new Button("Update Candidate");
		updateCandidateButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					Statement statement = connection.createStatement();
					int i = statement.executeUpdate("UPDATE candidates "
					+ "SET user_id=" + useridText.getText() + ", "
					+ "aadhar_no=" + aadharnoText.getText() + ", "
					+ "symbol ='"+ symbolText.getText() + "' WHERE cid = "
					+ candidateIDList.getSelectedItem());
					errorText.append("\nUpdated " + i + " rows successfully");
					candidateIDList.removeAll();
					loadCandidates();
				} 
				catch (SQLException insertException) 
				{
					displaySQLErrors(insertException);
				}
			}
		});
		
		cidText = new TextField(15);
		cidText.setEditable(false);
		useridText = new TextField(15);
		aadharnoText = new TextField(15);
		symbolText = new TextField(15);
		
		errorText = new TextArea(10, 40);
		errorText.setEditable(false);

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
		first.add(new Label("Candidate ID:"));
		first.add(cidText);
		first.add(new Label("User Id:"));
		first.add(useridText);
		first.add(new Label("Aadhar No.:"));
		first.add(aadharnoText);
		first.add(new Label("Symbol:"));
		first.add(symbolText);
		
		Panel second = new Panel(new GridLayout(4, 1));
		second.add(updateCandidateButton);
		
		Panel third = new Panel();
		third.add(errorText);
		
		add(first);
		add(second);
		add(third);
	    
		setTitle("Update Candidate");
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
		UpdateCandidate upc = new UpdateCandidate();

		upc.addWindowListener(new WindowAdapter(){
		  public void windowClosing(WindowEvent e) 
		  {
			System.exit(0);
		  }
		});
		
		upc.buildGUI();
	}
}