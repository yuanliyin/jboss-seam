package org.jboss.seam.wiki.core.links;

import java.util.List;

/**
 * Called by the WikiTextParser to render [A Link=>Target] and [<=MacroName].
 *
 * @author Christian Bauer
 */
public interface WikiTextRenderer {

    public String renderInlineLink(WikiLink inlineLink);
    public String renderExternalLink(WikiLink externalLink);
    public String renderThumbnailImageInlineLink(WikiLink inlineLink);
    public String renderFileAttachmentLink(int attachmentNumber, WikiLink attachmentLink);

    public void setAttachmentLinks(List<WikiLink> attachmentLinks);
    public void setExternalLinks(List<WikiLink> externalLinks);

    public String renderMacro(String macroName);

}
