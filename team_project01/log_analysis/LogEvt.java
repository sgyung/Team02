package log_analysis;


import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class LogEvt extends WindowAdapter implements ActionListener {

	private LogView logView;
	private Logs logs;
	
	
	public LogEvt(LogView logView) {
		this.logView = logView;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		
		//load버튼
		if(e.getSource() ==  logView.getJbtnLoad()) {
			loadFile();								
		}// end if
		
		//view버튼
		if(e.getSource() == logView.getJbtnView()) {
			if(logs != null) {
				String str = logs.logView();
				String messageTitle = "로그 분석";
				JOptionPane.showMessageDialog(logView, str, messageTitle, JOptionPane.INFORMATION_MESSAGE);				
			} else {
				JOptionPane.showMessageDialog(logView, "Log파일을 선택해주세요.");
			}
		}// end if
		
		//report버튼
		if(e.getSource() == logView.getJbtnReport()) {
			if(logs != null) {
				try {
					logs.saveReport();
					JOptionPane.showMessageDialog(logView, "Log파일이 저장되었습니다.");
				} catch (IOException e1) {
					e1.printStackTrace();
				}				
			} else {
				JOptionPane.showMessageDialog(logView, "Log파일을 선택해주세요.");
			}
		}
		
		//라인범위 설정
		if(e.getSource() == logView.getJtfStart()) {
			if(!logView.getJtfStart().getText().isEmpty()) {
				logView.getJtfEnd().requestFocus();				
			} else {
				JOptionPane.showMessageDialog(logView, "분석하실 Log의 시작 라인을 입력해주세요.");
			}
		}
		
		if(e.getSource() == logView.getJbtnSet()) {
			if(logs != null) {
				printRangeLine();				
			} else {
				JOptionPane.showMessageDialog(logView, "Log파일을 선택해주세요.");
			}
		}
		
		
		//reset버튼
		if(e.getSource() == logView.getJbtnReset()) {
			if(logs != null) {
				rangeReset();
				JOptionPane.showMessageDialog(logView, "Log파일이 초기화 되었습니다.");
			} else {
				JOptionPane.showMessageDialog(logView, "Log파일을 선택해주세요.");
			}
		}
	}//actionPerformed
	
	
	
	@Override
	public void windowClosing(WindowEvent e) {
		logView.dispose();
	}

	public void loadFile() {
		File file;
		String path;
		
		FileDialog fd = new FileDialog(logView, "파일 불러오기",FileDialog.LOAD);
		fd.setVisible(true);
			
		String directory = fd.getDirectory();
		String fileName = fd.getFile();
		
		if(directory != null && fileName != null) {
			path = directory + fileName;
			
			file= new File(directory + fileName);
			logs = new Logs(file);
			
			logView.getJlblPath().setText(fileName);
			
			
			if(!logView.getJta().getText().isEmpty()) {
				logView.getJta().setText("");
				
				for(int i = 0; i<logs.getLogList().size(); i++) {
					logView.getJta().append(logs.getLogList().get(i) + "\n");
				}
			}else {
				for(int i = 0; i<logs.getLogList().size(); i++) {
					logView.getJta().append(logs.getLogList().get(i) + "\n");
				}	
			}
		}
	}	
	public void printRangeLine() {
		
		String start = logView.getJtfStart().getText();
		String end = logView.getJtfEnd().getText();
		
		if(!start.isEmpty() && !end.isEmpty()) {
			
			int startLine;
			int endLine;
			
			try {			
				startLine = Integer.parseInt(logView.getJtfStart().getText());
				endLine = Integer.parseInt(logView.getJtfEnd().getText());
				
				if(startLine > endLine || startLine < 1 || endLine > logs.getLogArr().length) {
					JOptionPane.showMessageDialog(logView, "라인 범위를 확인해주세요.");
				} else {
					logs.splitLogs(startLine, endLine);
					JOptionPane.showMessageDialog(logView, startLine + " ~ " + endLine + "\n설정되었습니다");
				}
				
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(logView, "숫자를 입력해주세요.");
			}			
			
			
		}else {
			JOptionPane.showMessageDialog(logView, "원하는 라인값을 입력해주세요");
		}
	}
	
	public void rangeReset() {
		logs = null;
		logView.getJta().setText("");
		logView.getJtfStart().setText("");
		logView.getJtfEnd().setText("");
		logView.getJlblPath().setText("");
	}

}
