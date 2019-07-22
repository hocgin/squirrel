package in.hocg.squirrel.builder;

import lombok.AllArgsConstructor;

/**
 * Created by hocgin on 2019-07-22.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@AllArgsConstructor
public enum SqlKeyword {
    EQ(" = "),
    NEQ(" <> "),
    ALL(" * "),
    LT(" < "),
    GT(" > "),
    LET(" <= "),
    GET(" >= "),
    LIKE(" LIKE "),
    LIMIT(" LIMIT "),
    SPLIT(" , "),
    AS("AS"),
    IN(" IN "),
    AND(" AND "),
    OR(" OR "),
    SET(" SET "),
    IS_NOT_NULL(" IS NOT NULL "),
    IS_NULL(" IS NULL "),
    IS_EMPTY(" = '' "),
    IS_NOT_EMPTY(" <> '' "),
    INSERT_INTO("INSERT INTO "),
    DELETE_FROM("DELETE FROM "),
    SELECT("SELECT "),
    COUNT("COUNT"),
    FROM(" FROM "),
    ORDER_BY("ORDER BY "),
    UPDATE("UPDATE "),
    VALUES(" ) VALUES "),
    SPLIT_PREFIX(" ( "),
    SPLIT_SUFFIX(" ) "),
    WHERE(" WHERE ");
    
    private String value;
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
}
