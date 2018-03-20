package com.syy.spring.cloud.weather.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syy.spring.cloud.weather.service.WeatherDataService;
import com.syy.spring.cloud.weather.vo.WeatherResponse;

/**
 * @author 作者：shenyuanyuan E-mail:
 * @version 创建时间：2018年3月7日 上午10:42:13 类说明
 */
@Service
public class WeatherDataServiceImpl implements WeatherDataService {

	private static final String WEATHER_URI = "https://www.sojson.com/open/api/weather/json.shtml?city=";

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public WeatherResponse getDataByCityId(String cityId) {
		String uri = WEATHER_URI + cityId;
		return this.doGetWeahter(uri);
	}

	@Override
	public WeatherResponse getDataByCityName(String cityName) {
		String uri = WEATHER_URI + cityName;
		//uri = "http://weatherapi.market.xiaomi.com/wtr-v2/weather?cityId=101121301";
		return this.doGetWeahter(uri);
	}

	private WeatherResponse doGetWeahter(String uri) {
		ResponseEntity<String> respString = restTemplate.getForEntity(uri, String.class);

		ObjectMapper mapper = new ObjectMapper();
		WeatherResponse resp = null;
		String strBody = null;

		if (respString.getStatusCodeValue() == 200) {
			strBody = respString.getBody();
		}

		try {
			resp = mapper.readValue(strBody, WeatherResponse.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return resp;
	}
}
