package dk.kea.dat18i.spring.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CarController {

    @Autowired
    private CarRepository carRepo;

    @Autowired
    private OwnerRepository ownerRepo;

    @Autowired
    private CarsOwnersRepository carsOwnersRepo;


    @GetMapping("/carview")
    public String car(Model model){
        List<Car> carList = carRepo.findAllCars();
        Map<Integer, List<Owner>> ownersForCar = new HashMap<>();
        for(Car car: carList) {
            ownersForCar.put(car.getId(), ownerRepo.findAllOwnersByCarId(car.getId()));
        }
        model.addAttribute("cars", carList);
        model.addAttribute("ownersForCar", ownersForCar);
        return "show-car";
    }

    @GetMapping("/addcar")
    public String addCar(Model m) {
        m.addAttribute("carform", new CarForm());
        m.addAttribute("ownerList", ownerRepo.findAllOwners());
        return "add-car";
    }

    @PostMapping("/savecar")
    public String saveCar(@ModelAttribute CarForm carForm){
        Car newCar = new Car();
        newCar.setId(carForm.getCarId());
        newCar.setReg(carForm.getReg());
        newCar.setBrand(carForm.getBrand());
        newCar.setColor(carForm.getColor());
        newCar.setMaxSpeed(carForm.getMaxSpeed());
        int id = carRepo.insertCar(newCar).getId();
        carsOwnersRepo.insertAssociations(id, carForm.getOwners());

        return "redirect:/carview";
    }

    @GetMapping("/deleteCar/{id}")
    public String deleteCar(@PathVariable ("id") int id){
        carsOwnersRepo.removeAssociations(id);
        carRepo.delete(id);

        return "redirect:/carview";
    }

    @GetMapping("/editCar/{id}")
    public String showCurrentCar(@PathVariable ("id") int id, Model model){

        Car car = carRepo.findCar(id);
        CarForm carForm = new CarForm(car.getId(),car.getReg(),car.getBrand(),car.getColor(),car.getMaxSpeed(),ownerRepo.findAllOwnersIdByCarId(id));
        model.addAttribute("carModel", carForm);
        model.addAttribute("ownerList", ownerRepo.findAllOwners());
        return "edit-car";
    }

   @PostMapping("/editCar/{id}")
    public String finalCar(@PathVariable("id") int id, @ModelAttribute CarForm  carForm ){
       Car newCar = new Car();
       newCar.setId(carForm.getCarId());
       newCar.setReg(carForm.getReg());
       newCar.setBrand(carForm.getBrand());
       newCar.setColor(carForm.getColor());
       newCar.setMaxSpeed(carForm.getMaxSpeed());

       carRepo.edit(id,newCar);
       carsOwnersRepo.removeAssociations(id);
       carsOwnersRepo.insertAssociations(id, carForm.getOwners());
       return "redirect:/carview";
    }

    @RequestMapping(value ="/carv")
    public Car showCar(){
        Car car = carRepo.findCar(1);
        return car;
    }

}
