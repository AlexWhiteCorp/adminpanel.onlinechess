package com.alexcorp.oc.adminpanel.repositories.filters.internal;

public class Condition {

    public Comparison comparison;
    public Type type;
    public String field;
    public Object value;

    public Condition() { }

    public Condition(Type type, Comparison comparison, Object value, String field) {
        this.type = type;
        this.comparison = comparison;
        this.value = value;
        this.field = field;
    }

    public static class Builder {
        private Type type;
        private Comparison comparison;
        private Object value;
        private String field;

        public Builder setType(Type type) {
            this.type = type;
            return this;
        }

        public Builder setComparison(Comparison comparison) {
            this.comparison = comparison;
            return this;
        }

        public Builder setValue(Object value) {
            this.value = value;
            return this;
        }

        public Builder setField(String field) {
            this.field = field;
            return this;
        }

        public Condition build() {
            return new Condition(type, comparison, value, field);
        }
    }

    public enum Type {
        numeric,
        string,
        enumerated,
        date,
        bool,
        uuid,
        list,
        raw
    }

    public enum Comparison {
        // equal
        eq,
        // greaterThan
        gt,
        // lowerThan
        lt,
        // not equal
        ne,
        // is null
        isnull,

        in
    }
}