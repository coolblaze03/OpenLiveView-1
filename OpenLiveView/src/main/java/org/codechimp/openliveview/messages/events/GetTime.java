package org.codechimp.openliveview.messages.events;

import java.nio.ByteBuffer;

import org.codechimp.openliveview.messages.LiveViewEvent;
import org.codechimp.openliveview.messages.MessageConstants;

public class GetTime extends LiveViewEvent {

    public GetTime() {
        super(MessageConstants.MSG_GETTIME);
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
