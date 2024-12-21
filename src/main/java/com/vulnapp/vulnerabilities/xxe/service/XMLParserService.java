package com.vulnapp.vulnerabilities.xxe.service;

import org.springframework.stereotype.Service;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import java.io.ByteArrayInputStream;

@Service
public class XMLParserService {

    public String parseXML(String xmlContent) {
        try {
            // XML 파서 설정 (외부 개체 참조 허용)
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", true);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", true);
            factory.setExpandEntityReferences(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xmlContent.getBytes()));

            // input 태그내용 파싱
            var element = doc.getElementsByTagName("input").item(0);
            if (element == null) {
                return "Error: Required XML element 'input' not found";
            }

            // 입력된 XML 데이터 디버깅 출력
            System.out.println("Parsed Document: " + doc.getDocumentElement().getTextContent());

            String parsedContent = element.getTextContent();
            System.out.println("Parsed XML content: " + parsedContent);

            return parsedContent;
        } catch (Exception e) {
            return "Error parsing XML: " + e.getMessage();
        }
    }
}
