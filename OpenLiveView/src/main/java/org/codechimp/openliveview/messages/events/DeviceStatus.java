package org.codechimp.openliveview.messages.events;

import java.nio.ByteBuffer;

import org.codechimp.openliveview.messages.LiveViewEvent;
import org.codechimp.openliveview.messages.MessageConstants;

public class DeviceStatus extends LiveViewEvent {

    private byte status;

    public byte getStatus() {
        return status;
    }

    public DeviceStatus() {
        super(MessageConstants.MSG_DEVICESTATUS);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.openliveview.messages.LiveViewResponse#readData(java.nio.ByteBuffer
     * )
     */
    @Override
    public void readData(ByteBuffer buffer) {
        status = buffer.get();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DeviceStatus = " + status;
    }

}
