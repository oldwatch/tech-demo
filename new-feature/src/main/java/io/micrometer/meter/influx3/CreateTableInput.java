package io.micrometer.meter.influx3;

/*
{
"db": "demo",
"table": "cpu",
"tags": [
"host", "region", "applications"
],
"fields": [
    {"name":"val","type":"int64"},
    {"name":"usage_percent","type":"float64"},
    {"name":"status","type":"utf8"}
]
}
 */

public record CreateTableInput(String db, String table, String[] tags, FieldInfo[] fields) {


    enum Type {
        int64, float64, utf8, uint64, bool;
    }

    public record FieldInfo(String name, Type type) {

    }
}


