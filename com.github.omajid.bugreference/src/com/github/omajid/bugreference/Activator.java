package com.github.omajid.bugreference;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class Activator extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "com.github.omajid.bugreference"; // $NON-NLS-1$

	private static Activator sharedInstance;

	public Activator() {
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		sharedInstance = this;
	}

	public void stop(BundleContext context) throws Exception {
		sharedInstance = null;
		super.stop(context);
	}

	public static Activator getDefault() {
		return sharedInstance;
	}

	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

}
