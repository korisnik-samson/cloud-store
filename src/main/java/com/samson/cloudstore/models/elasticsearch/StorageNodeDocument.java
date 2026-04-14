package com.samson.cloudstore.models.elasticsearch;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "storage_nodes")
public class StorageNodeDocument {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private UUID ownerId;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String name;

    @Field(type = FieldType.Keyword)
    private String type;

    @Field(type = FieldType.Keyword)
    private String mimeType;

    @Field(type = FieldType.Boolean)
    private boolean trashed;
}
