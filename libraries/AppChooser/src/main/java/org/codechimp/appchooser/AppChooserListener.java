package org.codechimp.appchooser;

public interface AppChooserListener {
	public void onAppChooserSelected(AppItem value);
	public void onAppChooserCancel();
}
