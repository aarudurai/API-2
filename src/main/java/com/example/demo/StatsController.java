package com.example.demo;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatsController {
StatsResponseModel d = new StatsResponseModel();
static double sum=0.0,avg=0.0,min=9999.99,max=0.0;
static int count=0;
	@RequestMapping(method = RequestMethod.POST, value="/statistics")
    public ResponseEntity<List<StatsModel>> statisticDetails(@RequestBody List<StatsModel> stats) {
		long unixTime = System.currentTimeMillis();
		System.out.println(unixTime);
		
		stats.stream().forEach(c -> {
			
			if((c.getTimestamp()-unixTime)<60000) {
			c.setTotal(sum += c.getAmount());
			c.setCount(count+=1);
			c.setAvg(avg=sum/count);
			if (c.getAmount() > max) 
			{
				max = c.getAmount();
			}
			if ( c.getAmount() < min) 
			{
				min =  c.getAmount();
			}
			
			}
		
		});
		System.out.println(sum);
		System.out.println(count);
		System.out.println(avg);
		System.out.println(min);
		System.out.println(max);
	    return new ResponseEntity<List<StatsModel>>(HttpStatus.OK);
	}
	
	/*@ResponseBody 
	@RequestMapping(method = RequestMethod.GET, value="/statsRes")
	public JSONObject MapResponse() {
		Map<String,Number> a =new HashMap<String, Number>();
		a.put("count", count);
		a.put("avg", avg);
		a.put("min", min);
		a.put("max", max);
		a.put("total",sum);
		JSONObject json = new JSONObject(a);
		System.out.println(json);
		return json;
		
	}*/
	@ResponseBody 
	@RequestMapping(method = RequestMethod.GET, value="/statsRes")
	 public ResponseEntity<StatsResponseModel> statsDetails(StatsResponseModel statsResponse) {
	
			statsResponse.setTotal(sum);
			statsResponse.setCount(count);
			statsResponse.setAvg(avg);
			statsResponse.setMin(min);
			statsResponse.setMax(max);
		 return new ResponseEntity<StatsResponseModel>(statsResponse,HttpStatus.OK);
		}
		
}

