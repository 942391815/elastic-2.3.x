package status.filter;

import insert.BoolQueryBuilder;
import insert.FilterBuilders;

/**
 * Created by qiaogu on 2018/1/26.
 */
public class Test {
    public static void main(String[] args) {
        BoolQueryBuilder filter = filter = FilterBuilders.boolFilter().must(FilterBuilders.rangeFilter("pick_up_operate_time").gte(1521014400000L).lt(1521018000000L));
        filter.must(FilterBuilders.termFilter("pick_up_status",20));//已经完成
        filter.must(FilterBuilders.missingFilter("pick_up_send_time"));//发货时间需为空

        //区域--片区
//        TermsBuilder org_area_agg = AggregationBuilders.terms("org_id_agg").field("receive_org_id").size(Integer.MAX_VALUE)
//                .subAggregation(AggregationBuilders.terms("area_id_agg").field("receive_area_code").size(Integer.MAX_VALUE));
        System.out.println(filter.toString());
    }
}
