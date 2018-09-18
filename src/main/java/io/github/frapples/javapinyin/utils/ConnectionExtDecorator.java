package io.github.frapples.javapinyin.utils;

import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/9/17
 */
@AllArgsConstructor
public class ConnectionExtDecorator implements Connection {

    @Delegate
    private Connection connection;

    public int exec(String sql, Object... args) throws SQLException {
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(sql);
            setPreparedStatementParameter(st, args);
            return st.executeUpdate();
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }

    public ResultSet query(String sql, Object... args) throws SQLException {
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement( sql);
            setPreparedStatementParameter(st, args);
            return st.executeQuery();
        } finally {
            if (st != null) {
                // st.close();
            }
        }
    }

    public int queryForCount(String sql, Object... args) throws SQLException {
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement( sql);
            setPreparedStatementParameter(st, args);
            ResultSet rs = st.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            return rs.getInt(meta.getColumnName(1));
        } finally {
            if (st != null) {
                // st.close();
            }
        }
    }

    private void setPreparedStatementParameter(PreparedStatement st, Object[] args) throws SQLException {
        for (int i = 0; i < args.length; i++) {
            int parameterIndex = i + 1;
            Object arg = args[i];
            st.setObject(parameterIndex, arg);
        }
    }

    public static ConnectionExtDecorator connect(String config) throws ClassNotFoundException, SQLException {
        List<String> seg = Splitter.on(":").splitToList(config);
        if (seg.size() >= 2
            && Objects.equal(seg.get(0), "jdbc")
            && Objects.equal(seg.get(1), "sqlite")) {
            Class.forName("org.sqlite.JDBC");
        }

        return new ConnectionExtDecorator(DriverManager.getConnection(config));
    }
}
