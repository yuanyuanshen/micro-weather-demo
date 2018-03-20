package com.syy.spring.cloud.weather.controller;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.syy.spring.cloud.weather.service.CityDataService;
import com.syy.spring.cloud.weather.service.WeatherReporterService;

/**
* @author 作者：shenyuanyuan E-mail:
* @version 创建时间：2018年3月15日 上午10:32:48
* 类说明
*/
@RestController
@RequestMapping(value = "/reporter")
public class WeatherReporterController {
	
	@Autowired
	private CityDataService cityDataService;// 获取城市列表
	
	@Autowired
	private WeatherReporterService weatherReporterService;// 获取城市天气预报
	
	@GetMapping("/cityName/{cityName}")
	public ModelAndView getReporterByCityName(@PathVariable("cityName") String cityName, Model model) throws JAXBException {
		model.addAttribute("title", "媛媛的天气预报");
		model.addAttribute("cityName", cityName);
		model.addAttribute("cityList", cityDataService.getCityList());
		model.addAttribute("report", weatherReporterService.getDataByCityName(cityName));
		return new ModelAndView("weather/report","reportModel",model);
	}
}
