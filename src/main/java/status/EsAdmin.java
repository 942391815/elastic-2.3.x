package status;

import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import poi.EsUtils;

/**
 * Created by qiaogu on 2018/1/24.
 */
public class EsAdmin {
    public static void main(String[] args) {
        TransportClient client = EsUtils.localClient("elasticsearch", "127.0.0.1", "9300");
        GetIndexResponse response = client.admin().indices().prepareGetIndex().execute().actionGet();
        System.out.println(response.getIndices().length);
        String[] indices = response.getIndices();
        for(String indice : indices){
            System.out.println(indice);
        }
    }
}
