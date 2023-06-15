package packag3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ProductMain {
	
	private static final String USER = "root";
	private static final String PASSWORD = "password";
	private static final String URL = "jdbc:mysql://localhost/serialization";
	

	
	public static void main(String[] args) {
		
		
		try {
			
			Product product = new Product();
			product.setName("Rivotril 20mg");
			product.setId(1);
			product.setQuantity(12);
			product.setPrice(89.90);
			
			
			//STARTS SERIALIZATION
			File file = new File("D:\\DADOS DO USUARIO\\Desktop\\file.txt");
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(product);
			
			
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			product = (Product) in.readObject();
			in.close();
			
			//String sql = "CREATE DATABASE Serialization";
			/*
			String sql2 = "CREATE TABLE Products("
					+ "id INT (80) PRIMARY KEY NOT NULL,"
					+ "name VARCHAR (255) NOT NULL, "
					+ "quantity INT (80) NOT NULL,"
					+ "price INT (80) NOT NULL"
					+ ")";*/
			String sql3 = "INSERT INTO products (id, name, quantity, price) VALUES (?,?,?,?)";
			
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement stmt = connection.prepareStatement(sql3);
			
			stmt.setLong(1, product.getId());
			stmt.setString(2, product.getName());
			stmt.setInt(3, product.getQuantity());
			stmt.setDouble(4, product.getPrice());
			
			stmt.execute();
			
			//System.out.println("Values Updated.");
			//System.out.println("Updated.");
			//System.out.println("Complete.");
			
			out.close();
			fileOut.close();
			
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			
			stmt.execute(sql3);
			
			System.out.println("Database has been created.");
			
			FileReader file_reading = new FileReader(file);
			int data = file_reading.read();
			
			while(data != -1) {
				System.out.print((char)data);
				data = file_reading.read();
				file_reading.close();
			}
			
			connection.close();
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			
			//That´s kinda useless.
		}
	}
}
