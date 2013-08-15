package org.codechimp.openliveview.messages.calls;
//Added by RN+

import org.codechimp.openliveview.messages.LiveViewCall;
import org.codechimp.openliveview.messages.MessageConstants;

public class ClearDisplay extends LiveViewCall {

    public ClearDisplay() {
        super(MessageConstants.MSG_CLEARDISPLAY);
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.openliveview.messages.LiveViewRequest#getPayload()
     */
    @Override
    protected byte[] getPayload() {
        return new byte[]{0};
    }

}
