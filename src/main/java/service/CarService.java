package service;

import DAO.CarDao;
import DAO.DailyReportDao;
import model.Car;
//import org.eclipse.jetty.server.session.JDBCSessionManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
//import org.hibernate.engine.spi.SessionDelegatorBaseImpl;
//import sun.security.pkcs11.Secmod;
import util.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class CarService {

    private static CarService carService;

    private SessionFactory sessionFactory;

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }


    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        try {
            Session session =  sessionFactory.openSession();
            CarDao carDao = new CarDao(session);
            return carDao.getAllCars();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cars;
    }

    public boolean addCar(String brand, String model, String licensePlate, Long price) {
        try {
            Session session =  sessionFactory.openSession();
            CarDao carDao = new CarDao(session);
            return carDao.addCar( brand, model, licensePlate, price);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean buyCar(String brand, String model, String licensePlate) {
        try {
            Session session =  sessionFactory.openSession();
            CarDao carDao = new CarDao(session);
            return carDao.buyCar( brand, model, licensePlate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deleteAllCars() {
        new CarDao(sessionFactory.openSession()).deleteAllCars();
    }
}
