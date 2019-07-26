package in.hocg.squirrel.builder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

/**
 * @author hocgin
 * @date 2019/7/26
 */
class XmlScriptsTest {
    
    @Test
    void script() {
        String sql = XmlScripts.script("sql");
        Assertions.assertEquals(sql, "<script>sql</script>");
    }
    
    @Test
    void _if() {
        String sql = XmlScripts._if("param != null","i = #{i}");
        Assertions.assertEquals(sql, "<if test=\"param != null\">i = #{i}</if>");
    }
    
    @Test
    void ifNotNull() {
        String sql = XmlScripts.ifNotNull("param", "i = #{i}");
        Assertions.assertEquals(sql, "<if test=\"param != null\">i = #{i}</if>");
    }
    
    @Test
    void foreach() {
        String xml = XmlScripts.foreach("collection", "item", ",", "(", ")");
        Assertions.assertEquals(xml, "<foreach item=\"item\" collection=\"collection\" separator=\",\" close=\")\" open=\"(\">#{item}</foreach>");
    }
    
    @Test
    void in() {
        String xml = XmlScripts.in("id", "collection", "item");
        Assertions.assertEquals(xml, "<if test=\"collection != null\">id IN <foreach item=\"item\" collection=\"collection\" separator=\" , \" close=\" ) \" open=\" ( \">#{item}</foreach></if>");
    }
    
    @Test
    void where() {
        String xml = XmlScripts.where("id > 5");
        Assertions.assertEquals(xml, "<where>id > 5</where>");
    }
    
    @Test
    void set() {
        String xml = XmlScripts.set("id = 5");
        Assertions.assertEquals(xml, "<set>id = 5</set>");
    }
    
    @Test
    void node() {
        HashMap<String, String> attrs = new HashMap<>();
        attrs.put("test", "id != null && id != ''");
        String anIf = XmlScripts.node("if", attrs, "A.ID >= #{id}");
        Assertions.assertEquals(anIf, "<if test=\"id != null && id != ''\">A.ID >= #{id}</if>");
    }
    
    @Test
    void node1() {
        HashMap<String, String> attrs = new HashMap<>();
        String anIffI = XmlScripts.node("where", "where", attrs, "A.ID >= #{id}");
        Assertions.assertEquals(anIffI, "<where>A.ID >= #{id}</where>");
    }
    
    @Test
    void node2() {
        String set = XmlScripts.node("set", "A.ID = #{id},");
        Assertions.assertEquals(set, "<set>A.ID = #{id},</set>");
    }
    
    @Test
    void select() {
        String sql = XmlScripts.select("t_example", new String[]{"a", "b"});
        Assertions.assertEquals(sql, "SELECT a, b\nFROM t_example");
        
        sql = XmlScripts.select("t_example", new String[]{"*"});
        Assertions.assertEquals(sql, "SELECT *\nFROM t_example");
    }
    
    @Test
    void update() {
        String sql = XmlScripts.update("t_example");
        Assertions.assertEquals(sql, "UPDATE t_example");
    }
    
    @Test
    void eq() {
        String sql = XmlScripts.eq("field", "field");
        Assertions.assertEquals(sql, "field = #{field}");
    }
    
    @Test
    void sql() {
        String sql = XmlScripts.sql("SELECT * FROM t_example");
        Assertions.assertEquals(sql, "SELECT * FROM t_example");
    }
    
    @Test
    void count() {
        String sql = XmlScripts.count("t_example", "id");
        Assertions.assertEquals(sql, "SELECT COUNT ( id ) \nFROM t_example");
    }
}