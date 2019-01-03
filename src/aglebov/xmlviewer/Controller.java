package aglebov.xmlviewer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Controller {

    @FXML
    AnchorPane root;

    @FXML
    TreeView<Element> treeView;

    @FXML
    TextArea textArea;

    private final class CustomTreeCell extends TreeCell<Element> {

        public CustomTreeCell() {
        }

        @Override
        public void updateItem(Element item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                setText(item.getTagName());
            }
        }
    }

    public void initialize() {
        treeView.setCellFactory((TreeView<Element> node) -> {
            TreeCell<Element> cell = new CustomTreeCell();
            cell.setOnMouseClicked((MouseEvent event) ->
                textArea.setText(XmlPrinter.prettyPrint(cell.getTreeItem().getValue()))
            );
            return cell;
        });
    }

    @FXML
    protected void openFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(selectedFile);
            treeView.setRoot(toTree(doc));

        } catch (Exception e) {
            System.err.println("Failed to parse the file: " + selectedFile.getAbsolutePath() + " " + e);
        }
    }

    private static TreeItem<Element> toTree(Document document) {
        return toTree(document.getDocumentElement());
    }

    private static TreeItem<Element> toTree(Element element) {
        TreeItem<Element> item = new TreeItem<>();
        item.setExpanded(true);
        item.setValue(element);
        for (Node node : XmlUtils.toIterable(element.getChildNodes())) {
            if (node instanceof Element) {
                item.getChildren().add(toTree((Element) node));
            }
        }
        return item;
    }
}
