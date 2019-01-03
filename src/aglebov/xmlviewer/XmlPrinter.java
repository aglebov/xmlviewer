package aglebov.xmlviewer;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import java.util.List;

public class XmlPrinter {

    public static String prettyPrint(Element element) {
        StringBuilder stringBuilder = new StringBuilder();
        prettyPrint(element, stringBuilder, "");
        return stringBuilder.toString();
    }

    private static void prettyPrint(Element element, StringBuilder stringBuilder, String indent) {
        List<Element> childElements = XmlUtils.filterElements(XmlUtils.toList(element.getChildNodes()));

        stringBuilder.append(indent);

        stringBuilder.append('<');

        if (element.getPrefix() != null && !element.getPrefix().isEmpty()) {
            stringBuilder.append(element.getPrefix());
            stringBuilder.append(':');
        }
        stringBuilder.append(element.getTagName());

        NamedNodeMap attributesMap = element.getAttributes();
        for (int i = 0; i < attributesMap.getLength(); i++) {
            Attr attribute = (Attr) attributesMap.item(i);
            stringBuilder.append(' ');
            if (attribute.getPrefix() != null && !attribute.getPrefix().isEmpty()) {
                stringBuilder.append(attribute.getPrefix());
                stringBuilder.append(':');
            }
            stringBuilder.append(attribute.getName());
            stringBuilder.append('=');
            stringBuilder.append('"');
            stringBuilder.append(attribute.getValue());
            stringBuilder.append('"');
        }

        if (childElements.isEmpty()) {
            stringBuilder.append("/>");
            stringBuilder.append('\n');
            return;
        }

        stringBuilder.append('>');

        stringBuilder.append('\n');

        for (Element child: childElements) {
            prettyPrint(child, stringBuilder, indent + "\t");
        }

        stringBuilder.append(indent);

        stringBuilder.append("</");

        if (element.getPrefix() != null && !element.getPrefix().isEmpty()) {
            stringBuilder.append(element.getPrefix());
            stringBuilder.append(':');
        }
        stringBuilder.append(element.getTagName());

        stringBuilder.append('>');

        stringBuilder.append('\n');
    }
}
