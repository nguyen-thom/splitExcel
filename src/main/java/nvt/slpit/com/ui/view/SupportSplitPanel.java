package nvt.slpit.com.ui.view;

import lombok.extern.slf4j.Slf4j;
import nvt.slpit.com.service.impl.SplitServiceImpl;
import nvt.slpit.com.ui.components.MessageBox;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CancellationException;

@Slf4j
public class SupportSplitPanel extends JPanel implements ActionListener {

    private SplitServiceImpl excelService;

    JLabel inputFileLabel = new JLabel("File excel xác minh:");
    JLabel outputFolderLabel = new JLabel("Thư mục chứa kết quả:");
    JLabel templateFileLabel = new JLabel("File Excel mẫu:");
    JLabel startRowLabel = new JLabel("Dòng bắt đầu đọc:");
    JLabel startIndex = new JLabel("Số index bắt đầu:");
    JLabel resultLabel = new JLabel("Kết quả: ");
    JLabel resultSummaryLabel = new JLabel("");

    JButton inputFileButton = new JButton("...");
    JButton outputFolderButton = new JButton("...");
    JButton templateFileButton = new JButton("...");
    JButton startButton = new JButton("Start Process");


    JTextField inputFileField = new JTextField("");
    JTextField outputFolderField = new JTextField("");
    JTextField templateFileField = new JTextField("");
    JTextField startRowField = new JTextField("13");
    JTextField startIndexField = new JTextField("0");
    JTextArea resultTextArea = new JTextArea("", 10, 20);
    JScrollPane jp = new JScrollPane(resultTextArea);

    JFileChooser fileChooser;

    private List<JComponent> componentListLine1 = Arrays.asList(inputFileLabel, inputFileField, inputFileButton);

    public SupportSplitPanel() {
        init();

    }


    public void init() {

        fileChooser = createJFileChooser();

        this.setLayout(new GridBagLayout());
        JPanel details = this;
        details.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHEAST;
        //this.setBackground(Color.DARK_GRAY);
        int i = 0;

        //Line 1
        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.fill = GridBagConstraints.NONE;
        details.add(inputFileLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = i;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        details.add(inputFileField, gbc);

        gbc.gridx = 2;
        gbc.gridy = i++;
        gbc.gridwidth = 1;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        inputFileButton.addActionListener(this);
        details.add(inputFileButton, gbc);

        //line 2 - output file
        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        details.add(outputFolderLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = i;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        details.add(outputFolderField, gbc);

        gbc.gridx = 2;
        gbc.gridy = i++;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        outputFolderButton.addActionListener(this);
        details.add(outputFolderButton, gbc);

        //line 3 - template

        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        details.add(templateFileLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = i;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        details.add(templateFileField, gbc);

        gbc.gridx = 2;
        gbc.gridy = i++;
        gbc.gridwidth = 1;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        templateFileButton.addActionListener(this);
        details.add(templateFileButton, gbc);

        //line 4 - start row
        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        details.add(startRowLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = i++;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        details.add(startRowField, gbc);

        //line 5 - start index
        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        details.add(startIndex, gbc);

        gbc.gridx = 1;
        gbc.gridy = i++;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        details.add(startIndexField, gbc);

        //line 6  - button start processing

        gbc.gridx = 1;
        gbc.gridy = i++;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        startButton.addActionListener(this);
        details.add(startButton, gbc);

        //line 7 - result
        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        details.add(resultLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = i++;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        details.add(resultSummaryLabel, gbc);

        //line 8 - detail result
        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        details.add(jp, gbc);

        resultTextArea.setEditable(false);


    }

    private JFileChooser createJFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileFilter() {

            public String getDescription() {
                return "Excel File (*.xls)";
            }

            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    String filename = f.getName().toLowerCase();
                    return filename.endsWith(".xlsx") || filename.endsWith(".xls");
                }
            }
        });

        return fileChooser;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        resultSummaryLabel.setText("");
        if (e.getSource() == inputFileButton) {
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int i = fileChooser.showOpenDialog(this);
            if (i == JFileChooser.APPROVE_OPTION) {
                File f = fileChooser.getSelectedFile();
                this.inputFileField.setText(f.getPath());
            }
        }
        if (e.getSource() == outputFolderButton) {
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int i = fileChooser.showOpenDialog(this);
            if (i == JFileChooser.APPROVE_OPTION) {
                File f = fileChooser.getSelectedFile();
                this.outputFolderField.setText(f.getPath());
            }
        }
        if (e.getSource() == templateFileButton) {
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int i = fileChooser.showOpenDialog(this);
            if (i == JFileChooser.APPROVE_OPTION) {
                File f = fileChooser.getSelectedFile();
                this.templateFileField.setText(f.getPath());
            }
        }
        if (e.getSource() == startButton) {
            String inputFilePath = inputFileField.getText();
            String outputFolder = outputFolderField.getText();
            String templateFile = templateFileField.getText();

            int startRow = Integer.parseInt(startRowField.getText());
            int startIndex = Integer.parseInt(startIndexField.getText());
            resultSummaryLabel.setText("Đang xử lý ....");
            excelService = new SplitServiceImpl(inputFilePath, outputFolder, templateFile, startRow, startIndex, resultTextArea);
            this.listerFromSplitService(excelService);
            excelService.execute();
        }
    }

    private void listerFromSplitService(SplitServiceImpl sliptService) {
        sliptService.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent event) {
                switch (event.getPropertyName()) {
                    case "progress":
                        resultSummaryLabel.setText(event.getNewValue() + "");
                        log.info(event.getNewValue().toString());
                        //searchProgressBar.setIndeterminate(false);
                        //searchProgressBar.setValue((Integer) event.getNewValue());
                        break;
                    case "state":
                        switch ((SwingWorker.StateValue) event.getNewValue()) {
                            case DONE:
                                //searchProgressBar.setVisible(false);
                                //searchCancelAction.putValue(Action.NAME, "Search");
                                try {
                                    final int count = sliptService.get();
                                    log.info("Total:" + count);
                                    resultSummaryLabel.setText(count + "");
                                } catch (final CancellationException e) {
                                    MessageBox.showError("Canceled Process", e);
                                } catch (final Exception e) {
                                    MessageBox.showError("Happen error when split excel", e);
                                }
                                //sliptService = null;
                                break;
                            case STARTED:
                            case PENDING:

                                //searchCancelAction.putValue(Action.NAME, "Cancel");
                                //searchProgressBar.setVisible(true);
                                //searchProgressBar.setIndeterminate(true);
                                break;
                        }
                        break;
                }
            }
        });
    }
}

