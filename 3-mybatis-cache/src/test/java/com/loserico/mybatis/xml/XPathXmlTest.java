package com.loserico.mybatis.xml;

import lombok.SneakyThrows;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.parsing.PropertyParser;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.Reader;

/**
 * <p>
 * Copyright: (C), 2021-01-10 12:18
 * <p>
 * <p>
 * Company: Information & Data Security Solutions Co., Ltd.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class XPathXmlTest {
	
	private Document document;
	
	@SneakyThrows
	@Before
	public void before() {
		String resource = "mybatis-config.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(false);
		factory.setIgnoringComments(true);
		factory.setIgnoringElementContentWhitespace(false);
		factory.setCoalescing(false);
		factory.setExpandEntityReferences(true);
		
		DocumentBuilder builder = factory.newDocumentBuilder();
		builder.setEntityResolver(new XMLMapperEntityResolver());
		
		//document 表示整个文档（DOM 树的根节点）
		document = builder.parse(new InputSource(reader));
	}
	
	@SneakyThrows
	@Test
	public void testConfigurationNode() {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xPath = factory.newXPath();
		//node 表示一个节点
		Node configurationNode = (Node) xPath.evaluate("/configuration", document, XPathConstants.NODE);
		System.out.println(toStringNode(configurationNode));
	}
	
	private static String toStringNode(Node node) {
		StringBuilder sb = new StringBuilder();
		sb.append("<")
				.append(node.getNodeName());
		
		NamedNodeMap attributes = node.getAttributes();
		if (attributes != null) {
			for (int i = 0; i < attributes.getLength(); i++) {
				Node attribute = attributes.item(i);
				String value = PropertyParser.parse(attribute.getNodeValue(), null);
				sb.append(" ");
				sb.append(attribute.getNodeName());
				sb.append("=\"");
				sb.append(value);
				sb.append("\"");
			}
		}
		
		NodeList childNodes = node.getChildNodes();
		if (childNodes != null) {
			sb.append(childNodes.getLength()==0?">":">\n");
			for (int i = 0, n = childNodes.getLength(); i < n; i++) {
				Node cNode = childNodes.item(i);
				if (cNode.getNodeType() == Node.ELEMENT_NODE) {
					//System.out.println(cNode.getNodeName()+":"+ cNode.getNodeValue());
					
					sb.append(toStringNode(cNode))  ;
				}
			}
			
			sb.append("</");
			sb.append(node.getNodeName());
			sb.append(">");
		}
		sb.append("\n");
		return  sb.toString();
	}
}
