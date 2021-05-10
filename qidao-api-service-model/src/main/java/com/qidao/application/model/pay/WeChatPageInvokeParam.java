package com.qidao.application.model.pay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeChatPageInvokeParam extends InvokeParam{
    private String codeUrl;
}
