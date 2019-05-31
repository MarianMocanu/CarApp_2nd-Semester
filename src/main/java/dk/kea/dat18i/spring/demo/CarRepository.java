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
public class CarRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public Car findCar(int id){
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM cars WHERE id = "+id);
        Car car = new Car();
        while (rs.next()){
            car.setId(rs.getInt("id"));
            car.setBrand(rs.getString("brand"));
            car.setColor(rs.getString("color"));
            car.setMaxSpeed(rs.getDouble("max_speed"));
            car.setReg(rs.getString("reg"));
        }
        return car;
    }



    public List<Car> findAllCars(){
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM cars");
        List<Car> carList = new ArrayList<>();
        while (rs.next()){
            Car car = new Car();
            car.setId(rs.getInt("id"));
            car.setBrand(rs.getString("brand"));
            car.setColor(rs.getString("color"));
            car.setMaxSpeed(rs.getDouble("max_speed"));
            car.setReg(rs.getString("reg"));

            carList.add(car);
        }
        return carList;
    }

    public Car insertCar(Car car){

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO cars values (NULL, ?,?,?,?);", new String[]{"id"});
                ps.setString(1, car.getReg());
                ps.setString(2,car.getBrand());
                ps.setString(3,car.getColor());
                ps.setDouble(4,car.getMaxSpeed());
                return ps;
            }
        };

        KeyHolder id = new GeneratedKeyHolder();
        jdbc.update(psc,id);

        car.setId(id.getKey().intValue());
        return car;
    }

    public void delete(int id){
        jdbc.update("delete from cars where id =" +id);
    }

    public void edit(int id, Car car) {
        jdbc.update("UPDATE cars SET reg = '"+car.getReg()+"', brand = '"+car.getBrand()+"' , color = '"+car.getColor()+"', max_speed = "+car.getMaxSpeed()+" WHERE id="+id ) ;
    }


}
