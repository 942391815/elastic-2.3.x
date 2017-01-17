package poi;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiaogu on 2017/1/17.
 */
public class EsAggregation {
    public static void main(String[] args) {
        TransportClient client = EsUtils.localClient();
        List<String> codeList = new ArrayList<String>();
        codeList.add("160101");
        codeList.add("160102");
        TermsQueryBuilder queryBuilder = QueryBuilders.termsQuery("job_code", codeList);
        RangeQueryBuilder written_score = QueryBuilders.rangeQuery("written_score").gte(84.30);
        RangeQueryBuilder Interview_score = QueryBuilders.rangeQuery("Interview_score").gte(82.20);

        BoolQueryBuilder must = QueryBuilders.boolQuery().must(queryBuilder).must(written_score).must(Interview_score);

        SearchRequestBuilder searchRequestBuilder = client.prepareSearch("exam").setTypes("exam").setSearchType(SearchType.DFS_QUERY_THEN_FETCH).
                setQuery(must);
        searchRequestBuilder.addSort("Interview_score",SortOrder.DESC);
        searchRequestBuilder.setFrom(1).setSize(20);
        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
        SearchHits hits = searchResponse.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit each : hits1) {
            System.out.println(each.getSourceAsString());
        }
        System.out.println(searchResponse.getHits().getTotalHits());
    }
}
