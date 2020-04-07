package DAO;

import model.DailyReport;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
//import org.hibernate.criterion.Projections;
//import org.hibernate.criterion.Restrictions;

import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public List<DailyReport> getAllDailyReport() {
        Transaction transaction = session.beginTransaction();
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        transaction.commit();
        session.close();
        return dailyReports;
    }

    public DailyReport getLastReport() {
        Transaction transaction = session.beginTransaction();
//        DailyReport dailyReport = session.createQuery("SELECT *  FROM DailyReport WHERE MAX(id)").list();
        Criteria criteria = session.createCriteria(DailyReport.class);
        criteria.addOrder(Order.desc("id"));
        criteria.setMaxResults(1);
        DailyReport result = (DailyReport) criteria.uniqueResult();
        transaction.commit();
        session.close();
        return result;
    }

    public void deleteAllDailyReport() {
        Transaction transaction = session.beginTransaction();
        session.createQuery("DELETE FROM DailyReport").executeUpdate();
        transaction.commit();
        session.close();
    }

    public void addNew() {
        Transaction transaction = session.beginTransaction();
        DailyReport dailyReport = new DailyReport();
        dailyReport.setEarnings(CarDao.money);
        dailyReport.setSoldCars(CarDao.cars);
        CarDao.setCarsNull();
        CarDao.setMoneyNull();
        session.save(dailyReport);
        transaction.commit();
        session.close();
    }
}
