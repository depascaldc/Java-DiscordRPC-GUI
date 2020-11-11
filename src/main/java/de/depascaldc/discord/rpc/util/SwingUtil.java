/**
 *   Copyright © 2020 | depascaldc <https://depascaldc.xyz/> | Discord: [depascaldc]#4093
 *   
 *    ____  _                       _       ____  ____   ____ 
 *   |  _ \(_)___  ___ ___  _ __ __| |     |  _ \|  _ \ / ___|
 *   | | | | / __|/ __/ _ \| '__/ _` |_____| |_) | |_) | |    
 *   | |_| | \__ \ (_| (_) | | | (_| |_____|  _ <|  __/| |___ 
 *   |____/|_|___/\___\___/|_|  \__,_|     |_| \_\_|    \____|
 *                                                         
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *   
 */

package de.depascaldc.discord.rpc.util;

import de.depascaldc.discord.rpc.util.exceptions.ExceptionUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

public class SwingUtil {

	static long lastMouseClicked;

	public static boolean canClickAgain() {
		long clickTS = new Date().getTime();
		if (lastMouseClicked > 0) {
			if (clickTS - lastMouseClicked >= 250) {
				lastMouseClicked = clickTS;
			} else
				return false;
		} else
			lastMouseClicked = clickTS;
		return true;
	}

	public static Image getImage(String iconPath) {
		return Toolkit.getDefaultToolkit().getImage(SwingUtil.class.getResource(iconPath));
	}

	public static ImageIcon newImageIcon(String iconPath) {
		return new ImageIcon(getImage(iconPath));
	}

	public static void invokeLater(Runnable runnable) {
		if (SwingUtilities.isEventDispatchThread()) {
			runnable.run();
		} else {
			SwingUtilities.invokeLater(runnable);
		}
	}

	public static void installGtkPopupBugWorkaround() {
		LookAndFeel laf = UIManager.getLookAndFeel();
		Class<?> lafClass = laf.getClass();
		if (!lafClass.getName().equals("com.sun.java.swing.plaf.gtk.GTKLookAndFeel"))
			return;
		try {
			Field field = lafClass.getDeclaredField("styleFactory");
			@SuppressWarnings("deprecation")
			boolean accessible = field.isAccessible();
			field.setAccessible(true);
			Object styleFactory = field.get(laf);
			field.setAccessible(accessible);
			Object style = getGtkStyle(styleFactory, new JPopupMenu(), "POPUP_MENU");
			fixGtkThickness(style, "yThickness");
			fixGtkThickness(style, "xThickness");
			style = getGtkStyle(styleFactory, new JSeparator(), "POPUP_MENU_SEPARATOR");
			fixGtkThickness(style, "yThickness");
		} catch (Exception e) {
			assert ExceptionUtil.printStackTrace(e);
		}
	}

	private static void fixGtkThickness(Object style, String fieldName) throws Exception {
		Field field = style.getClass().getDeclaredField(fieldName);
		@SuppressWarnings("deprecation")
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		field.setInt(style, Math.max(1, field.getInt(style)));
		field.setAccessible(accessible);
	}

	@SuppressWarnings({ "deprecation" })
	private static Object getGtkStyle(Object styleFactory, JComponent component, String regionName) throws Exception {
		// Create the region object
		Class<?> regionClass = Class.forName("javax.swing.plaf.synth.Region");
		Field field = regionClass.getField(regionName);
		Object region = field.get(regionClass);

		// Get and return the style
		Class<?> styleFactoryClass = styleFactory.getClass();
		Method method = styleFactoryClass.getMethod("getStyle", JComponent.class, regionClass);
		boolean accessible = method.isAccessible();
		method.setAccessible(true);
		Object style = method.invoke(styleFactory, component, region);
		method.setAccessible(accessible);
		return style;
	}

	public static Action newAction(String name, boolean enable, ActionListener listener) {
		Action action = new AbstractAction(name) {
			private static final long serialVersionUID = 6095788559260780630L;

			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				listener.actionPerformed(actionEvent);
			}
		};
		action.setEnabled(enable);
		return action;
	}

	public static Action newAction(String name, ImageIcon icon, boolean enable, ActionListener listener) {
		Action action = newAction(name, enable, listener);
		action.putValue(Action.SMALL_ICON, icon);
		return action;
	}

	public static Action newAction(ImageIcon icon, boolean enable, ActionListener listener) {
		Action action = newAction(null, icon, enable, listener);
		action.putValue(Action.SMALL_ICON, icon);
		return action;
	}

	public static Action newAction(String name, ImageIcon icon, boolean enable, String shortDescription,
			ActionListener listener) {
		Action action = newAction(name, icon, enable, listener);
		action.putValue(Action.SHORT_DESCRIPTION, shortDescription);
		return action;
	}

	public static Action newAction(String name, boolean enable, String shortDescription, ActionListener listener) {
		Action action = newAction(name, enable, listener);
		action.putValue(Action.SHORT_DESCRIPTION, shortDescription);
		return action;
	}
}
