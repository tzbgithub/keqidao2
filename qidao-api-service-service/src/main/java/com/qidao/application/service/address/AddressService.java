package com.qidao.application.service.address;


import com.qidao.application.model.address.Area;
import com.qidao.application.model.address.City;
import com.qidao.application.model.address.Province;

import java.util.List;

public interface AddressService {

    /**
     * describe：查询所有的省份
     * @return
     */
    List<Province> getAllProvince();

    /**
     * describe：根据省份编码查询所属的城市
     * @param provinceCode
     * @return
     */
    List<City> getCityByProvinceCode(String provinceCode);

    /**
     * describe：根据城市编码查询所属的区县
     * @param cityCode
     * @return
     */
    List<Area> getAreaByCityCode(String cityCode);

}
