package status;


import insert.BoolQueryBuilder;
import insert.FilterBuilders;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import poi.EsUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by qiaogu on 2018/1/24.
 */
public class DeleteForQuerys implements Runnable {
    private String index;
    private String type;

    public DeleteForQuerys(String index) {
        this.index = index;
        this.type = index;
    }

    public void run() {
        List<String> idsList = new ArrayList<String>();
        BoolQueryBuilder filter = FilterBuilders.boolFilter().must(
                FilterBuilders.rangeFilter("dead_time").lt(2517636730311L)
        );

        TransportClient client = EsUtils.localClient("elasticsearch", "localhost", "9300");
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        searchRequestBuilder.setQuery(filter);
        final SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
        final SearchHits hits = searchResponse.getHits();
        for (SearchHit each : hits) {
            final String id = each.id();
            idsList.add(id);
            System.out.println(id);
        }
        final BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
        for (String each : idsList) {

            DeleteRequestBuilder deleteRequestBuilder = client.prepareDelete(index, type, each);
            bulkRequestBuilder.add(deleteRequestBuilder);
        }

        bulkRequestBuilder.execute().actionGet();
    }


    public static void main(String[] args) {
        DeleteForQuerys deleteForQuerys = new DeleteForQuerys("receive_orderinfo");
        deleteForQuerys.run();
    }
}
