package common.util.pdfUtil;
import com.itextpdf.text.Image;
import com.itextpdf.text.Element;
import com.itextpdf.tool.xml.NoCustomContextException;
import com.itextpdf.tool.xml.Tag;
import com.itextpdf.tool.xml.WorkerContext;
import com.itextpdf.tool.xml.exceptions.RuntimeWorkerException;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.itextpdf.tool.xml.html.HTML;
import java.util.ArrayList;
import com.itextpdf.text.pdf.codec.Base64;
import java.util.List;
import java.util.Map;
import com.itextpdf.text.Chunk;

/**
 * @author snail
 * 2020/8/11 2:51
 */
public class ZrgImageTagProcessor extends  com.itextpdf.tool.xml.html.Image {

    @Override
    public List<Element> end(WorkerContext ctx, Tag tag, List<Element> currentContent) {
//        return super.end(ctx, tag, currentContent);
        final Map<String, String> attributes = tag.getAttributes();
        String src = attributes.get(HTML.Attribute.SRC);
        List<Element> elements = new ArrayList<Element>(1);
        if (null != src && src.length() > 0) {
            Image img = null;
            if (src.trim().startsWith("data:image/")) {
                final String base64Data = src.substring(src.indexOf(",") + 1);
                try {
                    img = Image.getInstance(Base64.decode(base64Data));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (img != null) {
                    try {
                        final HtmlPipelineContext htmlPipelineContext = getHtmlPipelineContext(ctx);
                        elements.add(getCssAppliers().apply(new Chunk((com.itextpdf.text.Image) getCssAppliers().apply(img, tag, htmlPipelineContext), 0, 0, true), tag,
                                htmlPipelineContext));

                        com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext htmlPipelineContext1 = getHtmlPipelineContext(ctx);
                        elements.add(getCssAppliers().apply(new Chunk((com.itextpdf.text.Image)getCssAppliers().apply(img, tag, htmlPipelineContext1), 0.0F, 0.0F, true), tag, htmlPipelineContext1));

                    } catch (NoCustomContextException e) {
                        throw new RuntimeWorkerException(e);
                    }
                }
            }
            if (img == null) {
                elements = super.end(ctx, tag, currentContent);
            }
        }
        return elements;
    }

}
