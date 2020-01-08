// Movie Recommendation engine
// JDBC, JAVA, MYSQL
// Hunter Riley
//package n01378944_finalproj;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

class Movie {
	int vote;
	int score;
	String movieid;
	String name;
	Double popularity;
	ArrayList<String> genre_id;
	ArrayList<String> key_id;
	ArrayList<String> actor_id;
	ArrayList<String> director_id;
	
	public int getscore() {
		return this.score;
	}
	
}

public class Finalproj {
	static final String DB_URL = "jdbc:mysql://localhost:3306/?useSSL=false";
	static final String USER = "root";
	static final String PASS = "mysql123";
	private static Scanner scanner;
	private static boolean found;

	private static void Menu() {
		System.out.println("Enter a movie name(Case Sensitive): ");
		System.out.println("Press q to quit Program");
	}

	private static Boolean isInt(String x) {
		try {
			Integer.parseInt(x);
		} catch (NumberFormatException e){
			return false;
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
	Connection conn = null; 

		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS); 
			Statement stmt = conn.createStatement();
			Statement stmt2 = conn.createStatement();
			String sql;

		//Create database
			sql = "CREATE DATABASE IF NOT EXISTS moviesdb";
			stmt.executeUpdate(sql);
			System.out.println("Database created.");

		//Use database
			
			sql = "USE moviesdb";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE IF NOT EXISTS movies(movie_id INT UNSIGNED, movie_name VARCHAR(200), popularity DOUBLE UNSIGNED, vote INT UNSIGNED)";
			stmt.executeUpdate(sql);
			System.out.println("Table movies created");
			
			sql = "CREATE TABLE IF NOT EXISTS keywords(keyword_id INT UNSIGNED, name VARCHAR(200))";
			stmt.executeUpdate(sql);
			System.out.println("Table keywords created");
			
			sql = "CREATE TABLE IF NOT EXISTS keyword_connect(movie_id INT UNSIGNED, keyword_id INT UNSIGNED)";
			stmt.executeUpdate(sql);
			System.out.println("Table keyword_connect created");
			
			sql = "CREATE TABLE IF NOT EXISTS actors(actor_id INT UNSIGNED, name VARCHAR(200))";
			stmt.executeUpdate(sql);
			System.out.println("Table actors created");
			
			sql = "CREATE TABLE IF NOT EXISTS actor_connect(movie_id INT UNSIGNED, actor_id INT UNSIGNED)";
			stmt.executeUpdate(sql);
			System.out.println("Table actors_connect created");
			
			sql = "CREATE TABLE IF NOT EXISTS genres(genre_id INT UNSIGNED, name VARCHAR(200))";
			stmt.executeUpdate(sql);
			System.out.println("Table genres created");
			
			sql = "CREATE TABLE IF NOT EXISTS genre_connect(movie_id INT UNSIGNED, genre_id INT UNSIGNED)";
			stmt.executeUpdate(sql);
			System.out.println("Table genre_connect created");
			
			sql = "CREATE TABLE IF NOT EXISTS directors(movie_id INT UNSIGNED, director_id INT UNSIGNED, name VARCHAR(50))";
			stmt.executeUpdate(sql);
			System.out.println("Table directors created");
			
			
	
	        
	        BufferedReader movies = new BufferedReader(new FileReader("movies.csv"));
	        BufferedReader keywords = new BufferedReader(new FileReader("keywords.csv"));
	        BufferedReader keywordconnect = new BufferedReader(new FileReader("keyword_connect.csv"));
	        BufferedReader genres = new BufferedReader(new FileReader("genres.csv"));
	        BufferedReader genreconnect = new BufferedReader(new FileReader("genre_connect.csv"));
	        BufferedReader actors = new BufferedReader(new FileReader("actors.csv"));
	        BufferedReader actorconnect = new BufferedReader(new FileReader("actor_connect.csv"));
	        BufferedReader directors = new BufferedReader(new FileReader("directors.csv"));

	        conn.setAutoCommit(false);
	        String row;
		    String[] string;
		    
		    row = null;
	        int pp = 0;
	        while ((row = movies.readLine()) != null) {
	        	string = row.split(",");
	        	sql = "INSERT INTO movies VALUES (?, ?, ?, ?)";
	        	PreparedStatement pstmt = conn.prepareStatement(sql);
	        	pstmt.clearParameters();
	        	if (string.length == 4) {
                    pstmt.setInt(1, Integer.parseInt(string[0]));
                    pstmt.setString(2, string[1].replaceAll("^\"|\"$", ""));
                    pstmt.setDouble(3, Double.parseDouble(string[2]));
                    pstmt.setInt(4, Integer.parseInt(string[3]));
                } else if (string.length == 5) {
                    pstmt.setInt(1, Integer.parseInt(string[0]));
                    pstmt.setString(2, string[1].replaceAll("^\"|\"$", "") + string[2].replaceAll("^\"|\"$", ""));
                    pstmt.setDouble(3, Double.parseDouble(string[3]));
                    pstmt.setInt(4, Integer.parseInt(string[4]));
                } else if (string.length == 6) {
                    pstmt.setInt(1, Integer.parseInt(string[0]));
                    pstmt.setString(2, string[1].replaceAll("^\"|\"$", "") + string[2].replaceAll("^\"|\"$", "") + string[3].replaceAll("^\"|\"$", ""));
                    pstmt.setDouble(3, Double.parseDouble(string[4]));
                    pstmt.setInt(4, Integer.parseInt(string[5]));
                } else if (string.length == 7) {
                    pstmt.setInt(1, Integer.parseInt(string[0]));
                    pstmt.setString(2, string[1].replaceAll("^\"|\"$", "") + string[2].replaceAll("^\"|\"$", "") + string[3].replaceAll("^\"|\"$", "") + string[4].replaceAll("^\"|\"$", ""));
                    pstmt.setDouble(3, Double.parseDouble(string[5]));
                    pstmt.setInt(4, Integer.parseInt(string[6]));

                }
                pstmt.executeUpdate();
    	        pstmt.close();
	        } 
	        conn.commit();
	        movies.close();
	        System.out.println("Movies filled");
		    
	        row = null;
	        while ((row = keywords.readLine()) != null) {
	        	string = row.split(",");
	        	if(pp == 0 ){
	        		pp++;
	        		continue;
	        	}
	        	int keyid = Integer.parseInt(string[0]);
	        	String name = string[1].replaceAll("^\"|\"$", "");
	        	sql = "INSERT INTO keywords VALUES (?, ?)";
	        	PreparedStatement pstmt = conn.prepareStatement(sql);
	        	pstmt.clearParameters();
	        	pstmt.setInt(1, keyid);
	        	pstmt.setString(2, name);
	        	pstmt.executeUpdate();
	        	pstmt.close();	
	        } 
	        conn.commit();
	        keywords.close();
	        System.out.println("Keywords filled");
	        
	        row = null;
	        pp = 0;
	        while ((row = keywordconnect.readLine()) != null) {
	        	string = row.split(",");
	        	if(pp == 0 ){
	        		pp++;
	        		continue;
	        	}
	        	int movieid = Integer.parseInt(string[0]);
	        	int keyid = Integer.parseInt(string[1]);
	        	sql = "INSERT INTO keyword_connect VALUES (?, ?)";
	        	PreparedStatement pstmt = conn.prepareStatement(sql);
	        	pstmt.clearParameters();
	        	pstmt.setInt(1, movieid);
	        	pstmt.setInt(2, keyid);
	        	pstmt.executeUpdate();
	        	pstmt.close();	
	        } 
	        conn.commit();
	        keywordconnect.close();
	        System.out.println("Keyword_connect filled");
	        
	        row = null;
	        pp = 0;
	        while ((row = genres.readLine()) != null) {
	        	string = row.split(",");
	        	if(pp == 0 ){
	        		pp++;
	        		continue;
	        	}
	        	int genreid = Integer.parseInt(string[0]);
	        	String name = string[1].replaceAll("^\"|\"$", "");
	        	sql = "INSERT INTO genres VALUES (?, ?)";
	        	PreparedStatement pstmt = conn.prepareStatement(sql);
	        	pstmt.clearParameters();
	        	pstmt.setInt(1, genreid);
	        	pstmt.setString(2, name);
	        	pstmt.executeUpdate();
	        	pstmt.close();	
	        } 
	        conn.commit();
	        genres.close();
	        System.out.println("Genres filled");
	        
	        row = null;
	        pp = 0;
	        while ((row = genreconnect.readLine()) != null) {
	        	string = row.split(",");
	        	if(pp == 0 ){
	        		pp++;
	        		continue;
	        	}
	        	int movieid = Integer.parseInt(string[0]);
	        	int genreid = Integer.parseInt(string[1]);
	        	sql = "INSERT INTO genre_connect VALUES (?, ?)";
	        	PreparedStatement pstmt = conn.prepareStatement(sql);
	        	pstmt.clearParameters();
	        	pstmt.setInt(1, movieid);
	        	pstmt.setInt(2, genreid);
	        	pstmt.executeUpdate();
	        	pstmt.close();	
	        } 
	        conn.commit();
	        genreconnect.close();
	        System.out.println("Genre_connect filled");
	        
	        row = null;
	        pp = 0;
	        while ((row = actorconnect.readLine()) != null) {
	        	string = row.split(",");
	        	if(pp == 0 ){
	        		pp++;
	        		continue;
	        	}
	        	int movieid = Integer.parseInt(string[0]);
	        	int actorid = Integer.parseInt(string[1]);
	        	sql = "INSERT INTO actor_connect VALUES (?, ?)";
	        	PreparedStatement pstmt = conn.prepareStatement(sql);
	        	pstmt.clearParameters();
	        	pstmt.setInt(1, movieid);
	        	pstmt.setInt(2, actorid);
	        	pstmt.executeUpdate();
	        	pstmt.close();	
	        } 
	        conn.commit();
	        actorconnect.close();
	        System.out.println("Actor_connect filled");
	        
	        row = null;
	        pp = 0;
	        while ((row = actors.readLine()) != null) {
	        	string = row.split(",");
	        	if(pp == 0 ){
	        		pp++;
	        		continue;
	        	}
	        	int actorid = Integer.parseInt(string[0]);
	        	String name = string[1].replaceAll("^\"|\"$", "");
	        	sql = "INSERT INTO actors VALUES (?, ?)";
	        	PreparedStatement pstmt = conn.prepareStatement(sql);
	        	pstmt.clearParameters();
	        	pstmt.setInt(1, actorid);
	        	pstmt.setString(2, name);
	        	pstmt.executeUpdate();
	        	pstmt.close();	
	        } 
	        conn.commit();
	        actors.close();
	        System.out.println("Actors filled");
	        
	        row = null;
	        pp = 0;
	        while ((row = directors.readLine()) != null) {
	        	string = row.split(",");
	        	if(pp == 0 ){
	        		pp++;
	        		continue;
	        	}
	        	//System.out.println(string[0] + string[1] + string[2]);
	        	int movieid = Integer.parseInt(string[0]);
	        	int directorid = Integer.parseInt(string[1]);
	        	String name = string[2].replaceAll("^\"|\"$", "");
	        	sql = "INSERT INTO directors VALUES (?, ?, ?)";
	        	PreparedStatement pstmt = conn.prepareStatement(sql);
	        	pstmt.clearParameters();
	        	pstmt.setInt(1, movieid);
	        	pstmt.setInt(2, directorid);
	        	pstmt.setString(3, name);
	        	pstmt.executeUpdate();
	        	pstmt.close();	
	        } 
	        conn.commit();
	        directors.close();
	        System.out.println("Directors filled");
       
	        
	        PreparedStatement stmtx = conn.prepareStatement("SELECT * FROM movies");
	        PreparedStatement stmty = null;
	        ResultSet output = stmtx.executeQuery();
	        ResultSet output2;
			String sql2;
			String sql3;
			String sql4;
			String sql5;
			String userinput;
			String movidnum = null;
			String genreid = null;
			String keyid = null;
			String actorid = null;
			String directorid = null;
			ArrayList<Movie> movielist = new ArrayList<>();
	        	while (output.next()) {
	        		Movie film = new Movie();
	        		film.movieid = output.getString("movie_id");
	        		film.name = output.getString("movie_name");
	        		film.popularity = output.getDouble("popularity");
	        		film.vote = output.getInt("vote");
	        		ArrayList<String> movarr = new ArrayList<>();
	        		ArrayList<String> genrearr = new ArrayList<>();
	        		ArrayList<String> keyarr = new ArrayList<>();
	        		ArrayList<String> actorarr = new ArrayList<>();
	        		ArrayList<String> directorarr = new ArrayList<>();
	        		
	        		sql3 = "SELECT keyword_connect.keyword_id FROM movies INNER JOIN keyword_connect" +
							" ON movies.movie_id = keyword_connect.movie_id" +
							" AND movies.movie_id = " + film.movieid + ";";
					ResultSet myRS3 = stmt.executeQuery(sql3);
					if (myRS3.next()) {
						do {
						//System.out.println("Keyword IDs: " + myRS3.getString("keyword_id"));
						keyid = myRS3.getString("keyword_id");
						keyarr.add(keyid);
						} while (myRS3.next());
					}
					
					sql2 = "SELECT genre_connect.genre_id FROM movies INNER JOIN genre_connect" +
							" ON movies.movie_id = genre_connect.movie_id" +
							" AND movies.movie_id = " + film.movieid + ";";
					ResultSet myRS2 = stmt.executeQuery(sql2);
					if (myRS2.next()) {
						do {
						//System.out.println("Genre IDs: " + myRS2.getString("genre_id"));
						genreid = myRS2.getString("genre_id");
						genrearr.add(genreid);
						} while (myRS2.next());
					}
					
					sql4 = "SELECT actor_connect.actor_id FROM movies INNER JOIN actor_connect" +
							" ON movies.movie_id = actor_connect.movie_id" +
							" AND movies.movie_id = " + film.movieid + ";";
					ResultSet myRS4 = stmt.executeQuery(sql4);
					if (myRS4.next()) {
						do {
						//System.out.println("Actor IDs: " + myRS4.getString("actor_id"));
						actorid = myRS4.getString("actor_id");
						actorarr.add(actorid);
						} while (myRS4.next());
					}
					
					sql5 = "SELECT directors.director_id FROM movies INNER JOIN directors" +
							" ON movies.movie_id = directors.movie_id" +
							" AND movies.movie_id = " + film.movieid + ";";
					ResultSet myRS5 = stmt.executeQuery(sql5);
					if (myRS5.next()) {
						do {
						//System.out.println("Director IDs: " + myRS5.getString("director_id"));
						directorid = myRS5.getString("director_id");
						directorarr.add(directorid);
						} while (myRS5.next());
					}
	        	film.genre_id = genrearr;
	        	film.key_id = keyarr;
	        	film.actor_id = actorarr;
	        	film.director_id = directorarr;
	        	
	        	movielist.add(film);
	        		
	        	}
	        	scanner = new Scanner(System.in);
	        	Movie userfilm = null;
	        	while(true) {
					Menu();
					found = false;
					userinput = scanner.nextLine();
					if(userinput.equals("q")){
						System.out.println("Exiting program");
						sql = "DROP DATABASE moviesdb";
						stmt.executeUpdate(sql);
						System.exit(0);
						
					}
					for (int i=0;i<movielist.size();i++){
						if (userinput.equals(movielist.get(i).name)){
							userfilm = movielist.get(i);
							found = true;
							break;
						}
					}
					if (found == true){
						for(int i=0;i<movielist.size();i++){
							Movie listfilm = movielist.get(i);
							for(int j=0;j<userfilm.genre_id.size();j++){
								for(int k = 0;k < listfilm.genre_id.size();k++){
									if(userfilm.genre_id.get(j).equals(listfilm.genre_id.get(k))){
										listfilm.score++;
									}
								}
								
							}
							for(int j=0;j<userfilm.key_id.size();j++){
								for(int k = 0;k < listfilm.key_id.size();k++){
									
									if(userfilm.key_id.get(j).equals(listfilm.key_id.get(k))){
										listfilm.score++;
									}
								}
								
							}
							for(int j=0;j<userfilm.actor_id.size();j++){
								for(int k = 0;k < listfilm.actor_id.size();k++){
									
									if(userfilm.actor_id.get(j).equals(listfilm.actor_id.get(k))){
										listfilm.score++;
									}
								}
								
							}
							for(int j=0;j<userfilm.director_id.size();j++){
								for(int k = 0;k < listfilm.director_id.size();k++){
									
									if(userfilm.genre_id.get(j).equals(listfilm.director_id.get(k))){
										listfilm.score++;
									}
								}
								
							}
						}
						Collections.sort(movielist, (o1, o2) -> o1.getscore() - o2.getscore());
						int movieCount = 0;
						for(int z = movielist.size() - 1; z >= 0; z--) {
							if( movielist.get(z).name.equals(userfilm.name)){
								movielist.get(z).score = 0;
							}
							else if(movieCount < 5){
								System.out.println(movielist.get(z).name + " score = " + movielist.get(z).score);
								movielist.get(z).score = 0;
								movieCount++;
							}
							else{
								movielist.get(z).score = 0;
							}
							
						}
					}
					
					else
						System.out.println("ERROR MOVIE NOT FOUND");
					
	        	
	        	}
	}
	catch(SQLException se)
	{
	System.out.println("SQL Exception.");
	se.printStackTrace();
	}
	catch(Exception e)
	{
	e.printStackTrace();
	}
	finally
	{ 
	try
	{
	if(conn!=null){
	conn.close();
	}
	}
	catch(SQLException se){
	se.printStackTrace();
	} 
	}

	System.out.println("Run complete. Shutting down.");
	}	
}
