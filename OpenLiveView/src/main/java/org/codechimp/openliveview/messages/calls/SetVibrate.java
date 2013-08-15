package org.codechimp.openliveview.messages.calls;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.codechimp.openliveview.messages.LiveViewCall;
import org.codechimp.openliveview.messages.MessageConstants;
import org.codechimp.openliveview.messages.UShort;

public class SetVibrate extends LiveViewCall {

    private final UShort delay;
    private final UShort time;

    public SetVibrate(int delay, int time) {
        super(MessageConstants.MSG_SETVIBRATE);
        this.delay = new UShort((short) delay);
        this.time = new UShort((short) time);
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.openliveview.messages.LiveViewRequest#getPayload()
     */
    @Override
    protected byte[] getPayload() {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putShort(delay.getValue());
        buffer.putShort(time.getValue());
        return buffer.array();
    }
}
