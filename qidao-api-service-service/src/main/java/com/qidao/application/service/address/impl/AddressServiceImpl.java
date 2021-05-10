package com.qidao.application.service.address.impl;

import com.qidao.application.entity.address.*;
import com.qidao.application.mapper.address.AddressAreaMapper;
import com.qidao.application.mapper.address.AddressCityMapper;
import com.qidao.application.mapper.address.AddressProvinceMapper;
import com.qidao.application.model.address.Area;
import com.qidao.application.model.address.City;
import com.qidao.application.model.address.Province;
import com.qidao.application.service.address.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("addressService")
@Slf4j
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressProvinceMapper provinceMapper;

    @Autowired
    private AddressCityMapper cityMapper;

    @Autowired
    private AddressAreaMapper areaMapper;

    /**
     * describe：查询所有的省份
     * @return
     */
    @Override
    @Cacheable(value = "CAFFEINE",key = "'getAllProvince'")
    public List<Province> getAllProvince() {
        log.info("AddressServiceImpl -> getAllProvince -> start");
        List<AddressProvince> provinceList = provinceMapper.selectByExample(new AddressProvinceExample());
        List<Province> provinces = new ArrayList<>();
        log.info("Judge provinceList != null and provinceList.size > 0");
        if (provinceList != null && provinceList.size() > 0){
            for (AddressProvince addressProvince : provinceList) {
                Province province = new Province();
                BeanUtils.copyProperties(addressProvince , province);
                provinces.add(province);
            }
        }
        log.info("AddressServiceImpl -> getAllProvince -> end");
        return provinces;
    }

    /**
     * describe：根据省份编码查询所属的城市
     * @param provinceCode
     * @return
     */
    @Override
    @Cacheable(value = "CAFFEINE", key = "#provinceCode")
    public List<City> getCityByProvinceCode(String provinceCode) {
        log.info("AddressServiceImpl -> getCityByProvinceCode -> start -> provinceCode:{}" , provinceCode);
        AddressCityExample example = new AddressCityExample();
        AddressCityExample.Criteria criteria = example.createCriteria();
        criteria.andProvinceCodeEqualTo(provinceCode);
        List<AddressCity> addressCities = cityMapper.selectByExample(example);
        List<City> cities = new ArrayList<>();
        log.info("judge addressCities != null and addressCities.size > 0");
        if (addressCities != null && addressCities.size() > 0){
            for (AddressCity addressCity : addressCities) {
                City city = new City();
                BeanUtils.copyProperties(addressCity , city);
                cities.add(city);
            }
        }
        log.info("AddressServiceImpl -> getCityByProvinceCode -> end");
        return cities;
    }

    /**
     * describe：根据城市编码查询所属的区县
     * @param cityCode
     * @return
     */
    @Override
    @Cacheable(value = "CAFFEINE", key = "#cityCode")
    public List<Area> getAreaByCityCode(String cityCode) {
        log.info("AddressServiceImpl -> getAreaByCityCode -> start -> cityCode:{}" , cityCode );
        AddressAreaExample example = new AddressAreaExample();
        AddressAreaExample.Criteria criteria = example.createCriteria();
        criteria.andCityCodeEqualTo(cityCode);
        List<AddressArea> addressAreas = areaMapper.selectByExample(example);
        List<Area> areas = new ArrayList<>();
        log.info("judge addressAreas != null and addressAreas.size > 0");
        if (addressAreas != null && addressAreas.size() > 0){
            for (AddressArea addressArea : addressAreas) {
                Area area = new Area();
                BeanUtils.copyProperties(addressArea , area);
                areas.add(area);
            }
        }
        log.info("AddressServiceImpl -> getAreaByCityCode -> end");
        return areas;
    }
}
