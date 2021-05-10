package com.qidao.application.vo.offical;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class WxChatUnionRep {
        private  Integer subscribe;
        private  String openid;
        private  String nickname;
        private  Integer sex;
        private  String city;
        private  String country;
        private  String headimgurl;
        private  String unionid;
        private  String province;
        private List<Integer> tagidList;
}
