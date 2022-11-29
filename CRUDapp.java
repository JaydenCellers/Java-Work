
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CRUDapp {
	static Scanner scn = new Scanner(System.in);

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		String ans = "";
		System.out.println("Welcome to the Employee Database for XYZ Inc.");
		// Load the mysql driver
		Class.forName("com.mysql.cj.jdbc.Driver");
		// Create a connection
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sept2022", "root", "root");
		// Create a Statement
		Statement st = con.createStatement();

		while (!ans.equalsIgnoreCase("quit")) {
			System.out.println("Select one of the following: Add, Update, Remove, Search, or Quit");
			System.out.print("What would you like to do: ");
			ans = scn.nextLine().toLowerCase();
			switch (ans) {
			case "add":
				add(st);
				break;
			case "update":
				update(st);
				break;
			case "remove":
				remove(st);
				break;
			case "search":
				search(st);
				break;
			case "quit":
				break;
			default:
				System.out.println("Invalid input; please try again.");
				break;
			}
		}
		System.out.println("Employee Database session ended. Goodbye.");
		scn.close();
	}

	private static void add(Statement st) {
		int empId;
		String empName;
		int empSal;
		System.out.println("Adding employee to Employee Database.");
		System.out.print("Please enter the employee ID: ");
		empId = scn.nextInt();
		scn.nextLine();
		System.out.print("Please enter the employee Name: ");
		empName = scn.nextLine();
		System.out.print("Please enter the employee Salary: ");
		empSal = scn.nextInt();
		scn.nextLine();

		try {
			String addStatement = "insert into emp values(" + empId + ", '" + empName + "', " + empSal + ")";
			st.execute(addStatement);
			System.out.println("Employee added successfully!");
		} catch (SQLException e) {
			System.out.println("Insert failed; please try again.");
		}
	}

	private static void remove(Statement st) {
		int empId;
		System.out.println("Removing employee from Employee Database");
		System.out.print("Please enter the employee ID: ");
		empId = scn.nextInt();
		scn.nextLine();

		try {
			String rmvStatement = "delete from emp where eno = " + empId;
			int rowsAff = st.executeUpdate(rmvStatement);
			if (rowsAff == 0) {
				System.out.println("No employee found; please try again.");
			} else {
				System.out.println("Employee removed successfully!");
			}

		} catch (SQLException e) {
			System.out.println("Removal failed; please try again.");
		}

	}

	private static void update(Statement st) {
		int empId;
		String ans;
		String updStatement = "";
		String newName;
		int newSal;

		System.out.println("Update employee in Employee Database");
		System.out.print("Please enter the employee ID: ");
		empId = scn.nextInt();
		scn.nextLine();
		System.out.print("Would you like to update Name, Salary, or both: ");
		ans = scn.nextLine().toLowerCase();

		switch (ans) {
		case "name":
			System.out.print("Enter new name: ");
			newName = scn.nextLine();
			updStatement = "update emp set ename = '" + newName + "' where eno = " + empId;
			break;
		case "salary":
			System.out.print("Enter new salary: ");
			newSal = scn.nextInt();
			scn.nextLine();
			updStatement = "update emp set esal = " + newSal + " where eno = " + empId;
			break;
		case "both":
			System.out.print("Enter new name: ");
			newName = scn.nextLine();
			System.out.print("Enter new salary: ");
			newSal = scn.nextInt();
			scn.nextLine();
			updStatement = "update emp set ename = '" + newName + "', esal =" + newSal + " where eno = " + empId;
			break;
		default:
			System.out.println("Invalid input; please try again.");
		}

		try {
			st.execute(updStatement);
			System.out.println("Employee updated successfully!");
		} catch (SQLException e) {
			System.out.println("Update failed; please try again.");
		}
	}

	private static void search(Statement st) {
		int empId;

		System.out.println("Searching for employee in Employee Database");
		System.out.print("Please enter the employee ID: ");
		empId = scn.nextInt();
		scn.nextLine();
		try {
			ResultSet rs = st.executeQuery("select * from emp where eno = " + empId);
			while (rs.next()) {
				System.out.println("Emp Number: " + rs.getInt("eno"));
				System.out.println("Emp Name: " + rs.getString(2));
				System.out.printf("Emp Salary: $%,d\n", rs.getInt("esal"));
			}
		} catch (SQLException e) {
			System.out.println("Search failed; please try again.");
		}
	}

}