package database.materialcreator;

import shared.person.MaterialCreator;
import database.BaseDAO;

import java.sql.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Material creator data access object implementation
 *
 * @author Kutaiba
 * @version 1.0
 */
public class MaterialCreatorImpl extends BaseDAO implements MaterialCreatorDAO
{
  private static MaterialCreatorDAO instance;
  private static final Lock lock = new ReentrantLock();

  private MaterialCreatorImpl()
  {
  }

  public static MaterialCreatorDAO getInstance()
  {
    if (instance == null)
    {
      synchronized (lock)
      {
        if (instance == null)
        {
          instance = new MaterialCreatorImpl();
        }
      }
    }
    return instance;
  }

  @Override public synchronized MaterialCreator create(String fName, String lName,
      String dob, String country) throws SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement stm = connection.prepareStatement(
          "INSERT INTO material_creator (f_name, l_name, dob, country) VALUES(?,?,?,?)",
          PreparedStatement.RETURN_GENERATED_KEYS);
      stm.setString(1, fName);
      stm.setString(2, lName);
      stm.setDate(3, Date.valueOf(dob));
      stm.setString(4, country);
      stm.executeUpdate();
      ResultSet keys = stm.getGeneratedKeys();
      keys.next();
      connection.commit();
      return new MaterialCreator(keys.getInt(1) ,fName, lName, dob, country);
    }
  }

  @Override public int getCreatorId(String fName, String lName, String dob,
      String country) throws SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement stm = connection.prepareStatement(
          "SELECT person_id from material_creator WHERE f_name = ? AND l_name = ? AND dob = ? AND country = ?");
      stm.setString(1, fName);
      stm.setString(2, lName);
      stm.setDate(3, Date.valueOf(dob));
      stm.setString(4, country);
      ResultSet result = stm.executeQuery();
      if (result.next())
      {
        return result.getInt(1);
      }
      else
      {
        return -1;
      }
    }
  }
}
