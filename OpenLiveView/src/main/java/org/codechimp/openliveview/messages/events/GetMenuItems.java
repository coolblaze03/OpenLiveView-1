package org.codechimp.openliveview.messages.events;

import java.nio.ByteBuffer;

import org.codechimp.openliveview.messages.LiveViewEvent;
import org.codechimp.openliveview.messages.MessageConstants;

public class GetMenuItems extends LiveViewEvent {

    public GetMenuItems() {
        super(MessageConstants.MSG_GETMENUITEMS);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.openliveview.messages.LiveViewResponse#readData(java.nio.ByteBuffer
     * )
     */
    @Override
    public void readData(ByteBuffer buffer) {
    }

}
