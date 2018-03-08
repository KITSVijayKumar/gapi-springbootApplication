package com.pim.migration.configuration;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PIMController {
	
	@Autowired
	Configuration configuration;
	
	@RequestMapping (method={RequestMethod.GET},value={"/version"})
	public String version(){
		return "1.0";
	}
	
	/**
	 * Test API for cucumber test
	 * @param response
	 * @return
	 */
    @RequestMapping(method = { RequestMethod.GET }, value = { "/customer" })
    public Customer getEmployee(HttpServletResponse response) {
    	//API Response with STUB data.
        return new Customer("Vijay","Kumar","QA-Integration","SouthWales, UK");
    }
}
