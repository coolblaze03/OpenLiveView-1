package org.codechimp.openliveview.messages.calls;

import org.codechimp.openliveview.messages.LiveViewCall;
import org.codechimp.openliveview.messages.MessageConstants;

/**
 * @author Xperimental
 */
public class MessageAck extends LiveViewCall {

    private final byte ackMsgId;

    public MessageAck(byte ackMsgId) {
        super(MessageConstants.MSG_ACK);
        this.ackMsgId = ackMsgId;
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.openliveview.messages.LiveViewRequest#getPayload()
     */
    @Override
    protected byte[] getPayload() {
        return new byte[]{ackMsgId};
    }

}
