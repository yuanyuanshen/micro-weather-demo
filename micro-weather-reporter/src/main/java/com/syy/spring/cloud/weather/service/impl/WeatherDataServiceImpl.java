package com.syy.spring.cloud.weather.service.impl;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.syy.spring.cloud.weather.service.WeatherDataService;
import com.syy.spring.cloud.weather.vo.WeatherResponse;

/**
 * @author 作者：shenyuanyuan E-mail:
 * @version 创建时间：2018年3月7日 上午10:42:13 类说明
 */
@Service
public class WeatherDataServiceImpl implements WeatherDataService {

	private final static Logger logger = LoggerFactory.getLogger(WeatherDataServiceImpl.class);
	private static final String WEATHER_URI = "https://www.sojson.com/open/api/weather/json.shtml?city=";

	private static final Long TIME_OUT = 1800L;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public WeatherResponse getDataByCityId(String cityId) {
		String uri = WEATHER_URI + cityId;
		// uri = "https://www.sojson.com/open/api/weather/json.shtml?city=北京";
		return this.doGetWeahter(uri);
	}

	@Override
	public WeatherResponse getDataByCityName(String cityName) {
		String uri = WEATHER_URI + cityName;
		// uri = "https://www.sojson.com/open/api/weather/json.shtml?city=北京";
		return this.doGetWeahter(uri);
	}

	private WeatherResponse doGetWeahter(String uri) {

		String key = uri;
		String strBody = null;
		ObjectMapper mapper = new ObjectMapper();
		WeatherResponse resp = null;
		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
		// 缓存中有数据就从缓存中提取
		if (stringRedisTemplate.hasKey(key)) {
			logger.info("Redis has data");
			strBody = ops.get(key);
		} else {
			// 缓存中没有数据就发送请求
			logger.info("Redis don't has data");
			ResponseEntity<String> respString = restTemplate.getForEntity(uri, String.class);

			if (respString.getStatusCodeValue() == 200) {
				strBody = respString.getBody();
			}

			// 写入缓存数据
			ops.set(key, strBody, TIME_OUT, TimeUnit.SECONDS);
		}
		try {
			resp = mapper.readValue(strBody, WeatherResponse.class);

		} catch (IOException e) {
			// e.printStackTrace();
			logger.error("Error", e);
		}

		return resp;
	}

	@Override
	public void syncDataByCityName(String cityName) {
		// TODO Auto-generated method stub
		String uri = WEATHER_URI + cityName;
		this.saveWeatherData(uri);
	}

	private void saveWeatherData(String uri) {
		// TODO Auto-generated method stub
		String key = uri;
		String strBody = null;
		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
		// 缓存中没有数据就发送请求
		ResponseEntity<String> respString = restTemplate.getForEntity(uri, String.class);

		if (respString.getStatusCodeValue() == 200) {
			strBody = respString.getBody();
		}
		logger.info("redis 添加缓存 ，key={}",uri);
		// 写入缓存数据
		ops.set(key, strBody, TIME_OUT, TimeUnit.SECONDS);

	}
}
