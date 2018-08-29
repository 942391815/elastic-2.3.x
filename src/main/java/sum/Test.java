package sum;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumBuilder;
import poi.EsUtils;

import java.util.List;

/**
 * Created by qiaogu on 2018/5/4.
 */
public class Test {
    public static void main(String[] args) {

        TransportClient client = EsUtils.localClient("elasticsearch", "localhost", "9300");

        SearchRequestBuilder searchRequestBuilder = client.prepareSearch("school").setTypes("student")
                .setSearchType(SearchType.COUNT);
        TermsBuilder outer = AggregationBuilders.terms("outer").field("name").size(Integer.MAX_VALUE);
        SumBuilder innter = AggregationBuilders.sum("sum").field("id");
        searchRequestBuilder.addAggregation(outer.subAggregation(innter));
        SearchResponse sr = searchRequestBuilder.execute().actionGet();
        Terms es = sr.getAggregations().get("outer");

        List<Terms.Bucket> buckets = es.getBuckets();
        for(Terms.Bucket bucket:buckets){
            System.out.println(bucket.getKey());
            Sum sum = bucket.getAggregations().get("sum");
            System.out.println(sum.getValue());
        }
    }
}
