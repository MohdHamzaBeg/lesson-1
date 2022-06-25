package model;

// This class is the BTS of using and operating on Database and it is used by a user through
// App.java class

// IMPORTANT NOTE- The connection should be made with Database class in every method, not with the
// TestDatabase class. Use TestDatabase class only during testing
import java.sql.SQLException;
import java.util.Optional;

public class UserDao implements Dao {

	@Override
	public void save(Object t) {

		try {
			var conn = SwingDatabase.ins().getconnection();
			var stmt = conn.prepareStatement("insert into user (name, password) values (?,?)");

			stmt.setString(1, ((User) t).getName());
			stmt.setString(2, ((User) t).getPassword());

			stmt.executeUpdate();

			stmt.close();
		} catch (SQLException e) {
			throw new DaoExceptions(e);
		}
	}

	@Override
	public void update(Object t) {

		try {
			var conn = SwingDatabase.ins().getconnection();
			var stmt = conn.prepareStatement("Update user set name=?, password=? where id=?");
			stmt.setString(1, ((User) t).getName());
			stmt.setString(2, ((User) t).getPassword());
			stmt.setInt(3, ((User) t).getId());
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			throw new DaoExceptions(e);
		}
	}

	@Override
	public void delete(Object t) {

		try {
			var conn = SwingDatabase.ins().getconnection();
			var stmt = conn.prepareStatement("delete from user where id=?");
			stmt.setInt(1, ((User) t).getId()); // setting first value of id=1
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			throw new DaoExceptions(e);
		}

	}

	@Override
	public Optional findbyId(int id) {

		try {
			var conn = SwingDatabase.ins().getconnection();
			var stmt = conn.prepareStatement("select name, password from user where id=?");
			stmt.setInt(1, id);
			var rs = stmt.executeQuery();
			if (rs.next()) {
				var name = rs.getString("name");
				var password = rs.getString("password");
				User user = new User(id, name, password);
				return Optional.of(user);
			}

			stmt.close();
		} catch (SQLException e) {
			throw new DaoExceptions(e);
		}
		return Optional.empty();
	}

}
