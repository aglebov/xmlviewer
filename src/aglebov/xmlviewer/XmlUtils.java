package aglebov.xmlviewer;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class XmlUtils {

    public static Iterable<Node> toIterable(NodeList nodeList) {
        return () -> new NodeListIterator(nodeList);
    }

    public static List<Node> toList(NodeList nodeList) {
        List<Node> result = new ArrayList<>(nodeList.getLength());
        for (int i = 0; i < nodeList.getLength(); i++) {
            result.add(nodeList.item(i));
        }
        return result;
    }

    public static List<Element> filterElements(List<Node> nodes) {
        return nodes.stream()
                .filter(node -> node instanceof Element)
                .map(node -> (Element) node)
                .collect(Collectors.toList());
    }
}
