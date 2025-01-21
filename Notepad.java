package Notepad_clone;

	import java.awt.FileDialog;
	import java.awt.Font;
	import java.awt.Image;
	import java.awt.Toolkit;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.io.BufferedReader;
	import java.io.BufferedWriter;
	import java.io.File;
	import java.io.FileNotFoundException;
	import java.io.FileReader;
	import java.io.FileWriter;
	import java.io.IOException;
	import javax.swing.JFrame;
	import javax.swing.JMenu;
	import javax.swing.JMenuBar;
	import javax.swing.JMenuItem;
    import javax.swing.JScrollPane;
	import javax.swing.JTextArea;

	public class Notepad {

		JFrame frame;
		JTextArea textarea;
		JMenuBar menuBar;
		JMenu fileMenu, languageMenu, formatMenu, commandPrompt;

		// File Menu Items
		JMenuItem itemNew, itemNewWindow, itemOpen, itemSaveAs, itemSave, itemExit;

		// Format Menu Item
		JMenuItem itemWordWrap, itemFont, itemFontSize;

		// Command Prompt Menu Item
		JMenuItem itemcmd;

		String openPath = null;

		String openFileName = null;

		boolean wrap = false;

		Font f1, f2, f3;

		String fontStyle = "Arial";

		public Notepad() {

			createFrame();
			createTextArea();
			createScrollBars();
			createMenuBar();
			createFileMenuItems();
			createFormatItem();
			createCommandPrompt();
			createLanguage();
		}

		public void createFrame() {

			frame = new JFrame("NotePad1");

			frame.setSize(700, 450);

			Image logo = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Vaishnavi\\Desktop\\qicon.jpg");

			frame.setIconImage(logo);

			frame.setVisible(true);

			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		}

		public void createTextArea() {
			textarea = new JTextArea();

			frame.add(textarea);

			textarea.setFont(new Font("Consolas", Font.PLAIN, 24));
		}
		

		public void createScrollBars() {
			JScrollPane scroll = new JScrollPane(textarea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

			frame.add(scroll);

		}

		public void createMenuBar() {

			menuBar = new JMenuBar();
            frame.setJMenuBar(menuBar);

			fileMenu = new JMenu("File");
            menuBar.add(fileMenu);

			languageMenu = new JMenu("Language");
            menuBar.add(languageMenu);

			formatMenu = new JMenu("Format");
            menuBar.add(formatMenu);

			commandPrompt = new JMenu("Command Prompt ");
            menuBar.add(commandPrompt);
		}

		public void createFileMenuItems() {

			itemNew = new JMenuItem("New");

			// Action Listener For New Option Which will Create A New File For Writting
			itemNew.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
//clears the textarea
					textarea.setText("");
                    frame.setTitle("Untitled NotePad");

                    //indicating no file is currenty open
					openPath = null;
					openFileName = null;

					FileDialog fd = new FileDialog(frame, "Save As", FileDialog.SAVE);
                    fd.setVisible(true);

					String path = fd.getDirectory();
                    String filename = fd.getFile();

					if (filename != null && path != null) {

						writeDataToFile(path, filename);

					}}});
            fileMenu.add(itemNew);
			
			//it will open the new window of note pad
			itemNewWindow = new JMenuItem("New Window");

			// add ActionListener
			itemNewWindow.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Notepad n1 = new Notepad();

					n1.frame.setTitle("Untitled");

				}
			});
            fileMenu.add(itemNewWindow);

			itemOpen = new JMenuItem("Open");

			itemOpen.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					FileDialog fd = new FileDialog(frame, "Open", FileDialog.LOAD);

					fd.setVisible(true);

					String file = fd.getFile();

					String path = fd.getDirectory();

					System.out.println(file);

					System.out.println(path);

					if (file != null) {
						frame.setTitle(file);

						openFileName = file;
						openPath = path;
						readFile(path + file);

					} else {
						frame.setTitle("NotePad");
					}

				}});
            fileMenu.add(itemOpen);

			itemSaveAs = new JMenuItem("Save As");

			itemSaveAs.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					FileDialog fd = new FileDialog(frame, "Save As", FileDialog.SAVE);

					fd.setVisible(true);

					String path = fd.getDirectory();

					String filename = fd.getFile();

					if (filename != null && path != null) {

						writeDataToFile(path, filename);

					}}});

			fileMenu.add(itemSaveAs);

			itemSave = new JMenuItem("Save");

			itemSave.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if (openFileName != null && openPath != null) {
						writeDataToFile(openPath, openFileName);
					} else {
						FileDialog fd = new FileDialog(frame, "Save As", FileDialog.SAVE);

						fd.setVisible(true);

						String path = fd.getDirectory();

						String filename = fd.getFile();

						if (filename != null && path != null) {

							writeDataToFile(path, filename);

						}
					}
				}
			});

			fileMenu.add(itemSave);

			itemExit = new JMenuItem("Exit");

			itemExit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					frame.dispose();

				}
			});

			fileMenu.add(itemExit);

		}

		public void createLanguage() {

			JMenuItem itemJava = new JMenuItem("Java");
			languageMenu.add(itemJava);
			
			itemJava.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	               
	            	setLanguage("Java");
	            }
	        });

			JMenuItem itemCpuls = new JMenuItem("C++");
            languageMenu.add(itemCpuls);
			
			itemCpuls.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setLanguage("Cpp");
					
				}
			});
			
			

			JMenuItem itemC = new JMenuItem("C");
			languageMenu.add(itemC);
			
			itemC.addActionListener( new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					setLanguage("C");
					
				}
			});

			JMenuItem itemHtml = new JMenuItem("HTML");
			languageMenu.add(itemHtml);
			
			itemHtml.addActionListener( new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setLanguage("Html");
					
				}
			});
			}

		public void createFormatItem() {

			itemWordWrap = new JMenuItem("Word Wrap off");
            formatMenu.add(itemWordWrap);

			itemFont = new JMenu("Font");
            formatMenu.add(itemFont);

			JMenuItem itemArial = new JMenuItem("Arial");
			itemFont.add(itemArial);

			JMenuItem itemTimesNewRoman = new JMenuItem("Times New Roman");
            itemFont.add(itemTimesNewRoman);

			JMenuItem itemConsolas = new JMenuItem("Consolas");
			itemFont.add(itemConsolas);

			itemFontSize = new JMenu("Font Size");

			JMenuItem size10 = new JMenuItem("10");
            itemFontSize.add(size10);

			JMenuItem size14 = new JMenuItem("14");
            itemFontSize.add(size14);

			JMenuItem size18 = new JMenuItem("18");
            itemFontSize.add(size18);

			JMenuItem size22 = new JMenuItem("22");
			itemFontSize.add(size22);

			JMenuItem size26 = new JMenuItem("26");
			itemFontSize.add(size26);

			JMenuItem size30 = new JMenuItem("30");
			itemFontSize.add(size30);

			JMenuItem size34 = new JMenuItem("34");
			itemFontSize.add(size34);

			formatMenu.add(itemFontSize);

			itemWordWrap.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if (wrap == false) {
						textarea.setLineWrap(true);

						textarea.setWrapStyleWord(true);

						wrap = true;

						itemWordWrap.setText("Word Wrap On");

					} else {
						textarea.setLineWrap(false);

						textarea.setWrapStyleWord(false);

						wrap = false;

						itemWordWrap.setText("Word Wrap Off");

					}

				}
			});

			itemArial.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					setFontType("Arial");

				}});

			itemTimesNewRoman.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					setFontType("Times New Roman");

				}
			});

			size10.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setFontSize(10);

				}});

			size14.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setFontSize(14);

				}});

			size18.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setFontSize(18);

				}});

			size22.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setFontSize(22);

				}});

			size26.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setFontSize(26);

				}});

			size30.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setFontSize(30);

				}});

			size34.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setFontSize(34);

				}});
		}

		public void createCommandPrompt() {
			itemcmd = new JMenuItem("Open cmd ");

			itemcmd.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						//Uses Runtime.getRuntime().exec() to launch the command prompt in the current directory.
						if(openPath!=null) {
                       Runtime.getRuntime().exec(new String[] { "cmd", "/c", "start" }, null, new File(openPath));
						}
						else {
							Runtime.getRuntime().exec(new String[] {"cmd","/c","start"},null,null);
						}
					} catch (IOException e1) {
						System.out.println("Could not lauch cmd ");
						e1.printStackTrace();
					}}});
			commandPrompt.add(itemcmd);
}
		
//Writes the current text area content to a file using BufferedWriter.
		public void writeDataToFile(String path, String filename) {
			BufferedWriter bw = null;

			try {

				bw = new BufferedWriter(new FileWriter(path + filename));

				String text = textarea.getText();

				bw.write(text);

			} catch (IOException e1) {

				System.out.println("Data can not be Written !");
			} finally {
				try {
					bw.close();
				} catch (IOException e1) {

					System.out.println("File Can not be closed");
				} catch (NullPointerException e2) {
					System.out.println("File not found ");
				}
			}
		}

		//Reads the content of a file line by line using BufferedReader and displays it in the text area.
          public void readFile(String filePath) {

			try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

				String sentence;

				textarea.setText(""); // Clear existing content

				while ((sentence = br.readLine()) != null) {

					textarea.append(sentence + "\n");
				}

			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		public void setFontSize(int size) {

			f1 = new Font("Arial", Font.PLAIN, size);

			f2 = new Font("Times New Roman", Font.PLAIN, size);

			f3 = new Font("Consolas", Font.PLAIN, size);

			setFontType(fontStyle);

		}

		public void setFontType(String fontName) {

			fontStyle = fontName;
			switch (fontName) {

			case "Arial":

				textarea.setFont(f1);
				break;

			case "Times New Roman":

				textarea.setFont(f2);
				break;

			case "Consolas":
				textarea.setFont(f3);

			}

		}


		public void setLanguage(String lang)
		{
			String path="D:\\filehandle\\";
			System.out.println(lang);
			BufferedReader reader=null;
			try {
				reader= new BufferedReader(new FileReader(path+lang+".txt"));
				String sentence=reader.readLine();
				textarea.setText("");
				while(sentence!=null)
				{
					textarea.append(sentence +"\n");
					sentence=reader.readLine();
				}
				
				
			} catch (FileNotFoundException e1) {
				
				System.out.println("File Not Found");
			} catch (IOException e1) {
				System.out.println("Data Could Not be Read");
				
			} catch (NullPointerException e1) {
				System.out.println("Not be Read");
				
			}
			finally {
				try {
					reader.close();
					System.out.println("File Closed ");
				} catch (IOException e1) {
					System.out.println("File Could Not Close ");
				}
				catch (NullPointerException e1) {
					System.out.println("File is null");
				}
			}
		}

	}
