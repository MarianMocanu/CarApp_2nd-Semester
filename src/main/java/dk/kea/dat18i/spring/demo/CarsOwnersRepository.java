package dk.kea.dat18i.spring.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Statement;
import java.util.List;

@Repository
public class CarsOwnersRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public void insertAssociations(int carId, List<Integer> owners){
        for(int owner:owners){
            jdbc.update("INSERT INTO cars_owners values(NULL," + carId + ", " + owner + ")");
        }
    }

    public void removeAssociations(int carId){
        jdbc.update("DELETE FROM cars_owners WHERE car_id=" + carId);
    }
}
