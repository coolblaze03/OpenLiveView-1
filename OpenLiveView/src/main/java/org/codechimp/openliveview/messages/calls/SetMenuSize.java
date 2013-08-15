package org.codechimp.openliveview.messages.calls;

import org.codechimp.openliveview.messages.LiveViewCall;
import org.codechimp.openliveview.messages.MessageConstants;

public class SetMenuSize extends LiveViewCall {

    private final byte menuSize;

    public SetMenuSize(byte menuSize) {
        super(MessageConstants.MSG_SETMENUSIZE);
        this.menuSize = menuSize;
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.openliveview.messages.LiveViewRequest#getPayload()
     */
    @Override
    protected byte[] getPayload() {
        return new byte[]{menuSize};
    }

}
