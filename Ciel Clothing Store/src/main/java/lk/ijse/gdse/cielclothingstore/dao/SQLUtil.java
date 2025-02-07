package lk.ijse.gdse.cielclothingstore.dao;

import lk.ijse.gdse.cielclothingstore.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUtil {

//    public static <T> T execute(String sql, Object... args) throws SQLException, ClassNotFoundException {
//        Connection connection = DBConnection.getInstance().getConnection();
//        PreparedStatement pstm = connection.prepareStatement(sql);
//
//        for (int i = 0; i < args.length; i++) {
//            pstm.setObject(i + 1, args[i]);
//        }
//        if (sql.startsWith("SELECT")) {
//            return (T) pstm.executeQuery();
//        }else {
//            return (T) (Boolean) (pstm.executeUpdate()>0);
//        }
//    }
    public static<T>T execute(String sql,Object... obj) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement(sql);

        for (int i = 0; i < obj.length; i++) {
            pst.setObject(i + 1, obj[i]);
        }

        if (sql.startsWith("SELECT")) {
            ResultSet rst = pst.executeQuery();
            return (T) rst;
        } else {
            int i = pst.executeUpdate();

            boolean isSaved = i > 0;

            return (T) ((Boolean) isSaved);
        }
    }

//    public static boolean executeWithConnection(Connection connection, String query, Object... params) throws SQLException {
//        try (PreparedStatement pst = connection.prepareStatement(query)) {
//            for (int i = 0; i < params.length; i++) {
//                pst.setObject(i + 1, params[i]);
//            }
//            return pst.executeUpdate() > 0;
//        }
//    }
}
