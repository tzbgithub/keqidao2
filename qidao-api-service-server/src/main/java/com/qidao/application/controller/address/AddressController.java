package com.qidao.application.controller.address;

import com.qidao.application.model.address.Area;
import com.qidao.application.model.address.City;
import com.qidao.application.model.address.Province;
import com.qidao.application.service.address.AddressService;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Api(tags = "省市区地址")
@RequestMapping("address/address")
@RestController
@Slf4j
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * describe : 查询所有省份
     *
     * @return
     */
    @ApiOperation("查询所有省份")
    @GetMapping("/getAllProvince")
    public ResponseResult<List<Province>> getAllProvince() {
        log.info("AddressController -> getAllProvince -> start");
        try {
            List<Province> provinces = addressService.getAllProvince();
            log.info("AddressController -> getAllProvince -> end");
            return new ResponseResult<List<Province>>("Sx200", provinces, "success", LocalDateTime.now());
        } catch (Exception e) {
            log.error("get all province fail:{}", e.getMessage());
            return new ResponseResult<List<Province>>("LxAPI-CCCCCC", null, e.getMessage(), LocalDateTime.now());
        }
    }

    /**
     * describe：根据省份查询所属的城市
     *
     * @param provinceCode
     * @return
     */
    @ApiOperation("根据省份编码查询所属的城市")
    @GetMapping("/getCityByProvinceCode")
    public ResponseResult<List<City>> getCityByProvinceCode(@RequestParam String provinceCode) {
        log.info("AddressController -> getCityByProvinceCode -> start -> provinceCode:{}", provinceCode);
        try {
            List<City> cities = addressService.getCityByProvinceCode(provinceCode);
            log.info("AddressController -> getAllProvince -> end");
            return new ResponseResult<List<City>>("Sx200", cities, "success", LocalDateTime.now());
        } catch (Exception e) {
            log.error("get city by provinceCode error:{}", e.getMessage());
            return new ResponseResult<List<City>>("LxAPI-CCCCCC", null, e.getMessage(), LocalDateTime.now());
        }
    }

    /**
     * describe：根据城市编码查询所属的区县
     *
     * @param cityCode
     * @return
     */
    @ApiOperation("根据城市编码查询所属的区县")
    @GetMapping("/getCityByCityCode")
    public ResponseResult<List<Area>> getAreaByCityCode(@RequestParam String cityCode) {
        log.info("AddressController -> getAreaByCityCode -> start -> cityCode:{}", cityCode);
        try {
            List<Area> areas = addressService.getAreaByCityCode(cityCode);
            log.info("AddressController -> getAreaByCityCode -> end");
            return new ResponseResult<List<Area>>("Sx200", areas, "success", LocalDateTime.now());
        } catch (Exception e) {
            log.error("get area by cityCode error:{}", e.getMessage());
            return new ResponseResult<List<Area>>("LxAPI-CCCCCC", null, e.getMessage(), LocalDateTime.now());
        }
    }
}
