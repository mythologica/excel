package org.example.common.freemarker;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.Builder;

import java.io.*;
import java.util.Map;

public class FTLParser {
	private Configuration cfg;
	private static final  String DEFAULT_ENCODING = "UTF-8";

	public FTLParser(String path) throws IOException {
		this.cfg = getConfig(path);
	}


	private Configuration getConfig(String path) throws IOException {
		Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		// 절대경로
		FileTemplateLoader loader = new FileTemplateLoader(new File(path));
		// 클래스 경로 이용
		//ClassTemplateLoader loader = new ClassTemplateLoader(getClass(), "/resources/templates");
		cfg.setDefaultEncoding(DEFAULT_ENCODING);
		cfg.setTemplateLoader(loader);
		return cfg;
	}

	public String parse(String templateName, String lang, Map<String, Object> params) {
		/* ref: https://soft.plusblog.co.kr/99
		 *  https://m.blog.naver.com/cana01/221460866010
		 * */
		params.put("lang", lang);
		String result = "";
		StringWriter w = new StringWriter();
		try {
			Template template = cfg.getTemplate(templateName + "_" + lang + ".ftl");
			// Map 객체를 이용해서 템플릿 처리
			template.process(params, w);
			result = w.getBuffer().toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				w.flush();
				w.close();
			} catch (Exception e) {
				//NO work
			}
		}
		return result;
	}
}
