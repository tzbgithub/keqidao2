package com.qidao.application.mapper.msg;

import com.qidao.application.entity.msg.MemberVipMsg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomMsgMapper {

    List<MemberVipMsg> getMemberVipMsg(Long memberId);

}
