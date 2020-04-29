
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class DeleteCandidate extends Frame 
{
	Button deleteCandidateButton;
	List candidateIDList;
	TextField cidText,useridText, aadharnoText,symbolText;
	TextArea errorText;
	Connection connection;
	Statement statement;
	ResultSet rs;
	
	public DeleteCandidate() 
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
		  rs = statement.executeQuery("SELECT * FROM candidates");
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
					rs = statement.executeQuery("SELECT * FROM candidates");
					while (rs.next()) 
					{
						if (rs.getString("cid").equals(candidateIDList.getSelectedItem()))
						break;
					}
					if (!rs.isAfterLast()) 
					{
						cidText.setText(rs.getString("cid"));
						useridText.setText(rs.getString("user_id"));
						aadharnoText.setText(rs.getString("aadhar_no"));
						symbolText.setText(rs.getString("symbol"));
					}
				} 
				catch (SQLException selectException) 
				{
					displaySQLErrors(selectException);
				}
			}
		});		
		
	    
		
		deleteCandidateButton = new Button("Delete Candidate");
		deleteCandidateButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					Statement statement = connection.createStatement();
					int i = statement.executeUpdate("DELETE FROM candidates WHERE cid = "
							+ candidateIDList.getSelectedItem());
					errorText.append("\nDeleted " + i + " rows successfully");
					cidText.setText(null);
					useridText.setText(null);
					aadharnoText.setText(null);
					symbolText.setText(null);
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
		useridText = new TextField(15);
		aadharnoText = new TextField(15);
		symbolText = new TextField(15);
		
		errorText = new TextArea(10, 40);
		errorText.setEditable(false);

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
		first.add(new Label("Candidate ID:"));
		first.add(cidText);
		first.add(new Label("User ID:"));
		first.add(useridText);
		first.add(new Label("Aadhar No.:"));
		first.add(aadharnoText);
		first.add(new Label("Symbol:"));
		first.add(symbolText);
		
		Panel second = new Panel(new GridLayout(4, 1));
		second.add(deleteCandidateButton);
		
		Panel third = new Panel();
		third.add(errorText);
		
		add(first);
		add(second);
		add(third);
	    
		setTitle("Remove Candidate");
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
		DeleteCandidate delc = new DeleteCandidate();

		delc.addWindowListener(new WindowAdapter(){
		  public void windowClosing(WindowEvent e) 
		  {
			System.exit(0);
		  }
		});
		
		delc.buildGUI();
	}
}