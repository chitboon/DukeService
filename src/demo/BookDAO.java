package demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by chitboon on 10/23/15.
 */
public class BookDAO {

    Connection con;
    // Database configuration
    public static String url = "jdbc:mysql://localhost/test";
    public static String dbdriver = "com.mysql.jdbc.Driver";
    public static String username = "root";
    public static String password = "mysql";
    private Random random = new Random();

    // constructor to load the jdbc driver, exception will be thrown if database driver is not found
    public BookDAO() {
        try {
            Class.forName(dbdriver);
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // this is to make sure that connection is not null when you used it
    public void getConnection() throws SQLException {
        if (con == null) con = DriverManager.getConnection(url, username, password);
    }

    // Retrieve book details based on bookId, null is returned if book is not found
    public BookDetail getBookDetails(String bookId) {
        String sql = "select * from books where id = ?";
        BookDetail book = null;
        try {
            getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,bookId);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null && rs.next()) {
                book = new BookDetail();
                book.setBookId(rs.getString("id"));
                book.setDescription(rs.getString("description"));
                book.setFirstName(rs.getString("first_name"));
                book.setInventory(rs.getInt("inventory"));
                book.setOnSale(rs.getBoolean("onSale"));
                book.setPrice(rs.getFloat("price"));
                book.setSurname(rs.getString("surname"));
                book.setTitle(rs.getString("title"));
                book.setYear(rs.getInt("calendar_year"));
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    // Get a random book from database
    public BookDetail getBook() {
        List<BookDetail> list = getAllBook();
        int i = random.nextInt(list.size());
        return list.get(i);
    }

    public int add(BookDetail book) {
        int i =0;
        String sql = "insert into books (" +
                "id, first_name, surname, description, inventory, onSale, price, title, calendar_year) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, book.getBookId());
            pstmt.setString(2, book.getFirstName());
            pstmt.setString(3, book.getSurname());
            pstmt.setString(4, book.getDescription());
            pstmt.setInt(5, book.getInventory());
            pstmt.setBoolean(6, book.isOnSale());
            pstmt.setDouble(7, book.getPrice());
            pstmt.setString(8, book.getTitle());
            pstmt.setInt(9, book.getYear());
            i = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    // Retrieve all the books from database
    public List<BookDetail> getAllBook() {
        String sql = "select * from books";
        ArrayList<BookDetail> list = new ArrayList<BookDetail>();
        try {
            getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs != null && rs.next()) {
                BookDetail book = new BookDetail();
                book.setBookId(rs.getString("id"));
                book.setDescription(rs.getString("description"));
                book.setFirstName(rs.getString("first_name"));
                book.setInventory(rs.getInt("inventory"));
                book.setOnSale(rs.getBoolean("onSale"));
                book.setPrice(rs.getFloat("price"));
                book.setSurname(rs.getString("surname"));
                book.setTitle(rs.getString("title"));
                book.setYear(rs.getInt("calendar_year"));
                list.add(book);
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
