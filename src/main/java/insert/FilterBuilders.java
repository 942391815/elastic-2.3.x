package insert;

/**
 * Created by qiaogu on 2017/12/14.
 */

import org.elasticsearch.index.query.*;

public final class FilterBuilders {
    public FilterBuilders() {
    }

    public static TermQueryBuilder termFilter(String name, Object value) {
        return new TermQueryBuilder(name, value);
    }

    public static BoolQueryBuilder boolFilter() {
        return new BoolQueryBuilder();
    }

    public static TermsQueryBuilder inFilter(String name, int... values) {
        return new TermsQueryBuilder(name, values);
    }

    public static TermsQueryBuilder inFilter(String name, long... values) {
        return new TermsQueryBuilder(name, values);
    }

    public static TermsQueryBuilder inFilter(String name, float... values) {
        return new TermsQueryBuilder(name, values);
    }

    public static TermsQueryBuilder inFilter(String name, double... values) {
        return new TermsQueryBuilder(name, values);
    }

    public static TermsQueryBuilder inFilter(String name, Object... values) {
        return new TermsQueryBuilder(name, values);
    }

    public static RegexpQueryBuilder regexpFilter(String name, String regexp) {
        return new RegexpQueryBuilder(name, regexp);
    }

    public static RangeQueryBuilder rangeFilter(String name) {
        return new RangeQueryBuilder(name);
    }

    public static ExistsQueryBuilder existsFilter(String name) {
        return new ExistsQueryBuilder(name);
    }

    public static BoolQueryBuilder missingFilter(String name) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.mustNot(new ExistsQueryBuilder(name));
        return boolQueryBuilder;
    }
}