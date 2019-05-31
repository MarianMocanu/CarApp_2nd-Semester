package dk.kea.dat18i.spring.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OwnerRepository {

  @Autowired
  private JdbcTemplate jdbc;

  public List<Owner> findAllOwnersByCarId(int id) {
    SqlRowSet rs = jdbc.queryForRowSet("SELECT owners.id, owners.first_name, owners.last_name, owners.phone_no FROM " +
        "owners INNER JOIN cars_owners " +
        "ON cars_owners.owner_id = owners.id " +
        "WHERE car_id =" + id);
    List<Owner> ownersList = new ArrayList<>();
    while (rs.next()) {
      Owner owner = new Owner();
      owner.setId(rs.getInt("id"));
      owner.setFirstName(rs.getString("first_name"));
      owner.setLastName(rs.getString("last_name"));
      owner.setPhoneNo(rs.getString("phone_no"));
      ownersList.add(owner);
    }
    return ownersList;
  }

  public List<Integer> findAllOwnersIdByCarId(int id) {
    List<Integer> ownersId = new ArrayList<>();
    List<Owner> owners = findAllOwnersByCarId(id);
    for (Owner owner : owners) {
      ownersId.add(owner.getId());
    }
    return ownersId;
  }

  public Owner addNewOwner(Owner owner) {
    PreparedStatementCreator psc = new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO owners values (NULL, ?,?,?);", new String[]{"id"});
        ps.setString(1, owner.getFirstName());
        ps.setString(2, owner.getLastName());
        ps.setString(3, owner.getPhoneNo());
        return ps;
      }
    };
    KeyHolder ownerId = new GeneratedKeyHolder();
    jdbc.update(psc, ownerId);

    owner.setId(ownerId.getKey().intValue());

    return owner;
  }

  public List<Owner> findAllOwners() {
    SqlRowSet rs = jdbc.queryForRowSet("select * from owners");
    List<Owner> ownersList = new ArrayList<>();
    while (rs.next()) {
      Owner owner = new Owner();
      owner.setId(rs.getInt("id"));
      owner.setFirstName(rs.getString("first_name"));
      owner.setLastName(rs.getString("last_name"));
      owner.setPhoneNo(rs.getString("phone_no"));
      ownersList.add(owner);
    }
    return ownersList;
  }
}
