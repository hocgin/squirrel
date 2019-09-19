package in.hocg.squirrel.sample.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

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
public class CustomTypeHandler extends BaseTypeHandler<String> {
    
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
    
    }
    
    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String string = rs.getString(columnName);
        return String.format("%s::::cache", string);
    }
    
    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String string = rs.getString(columnIndex);
        return String.format("%s::::cache", string);
    }
    
    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String string = cs.getString(columnIndex);
        return String.format("%s::::cache", string);
    }
}
