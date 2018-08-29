package upsert;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import poi.EsUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qiaogu on 2017/11/2.
 */
public class Test {
    public static void main(String[] args) throws Exception {
//        update();
//        System.out.println(1 << 14);
        insert();
    }

    private static void insert() {
        TransportClient client = EsUtils.localClient();
        IndexResponse indexResponse = client.prepareIndex("receive_orderinfo", "receive_orderinfo").setId("4").setSource(mapToJson()).execute().actionGet();
        System.out.println(indexResponse.toString());
        client.close();
    }

    private static void update() throws Exception {

        XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
        jsonBuild.startObject();
        jsonBuild.field("content", "sql query.dsfsdfsdfx123123..");
        jsonBuild.endObject();
        String jsonData = jsonBuild.string();

        TransportClient client = EsUtils.localClient();
        UpdateResponse updateResponse = client.prepareUpdate("test", "test", "1").setDoc(jsonData.getBytes()).execute().actionGet();
        System.out.println(updateResponse);
        client.close();
    }

    private static String mapToJson() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("org_id", "6");
        result.put("delivery_id", "VE12123378");
        return JSON.toJSON(result).toString();
    }
}
