package org.example.common.freemarker;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.File;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class FTLParser {
	private static Configuration cfg;


	private static final  String DEFAULT_ENCODING = "UTF-8";

	public FTLParser(String path) throws Exception {
		initConfig(path);
	}

	private void initConfig(String path) throws Exception {
		this.cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		//this.cfg = new Configuration(Configuration.VERSION_2_3_32);
		//this.cfg = new Configuration(Configuration.getVersion());
		this.cfg.setEncoding(Locale.getDefault(),DEFAULT_ENCODING);
//		this.cfg.setSQLDateAndTimeTimeZone(TimeZone.getDefault());

		// 절대경로
		//this.cfg.setTemplateLoader(new FileTemplateLoader(new File(path)));

		// 클래스 경로 이용
		this.cfg.setClassForTemplateLoading(FTLParser.class, "/templates/");
	}

	public String parse(String templateName, String lang, Map<String, Object> params) throws Exception {

		/* ref: https://soft.plusblog.co.kr/99
		 *  https://m.blog.naver.com/cana01/221460866010
		 * */
		params.put("lang", lang);
		String result = "";
		StringWriter w = new StringWriter();
		try {
			Template template = this.cfg.getTemplate(templateName+"_"+lang+".ftl");
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
