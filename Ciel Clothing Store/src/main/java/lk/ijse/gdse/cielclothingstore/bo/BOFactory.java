package lk.ijse.gdse.cielclothingstore.bo;

import lk.ijse.gdse.cielclothingstore.bo.custom.CustomerBO;
import lk.ijse.gdse.cielclothingstore.bo.custom.impl.*;

public class BOFactory {

    private static BOFactory boFactory;
    private BOFactory() {}
    public static BOFactory getInstance() {
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOType {
        CUSTOMER, EMPLOYEE, ORDER_DETAILS, ORDERS, PAYMENT, PRODUCT, PRODUCT_DETAILS, SUPPLIER, USERACCOUNT
    }

    public SuperBO getBO(BOType type) {
        switch (type) {
            case CUSTOMER:
                return new CustomerBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case ORDER_DETAILS:
                return new OrderDetailsBOImpl();
            case ORDERS:
                return new OrderBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case PRODUCT:
                return new ProductBOImpl();
            case PRODUCT_DETAILS:
                return new ProductDetailsBOImpl();
            case SUPPLIER:
                return new SuppilerBOImpl();
            case USERACCOUNT:
                return new UserBOImpl();
            default:
                return null;
        }
    }
}
