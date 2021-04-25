package database;

import java.sql.Connection;
import java.sql.SQLException;

public interface MaterialDAO
{
  /**
  * Creates a new Material entry in the Database. Method to be used when creating every concrete type of material.
   * @return int returns the autogenerated materialID for the new Material as an int since Material will never be instantiated.
  * */
  int create( String title,
      String publisher, String releaseDate, String description, String tags,
      String targetAudience, String language) throws SQLException;
  void create(String title, String publisher, String releaseDate, String description, String tags,
      String targetAudience, String language, Connection connection);

  boolean materialExistInDB(int materialID) throws SQLException;
//  Material findByID(int id);
}