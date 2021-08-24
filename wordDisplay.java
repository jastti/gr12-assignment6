package WordFrequency;

import java.awt.ComponentOrientation;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class wordDisplay extends JFrame implements ActionListener, ItemListener {

	private JPanel contentPane;
	Vector<String> myList = new Vector<>(10);
	static String selectedString = null;
	frequency fileCalculate;
	JTextArea ShowBook;
	JTextArea WordsCal;
	static long start = 0;
	static long end = 0;
	static wordDisplay frame;
	// create and initialize

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new wordDisplay();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					// set frame as visible and work
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// create the frame
	public wordDisplay() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 930, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		// set the panel

		JButton AddFile = new JButton("Add File");
		AddFile.setFont(new Font("SimSun", Font.PLAIN, 14));
		AddFile.setBounds(67, 28, 165, 23);
		contentPane.add(AddFile);
		AddFile.addActionListener(this);
		// set the "add file" button

		JComboBox<String> BookSelect = new JComboBox<>(myList);
		BookSelect.setFont(new Font("SimSun", Font.PLAIN, 14));
		BookSelect.setBounds(28, 61, 260, 25);
		contentPane.add(BookSelect);
		BookSelect.addItemListener(this);
		// set the select comboBox

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 95, 440, 350);
		contentPane.add(scrollPane);
		// set the scroll panel

		ShowBook = new JTextArea();
		ShowBook.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		ShowBook.setRows(10000);
		ShowBook.setBounds(20, 95, 440, 350);
		// set the show book content text area that have the same size and location as
		// scroll panel
		scrollPane.setViewportView(ShowBook);
		// add and cover the scrollPane to the text area

		WordsCal = new JTextArea();
		WordsCal.setBounds(520, 30, 375, 400);
		contentPane.add(WordsCal);
		// set the text area that display the top 20 most frequent words

		JButton start = new JButton("Start Calculate");
		start.setFont(new Font("SimSun", Font.PLAIN, 14));
		start.setBounds(301, 28, 150, 23);
		start.addActionListener(this);
		contentPane.add(start);
		// set the "start" button

		JButton exit = new JButton("Exit");
		exit.setFont(new Font("SimSun", Font.PLAIN, 14));
		exit.setBounds(301, 61, 150, 23);
		exit.addActionListener(this);
		contentPane.add(exit);
		// set the "exit" button

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		/*
		 * add file --> start --> exit
		 */
		String eventName = e.getActionCommand();
		Frame frame = null;
		AbstractButton message;
		// initialize

		if (eventName.equals("Add File")) {
			addFile();
			// once click "Add File" button, start adding files
		} else if (eventName.equals("Start Calculate")) {
			// once click "Start Calculate" button
			if (myList.isEmpty()) {
				JOptionPane.showMessageDialog(frame, (Object) "\nPlease add a file first!", null,
						JOptionPane.INFORMATION_MESSAGE);
				// if there was no files added before, display the notice(but not going to exit
				// the program)
				return;
			}
			calculate();
			// start calculating

		} else if (eventName.equals("Exit")) {
			System.exit(0);
			// once click "Exit" button, exit the program
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		JComboBox comboBox = (JComboBox) e.getSource();
		int k = comboBox.getSelectedIndex() + 1;
		selectedString = (String) comboBox.getSelectedItem();
		// get the added file(s), and selected the file to be calculated
	}

	private void calculate() {
		// TODO Auto-generated method stub
		/*
		 * try catch 
		 * 1. start time, initialize 
		 * 2. use FileReader and BufferedReader to read the text 
		 * 3. while reading, display the text content 
		 * 4. finish reading, sort the words, end time 
		 * 5. display by form, use iterator to get the key & value from the map and display 
		 * 6. ShowBook.setSelectionStart(1);
		 */
		start = System.currentTimeMillis();
		WordsCal.setText("");
		// to avoid book content crash, set text as empty first
		fileCalculate = new frequency();
		// use class frequency to do the calculation
		StringBuilder sb = new StringBuilder();
		// create a string builder to save data that has read

		try {
			ShowBook.setText(null);
			File file = new File(selectedString);
			InputStreamReader br = new InputStreamReader(new FileInputStream(file));
			BufferedReader bufferedReader = new BufferedReader(br);
			String line;
			// start reading file that has been selected to calculate

			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line + "\n");
				// save the data from each line that has been read into the String Builder
				fileCalculate.getWordCount(line.toLowerCase());
				// convert the string to lower case first, and start counting
			}

			fileCalculate.sortedwords = fileCalculate.sortWordCount(fileCalculate.allWords);
			// calculate the sortedwords
			showResult();
			// display the results
			bufferedReader.close();
			br.close();
			// stop reading the file

			ShowBook.setText(sb.toString());
			ShowBook.setSelectionStart(1);
			ShowBook.setSelectionEnd(100);
			// set the selection part
			// **search for a end position depends on the length of text

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// catch all the exceptions
	}

	private void showResult() {
		// show the result of calculation
		int i = 1;
		end = System.currentTimeMillis();
		long timeUsed = end - start;
		// the total time used for calculation is end time minus start time
		WordsCal.append("\nThe total time is: " + timeUsed + " milliseconds\n");
		WordsCal.append("\t 20 Most Frequent Words" + "\n");
		WordsCal.append("\t Words \t\t Frequency\n");
		// display
		for (Iterator<Entry<String, Integer>> iterator = fileCalculate.sortedwords.iterator(); i < 21; i++) {
			Entry<String, Integer> entry = iterator.next();
			WordsCal.append(" " + i + ")\t " + entry.getKey() + "\t\t" + entry.getValue() + "\n");
			// display 20 most frequent words and how many time they appear
		}
	}

	private void addFile() {
		// TODO Auto-generated method stub
		/*
		 * 1. use fileDialog to open a file 
		 * 2. determine if there are duplicate files added; if yes, display the notice 
		 * 3. add file
		 */
		Frame frame = null;
		FileDialog open = new FileDialog(frame, "Open a new file", FileDialog.LOAD);
		open.setVisible(true);
		String fileName = open.getFile();
		String dirct = open.getDirectory();
		// use fileDialog to open a file 
		for (int j = 0; j < myList.size(); j++) {
			if (myList.get(j).equals(fileName)) {
				// if there is a file that to be added has the same name as the file that added before
				JOptionPane.showMessageDialog(frame, (Object) "\nYou've alreay added: " + myList.get(j), null,
						JOptionPane.INFORMATION_MESSAGE);
				return;
				// display the notice and return 
			}
		}
		myList.add(fileName);
		// add the file 
		return;
	}

}
