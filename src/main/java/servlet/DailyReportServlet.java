package servlet;

import model.DailyReport;
import service.CarService;
import service.DailyReportService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DailyReportServlet extends HttpServlet {
//Начальство может потребовать отчет GET запросом по url "/report/last" за прошедший день, либо же за все дни,
//отправив GET запрос на "/report/all".  Так же есть возможность удалить все данные об отчетах и машинах,
//отправив DELETE запрос на url "/report".
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo().contains("all")) {
            DailyReportService.getInstance().getAllDailyReports();
        } else if (req.getPathInfo().contains("last")) {
            DailyReportService.getInstance().getLastReport();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doDelete(req, resp);
        List<DailyReport> reports =  DailyReportService.getInstance().getAllDailyReports();
        CarService.getInstance().deleteAllCars();
        DailyReportService.getInstance().deleteAllDailyReports();

    }
}
