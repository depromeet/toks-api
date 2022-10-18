package com.tdns.toks.core.common.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "FileResponseEntity", description = "File Response Entity")
public class FileResponseEntity {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, required = true, name = "filename", description = "filename")
    private String filename;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, required = true, name = "originFilename", description = "original file name")
    private String originFilename;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, required = true, name = "s3path", description = "s3 path")
    private String s3path;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, required = true, name = "height", description = "original file height")
    private Integer height;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, required = true, name = "width", description = "original file width")
    private Integer width;

}
