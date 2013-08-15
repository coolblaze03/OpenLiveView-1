package org.codechimp.openliveview.messages.calls;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.codechimp.openliveview.messages.EncodingException;
import org.codechimp.openliveview.messages.LiveViewCall;
import org.codechimp.openliveview.messages.MessageConstants;

public class CapsRequest extends LiveViewCall {

    public CapsRequest() {
        super(MessageConstants.MSG_GETCAPS);
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.openliveview.LiveViewMessage#getPayload()
     */
    @Override
    protected byte[] getPayload() {
        try {
            byte[] version = MessageConstants.CLIENT_SOFTWARE_VERSION
                    .getBytes("iso-8859-1");
            byte msgLength = (byte) version.length;
            ByteBuffer buffer = ByteBuffer.allocate(msgLength + 1);
            buffer.order(ByteOrder.BIG_ENDIAN);
            buffer.put(msgLength);
            buffer.put(version);
            return buffer.array();
        } catch (UnsupportedEncodingException e) {
            throw new EncodingException(e);
        }
    }
}
