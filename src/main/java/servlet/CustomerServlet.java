package servlet;

import com.google.gson.Gson;
import model.Car;
import service.CarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String json = gson.toJson(CarService.getInstance().getAllCars());
    }
//Каждый день в салон поступают машины разных марок. Машин одной марки может быть не более 10ти штук.
//Покупатели могут запросить список имеющихся машин по url "/customer" используя GET запрос.
//Купить машину можно отправив POST запрос на тот же url, и передав в параметрах марку машины, название машины и госномер.

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");
        String message = "";
        try {
            message = CarService.getInstance().buyCar(brand, model, licensePlate) ? "Byu successful" : "Buy failed!";
        } catch (Exception e) {
            message = "Buy failed! with error = "+ e.getLocalizedMessage();
            e.printStackTrace();
        }
        System.out.println(message);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
