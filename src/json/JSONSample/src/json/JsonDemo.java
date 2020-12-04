package json;

import tijos.framework.util.json.JSONArray;
import tijos.framework.util.json.JSONException;
import tijos.framework.util.json.JSONObject;
import tijos.framework.util.json.JSONStringer;
import tijos.framework.util.json.JSONTokener;

/**
 * 本例程演示了TiJOS中JSON的用法
 * TiJOS JDK 中的JSON实现来自https://github.com/stleary/JSON-java,
 * 支持JSONArray, JSONObject, JSONString, 具体可参考相关资料.
 *
 * @author TiJOS
 */

public class JsonDemo {

    public static void main(String args[]) throws JSONException, Exception {
        System.out.println(prepareJSONObject());
        System.out.println(prepareJSONObject2());

        parseJsonObject();
    }

    /**
     * 通过JSONObject Key-Value 生成JSON字符串
     *
     * @return json 字符串
     */
    private static String prepareJSONObject() {
        JSONObject studentJSONObject = new JSONObject();
        try {
            studentJSONObject.put("name", "Jason");
            studentJSONObject.put("id", 20130001);
            studentJSONObject.put("phone", "13579246810");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return studentJSONObject.toString();
    }

    /**
     * 通过JSONStringer生成JSON字符串
     *
     * @return json字符串
     */
    private static String prepareJSONObject2() {
        JSONStringer jsonStringer = new JSONStringer();
        try {
            jsonStringer.object();
            jsonStringer.key("name");
            jsonStringer.value("Jason");
            jsonStringer.key("id");
            jsonStringer.value(20130001);
            jsonStringer.key("phone");
            jsonStringer.value("13579246810");
            jsonStringer.endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonStringer.toString();
    }

    /**
     * JSON 字符串解析
     */
    private static void parseJsonObject() {
        final String jsonPerson =
                "{" +
                        "   \"phone\" : [\"12345678\", \"87654321\"]," +
                        "   \"name\" : \"jack\"," +
                        "   \"age\" : 21," +
                        "   \"address\" : { \"country\" : \"china\", \"province\" : \"beijing\" }," +
                        "   \"married\" : false," +
                        "}";

        try {
            JSONTokener jsonTokener = new JSONTokener(jsonPerson);
            JSONObject person = (JSONObject) jsonTokener.nextValue();

            JSONArray phoneArray = person.getJSONArray("phone");
            for (int i = 0; i < phoneArray.length(); i++) {
                System.out.println("Phone" + i);
                System.out.println(phoneArray.getString(i));
            }

            System.out.println(person.getString("name"));
            System.out.println(person.getInt("age"));

            JSONObject addr = person.getJSONObject("address");
            System.out.println(addr.getString("country"));
            System.out.println(addr.getString("province"));


            System.out.println(person.getBoolean("married"));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

}
