package common.util.pdfUtil;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.NoCustomContextException;
import com.itextpdf.tool.xml.Tag;
import com.itextpdf.tool.xml.WorkerContext;
import com.itextpdf.tool.xml.html.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author snail
 * 2020/8/10 2:38
 */
public class ImageTagProcessor extends Image {


    public ImageTagProcessor()
    {
    }

    public List end(WorkerContext ctx, Tag tag, List currentContent)
    {
        Map attributes = tag.getAttributes();
        String src = (String)attributes.get("src");
        List elements = new ArrayList(1);
        if(src != null && src.length() > 0)
        {
            com.itextpdf.text.Image img = null;
            if(src.startsWith("data:image/"))
            {
                String base64Data = src.substring(src.indexOf(",") + 1);
                try
                {
                    img = com.itextpdf.text.Image.getInstance(Base64.decode(base64Data));
                }
                catch(Throwable e)
                {
                    e.printStackTrace();
                }
                if(img != null)
                    try
                    {
                        com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext htmlPipelineContext = getHtmlPipelineContext(ctx);
                        elements.add(getCssAppliers().apply(new Chunk((com.itextpdf.text.Image)getCssAppliers().apply(img, tag, htmlPipelineContext), 0.0F, 0.0F, true), tag, htmlPipelineContext));
                    }
                    catch(NoCustomContextException e)
                    {
                        e.printStackTrace();
                    }
            }
            if(img == null)
                elements = super.end(ctx, tag, currentContent);
        }
        return elements;
    }
}
