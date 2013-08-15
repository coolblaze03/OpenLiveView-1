package org.codechimp.openliveview.messages.calls;

import org.codechimp.openliveview.messages.LiveViewCall;
import org.codechimp.openliveview.messages.MessageConstants;

public class DeviceStatusAck extends LiveViewCall {

    public DeviceStatusAck() {
        super(MessageConstants.MSG_DEVICESTATUS_ACK);
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.openliveview.messages.LiveViewRequest#getPayload()
     */
    @Override
    protected byte[] getPayload() {
        return new byte[]{MessageConstants.RESULT_OK};
    }

}
