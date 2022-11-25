import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

interface Attributes {
    Map<String, String> createAttributesMap(String inputMessage) throws ParserConfigurationException, IOException, SAXException;
}

public class ExternalMessage implements Attributes {
    private static void recParseNode(Node rootNode, Stack<String> nodeNames, HashMap<String, String> results) {
        NodeList childNodes = rootNode.getChildNodes();
        StringBuilder path = new StringBuilder();
        int counter = 1;

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node currentNode = childNodes.item(i);

            // Rekursiva anrop för att nå textnoden
            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                recParseNode(currentNode, nodeNames, results);
            } else if (currentNode.getNodeType() == Node.TEXT_NODE) {
                // Printa inte whitespace eller "tomma noder"
                if (currentNode.getTextContent().trim().length() > 0) {

                    // För att få ut nodens sökväg så börjar vi från textnoden och vandrar uppåt i trädet
                    // Eftersom vi börjar nedifrån och går uppåt blir lifo lämpligt, använder därför en stack
                    Node tempNode = currentNode;
                    while (tempNode != null) {
                        // tempNode är null om noden inte har någon förälder
                        if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                            nodeNames.push(trimNodeName(tempNode.getNodeName()));
                        }
                        tempNode = tempNode.getParentNode();
                    }

                    while (!nodeNames.empty()) {
                        if (nodeNames.size() > 1) {
                            path.append(nodeNames.pop()).append("_");
                        } else {
                            path.append(nodeNames.pop());
                        }
                    }

                    // Kontroll av eventuella nyckeldubletter
                    while (results.containsKey(path.toString())) {
                        counter++;
                        path.append("_").append(counter);
                    }

                    results.put(path.toString(), currentNode.getTextContent());
                }
            }

        }
    }

    private static String trimNodeName(String nodeName) {
        return nodeName.replaceFirst("^cal:", "");
    }

    public Map<String, String> createAttributesMap(String inputMessage) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
        Document doc = documentBuilder.parse(new InputSource(new StringReader(inputMessage)));
        Node rootNode = doc.getDocumentElement();
        HashMap<String, String> results = new HashMap<String, String>();
        Stack<String> nodeNames = new Stack<String>();

        recParseNode(rootNode, nodeNames, results);

        return results;
    }
}