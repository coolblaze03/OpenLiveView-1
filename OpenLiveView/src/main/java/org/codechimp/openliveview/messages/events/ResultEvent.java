package org.codechimp.openliveview.messages.events;

import java.nio.ByteBuffer;

import org.codechimp.openliveview.messages.LiveViewEvent;

public class ResultEvent extends LiveViewEvent {

    private byte result;

    public byte getResult() {
        return result;
    }

    public ResultEvent(byte id) {
        super(id);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.openliveview.messages.LiveViewResponse#readData(java.nio.ByteBuffer
     * )
     */
    @Override
    public void readData(ByteBuffer buffer) {
        result = buffer.get();
    }

}
