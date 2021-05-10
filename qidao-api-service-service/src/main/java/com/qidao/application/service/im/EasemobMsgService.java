package com.qidao.application.service.im;

import com.qidao.application.model.easemob.EasemobSendTextMessageReq;
import com.qidao.application.model.easemob.EasemobSendTextMessageRes;

/**
 * todo 注释补全 (Autuan)[start.21.3.15]
 */
public interface EasemobMsgService {
    /**
     * 发送文本/透传消息	直接编辑内容发送
     */
    EasemobSendTextMessageRes sendTextMessage(EasemobSendTextMessageReq req);
    /**
     * 发送图片/语音/视频消息	需要先上传这三类文件，从接口返回值中获取到相应的参数，按照 API 要求编辑到消息体中然后的发送
     */

    /**
     * 发送图片消息
     * 给一个或者多个用户，或者一个或者多个群组发送消息，并且通过可选的 from 字段让接收方看到发送方是不同的人。同时，支持扩展字段，通过 ext 属性，APP 可以发送自己专属的消息结构。
     *
     * 注意：在调用程序中，请求体如果超过 5kb 会导致413错误，需要拆成几个更小的请求体重试。详见接口限流说明。
     *
     * HTTP Request
     * 	//{org_name}/{app_name}/messages
     */
    Object sendPictureMessage();
    /**
     * 发送语音消息
     * 发送语音文件，需要先上传语音文件，然后再发送此消息。（URL 中的 UUID 和 secret 可以从上传后的 response 获取）
     *
     * 注意：在调用程序中，请求体如果超过 5kb 会导致413错误，需要拆成几个更小的请求体重试。详见接口限流说明。
     *
     * HTTP Request
     * 	//{org_name}/{app_name}/messages
     */
    Object sendAudioMessage();

    /**
     * 发送视频消息
     * 发送视频消息，需要先上传视频文件和视频缩略图文件，然后再发送此消息。（URL 中的 UUID 和 secret 可以从上传后的 response 获取）
     *
     * 注意：在调用程序中，请求体如果超过 5kb 会导致413错误，需要拆成几个更小的请求体重试。详见接口限流说明。
     *
     * HTTP Request
     * 	//{org_name}/{app_name}/messages
     * Request Headers
     * 参数	说明
     * Content-Type	application/json
     * Authorization	Bearer ${token}
     * Request Body
     * 参数	说明
     * target_type	发送的目标类型；users：给用户发消息，chatgroups：给群发消息，chatrooms：给聊天室发消息
     * target	发送的目标；注意这里需要用数组，并且向数组内添加的用户不能超过1000个，即使只有一个用户，也要用数组 ['u1']；给用户发送时数组元素是用户名，给群组发送时，数组元素是groupid
     * type	消息类型；txt:文本消息，img：图片消息，loc：位置消息，audio：语音消息，video：视频消息，file：文件消息
     * filename	视频文件名称
     * thumb	成功上传视频缩略图返回的UUID
     * length	视频播放长度
     * secret	成功上传视频文件后返回的secret
     * file_length	视频文件大小（单位：字节）
     * thumb_secret	成功上传视频缩略图后返回的secret
     * url	成功上传视频文件返回的UUID
     * Response Body
     * 在返回值中查看data字段包含的信息
     *
     * 参数	说明
     * username	接受消息的用户名
     * success	表示消息发送成功
     */
    Object sendVideoMessage();

    /**
     * 发送位置消息
     * 位置消息：获取到地址的经纬度，填写正确地址发送。
     *
     * 注意：在调用程序中，请求体如果超过 5kb 会导致413错误，需要拆成几个更小的请求体重试，同时用户消息+扩展字段的长度在4k字节以内。详见接口限流说明。
     *
     * HTTP Request
     * 	//{org_name}/{app_name}/messages
     * Request Headers
     * 参数	说明
     * Content-Type	application/json
     * Authorization	Bearer ${token}
     * Request Body
     * 参数	说明
     * target_type	发送的目标类型；users：给用户发消息，chatgroups：给群发消息，chatrooms：给聊天室发消息
     * target	发送的目标；注意这里需要用数组，并且向数组内添加的用户不能超过1000个，即使只有一个用户，也要用数组 ['u1']；给用户发送时数组元素是用户名，给群组发送时，数组元素是groupid
     * msg	消息内容
     * type	消息类型；txt:文本消息，img：图片消息，loc：位置消息，audio：语音消息，video：视频消息，file：文件消息
     * from	表示消息发送者;无此字段Server会默认设置为“from”:“admin”，有from字段但值为空串(“”)时请求失败
     * Response Body
     * 在返回值中查看data字段包含的信息
     *
     * 参数	说明
     * username	接受消息的用户名
     * success	表示消息发送成功
     */
    Object sendLocMessage();
    /**
     * 发送透传消息
     * 透传消息：不会在客户端提示（铃声、震动、通知栏等），也不会有 APNS 推送（苹果推送），但可以在客户端监听到，具体功能可以根据自身自定义。
     *
     * 注意：在调用程序中，请求体如果超过 5kb 会导致413错误，需要拆成几个更小的请求体重试。详见接口限流说明。
     *
     * HTTP Request
     * 	//{org_name}/{app_name}/messages
     * Request Headers
     * 参数	说明
     * Content-Type	application/json
     * Authorization	Bearer ${token}
     * Request Body
     * 参数	说明
     * target_type	发送的目标类型；users：给用户发消息，chatgroups：给群发消息，chatrooms：给聊天室发消息
     * target	发送的目标；注意这里需要用数组，并且向数组内添加的用户不能超过1000个，即使只有一个用户，也要用数组 ['u1']；给用户发送时数组元素是用户名，给群组发送时，数组元素是groupid
     * msg	消息内容
     * type	消息类型；txt:文本消息，img：图片消息，loc：位置消息，audio：语音消息，video：视频消息，file：文件消息，cmd：透传消息
     * from	表示消息发送者;无此字段Server会默认设置为“from”:“admin”，有from字段但值为空串(“”)时请求失败
     * Response Body
     * 在返回值中查看data字段包含的信息
     *
     * 参数	说明
     * username	接受消息的用户名
     * success	表示消息发送成功
     */
    Object sendCmdMessage();

    /**
     * 发送自定义消息
     * 自定义消息：若普通消息类型不满足用户消息需求，可以使用自定义消息来自定义消息类型，主要是通过message的customEvent字段实现。
     *
     * 注意：在调用程序中，请求体如果超过 5kb 会导致413错误，需要拆成几个更小的请求体重试。详见接口限流说明。
     *
     * HTTP Request
     * 	//{org_name}/{app_name}/messages
     * Request Headers
     * 参数	说明
     * Content-Type	application/json
     * Authorization	Bearer ${token}
     * Request Body
     * 参数	说明
     * target_type	发送的目标类型；users：给用户发消息，chatgroups：给群发消息，chatrooms：给聊天室发消息
     * target	发送的目标；注意这里需要用数组，并且向数组内添加的用户不能超过1000个，即使只有一个用户，也要用数组 ['u1']；给用户发送时数组元素是用户名，给群组发送时，数组元素是groupid
     * msg	消息内容
     * type	消息类型；txt:文本消息，img：图片消息，loc：位置消息，audio：语音消息，video：视频消息，file：文件消息，custom：自定义消息
     * customEvent	用户自定义的事件类型，必须是string，值必须满足正则表达式 [a-zA-Z0-9-_/\.]{1,32}，最短1个字符 最长32个字符
     * customExts	用户自定义的事件属性，类型必须是Map<String,String>，最多可以包含16个元素。customExts 是可选的，不需要可以不传
     * from	表示消息发送者;无此字段Server会默认设置为“from”:“admin”，有from字段但值为空串(“”)时请求失败
     * ext	扩展属性，由APP自己定义。可以没有这个字段，但是如果有，值不能是“ext:null”这种形式，否则出错
     * attr1	消息的扩展内容，可以增加字段，扩展消息主要解析部分，必须是基本类型数据
     * Response Body
     * 在返回值中查看data字段包含的信息
     *
     * 参数	说明
     * username	接受消息的用户名
     * success	表示消息发送成功
     */
    Object sendCustomMessage();
    /**
     * 发送扩展消息
     * 扩展消息：若普通消息类型不满足用户消息需求，可以使用扩展消息。任何类型的消息都支持扩展，主要是通过message的ext字段实现。
     *
     * 注意：在调用程序中，请求体如果超过 5kb 会导致413错误，需要拆成几个更小的请求体重试。详见接口限流说明。
     *
     * HTTP Request
     * 	//{org_name}/{app_name}/messages
     * Request Headers
     * 参数	说明
     * Content-Type	application/json
     * Authorization	Bearer ${token}
     * Request Body
     * 参数	说明
     * target_type	发送的目标类型；users：给用户发消息，chatgroups：给群发消息，chatrooms：给聊天室发消息
     * target	发送的目标；注意这里需要用数组，并且向数组内添加的用户不能超过1000个，即使只有一个用户，也要用数组 ['u1']；给用户发送时数组元素是用户名，给群组发送时，数组元素是groupid
     * msg	消息内容
     * type	消息类型，不局限与文本消息。任何消息类型都可以加扩展消息；txt:文本消息，img：图片消息，loc：位置消息，audio：语音消息，video：视频消息，file：文件消息
     * msg	消息；随意传入都可以
     * from	表示消息发送者;无此字段Server会默认设置为“from”:“admin”，有from字段但值为空串(“”)时请求失败
     * ext	扩展属性，由APP自己定义。可以没有这个字段，但是如果有，值不能是“ext:null”这种形式，否则出错
     * attr1	消息的扩展内容，可以增加字段，扩展消息主要解析部分，必须是基本类型数据
     * Response Body
     * 在返回值中查看data字段包含的信息
     *
     * 参数	说明
     * username	接受消息的用户名
     * success	表示消息发送成功
     */
    Object sendExtendMessage();
}
