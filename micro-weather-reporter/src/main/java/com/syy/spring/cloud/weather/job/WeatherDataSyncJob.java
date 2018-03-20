package com.syy.spring.cloud.weather.job;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.syy.spring.cloud.weather.service.CityDataService;
import com.syy.spring.cloud.weather.service.WeatherDataService;
import com.syy.spring.cloud.weather.vo.City;

/**
* @author 作者：shenyuanyuan E-mail:
* @version 创建时间：2018年3月13日 下午3:15:05
* 类说明
*/
public class WeatherDataSyncJob extends QuartzJobBean {

	private final static Logger logger = LoggerFactory.getLogger(WeatherDataSyncJob.class);
	@Autowired
	private CityDataService cityDataService;
	@Autowired
	private WeatherDataService weatherDataService;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		logger.info("WeatherDataSyncJob start  ...");
		try {
			List<City> cityList = cityDataService.getCityList();
			for(City city : cityList){
				Thread.sleep(4000);
				weatherDataService.syncDataByCityName(city.getCityName());
				logger.info("当前缓存城市id = {},城市名字={}",city.getCityId(),city.getCityName());  
			}
			logger.info("WeatherDataSyncJob end ...."); 
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
