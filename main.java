import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;

public class main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        ExternalMessage em = new ExternalMessage();
        String inputMessageAddMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<cal:addMessage xmlns:cal=\"http://klockren.se/version/1.0/schemas/integration/callback\">\n" +
                "\t<cal:flowInstanceID>1337</cal:flowInstanceID>\n" +
                "    <cal:externalID>\n" +
                "      <cal:ID>T800</cal:ID>\n" +
                "      <cal:System>Skynet</cal:System>\n" +
                "    </cal:externalID>\n" +
                "    <cal:message>\n" +
                "      <cal:added>2022-09-09T11:57:11.9283614+02:00</cal:added>\n" +
                "      <cal:attachments>\n" +
                "          <cal:encodedData>a2FuZWxidWxsZQ==</cal:encodedData>\n" +
                "          <cal:filename>bulle.txt</cal:filename>\n" +
                "          <cal:size>11</cal:size>\n" +
                "      </cal:attachments>\n" +
                "      <cal:attachments>\n" +
                "          <cal:encodedData>YmFuYW4=</cal:encodedData>\n" +
                "          <cal:filename>frukt.txt</cal:filename>\n" +
                "          <cal:size>12</cal:size>\n" +
                "      </cal:attachments>\n" +
                "      <cal:message>Detta är ett testmeddelande.</cal:message>\n" +
                "      <cal:userID>TestUser001</cal:userID>\n" +
                "      <cal:readReceiptEnabled>false</cal:readReceiptEnabled>\n" +
                "    </cal:message>\n" +
                "    <cal:principal>\n" +
                "      <cal:name>Dwight Shrute</cal:name>\n" +
                "      <cal:userID>dwishu</cal:userID>\n" +
                "    </cal:principal>\n" +
                "</cal:addMessage>";

        Map<String, String> results = em.createAttributesMap(inputMessageAddMessage);
        System.out.println(results);
    }
}