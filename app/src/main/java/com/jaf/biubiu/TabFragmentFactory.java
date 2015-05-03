package com.jaf.biubiu;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class TabFragmentFactory {

	public TabFragmentFactory() {
	}

	public static Fragment createMain(int tabIndex, Bundle arg) {
		Fragment tab = null;
		switch (tabIndex) {
		case 0:
			tab = FragmentNearby.newInstance(arg);
			break;
		case 1:
			tab = FragmentUnion.newInstance(arg);
			break;
		case 2:
			tab = FragmentMessage.newInstance(arg);
			break;
		case 3:
			tab = FragmentMe.newInstance(arg);
			break;
		default:
			break;
		}
		return tab;
	}
}
