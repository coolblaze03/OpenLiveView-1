package org.codechimp.openliveview.messages.calls;

import org.codechimp.openliveview.messages.LiveViewCall;
import org.codechimp.openliveview.messages.MessageConstants;

/**
 * @author Xperimental
 */
public class NavigationResponse extends LiveViewCall {

    private byte response;

    public NavigationResponse(byte response) {
        super(MessageConstants.MSG_NAVIGATION_RESP);
        this.response = response;
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.openliveview.messages.LiveViewRequest#getPayload()
     */
    @Override
    protected byte[] getPayload() {
        return new byte[]{response};
    }

}
