package hello.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;

import hello.entity.BakedBean;

@Controller
public class HomeController {
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	ServletContext sContext; 
	
	@Autowired
	private SqlSession sqlSession;
	
	PropertyPlaceholderConfigurer cc;
	
	
	@RequestMapping("/")
	public String home() {
		ApplicationContext currentContext = ContextLoader.getCurrentWebApplicationContext();
		System.out.println(currentContext.getApplicationName());
		System.out.println(currentContext.getParent());
		
		System.out.println(context.getParent());
		String[] beans = currentContext.getBeanDefinitionNames();
		for(String str : beans) {
			System.out.println(str);
		}
		
		return "home";
	}
	
	@RequestMapping("/bakedBean/init")
	@ResponseBody
	public String init() {
		try {
			String initTable = "create table IF NOT EXISTS bakedbean (id bigint, name varchar(100), constraint pk primary key (id))";
			Map<String,String> map = new HashMap<String,String>();
			map.put("init_table", initTable);
			sqlSession.selectOne("BakedBean.createTable", map);
		} catch (Exception e) {
			e.printStackTrace();
			return "caught Error";
		}
		return "table 'BakedBean' init";
	}
	
	@RequestMapping("/bakedBean/testData")
	@ResponseBody
	public String testData() {
		String[] beanNames = {"GreenBean", "RedBean", "WhiteBean", "BlackBean"};
		BakedBean param = new BakedBean();
		try {
			for(int i = 0; i < 100; i++) {
				param.setId(i);
				param.setName(beanNames[i % 4]);
				sqlSession.insert("BakedBean.insertOne", param);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "create test data";
	}
	
	@RequestMapping("/bakedBean/select")
	@ResponseBody
	public String select(@RequestParam(value="id", required=false)String id) {
		List<BakedBean> result;
		if ("".equals(id) || id == null) {
			result = sqlSession.selectList("BakedBean.select", null); 
		} else {
			result = sqlSession.selectList("BakedBean.select", Long.parseLong(id));
		}

		StringBuffer sb = new StringBuffer();
		result.stream().forEach(data -> {
			sb.append(data.toString());
			sb.append("<br/>");
		});
		
		return sb.toString();
	}
	
	
	
}
