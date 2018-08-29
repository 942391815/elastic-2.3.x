package insert;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.ConstantScoreQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import poi.EsUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qiaogu on 2017/12/14.
 */
public class Test {
    public static void main(String[] args) {
        insert();
//        query();
    }

    private static void insert() {
        TransportClient client = EsUtils.localClient("elasticsearch", "localhost", "9300");
        IndexResponse indexResponse = client.prepareIndex("po_ord_detail_d", "po_ord_detail_d").setId("71327983144").setSource(mapToJson()).execute().actionGet();
        System.out.println(indexResponse.toString());
        client.close();
    }

    private static void query(){
        TransportClient client = EsUtils.localClient("elasticsearch", "localhost", "9300");

        BoolQueryBuilder filter = FilterBuilders.boolFilter().must(FilterBuilders.rangeFilter("sign_in_time").gte(1516118400000L).lt(1516136400000L));
        filter.must(FilterBuilders.termFilter("waybill_state", 150));//妥投

//        filter.mustNot(FilterBuilders.missingFilter("sign_in_site_id"));
//        filter.mustNot(FilterBuilders.missingFilter("sign_in_area_id"));
//        filter.mustNot(FilterBuilders.missingFilter("sign_in_org_id"));

        org.elasticsearch.index.query.BoolQueryBuilder filter1 = QueryBuilders.boolQuery().filter(filter);
        System.out.println(filter.toString());
        SearchResponse sr = client.prepareSearch("fresh_tms_waybill").setTypes("fresh_tms_waybill")
//                .setSearchType(SearchType.COUNT)
                .setQuery(filter1)
                .execute()
                .actionGet();
        System.out.println(sr.getHits().getHits().length);
    }
    private static String mapToJson() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("fashion_no", "fashionNo");
        result.put("goods_barcode", "goodsBar,badBar");
        result.put("goods_name", "goodsName");
        result.put("po_status", "poStatus");
        result.put("seller_first_category", "firstCategory");
        result.put("seller_second_category", "secondCategory");

        result.put("seller_third_category", "thirdCategory");
        result.put("supplier_name", "supplierName");
        return JSON.toJSON(result).toString();
    }
}
