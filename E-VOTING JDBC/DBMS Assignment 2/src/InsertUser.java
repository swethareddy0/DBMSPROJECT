import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class InsertUser extends Frame 
{
	Button insertUserButton;
	TextField useridText, nameText, dateofbirthText, genderText,aadharnoText,voteridText,addressText;
	TextArea errorText;
	Connection connection;
	Statement statement;
	public InsertUser() 
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
	public void buildGUI() 
	{		
		insertUserButton = new Button("Insert User");
		insertUserButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
				   String query= "INSERT INTO users VALUES(" + useridText.getText() + ", " + "'" + nameText.getText() + "'," + "'" + dateofbirthText.getText() + "'," + "'" + genderText.getText() + "'," +aadharnoText.getText() + "," +voteridText.getText() + "," + "'" + addressText.getText() +"')";
				  int i = statement.executeUpdate(query);
				  errorText.append("\nInserted " + i + " rows successfully");
				} 
				catch (SQLException insertException) 
				{
				  displaySQLErrors(insertException);
				}
			}
		});

	
		useridText = new TextField(15);
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
		first.add(new Label("Name:"));
		first.add(nameText);
		first.add(new Label("Date of Birth:"));
		first.add(dateofbirthText);
		first.add(new Label("Gender:"));
		first.add(genderText);
		first.add(new Label("Aadhar No.:"));
		first.add(aadharnoText);
		first.add(new Label("Voter ID:"));
		first.add(voteridText);
		first.add(new Label("Address:"));
		first.add(addressText);
		first.setBounds(150,90,200,100);
		
		Panel second = new Panel(new GridLayout(4, 1));
		second.add(insertUserButton);
        second.setBounds(150,220,150,100);         
		
		Panel third = new Panel();
		third.add(errorText);
		third.setBounds(150,320,300,200);
		
		setLayout(null);

		add(first);
		add(second);
		add(third);
	    
		setTitle("New User Creation");
		setSize(600,750);
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
		InsertUser inu = new InsertUser();

		inu.addWindowListener(new WindowAdapter(){
		  public void windowClosing(WindowEvent e) 
		  {
			System.exit(0);
		  }
		});
		
		inu.buildGUI();
	}
}