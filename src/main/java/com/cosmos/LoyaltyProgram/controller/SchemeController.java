package com.cosmos.LoyaltyProgram.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cosmos.LoyaltyProgram.model.DatatableCollection;
import com.cosmos.LoyaltyProgram.model.Scheme;
import com.cosmos.LoyaltyProgram.service.SchemeService;

@Controller
public class SchemeController {
	
	@Autowired
	SchemeService schemeService;
	
	@RequestMapping(value="/admin/schemesview",method=RequestMethod.GET)
	public ModelAndView drivers(ModelAndView modelAndView){
		
		modelAndView.setViewName("admin/schemes");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/schemes",method=RequestMethod.POST)
	@ResponseBody
	public Scheme add(@RequestBody  @Valid Scheme scheme){
		return schemeService.createScheme(scheme);
	}
	
	@RequestMapping(value="/admin/schemes",method=RequestMethod.GET)
	@ResponseBody
	public DatatableCollection<Scheme> findAll(){
		
		return schemeService.findAll();
	}
	
	@RequestMapping(value="/admin/schemes/{id}",method=RequestMethod.DELETE)
	@ResponseBody
	public void deleteScheme(@PathVariable("id") Long id){
		schemeService.deleteScheme(id);
		
	}

}
