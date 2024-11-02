package docx.converter;

import org.docx4j.XmlUtils;
import org.docx4j.convert.in.xhtml.DivToSdt;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author soumik.sarker
 * @since 10/24/24
 **/
public class xHtmlToDocxConverter {

    private static Logger log = LoggerFactory.getLogger(xHtmlToDocxConverter.class);

    private static String XHTML_FILE_PATH = "/tmp/xhtml/BasicComponents.xhtml";
    private static String OUTPUT_FILE_PATH = "/tmp/Output.docx";

    public static void main() throws Exception {

        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
        XHTMLImporterImpl importer = new XHTMLImporterImpl(wordMLPackage);

        importer.setDivHandler(new DivToSdt());

        String XHTML_CONTENT = getXHTMLFileContent(XHTML_FILE_PATH);

        System.out.printf(XHTML_CONTENT);

        wordMLPackage.getMainDocumentPart().getContent().addAll(
                importer.convert(XHTML_CONTENT, null) );

        System.out.println(XmlUtils.marshaltoString(wordMLPackage
                .getMainDocumentPart().getJaxbElement(), true, true));

        wordMLPackage.save(new java.io.File(OUTPUT_FILE_PATH));
    }

    public static String getXHTMLFileContent(String filePath) throws Exception {
        File file = new File(filePath);
        byte[] data = new byte[(int) file.length()];
        InputStream stream = null;

        try {
            stream = new BufferedInputStream(new FileInputStream(file));
            stream.read(data);
        } catch (IOException e) {
            log.error(e.toString(), e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    log.error("Error closing InputStream", e);
                }
            }
        }

        return new String(data);
    }

}