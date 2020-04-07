package servlet;

import service.CarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProducerServlet extends HttpServlet {

    //Новые поступления происходят в течение дня. Каждое новое поступление - это POST запрос на url "/producer".
    //На POST запрос, отправленный на url "/producer" с параметрами, по названию аналогичными полям класса Car,
    //нужно ответить 200-ым статусом, если машина принята, и 403-ым, если нет.
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");
        String price = req.getParameter("price");
        String message = "";
        try {
            if(CarService.getInstance().addCar(brand, model, licensePlate, Long.valueOf(price)))
            {
                resp.setStatus(HttpServletResponse.SC_OK);
            }else{
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
            System.out.println("Add car successful");
        } catch (Exception e) {
            System.out.println("Car not add");
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
