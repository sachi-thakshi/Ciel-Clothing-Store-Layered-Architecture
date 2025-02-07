package lk.ijse.gdse.cielclothingstore.dao;

import lk.ijse.gdse.cielclothingstore.dao.custom.*;
import lk.ijse.gdse.cielclothingstore.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {
    }
    public static DAOFactory getInstance() {
        return daoFactory==null?daoFactory=new DAOFactory():daoFactory;
    }

    public enum DAOType {
        CUSTOMER, EMPLOYEE, ORDER_DETAILS, ORDERS, PAYMENT, PRODUCT, PRODUCT_DETAILS, SUPPLIER, USERACCOUNT, QUERY
    }

    public SuperDAO getDAO(DAOType type) {
        switch (type) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case ORDER_DETAILS:
                return new OrderDetailsDAOImpl();
            case ORDERS:
                return new OrderDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case PRODUCT:
                return new ProductDAOImpl();
            case PRODUCT_DETAILS:
                return new ProductDetailsDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case USERACCOUNT:
                return new UserDAOImpl();
           case QUERY:
               return new QueryDAOImpl();
           default:
               return null;
        }
    }
}
