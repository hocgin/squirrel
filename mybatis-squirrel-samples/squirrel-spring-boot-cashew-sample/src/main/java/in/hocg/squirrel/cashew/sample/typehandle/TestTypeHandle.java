package in.hocg.squirrel.cashew.sample.typehandle;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hocgin on 2019-09-19.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class TestTypeHandle implements TypeHandler<Long> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Long parameter, JdbcType jdbcType) throws SQLException {
        System.out.println("设置参数");
    }
    
    @Override
    public Long getResult(ResultSet rs, String columnName) throws SQLException {
        long id = rs.getLong(columnName);
        return id + 1000;
    }
    
    @Override
    public Long getResult(ResultSet rs, int columnIndex) throws SQLException {
        long id = rs.getLong(columnIndex);
        return id + 1000;
    }
    
    @Override
    public Long getResult(CallableStatement cs, int columnIndex) throws SQLException {
        long id = cs.getLong(columnIndex);
        return id + 1000;
    }
}
