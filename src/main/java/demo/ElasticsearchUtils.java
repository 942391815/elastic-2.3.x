package demo;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import poi.EsUtils;


public class ElasticsearchUtils {

    /**
     * 创建索引
     *
     * @param indexName 索引名称，相当于数据库名称
     * @param typeName  索引类型，相当于数据库中的表名
     * @param id        id名称，相当于每个表中某一行记录的标识
     * @param jsonData  json数据
     */
    public static void insert(String indexName, String typeName, String id,
                              String jsonData) {
        TransportClient client = EsUtils.localClient("elasticsearch", "localhost", "9300");

        IndexRequestBuilder requestBuilder = client.prepareIndex(indexName,
                typeName, id).setRefresh(true);//设置索引名称，索引类型，id
//        requestBuilder.setSource(jsonData).execute().actionGet();//创建索引
        requestBuilder.setSource(jsonData).execute(new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                System.out.println(indexResponse.toString());
                throw new RuntimeException("123");
            }
            @Override
            public void onFailure(Throwable e) {

            }
        });

    }
}