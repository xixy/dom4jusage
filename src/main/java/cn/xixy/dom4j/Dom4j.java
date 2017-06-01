package cn.xixy.dom4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * Example of Dom4j usage
 *
 */
public class Dom4j {
	public static String xmlfile = "/Users/apple/WorkSpace/dom4j/src/main/resources/test.xml";

	public static void listNode(Element node) {
		System.out.println("当前节点名称：" + node.getName());
		@SuppressWarnings("unchecked")
		List<Attribute> attributes = node.attributes();
		for (Attribute attribute : attributes) {
			System.out.println(attribute.getText() + "|" + attribute.getName() + "|" + attribute.getValue());
		}

		if (!node.getTextTrim().equals(""))
			System.out.println("文本内容为：" + node.getText());

		@SuppressWarnings("unchecked")
		Iterator<Element> it = node.elementIterator();
		while (it.hasNext()) {
			Element e = it.next();
			listNode(e);
		}
	}

	public static void writer(Document document) throws Exception {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(new File(xmlfile)), "UTF-8"),
				format);
		writer.write(document);
		writer.flush();
		writer.close();
	}

	public static void main(String[] args) throws Exception {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(xmlfile));
		Element node = document.getRootElement();
		listNode(node);

		Element element = node.element("红楼梦");
		Attribute attr = element.attribute("id");
		element.remove(attr);
		element.addAttribute("name", "作者");
		Element newElement = element.addElement("朝代");
		newElement.setText("清朝");
		Element author = element.element("作者");
		element.remove(author);
		element.addCDATA("红楼梦，是一部爱情小说");
		writer(document);

	}
}
