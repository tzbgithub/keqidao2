package com.qidao.application.model.common.oss;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OssRep {
    private  String tmpSecretId;
    private  String tmpSecretKey;
    private  String sessionToken;
    private  String bucket;
    private  String regionName;
}
