package com.syy.spring.cloud.weather.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syy.spring.cloud.weather.service.WeatherDataService;
import com.syy.spring.cloud.weather.service.WeatherReporterService;
import com.syy.spring.cloud.weather.vo.Weather;
import com.syy.spring.cloud.weather.vo.WeatherResponse;

/**
* @author 作者：shenyuanyuan E-mail:
* @version 创建时间：2018年3月15日 上午10:27:33
* 类说明  天气预报接口实现
*/
@Service
public class WeatherReporterServiceImpl implements WeatherReporterService {

	@Autowired
	private WeatherDataService weatherDataService;
	@Override
	public Weather getDataByCityName(String cityName) {
		// TODO Auto-generated method stub
		WeatherResponse weatherResponse = weatherDataService.getDataByCityName(cityName);
		return weatherResponse.getData();
	}

}
