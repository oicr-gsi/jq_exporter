# jq_exporter

A general jq data transformation service.

## Build
```
mvn clean install
```

## Service configuration

```
[
 {
   "id": "endpoint name",
   "url": "input data source",
   "jq": "jq script",

…
 }
]
```

For example, configuration for an oozie metrics transformation service:
```
[
  {
    "id": "oozie-dev",
    "url": "http://oozie-host:11000/oozie/v1/admin/instrumentation",
    "jq": "to_entries[] | .key as $key | .value | map(.group as $group | .data | map(.name as $name | del(.name) | to_entries[] | $key + \" + \" + $group + \" + \" + $name + \" + \" + .key + \" \" + (.value | tostring))) | .[][]"
  }
]
```

## Usage

Arguments:

option | description | required? | default
---|---|---|---
path_config | absolute file path to the configuration file | yes |

Run:
```
java -jar jq_exporter-0.0.1-SNAPSHOT.jar --path_config=/path/to/config.json
```

Consume:
```
curl -s 127.0.0.1:9999/oozie-dev
```

```
...

variables + jobstatus + FAILED + value 0
variables + jobstatus + KILLED + value 25
variables + jvm + max.memory + value 11453595648
variables + jvm + total.memory + value 7495745536
variables + jvm + free.memory + value 6834981416
```


## Live configuration update

Modify configuration file and post to the refresh end point:
```
curl -d {} 127.0.0.1:9999/refresh
```
