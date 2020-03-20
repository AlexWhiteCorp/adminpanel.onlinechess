package com.alexcorp.oc.adminpanel.repositories;

import com.alexcorp.oc.adminpanel.domains.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.function.Consumer;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchQueryCriteriaConsumer implements Consumer<SearchCriteria> {

    private Predicate predicate;
    private CriteriaBuilder builder;
    private Root r;

    @Override
    public void accept(SearchCriteria param) {
        if (param.getOperation().equalsIgnoreCase(">")) {
            predicate = builder.and(predicate, builder
                    .greaterThanOrEqualTo(r.get(param.getKey()), param.getValue().toString()));
        } else if (param.getOperation().equalsIgnoreCase("<")) {
            predicate = builder.and(predicate, builder.lessThanOrEqualTo(
                    r.get(param.getKey()), param.getValue().toString()));
        } else if (param.getOperation().equalsIgnoreCase(":")) {
            Class paramType = r.get(param.getKey()).getJavaType();
            if(paramType == User.Active.class){
                predicate = builder.and(predicate, builder.equal(r.get(param.getKey()),
                                        User.Active.valueOf((String)param.getValue())));
                return;
            }
            if(paramType == User.Role.class){
                predicate = builder.and(predicate, builder.equal(r.get(param.getKey()),
                        User.Role.valueOf((String)param.getValue())));
                return;
            }
            if(paramType == String.class){
                predicate = builder.and(predicate, builder.like(r.get(param.getKey()),
                                     "%" + param.getValue() + "%"));
                return;
            }

            predicate = builder.and(predicate, builder.equal(r.get(param.getKey()), param.getValue()));
        }
    }
}
