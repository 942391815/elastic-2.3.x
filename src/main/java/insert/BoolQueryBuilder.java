package insert;

import org.elasticsearch.index.query.QueryBuilder;

/**
 * Created by qiaogu on 2017/12/14.
 */
public class BoolQueryBuilder extends org.elasticsearch.index.query.BoolQueryBuilder {
    public BoolQueryBuilder must(QueryBuilder... queryBuilder) {
        for (QueryBuilder each : queryBuilder) {
            super.must(each);
        }
        return this;
    }

    public BoolQueryBuilder mustNot(QueryBuilder queryBuilder) {
        super.mustNot(queryBuilder);
        return this;
    }

    public BoolQueryBuilder must(QueryBuilder queryBuilder) {
        super.must(queryBuilder);
        return this;
    }

    public BoolQueryBuilder mustNot(QueryBuilder... queryBuilder) {
        for (QueryBuilder each : queryBuilder) {
            super.mustNot(each);
        }
        return this;
    }

    public BoolQueryBuilder should(QueryBuilder... queryBuilder) {
        for (QueryBuilder each : queryBuilder) {
            super.should(each);
        }
        return this;
    }
}
