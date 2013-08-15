package org.codechimp.openliveview.messages.calls;
//Added by RN+
//Sets brightness of LiveView display

import org.codechimp.openliveview.messages.LiveViewCall;
import org.codechimp.openliveview.messages.MessageConstants;

public class SetScreenMode extends LiveViewCall {

    private final byte brightness;

    public SetScreenMode(byte brightness) {
        super(MessageConstants.MSG_SETSCREENMODE);
        this.brightness = brightness;
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.openliveview.messages.LiveViewRequest#getPayload()
     */
    @Override
    protected byte[] getPayload() {
        return new byte[]{brightness};
    }

}
