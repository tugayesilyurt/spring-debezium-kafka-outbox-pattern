package com.transform;

import java.util.Map;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.header.Headers;
import org.apache.kafka.connect.transforms.Transformation;


public class CustomTransformation<R extends ConnectRecord<R>> implements Transformation<R> {

    public R apply(R sourceRecord) {

        Struct kStruct = (Struct) sourceRecord.value();
        String databaseOperation = kStruct.getString("op");

        if ("c".equalsIgnoreCase(databaseOperation)) {

            Struct after = (Struct) kStruct.get("after");
            String UUID = after.getString("uuid");
            String payload = after.getString("payload");
            String eventType = after.getString("event_type").toLowerCase();
            String topic = eventType.toLowerCase();

            Headers headers = sourceRecord.headers();
            headers.addString("eventId", UUID);

            sourceRecord = sourceRecord.newRecord(topic, null, Schema.STRING_SCHEMA, UUID,
                    null, payload, sourceRecord.timestamp(), headers);
        }

        return sourceRecord;
    }

    public ConfigDef config() {
        return new ConfigDef();
    }

    public void close() {

    }

    public void configure(Map<String, ?> configs) {

    }
}
