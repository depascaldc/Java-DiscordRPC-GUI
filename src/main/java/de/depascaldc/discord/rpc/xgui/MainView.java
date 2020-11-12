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

package de.depascaldc.discord.rpc.xgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.depascaldc.discord.rpc.dc.RichPresenceManager;
import de.depascaldc.discord.rpc.util.*;
import de.depascaldc.discord.rpc.util.platform.PlatformService;
import de.depascaldc.discord.rpc.xgui.actions.*;
import de.depascaldc.discord.rpc.xgui.components.*;
import de.depascaldc.discord.rpc.xgui.content.GuiContent;
import javax.swing.border.LineBorder;

public class MainView {

	private MouseListener actionLogoClick;
	private MouseListener actionFireRPC;
	private MouseListener presenceViewUpdate;

	private ActionListener exitActionListener;
	private ActionListener webSiteActionListener;
	private ActionListener issuesActionListener;

	private JFrame frame;

	private RichPresenceManager rpcManager;
	private GuiContent guiContent;

	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;

	private Elements.JSwitchBox slider;

	private JPanel previewPanel;
	private JPanel presencePreviewPanel;
	private JTextField textField_7;

	private JLabel clockLabel;
	private Thread clockUpdateThread;

	public static MainView init(RichPresenceManager rpcManager) {
		return new MainView(rpcManager);
	}

	/**
	 * Create the application.
	 */
	public MainView(RichPresenceManager rpcManager) {
		this.rpcManager = rpcManager;
		this.guiContent = new GuiContent(this);
		listenerSetup();
		initialize();
	}

	private void listenerSetup() {
		actionFireRPC = new FirePresenceAction(this);
		actionLogoClick = new LogoClickListener();
		presenceViewUpdate = new PresenceViewUpdate(this);
		exitActionListener = new QuitActionListener();
		webSiteActionListener = new WebsiteActionListener();
		issuesActionListener = new IssueActionListener();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("deprecation")
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(new Color(192, 192, 192));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Java Discord RPC GUI | Copyright © 2020 - depascaldc");
		frame.setSize(1000, 620);
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(22, 22, 22));
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new AnimatedLabel();
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 556, 966, 9);
		frame.getContentPane().add(lblNewLabel);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 7));
		lblNewLabel.setForeground(new Color(0, 0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(30, 144, 255));
		panel.setBounds(0, 556, 994, 10);
		frame.getContentPane().add(panel);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY.darker());
		panel_1.setBounds(0, 0, 54, 566);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel label = new JLabel(SwingUtil.newImageIcon("/appicon_45.png"));
		label.addMouseListener(actionLogoClick);
		label.setBounds(-8, 12, 70, 70);
		panel_1.add(label);
		JLabel label_1 = new JLabel(SwingUtil.newImageIcon("/settings_default.png"));
		label_1.setBounds(-8, 87, 70, 68);
		panel_1.add(label_1);
		JLabel label_2 = new JLabel(SwingUtil.newImageIcon("/settings_extended.png"));
		label_2.setBounds(-8, 167, 70, 70);
		panel_1.add(label_2);

		boolean browser = InternetBrowser.isSupported();
		int menuShortcutKeyMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

		Action exitAction = SwingUtil.newAction("Quit", true, "Quit this Program", exitActionListener);
		JMenuBar menuBar = new JMenuBar();
		menuBar.setForeground(new Color(192, 192, 192));
		menuBar.setBorderPainted(false);
		menuBar.setBackground(Color.DARK_GRAY);
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		mnFile.setForeground(new Color(192, 192, 192));
		mnFile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		menuBar.add(mnFile);
		JMenuItem mnExit;
		if (!PlatformService.getInstance().isMac()) {
			mnFile.addSeparator();
			mnExit = mnFile.add(exitAction);
			mnExit.setBackground(new Color(0, 0, 0));
			mnExit.setForeground(new Color(192, 192, 192));
			mnExit.setAccelerator(KeyStroke.getKeyStroke('Q', menuShortcutKeyMask));
		} else {
			mnFile.addSeparator();
			mnExit = mnFile.add(exitAction);
			mnExit.setBackground(new Color(0, 0, 0));
			mnExit.setForeground(new Color(192, 192, 192));
			mnExit.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.ALT_MASK));
		}

		Action webSiteAction = SwingUtil.newAction("Website", browser, "Open depascaldc.xyz Website",
				webSiteActionListener);
		Action issuesActionAction = SwingUtil.newAction("Issues", browser, "Open IssuePage", issuesActionListener);
		JMenu mnHelp = new JMenu("Help");
		mnHelp.setForeground(new Color(192, 192, 192));
		mnHelp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		menuBar.add(mnHelp);
		JMenuItem menuItem = mnHelp.add(webSiteAction);
		menuItem.setBackground(new Color(0, 0, 0));
		menuItem.setForeground(new Color(192, 192, 192));
		mnHelp.addSeparator();
		JMenuItem menuItem_1 = mnHelp.add(issuesActionAction);
		menuItem_1.setForeground(new Color(192, 192, 192));
		menuItem_1.setBackground(new Color(0, 0, 0));

		JLabel lblClientId = new JLabel("Enter Client ID");
		lblClientId.setForeground(new Color(192, 192, 192));
		lblClientId.setBounds(72, 12, 128, 15);
		frame.getContentPane().add(lblClientId);
		textField = new JTextField();
		textField.setCaretColor(new Color(192, 192, 192));
		textField.setToolTipText("Enter the Rich Presence Client ID");
		textField.setForeground(new Color(192, 192, 192));
		textField.setBackground(Color.DARK_GRAY);
		textField.setBounds(66, 39, 486, 33);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnFirePresence = new JButton("Fire Presence!");
		btnFirePresence.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnFirePresence.setForeground(new Color(128, 0, 0));
		btnFirePresence.setIcon(SwingUtil.newImageIcon("/fire.png"));
		btnFirePresence.setHorizontalTextPosition(JButton.CENTER);
		btnFirePresence.setVerticalTextPosition(JButton.CENTER);
		btnFirePresence.setBackground(new Color(128, 128, 128));
		btnFirePresence.setBounds(690, 489, 175, 42);
		btnFirePresence.addMouseListener(actionFireRPC);
		frame.getContentPane().add(btnFirePresence);

		JLabel lblNewLabel_1 = new JLabel("Presence Details");
		lblNewLabel_1.setForeground(new Color(192, 192, 192));
		lblNewLabel_1.setBounds(72, 84, 480, 15);
		frame.getContentPane().add(lblNewLabel_1);
		textField_1 = new JTextField();
		textField_1.setCaretColor(new Color(192, 192, 192));
		textField_1.setToolTipText("This are the Presence Details ( can be empty ) ");
		textField_1.setForeground(new Color(192, 192, 192));
		textField_1.setBackground(Color.DARK_GRAY);
		textField_1.setBounds(66, 111, 486, 33);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Presence State");
		lblNewLabel_2.setBackground(Color.DARK_GRAY);
		lblNewLabel_2.setForeground(new Color(192, 192, 192));
		lblNewLabel_2.setBounds(72, 156, 480, 15);
		frame.getContentPane().add(lblNewLabel_2);
		textField_2 = new JTextField();
		textField_2.setCaretColor(new Color(192, 192, 192));
		textField_2.setToolTipText("This is the Presence State ( can be empty )");
		textField_2.setForeground(new Color(192, 192, 192));
		textField_2.setBackground(Color.DARK_GRAY);
		textField_2.setBounds(66, 183, 486, 33);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);

		JLabel lblLargeImageKey = new JLabel("Large Image Key");
		lblLargeImageKey.setForeground(new Color(192, 192, 192));
		lblLargeImageKey.setBounds(72, 228, 480, 15);
		frame.getContentPane().add(lblLargeImageKey);
		textField_3 = new JTextField();
		textField_3.setCaretColor(new Color(192, 192, 192));
		textField_3.setToolTipText(
				"The Large Image key is the key to a picture you have uploaded at the discord developer portal");
		textField_3.setBackground(Color.DARK_GRAY);
		textField_3.setForeground(new Color(192, 192, 192));
		textField_3.setBounds(66, 255, 486, 33);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);

		JLabel lblLargeImageText = new JLabel("Large Image Text");
		lblLargeImageText.setForeground(new Color(192, 192, 192));
		lblLargeImageText.setBounds(72, 300, 480, 15);
		frame.getContentPane().add(lblLargeImageText);
		textField_4 = new JTextField();
		textField_4.setForeground(new Color(192, 192, 192));
		textField_4.setCaretColor(new Color(192, 192, 192));
		textField_4.setBackground(Color.DARK_GRAY);
		textField_4.setToolTipText("This is the text wich will appear when you hover the logo.");
		textField_4.setBounds(66, 327, 486, 33);
		frame.getContentPane().add(textField_4);
		textField_4.setColumns(10);

		JLabel lblSmallImageKey = new JLabel("Small Image Key");
		lblSmallImageKey.setForeground(new Color(192, 192, 192));
		lblSmallImageKey.setBounds(72, 372, 480, 15);
		frame.getContentPane().add(lblSmallImageKey);
		textField_5 = new JTextField();
		textField_5.setCaretColor(new Color(192, 192, 192));
		textField_5.setToolTipText(
				"The Small Image key is the key to a picture you have uploaded at the discord developer portal");
		textField_5.setForeground(new Color(192, 192, 192));
		textField_5.setBackground(Color.DARK_GRAY);
		textField_5.setBounds(66, 399, 486, 33);
		frame.getContentPane().add(textField_5);
		textField_5.setColumns(10);

		JLabel lblSmallImageText = new JLabel("Small Image Text");
		lblSmallImageText.setForeground(new Color(192, 192, 192));
		lblSmallImageText.setBounds(72, 444, 480, 15);
		frame.getContentPane().add(lblSmallImageText);
		textField_6 = new JTextField();
		textField_6.setCaretColor(new Color(192, 192, 192));
		textField_6.setBackground(Color.DARK_GRAY);
		textField_6.setToolTipText("This is the text wich will appear when you hover the logo.");
		textField_6.setForeground(new Color(192, 192, 192));
		textField_6.setBounds(66, 471, 486, 33);
		frame.getContentPane().add(textField_6);
		textField_6.setColumns(10);

		slider = new Elements.JSwitchBox("on", "off");
		if (guiContent.isShowStartTime())
			slider.switchSelected();
		slider.setToolTipText("Rich Presence Start Timestamp");
		slider.setLocation(406, 529);
		slider.setSize(slider.getPreferredSize());
		frame.getContentPane().add(slider);

		JLabel lblRichPresenceStart = new JLabel("Rich Presence Start Timestamp  Enabled?");
		lblRichPresenceStart.setForeground(new Color(192, 192, 192));
		lblRichPresenceStart.setBounds(72, 529, 336, 15);
		frame.getContentPane().add(lblRichPresenceStart);

		// add content to fields
		textField.setText(guiContent.getRpcid());
		textField_1.setText(guiContent.getDetails());
		textField_2.setText(guiContent.getState());
		textField_3.setText(guiContent.getLargeimagekey());
		textField_4.setText(guiContent.getLargeimagetext());
		textField_5.setText(guiContent.getSmallimagekey());
		textField_6.setText(guiContent.getSmallimagetext());

		previewPanel = new JPanel();
		previewPanel.setBackground(new Color(0x2f3136));
		previewPanel.setBounds(663, 89, 223, 370);
		previewPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		frame.getContentPane().add(previewPanel);
		previewPanel.setLayout(null);

		textField_7 = new JTextField();
		textField_7.setBorder(null);
		textField_7.setCaretColor(new Color(192, 192, 192));
		textField_7.setForeground(new Color(192, 192, 192));
		textField_7.setBackground(new Color(0x202225));
		textField_7.setBounds(12, 319, 199, 25);
		previewPanel.add(textField_7);
		textField_7.setColumns(10);

		JPanel panel_2 = new JPanel();
		panel_2.setForeground(new Color(192, 192, 192));
		panel_2.setBackground(new Color(0x7289da));
		panel_2.setBounds(12, 12, 199, 271);
		previewPanel.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblUsername = new JLabel("\nUsername#0000");
		lblUsername.setBounds(27, 12, 144, 141);
		panel_2.add(lblUsername);
		lblUsername.setIcon(SwingUtil.newImageIcon("/discordAvatar.png"));
		lblUsername.setVerticalTextPosition(JLabel.BOTTOM);
		lblUsername.setHorizontalTextPosition(JLabel.CENTER);
		lblUsername.setVerticalAlignment(JLabel.CENTER);
		lblUsername.setHorizontalAlignment(JLabel.CENTER);
		lblUsername.setForeground(new Color(192, 192, 192));

		JLabel lblNote = new JLabel("Note");
		lblNote.setForeground(new Color(192, 192, 192));
		lblNote.setBounds(17, 295, 60, 15);
		previewPanel.add(lblNote);

		JLabel lblRichpresencePreviewShowcase = new JLabel("Presence Preview Showcase");
		lblRichpresencePreviewShowcase.setForeground(new Color(192, 192, 192));
		lblRichpresencePreviewShowcase.setFont(new Font("Fira Code Retina", Font.BOLD, 14));
		lblRichpresencePreviewShowcase.setHorizontalAlignment(SwingConstants.CENTER);
		lblRichpresencePreviewShowcase.setBounds(637, 48, 272, 29);
		frame.getContentPane().add(lblRichpresencePreviewShowcase);

		presencePreviewPanel = new JPanel();
		presencePreviewPanel.setForeground(new Color(192, 192, 192));
		presencePreviewPanel.setBackground(new Color(0x6c82cf));
		presencePreviewPanel.setBounds(0, 165, 199, 106);
		panel_2.add(presencePreviewPanel);
		presencePreviewPanel.setLayout(null);

		JButton btnUpdateView = new JButton("Update View");
		btnUpdateView.setBorder(new LineBorder(Color.BLACK, 2));
		btnUpdateView.setForeground(Color.WHITE);
		btnUpdateView.setHorizontalTextPosition(SwingConstants.CENTER);
		btnUpdateView.setFont(new Font("Cantarell", Font.PLAIN, 10));
		btnUpdateView.setBackground(Color.DARK_GRAY);
		btnUpdateView.setBounds(95, 292, 96, 15);
		btnUpdateView.addMouseListener(presenceViewUpdate);
		previewPanel.add(btnUpdateView);

		this.updatePreview();

		frame.setBackground(Color.BLACK);
		frame.setIconImage(SwingUtil.getImage("/appicon.png"));

		// center window
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);
	}

	public void open() {
		frame.setVisible(true);
	}

	public void updatePreview() {
		SwingUtil.invokeLater(() -> {
			new Thread(() -> {
				guiContent.update();
				presencePreviewPanel.removeAll();
				if (!isEmptyString(guiContent.getRpcid())) {
					repaintPreview();
				}
				presencePreviewPanel.revalidate();
				presencePreviewPanel.repaint();
			}).start();
		});
	}

	private void repaintPreview() {
		JLabel lblPlayingAGame = new JLabel("PLAYING A GAME");
		lblPlayingAGame.setFont(new Font("Cantarell", Font.BOLD, 12));
		lblPlayingAGame.setForeground(new Color(255, 255, 255));
		lblPlayingAGame.setBounds(12, 12, 175, 15);
		presencePreviewPanel.add(lblPlayingAGame);
		if (!isEmptyString(guiContent.getLargeimagekey())) {
			JLabel lblApplicationname = new JLabel("ApplicationName");
			lblApplicationname.setFont(new Font("Cantarell", Font.BOLD, 12));
			lblApplicationname.setForeground(Color.WHITE);
			lblApplicationname.setBounds(76, 30, 123, 15);
			presencePreviewPanel.add(lblApplicationname);
			// paint with images
			JLabel label_3 = new JLabel(SwingUtil.newImageIcon("/appicon_68.png"));
			label_3.setBounds(12, 30, 70, 70);
			presencePreviewPanel.add(label_3);
			if (!isEmptyString(guiContent.getSmallimagekey())) {
				JLabel label_4 = new JLabel(SwingUtil.newImageIcon("/jlogo.png"));
				label_4.setBounds(49, 49, 21, 21);
				label_4.setBackground(new Color(0x6c82cf));
				label_4.setBorder(new RoundedBorder(new Color(0x6c82cf), 10));
				label_4.setOpaque(true);
				label_3.add(label_4);
			}

			if (!isEmptyString(guiContent.getDetails())) {
				JLabel lblThisAreTheDetails = new JLabel(guiContent.getDetails());
				lblThisAreTheDetails.setFont(new Font("Cantarell", Font.PLAIN, 10));
				lblThisAreTheDetails.setForeground(Color.WHITE);
				lblThisAreTheDetails.setBounds(76, 49, 123, 15);
				lblThisAreTheDetails.setToolTipText(guiContent.getDetails());
				presencePreviewPanel.add(lblThisAreTheDetails);
			}

			if (!isEmptyString(guiContent.getState())) {
				JLabel lblThisIsTheState = new JLabel(guiContent.getState());
				lblThisIsTheState.setFont(new Font("Cantarell", Font.PLAIN, 10));
				lblThisIsTheState.setForeground(Color.WHITE);
				lblThisIsTheState.setBounds(76, 67, 111, 15);
				lblThisIsTheState.setToolTipText(guiContent.getState());
				presencePreviewPanel.add(lblThisIsTheState);
			}

			if (guiContent.isShowStartTime()) {

				if (clockUpdateThread != null) {
					try {
						clockUpdateThread.interrupt();
					} catch (Exception e) {
					}
					clockUpdateThread = null;
				}

				clockLabel = new JLabel("00:00:00 elapsed");
				clockLabel.setFont(new Font("Cantarell", Font.PLAIN, 8));
				clockLabel.setForeground(Color.WHITE);
				clockLabel.setBounds(85, 85, 113, 15);
				presencePreviewPanel.add(clockLabel);
				Date start = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
				clockUpdateThread = new Thread(() -> {
					Timer timer = new Timer(1000, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							if (clockLabel != null) {
								Date now = new Date();
								String elapsedString = sdf.format(new Date(now.getTime() - start.getTime()))
										+ " elapsed";
								clockLabel.setText(elapsedString);
							}
						}
					});
					timer.start();
				});
				clockUpdateThread.start();
			}

		} else {
			// paint without images
			if (!isEmptyString(guiContent.getDetails())) {
				JLabel lblThisAreTheDetails = new JLabel(guiContent.getDetails());
				lblThisAreTheDetails.setFont(new Font("Cantarell", Font.PLAIN, 10));
				lblThisAreTheDetails.setForeground(Color.WHITE);
				lblThisAreTheDetails.setBounds(12, 49, 175, 15);
				lblThisAreTheDetails.setToolTipText(guiContent.getDetails());
				presencePreviewPanel.add(lblThisAreTheDetails);
			}

			if (!isEmptyString(guiContent.getState())) {
				JLabel lblThisIsTheState = new JLabel(guiContent.getState());
				lblThisIsTheState.setFont(new Font("Cantarell", Font.PLAIN, 10));
				lblThisIsTheState.setForeground(Color.WHITE);
				lblThisIsTheState.setBounds(12, 67, 175, 15);
				lblThisIsTheState.setToolTipText(guiContent.getState());
				presencePreviewPanel.add(lblThisIsTheState);
			}

			if (guiContent.isShowStartTime()) {

				if (clockUpdateThread != null) {
					try {
						clockUpdateThread.interrupt();
					} catch (Exception e) {
					}
					clockUpdateThread = null;
				}

				clockLabel = new JLabel("00:00:00 elapsed");
				clockLabel.setFont(new Font("Cantarell", Font.PLAIN, 8));
				clockLabel.setForeground(Color.WHITE);
				clockLabel.setBounds(12, 85, 175, 15);
				presencePreviewPanel.add(clockLabel);
				Date start = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
				clockUpdateThread = new Thread(() -> {
					Timer timer = new Timer(1000, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							if (clockLabel != null) {
								Date now = new Date();
								String elapsedString = sdf.format(new Date(now.getTime() - start.getTime()))
										+ " elapsed";
								clockLabel.setText(elapsedString);
							}
						}
					});
					timer.start();
				});
				clockUpdateThread.start();
			}

		}

	}

	private static boolean isEmptyString(String string) {
		return string == null || string.isEmpty();
	}

	public RichPresenceManager getRpcManager() {
		return rpcManager;
	}

	public GuiContent getGuiContent() {
		return guiContent;
	}

	public JTextField getTextField() {
		return textField;
	}

	public JTextField getTextField_1() {
		return textField_1;
	}

	public JTextField getTextField_2() {
		return textField_2;
	}

	public JTextField getTextField_3() {
		return textField_3;
	}

	public JTextField getTextField_4() {
		return textField_4;
	}

	public JTextField getTextField_5() {
		return textField_5;
	}

	public JTextField getTextField_6() {
		return textField_6;
	}

	public Elements.JSwitchBox getSlider() {
		return slider;
	}

	public JPanel getPreviewPanel() {
		return previewPanel;
	}

	public JPanel getPresencePreviewPanel() {
		return presencePreviewPanel;
	}

	public JLabel getClockLabel() {
		return clockLabel;
	}

}
