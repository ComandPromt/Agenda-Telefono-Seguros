package utils;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class HtmlGenerator {

	public static String generate(String template, Map<String, Object> variables) throws Exception {

		Configuration config = new Configuration();
		Template tp = config.getTemplate(template);

		StringWriter stringWriter = new StringWriter();
		BufferedWriter writer = new BufferedWriter(stringWriter);
		tp.setEncoding("UTF-8");

		tp.process(variables, writer);
		String htmlStr = stringWriter.toString();
		writer.flush();
		writer.close();
		return htmlStr;
	}

}
