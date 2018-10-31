
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//import java.util.InputMismatchException;
import java.util.Scanner;

public class SuperBazaar {
	static String a = null;
	static String b = null;
	static String c = null;
	static String e = null;
	static int d = 0;
	static String pr = null;
	static float prp = 0;
	static int pri = 0;
	static byte isa;
	static byte isav;
	static int q;
	static Connection conn;

	private static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			if (null == conn) {
				String url = "jdbc:mysql://localhost:3306/SuperBazaar?user=root&password=root"
						+ "&useSSL=false&allowPublicKeyRetrieval=true";
				conn = DriverManager.getConnection(url, "root", "root");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}

	private static void EnterUser() {
		Scanner sc0 = new Scanner(System.in);
		System.out.println("WELCOME TO SUPERBAZAAR\n");
		System.out.println("Sign In:" + " " + " " + " " + "Press 1\nNew User?: Press 2");

		int x = sc0.nextInt();

		switch (x) {
		case 1:

			System.out.println("Please Login Here");
			loginmethod();
			break;
		case 2:
			System.out.println("Please Register Yourself");
			registermethod();
			break;

		}

		sc0.close();
	}

	private static void loginmethod() {
		// TODO Auto-generated method stub

		Scanner sc3 = new Scanner(System.in);
		System.out.println("Enter Your Email");
		String Em = sc3.nextLine();
		System.out.println("Enter Your Password");
		String Psw = sc3.nextLine();
		String query1 = "Select * from UserDetails where Email=? and UserPassword=?";
		try {
			PreparedStatement ps1 = conn.prepareStatement(query1);
			ps1.setString(1, Em);
			ps1.setString(2, Psw);
			ResultSet result = ps1.executeQuery();
			while (result.next()) {
				a = result.getString("Email");
				b = result.getString("UserPassword");
				c = result.getString("Type");
				d = result.getInt("UserID");
				e = result.getString("UserName");

			}

			if (Em.equals(a) && Psw.equals(b)) {
				System.out.println("Login Successful");
				if (c.equals("Admin")) {
					productAdmin();

				} else {
					cart();
				}

			} else {
				System.out.println("Login Failed");
				System.out.println("Please Enter Valid Credentials");
				loginmethod();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			cart();
		}
		sc3.close();
	}

	private static void cart() {
		// TODO Auto-generated method stub
		System.out.println("Now You Can Start Shopping\n");
		System.out.println("Here are the list of products\n");
		System.out.println("ProductID|Product Name|Price Per Product");
		String query = "select * from ProductDetails";

		try {
			PreparedStatement ps1 = conn.prepareStatement(query);
			ResultSet result = ps1.executeQuery();

			while (result.next()) {
				int prodid = result.getInt("ProductID");
				String proname = result.getString("ProductName");
				float proprice = result.getFloat("ProductPrice");
				System.out.println(+prodid + " |" + proname + " |" + proprice);

			}

		} catch (Exception e) {

		}

		System.out.println("\n\nEnter How many products You Want to buy");
		Scanner s = new Scanner(System.in);
		int n = s.nextInt();
		while (n != 0) {
			System.out.println("Enter the productID You want to Purchase");

			int prid = s.nextInt();
			System.out.println("Enter its quantity");
			int quan = s.nextInt();
			String query1 = "Select * from ProductDetails where ProductID=?";
			try {
				PreparedStatement ps1 = conn.prepareStatement(query1);
				ps1.setInt(1, prid);

				ResultSet result = ps1.executeQuery();
				while (result.next()) {
					pr = result.getString("ProductName");
					prp = result.getFloat("ProductPrice");
					pri = result.getInt("ProductID");
					isa = result.getByte("isActive");
					isav = result.getByte("isAvailable");
					q = result.getInt("ProductQuantity");

				}
				if (prid == pri && isa == 1 && isav == 1 && q != 0) {

					float ta = quan * prp;
					String query11 = "Insert into Cart(UserID,ProductId,ProductName,Quantity,ProductPrice,TotalAmount,IsPaid)"
							+ "values(?,?,?,?,?,?,?)";

					PreparedStatement ps = conn.prepareStatement(query11);
					ps.setInt(1, d);
					ps.setInt(2, pri);
					ps.setString(3, pr);
					ps.setInt(4, quan);
					ps.setFloat(5, prp);
					ps.setFloat(6, ta);
					ps.setString(7, "No");
					boolean status = ps.execute();
					if (!status) {
						System.out.println("Product Added In Your Cart");

					} else {
						System.out.println("Product Cannot Be Added In Cart");
					}

				} else {
					System.out.println("Product Not Found or is not available");
				}
				n--;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		billing();

	}

	private static void billing() {

		// TODO Auto-generated method stub
		System.out.println("Do you want to checkout? if yes:press 1 if no?:press 2");
		Scanner sc2 = new Scanner(System.in);
		int c = sc2.nextInt();
		switch (c) {
		case 1: {
			System.out.println("These are the products you purchased\n");
			System.out.println("ProductID|Product Name|Price Per Product|Quantity|Total Amount\n");
			String query1 = "Select * from Cart where UserID=? and IsPaid=?";
			PreparedStatement ps;
			try {
				ps = conn.prepareStatement(query1);
				ps.setInt(1, d);
				ps.setString(2, "No");
				ResultSet result = ps.executeQuery();
				List<Float> l = new ArrayList<Float>();
				while (result.next()) {

					int prodid = result.getInt("ProductID");
					String proname = result.getString("ProductName");
					float proprice = result.getFloat("ProductPrice");
					int proquan = result.getInt("Quantity");
					float t = result.getFloat("TotalAmount");

					System.out.println(+prodid + " |" + proname + " |" + proprice + " |" + proquan + " |" + t);
					l.add(+t);

				}

				float sum = 0;
				for (float i : l) {
					sum += i;
				}
				System.out.println("Your bill Amount is");
				System.out.println(+sum);
				String query = "Update Cart set IsPaid=? where UserID=?";
				PreparedStatement ps1 = conn.prepareStatement(query);
				ps1 = conn.prepareStatement(query);
				ps1.setString(1, "Yes");
				ps1.setInt(2, d);
				ps1.executeUpdate();
				updation();
				feedback();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		}
		case 2: {
			cart();
			break;

		}
		default: {
			System.out.println("invalid input");
			break;
		}
		}

	}

	private static void feedback() {
		// TODO Auto-generated method stub
		System.out.println("Thankyou For Shopping With Us");
		System.out.println("Please Rate Us Between 1 to 5 where\n5:Excellent\n4:Good\n3:OK\n2:Poor\n1:Very Poor");
		Scanner s = new Scanner(System.in);
		int fe = s.nextInt();

		switch (fe) {
		case 5: {

			try {
				String query = "Insert into Feedback (UserName,Rating)" + "values(?,?)";
				PreparedStatement ps2 = conn.prepareStatement(query);
				ps2.setString(1, e);
				ps2.setString(2, "Excellent");
				ps2.execute();
				System.out.println("Thankyou! Your Feedback is Important to Us");
				System.out.println("For LogOut:Press 1\nTo Continue Shopping:Press 2");
				Scanner sq = new Scanner(System.in);
				int lg = sq.nextInt();
				switch (lg) {
				case 1: {

					EnterUser();
					break;
				}
				case 2: {
					cart();
				}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		case 4: {

			try {
				String query = "Insert into Feedback (UserName,Rating)" + "values(?,?)";
				PreparedStatement ps2 = conn.prepareStatement(query);
				ps2.setString(1, e);
				ps2.setString(2, "Good");
				ps2.execute();
				System.out.println("Thankyou! Your Feedback is Important to Us");
				System.out.println("For LogOut:Press 1\nTo Continue Shopping:Press 2");
				Scanner sq = new Scanner(System.in);
				int lg = sq.nextInt();
				switch (lg) {
				case 1: {
					EnterUser();
					break;
				}
				case 2: {
					cart();
				}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		case 3: {

			try {
				String query = "Insert into Feedback (UserName,Rating)" + "values(?,?)";
				PreparedStatement ps2 = conn.prepareStatement(query);
				ps2.setString(1, e);
				ps2.setString(2, "OK");
				ps2.execute();
				System.out.println("Thankyou! Your Feedback is Important to Us");
				System.out.println("For LogOut:Press 1\nTo Continue Shopping:Press 2");
				Scanner sq = new Scanner(System.in);
				int lg = sq.nextInt();
				switch (lg) {
				case 1: {
					EnterUser();
					break;
				}
				case 2: {
					cart();
				}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		case 2: {

			try {
				String query = "Insert into Feedback (UserName,Rating)" + "values(?,?)";
				PreparedStatement ps2 = conn.prepareStatement(query);
				ps2.setString(1, e);
				ps2.setString(2, "Poor");
				ps2.execute();

				System.out.println("Thankyou! Your Feedback is Important to Us");
				System.out.println("For LogOut:Press 1\nTo Continue Shopping:Press 2");
				Scanner sq = new Scanner(System.in);
				int lg = sq.nextInt();
				switch (lg) {
				case 1: {
					EnterUser();
					break;
				}
				case 2: {
					cart();
				}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		case 1: {

			try {
				String query = "Insert into Feedback (UserName,Rating)" + "values(?,?)";
				PreparedStatement ps2 = conn.prepareStatement(query);
				ps2.setString(1, e);
				ps2.setString(2, "Very Poor");
				ps2.execute();
				System.out.println("Thankyou! Your Feedback is Important to Us");
				System.out.println("For LogOut:Press 1\nTo Continue Shopping:Press 2");
				Scanner sq = new Scanner(System.in);
				int lg = sq.nextInt();
				switch (lg) {
				case 1: {
					EnterUser();
					break;
				}
				case 2: {
					cart();
				}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		default: {
			System.out.println("Invalid Input");
		}
		}
	}

	private static void updation() {
		// TODO Auto-generated method stub
		int qa = 0;
		int qs = 0;
		int pi = 0;
		int quan = 0;

		try {

			String query11 = "Select * from Cart where UserID=? and IsPaid=?";
			PreparedStatement ps33;
			ps33 = conn.prepareStatement(query11);
			ps33.setInt(1, d);
			ps33.setString(2, "Yes");
			ResultSet result1 = ps33.executeQuery();

			while (result1.next()) {
				pi = result1.getInt("ProductID");
				quan = result1.getInt("Quantity");
				String query112 = "Select * from ProductDetails where ProductID=?";
				PreparedStatement ps3334 = conn.prepareStatement(query112);
				ps3334.setInt(1, pi);
				ResultSet result11 = ps3334.executeQuery();
				while (result11.next()) {
					qa = result11.getInt("Qty_Available");
					qs = result11.getInt("Quant_sold");

					String query22 = "Update ProductDetails set Qty_Available=?,Quant_sold=? where ProductID=?";
					PreparedStatement ps333 = conn.prepareStatement(query22);

					ps333.setInt(1, qa - quan);
					ps333.setInt(2, qs + quan);
					ps333.setInt(3, pi);
					ps333.executeUpdate();

				}
			}

			String query = "Truncate table Cart";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void registermethod() {
		// TODO Auto-generated method stub

		Scanner sc1 = new Scanner(System.in);

		System.out.println("Enter your User name:");
		String UserName = sc1.nextLine();
		System.out.println("Enter your Gender:");
		String Gender = sc1.nextLine();
		System.out.println("Enter your Phone no.:");
		String phoneno = sc1.nextLine();
		System.out.println("Enter your Password");
		String Userpassword = sc1.next();
		System.out.println("Enter your Email");
		String Email = sc1.next();
		String query = "Insert into UserDetails (UserName,Gender,PhoneNo,UserPassword,Email)" + "values(?,?,?,?,?);";
		try {
			PreparedStatement ps2 = conn.prepareStatement(query);
			ps2.setString(1, UserName);
			ps2.setString(2, Gender);
			ps2.setString(3, phoneno);
			ps2.setString(4, Userpassword);
			ps2.setString(5, Email);
			boolean status = ps2.execute();
			if (!status) {
				System.out.println("Registration succesful");
			} else {
				System.out.println("Registration failed");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sc1.close();
	}

	public static int booleanToInt(boolean value) {
		// Convert true to 1 and false to 0.
		return value ? 1 : 0;
	}

	private static void productAdmin() {
		// TODO Auto-generated method stub
		Scanner sc = null;
		System.out.println("Add Products:Press 1\nView Customer Feedback:Press 2\nLogout:Press 3");
		sc = new Scanner(System.in);
		int p = sc.nextInt();
		switch (p) {
		case 1: {

			System.out.println("How Many Products To be added?");
			int nop = sc.nextInt();
			for (int qw = nop; qw != 0; qw--) {
				sc = new Scanner(System.in);
				System.out.println("Enter the Product Name");
				String prodName = sc.nextLine();

				System.out.println("Enter the Price");
				float price = sc.nextFloat();
				System.out.println("Enter the Quantity");
				int qty = sc.nextInt();
				System.out.println("Enter the QuantityAvailable");
				int qtyavl = sc.nextInt();
				System.out.println("QuantitySold:");
				int qtysld = qty - qtyavl;
				System.out.println(+qtysld);
				System.out.println("Active or Not(Enter True or False)");
				boolean value = sc.nextBoolean();
				byte act = (byte) booleanToInt(value);
				System.out.println("Available for SALE?(Enter True or False)");
				boolean value1 = sc.nextBoolean();
				byte sale = (byte) booleanToInt(value1);

				String query = "Insert into ProductDetails(ProductName,ProductPrice,ProductQuantity,Qty_Available,Quant_sold,isActive,isAvailable)"
						+ "values(?,?,?,?,?,?,?)";
				try {
					PreparedStatement ps = conn.prepareStatement(query);
					ps.setString(1, prodName);
					ps.setFloat(2, price);
					ps.setInt(3, qty);
					ps.setInt(4, qtyavl);
					ps.setInt(5, qtysld);
					ps.setByte(6, act);
					ps.setByte(7, sale);

					boolean status = ps.execute();
					if (!status) {
						System.out.println("Product Added Successfully");

					} else {
						System.out.println("Product Cannot Be Added");

					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			productAdmin();
			break;
		}
		case 2: {
			System.out.println("UserName|Rating\n");
			try {
				String query = "Select * from Feedback";
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet result = ps.executeQuery();
				while (result.next()) {
					String ui = result.getString("UserName");
					String rat = result.getString("Rating");
					System.out.println( ui + "     |" + rat);
					
				

				}productAdmin();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		case 3: {
			EnterUser();
			break;
		}
		}

		sc.close();
	}

	public static void main(String[] args) {

		// TODO Auto-generated method stub

		SuperBazaar.getConnection();
		SuperBazaar.EnterUser();

	}

}
