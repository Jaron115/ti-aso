package Backend;

import java.lang.Class;
import java.sql.*;


public class DbConnection
{
    private Connection connection;
    private Statement statement;
    private String cm = "";

    private void Initialize()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://sql.jaron115.nazwa.pl/jaron115_1", "jaron115_1", "bazaTo!2#4");
            statement = connection.createStatement();
        }
        catch(Exception ex)
        {
            System.out.println("Error : " + ex);
        }
    }

    public ResultSet Select(String select, String from, String where) throws SQLException
    {
        Initialize();


        if(select.isEmpty())
            select = "*";

        if(where.isEmpty())
            where = "1";


        cm = "SELECT " + select + " FROM " + from + " WHERE " + where;


        return statement.executeQuery(cm);

    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public void Insert(String into, String column, String value) {
        try {
            Initialize();

            cm = "INSERT INTO " + into + " (" + column + ") VALUES (" + value + ")";
            statement.executeUpdate(cm);

            statement.close();
            connection.close();
        }
        catch(SQLException ex)
        {
            System.out.println("Error : " + ex);
        }
    }

    public void Update(String update, String set, String where) {

        try {
            Initialize();
            cm = "UPDATE " + update + " SET " + set + " WHERE " + where;

            statement.executeUpdate(cm);

            connection.close();
        }
        catch(SQLException ex) {
            System.out.println("Error : " + ex);
        }
    }

    public void Delete(String from, String where) {
        try {
            Initialize();
            cm = "DELETE FROM " + from + " WHERE " + where;

            statement.executeUpdate(cm);
        } catch(SQLException ex) {
            System.out.println("Error : " + ex);
        }
    }

}
