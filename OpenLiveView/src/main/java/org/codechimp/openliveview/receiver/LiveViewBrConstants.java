package org.codechimp.openliveview.receiver;

/**
 * Broadcast constants
 *
 * @author Renze Nicolai
 */

public final class LiveViewBrConstants {
    //Received by openliveview receiver
    public static final String ALERT_ADD = "org.codechimp.openliveview.alert.add";

    //Received by openliveview service
    public static final String ALERT_NOTIFY = "org.codechimp.openliveview.alert.notify";
    public static final String PLUGIN_COMMAND = "org.codechimp.openliveview.plugin.command";

    //Transmitted to other applications
    public static final String PLUGIN_RESULT = "org.codechimp.openliveview.plugin.result";

    //Other constants
    public static final String OTHER_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
}
