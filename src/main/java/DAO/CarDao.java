package DAO;

import model.Car;
import model.DailyReport;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public static  Long money = Long.valueOf(0);
    public static  Long cars = Long.valueOf(0);
    public static void setMoneyNull() {
        money = Long.valueOf(0);
    }
    public static void setCarsNull() {
        cars = Long.valueOf(0);
    }
    public boolean addCar(String brand, String model, String licensePlate, Long price) {

        try {
            Transaction transaction = session.beginTransaction();
            Car car = new Car();
            car.setBrand(brand);
            car.setModel(model);
            car.setLicensePlate(licensePlate);
            car.setPrice(price);
            session.save(car);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Car> getAllCars() {
        List<Car> carList = new ArrayList<>();
        try {
            Transaction transaction = session.beginTransaction();
            carList = session.createQuery("FROM Car").list();
            transaction.commit();
            session.close();
            return carList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean buyCar(String brand, String model, String licensePlate) {
        try {
            Car car = getCarByParametrs(brand, model, licensePlate);
            if (car != null) {
                Transaction transaction = session.beginTransaction();
                money = money + car.getPrice();
                session.delete(car);
                transaction.commit();
                session.close();
                cars++;

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Car getCarByParametrs(String brand, String model, String licensePlate) {
//        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Car.class);
        Car car = (Car) criteria.add(Restrictions.eq("brand", brand))
                .add(Restrictions.eq("model", model))
                .add(Restrictions.eq("licensePlate", licensePlate))
                .uniqueResult();
        if (car != null) {
            return car;
        }
        return null;
    }

    public void deleteAllCars() {
        Transaction transaction = session.beginTransaction();
        session.createQuery("DELETE FROM Car").executeUpdate();
        transaction.commit();
        session.close();
    }
}
