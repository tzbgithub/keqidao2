package com.qidao.application.service.recommend;

import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * 用于推荐调用的组件
 * @author Autuan.Yu
 */
@Component
@Getter
public class RecommendComponent {
    private DataModel dataModel;

    @SneakyThrows
    @Autowired
    public RecommendComponent(DataSource dataSource) {
        // todo constants 常量 （Autuan.Yu)[2021.1.31]
        this.dataModel =new MySQLJDBCDataModel(dataSource,"link_label","source_id","label_id","favor", "time");
    }
}
